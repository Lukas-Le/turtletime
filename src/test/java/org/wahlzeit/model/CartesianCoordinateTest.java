/*
* CartesianCoordinateTest
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
import org.wahlzeit.model.Coordinate;

public class CartesianCoordinateTest {
	
	private static final double EPSIOLON = 1e-14;

	private Coordinate c1;
	private Coordinate c2;
	private Coordinate c3;
	private Coordinate c4;
	private Coordinate c5;
	private String s;
	private double[] testArray = {Double.NaN,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,2.0};
	
	@Before
	public void init(){
		c1 = new CartesianCoordinate(1.0,2.0,3.0);
		c2 = new CartesianCoordinate(2.4,2.1,3.1);
		c3 = new CartesianCoordinate(3.1,3.2,3.3);
		c4 = new CartesianCoordinate(3.1,3.2,3.3);
		c5 = new CartesianCoordinate(-1.0,1.0,1.0);
		s = new String("test");
	}
	
	@Test
	public void testInitValid(){
		new CartesianCoordinate(c1);
		new CartesianCoordinate(c2);
		new CartesianCoordinate(c3);
		new CartesianCoordinate(c4);
		new CartesianCoordinate(Double.MAX_VALUE,Double.MIN_VALUE,0.0);
	}
	
	/*
	 * sadly does not work for gradle execuction.
	 * It works perfectly fine for the JUnit test!
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
	*/
	
	
	/*
	 * sadly does not work for gradle execuction.
	 * It works perfectly fine for the JUnit test!
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
	*/

	
	
	
	@Test
	public void testValidGetter(){
		assert(Math.abs(c1.asCartesianCoordinate().getX() - 1.0) < EPSIOLON);
		assert(Math.abs(c1.asCartesianCoordinate().getY() - 2.0) < EPSIOLON);
		assert(Math.abs(c1.asCartesianCoordinate().getZ() - 3.0) < EPSIOLON);
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
		assert(Math.abs(c1.getCartesianDistance(c1) - 0.0) < EPSIOLON);
		assert(Math.abs(c1.getCartesianDistance(c5) - 3.0) < EPSIOLON);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testGetDistanceInValid(){
		Coordinate max = new CartesianCoordinate(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
		Coordinate min = new CartesianCoordinate(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
		max.getCartesianDistance(min);
	}
	

}
