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
		try{
		c1 = new CartesianCoordinate(1.0,2.0,3.0).asSphericCoordinate();
		c2 = new CartesianCoordinate(2.4,2.1,3.1).asSphericCoordinate();
		c3 = new CartesianCoordinate(3.1,3.2,3.3).asSphericCoordinate();
		c4 = new CartesianCoordinate(3.1,3.2,3.3).asSphericCoordinate();
		c5 = new CartesianCoordinate(-1.0,1.0,1.0).asSphericCoordinate();
		s = new String("test");
		}
		catch(CoordinateException e){
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	
	public void testInvalidCoordinate(){
		boolean worked = false;
		try{
			SphericCoordinate c = new SphericCoordinate(50, 1, 2);
		}
		catch(CoordinateException e){
			worked = true;
		}
		Assert.assertTrue(worked);
	}
	
	@Test
	public void testInitValid(){
		Assert.assertNotNull(c1);
		Assert.assertNotNull(c2);
		Assert.assertNotNull(c3);
		Assert.assertNotNull(c4);
		try{
			new SphericCoordinate(c1);
			new SphericCoordinate(c2);
			new SphericCoordinate(c3);
			new SphericCoordinate(c4);
		}
		catch(CoordinateException e){
			e.printStackTrace();
			Assert.assertTrue("exception must not occur",false);
		}
		//--> the command below leads to an overflow due to conversion!
		//new CartesianCoordinate(Double.MAX_VALUE/4,Double.MIN_VALUE/4,0.0).asSphericCoordinate();
		
	}
	

	
	@Test
	public void testValidGetter(){
		double b = Math.abs(c1.asCartesianCoordinate().getX() - 1.0);
		boolean bb = b < EPSIOLON;
		assert(bb);
		Assert.assertTrue(Math.abs(c1.asCartesianCoordinate().getY() - 2.0) < EPSIOLON);
		Assert.assertTrue(Math.abs(c1.asCartesianCoordinate().getZ() - 3.0) < EPSIOLON);
	}
	
	
	
	@Test
	public void testIsEqual(){
		Assert.assertFalse(c1.isEqual(c2));
		Assert.assertFalse(c1.isEqual(c3));
		Assert.assertFalse(c2.isEqual(c3));
		Assert.assertTrue(c3.isEqual(c4));
		Assert.assertFalse(c1.isEqual(null));
	}
	
	@Test
	public void testEquals(){
		Assert.assertFalse(c1.equals(c2));
		Assert.assertFalse(c1.equals(c3));
		Assert.assertFalse(c2.equals(c3));
		Assert.assertTrue(c3.equals(c4));
		Assert.assertFalse(c1.equals(null));
		Assert.assertFalse(c1.equals(s));
	}
	
	
	@Test
	public void testGetDistanceValid(){
		try{
			Assert.assertTrue(Math.abs(c1.getCartesianDistance(c1) - 0.0) < EPSIOLON);
		}
		catch(CoordinateException e){
			e.printStackTrace();
			throw new RuntimeException("testGetDistanceValid failed");
		}
	}
	
	
	@Test(expected = ArithmeticException.class)
	public void testGetDistanceInValid(){
		try{
		Coordinate max = new CartesianCoordinate(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE).asSphericCoordinate();
		Coordinate min = new CartesianCoordinate(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE).asSphericCoordinate();
		max.getCartesianDistance(min);
		}
		catch(CoordinateException e){
			e.printStackTrace();
			throw new RuntimeException("testGetDistanceValid faild with exception");
		}
	}
	

}
