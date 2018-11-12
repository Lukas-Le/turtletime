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

import org.wahlzeit.model.Location;
import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;

import com.googlecode.objectify.annotation.Subclass;


@Subclass
public class TurtlePhoto extends Photo{

	
	
	
	private String color;
	private String breed;
	private int age;
	
	
	
	
	/**
	 * @methodtype constructor
	 */
	public TurtlePhoto(){
	
	}
	
	public TurtlePhoto(PhotoId myId) {
		id = myId;

		incWriteCount();
	}
	
	/**
	 * @methodtype constructor
	 */
	public TurtlePhoto(String color, String breed, int age){
		setColor(color);
		setBreed(breed);
		setAge(age);
	}
	
	
	/**
	 * @methodtype set
	 */
	public void setColor(String color){
		this.color = color;
	}
	
	/**
	 * @methodtype set
	 */
	public void setBreed(String breed){
		this.breed = breed;
	}
	
	/**
	 * @methodtype set
	 */
	public void setAge(int age){
		if(age < 0)
			throw new IllegalArgumentException("age must be >= 0");
		this.age = age;
	}
	
	/**
	 * @methodtype get
	 */
	public String getColor(){
		return color;
	}
	
	/**
	 * @methodtype get
	 */
	public String getBreed(){
		return breed;
	}
	
	/**
	 * @methodtype get
	 */
	public int getAge(){
		return age;
	}
}
