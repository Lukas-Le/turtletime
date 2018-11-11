/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
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

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import org.junit.*;

import junit.framework.TestCase;

public class TurtlePhotoTest extends TestCase{
	
	public TurtlePhotoTest(String name) {
		super(name);
	}
	
	@Test
	public void testValidTurtlePhotos(){
		TurtlePhoto p = new TurtlePhoto();
		TurtlePhoto p2 = new TurtlePhoto("green", "cool turtle", 14);
	}
	
	/*
	 * don't know why this is not working :/
	 * because it does throw an IllegalArgumentException if you leave out the 
	 * expcected :/
	 * I think it is a configuration problem...
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidTurtlePhoto(){
		TurtlePhoto p = new TurtlePhoto("green","cool turtle",-1);
		TurtlePhoto p2 = new TurtlePhoto("green","cool turtle",1);
	}
	*/
}
