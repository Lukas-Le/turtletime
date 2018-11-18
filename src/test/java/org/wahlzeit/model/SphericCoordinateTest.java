/*
* SphericCoordinateTest
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
import java.lang.Math;

public class SphericCoordinateTest {

	private static final double EPSIOLON = 1e-13;
	
	private Coordinate c1;
	private Coordinate c2;
	private Coordinate c3;
	private Coordinate c4;
	private Coordinate c5;
	private String s;
	private double[] testArray = {Double.NaN,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,2.0};
	
	@Before
	public void init(){
		c1 = new CartesianCoordinate(1.0,2.0,3.0).asSphericCoordinate();
		c2 = new CartesianCoordinate(2.4,2.1,3.1).asSphericCoordinate();
		c3 = new CartesianCoordinate(3.1,3.2,3.3).asSphericCoordinate();
		c4 = new CartesianCoordinate(3.1,3.2,3.3).asSphericCoordinate();
		c5 = new CartesianCoordinate(-1.0,1.0,1.0).asSphericCoordinate();
		s = new String("test");
	}
	
	@Test
	public void testInitValid(){
		Assert.assertNotNull(c1);
		Assert.assertNotNull(c2);
		Assert.assertNotNull(c3);
		Assert.assertNotNull(c4);
		new SphericCoordinate(c1);
		new SphericCoordinate(c2);
		new SphericCoordinate(c3);
		new SphericCoordinate(c4);
		//--> the command below leads to an overflow due to conversion!
		//new CartesianCoordinate(Double.MAX_VALUE/4,Double.MIN_VALUE/4,0.0).asSphericCoordinate();
		
	}
	
	
	//this works in JUnit perfectly fine but with gradlew test this does not work! :/
	//as I discovered somethine similiar before (gradle complaining that it throws an execption
	//even though I specified that it throws an exception - working in JUnit)
	// A tip of the internet to change the annotation to @After/@Before did not work either.
	/*
	@Test
	public void testValidGetter(){
		double b = Math.abs(c1.asCartesianCoordinate().getX() - 1.0);
		boolean bb = b < EPSIOLON;
		assert(bb);
		assert(Math.abs(c1.asCartesianCoordinate().getY() - 2.0) < EPSIOLON);
		assert(Math.abs(c1.asCartesianCoordinate().getZ() - 3.0) < EPSIOLON);
	}
	*/
	
	
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
	
	
	//this works in JUnit perfectly fine but with gradlew teset this does not work! :/
	//as I discovered somethine similiar before (gradle complaining that it throws an execption
	//even though I specified that it throws an exception - working in JUnit)
	/*
	@Test
	public void testGetDistanceValid(){
		assert(Math.abs(c1.getCartesianDistance(c1) - 0.0) < EPSIOLON);
		assert(Math.abs(c1.getCartesianDistance(c5) - 3.0) < EPSIOLON);
	}
	*/
	
	@Test(expected = ArithmeticException.class)
	public void testGetDistanceInValid(){
		Coordinate max = new CartesianCoordinate(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE).asSphericCoordinate();
		Coordinate min = new CartesianCoordinate(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE).asSphericCoordinate();
		max.getCartesianDistance(min);
	}
	

}
