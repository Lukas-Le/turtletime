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

import java.util.logging.Logger;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoFactory;
import org.wahlzeit.model.PhotoFilter;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.model.PhotoTagCollector;
import org.wahlzeit.services.LogBuilder;

public class TurtlePhotoFactory extends PhotoFactory{

	
	private static final Logger log = Logger.getLogger(TurtlePhotoFactory.class.getName());
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	private static TurtlePhotoFactory instance = null;

	/**
	 *
	 */
	protected TurtlePhotoFactory() {
		// do nothing
	}

	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		getInstance(); // drops result due to getInstance() side-effects
	}

	/**
	 * Public singleton access method.
	 */
	public static synchronized TurtlePhotoFactory getInstance() {
		if (instance == null) {
			log.config(LogBuilder.createSystemMessage().addAction("setting generic TurtlePhotoFactory").toString());
			setInstance(new TurtlePhotoFactory());
		}

		return instance;
	}

	/**
	 * Method to set the singleton instance of PhotoFactory.
	 */
	protected static synchronized void setInstance(TurtlePhotoFactory turtlePhotoFactory) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initalize TurtlePhotoFactory twice");
		}

		instance = turtlePhotoFactory;
	}

	/**
	 * @methodtype factory
	 */
	public Photo createPhoto() {
		return new TurtlePhoto();
	}

	/**
	 * Creates a new photo with the specified id
	 */
	public Photo createPhoto(PhotoId id) {
		return new TurtlePhoto(id);
	}
	
	
	/**
	 * @methodtype factory
	 */
	public TurtlePhoto createTurtlePhoto() {
		return new TurtlePhoto();
	}

	/**
	 * Creates a new photo with the specified id
	 */
	public TurtlePhoto createTurtlePhoto(PhotoId id) {
		return new TurtlePhoto(id);
	}
	
	public TurtlePhoto loadPhoto(PhotoId id) {
			return null;
		}



	/**
	 *
	 */
	public PhotoFilter createPhotoFilter() {
		return new PhotoFilter();
	}

	/**
	 *
	 */
	public PhotoTagCollector createPhotoTagCollector() {
		return new PhotoTagCollector();
	}
	
	
}
