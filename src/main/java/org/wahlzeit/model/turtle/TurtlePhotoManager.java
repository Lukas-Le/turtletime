/*
 * Copyright (c) 2006-2009 by Lukas Lehnert
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */


package org.wahlzeit.model.turtle;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.wahlzeit.model.Client;
import org.wahlzeit.model.GlobalsManager;
import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoFactory;
import org.wahlzeit.model.PhotoFilter;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.model.PhotoManager;
import org.wahlzeit.model.PhotoSize;
import org.wahlzeit.model.PhotoTagCollector;
import org.wahlzeit.model.PhotoUtil;
import org.wahlzeit.model.Tag;
import org.wahlzeit.model.UserManager;
import org.wahlzeit.model.persistence.ImageStorage;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.services.Persistent;
import org.wahlzeit.utils.PatternInstance;

import com.google.appengine.api.images.Image;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;


@PatternInstance(
		patternName = "Singelton",
		participants = {
			"Singelton"
		}
	)
public class TurtlePhotoManager extends PhotoManager{
	
	
	
	

	/**
	 *
	 */
	protected static final TurtlePhotoManager instance = new TurtlePhotoManager();

	private static final Logger log = Logger.getLogger(TurtlePhotoManager.class.getName());

	/**
	 * In-memory cache for photos
	 */
	protected Map<PhotoId, TurtlePhoto> photoCache = new HashMap<PhotoId, TurtlePhoto>();

	/**
	 *
	 */
	protected PhotoTagCollector photoTagCollector = null;

	/**
	 *
	 */
	public TurtlePhotoManager() {
		photoTagCollector = PhotoFactory.getInstance().createPhotoTagCollector();
	}

	/**
	 *
	 */
	public static final TurtlePhotoManager getTurtleInstance() {
		return instance;
	}



	
	/**
	 *
	 */
	public final TurtlePhoto getTurtlePhoto(PhotoId id) {
		return instance.getPhotoFromId(id);
	}

	/**
	 *
	 */
	public TurtlePhoto getPhotoFromId(PhotoId id) {
		if (id == null) {
			return null;
		}

		TurtlePhoto result = doGetTurtlePhotoFromId(id);

		if (result == null) {
			result = TurtlePhotoFactory.getInstance().loadPhoto(id);
			if (result != null) {
				doAddPhoto(result);
			}
		}

		return result;
	}

	/**
	 * @methodtype get
	 * @methodproperties primitive
	 */
	protected TurtlePhoto doGetTurtlePhotoFromId(PhotoId id) {
		return photoCache.get(id);
	}

	/**
	 * @methodtype command
	 * @methodproperties primitive
	 */
	protected void doAddPhoto(TurtlePhoto myPhoto) {
		photoCache.put(myPhoto.getId(), myPhoto);
	}

	/**
	 * @methodtype get
	 */
	public final TurtlePhoto getTurtlePhoto(String id) {
		return getTurtlePhoto(PhotoId.getIdFromString(id));
	}

	/**
	 * @methodtype init Loads all Photos from the Datastore and holds them in the cache
	 */
	public void init() {
		loadPhotos();
	}

	/**
	 * @methodtype command
	 *
	 * Load all persisted photos. Executed when Wahlzeit is restarted.
	 */
	public void loadPhotos() {
		Collection<TurtlePhoto> existingPhotos = ObjectifyService.run(new Work<Collection<TurtlePhoto>>() {
			@Override
			public Collection<TurtlePhoto> run() {
				Collection<TurtlePhoto> existingPhotos = new ArrayList<TurtlePhoto>();
				readObjects(existingPhotos, TurtlePhoto.class);
				return existingPhotos;
			}
		});

		for (TurtlePhoto photo : existingPhotos) {
			if (!doHasPhoto(photo.getId())) {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Load TurtlePhoto with ID", photo.getIdAsString()).toString());
				loadScaledImages(photo);
				doAddPhoto(photo);
			} else {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Already loaded TurtlePhoto", photo.getIdAsString()).toString());
			}
		}

		log.info(LogBuilder.createSystemMessage().addMessage("All turtle-photos loaded.").toString());
	}

	/**
	 * @methodtype boolean-query
	 * @methodproperty primitive
	 */
	protected boolean doHasPhoto(PhotoId id) {
		return photoCache.containsKey(id);
	}

	/**
	 * @methodtype command
	 *
	 * Loads all scaled Images of this Photo from Google Cloud Storage
	 */
	protected void loadScaledImages(Photo photo) {
		String photoIdAsString = photo.getId().asString();
		ImageStorage imageStorage = ImageStorage.getInstance();

		for (PhotoSize photoSize : PhotoSize.values()) {
			log.config(LogBuilder.createSystemMessage().
					addAction("loading image").
					addParameter("image size", photoSize.asString()).
					addParameter("photo ID", photoIdAsString).toString());
			if (imageStorage.doesImageExist(photoIdAsString, photoSize.asInt())) {
				try {
					Serializable rawImage = imageStorage.readImage(photoIdAsString, photoSize.asInt());
					if (rawImage != null && rawImage instanceof Image) {
						photo.setImage(photoSize, (Image) rawImage);
					}
				} catch (IOException e) {
					log.warning(LogBuilder.createSystemMessage().
							addParameter("size", photoSize.asString()).
							addParameter("photo ID", photoIdAsString).
							addException("Could not load image although it exists", e).toString());
				}
			} else {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Size does not exist", photoSize.asString()).toString());
			}
		}
	}

	/**
	 *
	 */
	public void savePhoto(Photo photo) {
		updateObject(photo);
	}

	@Override
	protected void updateDependents(Persistent obj) {
		if (obj instanceof Photo) {
			Photo photo = (Photo) obj;
			saveScaledImages(photo);
			updateTags(photo);
			UserManager userManager = UserManager.getInstance();
			Client owner = userManager.getClientById(photo.getOwnerId());
			userManager.saveClient(owner);
		}
	}

	/**
	 * @methodtype helper
	 */
	public List<Tag> addTagsThatMatchCondition(List<Tag> tags, String condition) {
		readObjects(tags, Tag.class, Tag.TEXT, condition);
		return tags;
	}

	/**
	 * @methodtype command
	 *
	 * Persists all available sizes of the Photo. If one size exceeds the limit of the persistence layer, e.g. > 1MB for
	 * the Datastore, it is simply not persisted.
	 */
	protected void saveScaledImages(Photo photo) {
		String photoIdAsString = photo.getId().asString();
		ImageStorage imageStorage = ImageStorage.getInstance();
		PhotoSize photoSize;
		int it = 0;
		boolean moreSizesExist = true;
		do{
			photoSize = PhotoSize.values()[it];
			it++;
			Image image = photo.getImage(photoSize);
			if (image != null) {
				try {
					if (!imageStorage.doesImageExist(photoIdAsString, photoSize.asInt())) {
						imageStorage.writeImage(image, photoIdAsString, photoSize.asInt());
					}
				} catch (Exception e) {
					log.warning(LogBuilder.createSystemMessage().
							addException("Problem when storing image", e).toString());
					moreSizesExist = false;
				}
			} else {
				log.config(LogBuilder.createSystemMessage().
						addParameter("No image for size", photoSize.asString()).toString());
				moreSizesExist = false;
			}
		} while (it < PhotoSize.values().length && moreSizesExist);
	}

	/**
	 * Removes all tags of the Photo (obj) in the datastore that have been removed by the user and adds all new tags of
	 * the photo to the datastore.
	 */
	protected void updateTags(Photo photo) {
		// delete all existing tags, for the case that some have been removed
		deleteObjects(Tag.class, Tag.PHOTO_ID, photo.getId().asString());

		// add all current tags to the datastore
		Set<String> tags = new HashSet<String>();
		photoTagCollector.collect(tags, photo);
		for (Iterator<String> i = tags.iterator(); i.hasNext(); ) {
			Tag tag = new Tag(i.next(), photo.getId().asString());
			log.config(LogBuilder.createSystemMessage().addParameter("Writing Tag", tag.asString()).toString());
			writeObject(tag);
		}
	}

	/**
	 *
	 */
	public void savePhotos() throws IOException{
		updateObjects(photoCache.values());
	}

	/**
	 * @methodtype get
	 */
	public Map<PhotoId, TurtlePhoto> getTurtlePhotoCache() {
		return photoCache;
	}

	/**
	 *
	 */
	public Set<Photo> findPhotosByOwner(String ownerName) {
		Set<Photo> result = new HashSet<Photo>();
		readObjects(result, Photo.class, Photo.OWNER_ID, ownerName);

		for (Iterator<Photo> i = result.iterator(); i.hasNext(); ) {
			doAddPhoto(i.next());
		}

		return result;
	}

	/**
	 *
	 */
	public Photo getVisiblePhoto(PhotoFilter filter) {
		filter.generateDisplayablePhotoIds();
		return getPhotoFromId(filter.getRandomDisplayablePhotoId());
	}

	/**
	 *
	 */
	public Photo createPhoto(String filename, Image uploadedImage) throws Exception {
		PhotoId id = PhotoId.getNextId();
		Photo result = PhotoUtil.createPhoto(filename, id, uploadedImage);
		addPhoto(result);
		return result;
	}

	/**
	 * @methodtype command
	 */
	public void addPhoto(Photo photo) throws IOException {
		PhotoId id = photo.getId();
		assertIsNewPhoto(id);
		doAddPhoto(photo);

		GlobalsManager.getInstance().saveGlobals();
	}

	/**
	 * @methodtype assertion
	 */
	protected void assertIsNewPhoto(PhotoId id) {
		if (hasPhoto(id)) {
			throw new IllegalStateException("Photo already exists!");
		}
	}
	
	
	
	
	
	
	
}
