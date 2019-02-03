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
import org.wahlzeit.utils.PatternInstance;

import com.googlecode.objectify.annotation.Serialize;
import com.googlecode.objectify.annotation.Subclass;


@PatternInstance(
		patternName = "Abstract Factory",
		participants = {
			"AbstractProduct","ConcreteProduct"
		}
	)
@Subclass

/************ Instantiation of my photo class *********
*
* -> new TurtlePhotoFactory()
* -> initialize()
* -> getInstance()
* -> createPhoto()/createPhoto(id)/createTurtlePhoto()/createTurtlePhoto(id)
* -> new TurtlePhoto()/TurtlePhoto(id)
* 
* 
* object creation table(six tuple)
* 1. Delegation: Separate-object
* 2. Selection: By-subclassing
* 3. Configuration: In-code
* 4. Instantiation: In-code
* 5. Initialization: Default
* 6. Building: Default
* 
*/

/************** Collaborations: ***********
 * 
 * see presentation C11, slide 20 and 21 using keywords collaboration, role,
 * 
 * Collaboration: TurtlePhoto / Turtle
 * 		role: TurtlePhoto(Client), Turtle(Service)
 */


public class TurtlePhoto extends Photo{

	@Serialize
	private Turtle turtle;
	
	
	/**
	 * @methodtype constructor
	 */
	public TurtlePhoto(){
		//turtle = new Turtle();
	}
	
	public TurtlePhoto(PhotoId myId) {
		super(myId);
		TurtleAssertions.assertNotNull(myId);
		id = myId;
		
		
		incWriteCount();
		//turtle = new Turtle();
	}
	
	/**
	 * @methodtype constructor
	 */
	public TurtlePhoto(String color, String breed, int age){
		TurtleAssertions.assertNotNull(color);
		TurtleAssertions.assertNotNull(breed);
		if(age < 0)throw new IllegalArgumentException("age must be >0");
		
		turtle = TurtleManager.getInstance().createTurtle("PhotoTurtle");
		setColor(color);
		setBreed(breed);
		setAge(age);
	}
	
	
	/**
	 * @methodtype set
	 */
	public void setColor(String color){
		TurtleAssertions.assertNotNull(color);
		turtle.setColor(color);
	}
	
	/**
	 * @methodtype set
	 */
	public void setBreed(String breed){
		TurtleAssertions.assertNotNull(breed);
		turtle.setBreed(breed);
	}
	
	/**
	 * @methodtype set
	 */
	public void setAge(int age){
		if(age < 0)
			throw new IllegalArgumentException("age must be >= 0");
		turtle.setAge(age);
	}
	
	/**
	 * @methodtype get
	 */
	public String getColor(){
		return turtle.getColor();
	}
	
	/**
	 * @methodtype get
	 */
	public String getBreed(){
		return turtle.getBreed();
	}
	
	/**
	 * @methodtype get
	 */
	public int getAge(){
		return turtle.getAge();
	}
}
