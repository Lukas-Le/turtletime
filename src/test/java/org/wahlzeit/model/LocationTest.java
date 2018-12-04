/*
* LocationTest
*
* version 1.0
*
* Date: 28.10.2018
*
* Copyright notice: AGPLv3
* 
*/

package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LocationTest {

	private Coordinate c1;
	private Coordinate c2;
	private Coordinate c3;
	Location l;
	
	
	@Before
	public void init() {
		try{
		c1 = new CartesianCoordinate(1.0, 1.0, 1.0);
		c2 = new CartesianCoordinate(c1);
		c3 = new CartesianCoordinate(1.0,2.0,3.0);
		}
		catch(CoordinateException e){
			e.printStackTrace();
			throw new RuntimeException("init in CartesianCoordinate class failed");
		}
		l = new Location(c1);
	}

	@Test
	public void testSetterAndGetter(){
		l.setCoordinate(c3);
		assert(c3 == l.getCoordinate());
	}
	
	@Test
	public void testEquals(){
		Location m = new Location(c2);
		assert(m.equals(l));
		assert(l.equals(m));
		
		Location n = new Location(c3);
		assert(!n.equals(m));
		assert(!m.equals(n));
		
		assert(!n.equals(null));
		assert(!n.equals(new String("hallo")));
		
	}

		
}
