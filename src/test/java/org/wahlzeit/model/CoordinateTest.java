/*
* CoordinateTest
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
import org.junit.*;

public class CoordinateTest {

	private Coordinate c1;
	private Coordinate c2;
	private Coordinate c3;
	private Coordinate c4;
	private Coordinate c5;
	private String s;
	private double[] testArray = {Double.NaN,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,2.0};
	
	@Before
	public void init(){
		c1 = new Coordinate(1.0,2.0,3.0);
		c2 = new Coordinate(2.4,2.1,3.1);
		c3 = new Coordinate(3.1,3.2,3.3);
		c4 = new Coordinate(3.1,3.2,3.3);
		c5 = new Coordinate(-1.0,1.0,1.0);
		s = new String("test");
	}
	
	@Test
	public void testInitValid(){
		new Coordinate(c1);
		new Coordinate(c2);
		new Coordinate(c3);
		new Coordinate(c4);
		new Coordinate(Double.MAX_VALUE,Double.MIN_VALUE,0.0);
	}
	
	@Test
	public void testInitInvalidConstructor(){
		
		for(double d : testArray){
			for(double e : testArray){
				for(double f: testArray){
					if(d == e && e == f && f == 2.0)
						continue;
					boolean mistake = false;
					try{
						new Coordinate(d,e,f);
					}
					catch(IllegalArgumentException g){
						mistake = true;
					}
					assert(mistake);
				}
			}
		}
	}
	
	@Test
	public void testInvalidSetter(){
		
		for(double d : testArray){
			if(d == 2.0)
				continue;
			boolean mistake = false;
			try{
				c1.setX(d);
			}
			catch(IllegalArgumentException g){
				mistake = true;
			}
			assert(mistake);
		}
		for(double d : testArray){
			if(d == 2.0)
				continue;
			boolean mistake = false;
			try{
				c1.setY(d);
			}
			catch(IllegalArgumentException g){
				mistake = true;
			}
			assert(mistake);
		}
		for(double d : testArray){
			if(d == 2.0)
				continue;
			boolean mistake = false;
			try{
				c1.setZ(d);
			}
			catch(IllegalArgumentException g){
				mistake = true;
			}
			assert(mistake);
		}
		
	}
	
	@Test
	public void testValidGetter(){
		assert(c1.getX() == 1.0);
		assert(c1.getY() == 2.0);
		assert(c1.getZ() == 3.0);
	}
	
	@Test
	public void testIsEqual(){
		assert(!c1.isEqual(c2));
		assert(!c1.isEqual(c3));
		assert(!c2.isEqual(c3));
		assert(c3.isEqual(c4));
		assert(!c1.isEqual(null));
	}
	
	@Test
	public void testEquals(){
		assert(!c1.equals(c2));
		assert(!c1.equals(c3));
		assert(!c2.equals(c3));
		assert(c3.equals(c4));
		assert(!c1.equals(null));
		assert(!c1.equals(s));
	}
	
	@Test
	public void testGetDistanceValid(){
		assert(c1.getDistance(c1) == 0.0);
		assert(c1.getDistance(c5) == 3.0);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testGetDistanceInValid(){
		Coordinate max = new Coordinate(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
		Coordinate min = new Coordinate(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
		max.getDistance(min);
	}
	

}
