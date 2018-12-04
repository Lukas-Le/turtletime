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
import org.wahlzeit.model.turtle.TurtlePhoto;

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
		try{
		c1 = new CartesianCoordinate(1.0,2.0,3.0);
		c2 = new CartesianCoordinate(2.4,2.1,3.1);
		c3 = new CartesianCoordinate(3.1,3.2,3.3);
		c4 = new CartesianCoordinate(3.1,3.2,3.3);
		c5 = new CartesianCoordinate(-1.0,1.0,1.0);
		s = new String("test");
		}
		catch(CoordinateException e){
			e.printStackTrace();
			throw new RuntimeException("CartesianCoordinate init testcase failed");
		}
	}
	
	//this is basicly an @expected but this annotation did not work for me:(
	@Test
	public void testInvalidCoordinate(){
		boolean worked = false;
		try{
			CartesianCoordinate c = new CartesianCoordinate(Double.NaN, 1, 2);
		}
		catch(CoordinateException e){
			worked = true;
		}
		Assert.assertTrue(worked);
	}
	
	
	
	@Test
	public void testInitValid(){
		try{
		new CartesianCoordinate(c1);
		new CartesianCoordinate(c2);
		new CartesianCoordinate(c3);
		new CartesianCoordinate(c4);
		new CartesianCoordinate(Double.MAX_VALUE,Double.MIN_VALUE,0.0);
		}
		catch(CoordinateException e){
			e.printStackTrace();
			throw new RuntimeException("CartesianCoordinate testcase failed in testInitValid");
		}
	}
	
	
	
	
	@Test
	public void testValidGetter(){
		assert(Math.abs(c1.asCartesianCoordinate().getX() - 1.0) < EPSIOLON);
		assert(Math.abs(c1.asCartesianCoordinate().getY() - 2.0) < EPSIOLON);
		assert(Math.abs(c1.asCartesianCoordinate().getZ() - 3.0) < EPSIOLON);
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
			Assert.assertTrue(Math.abs(c1.getCartesianDistance(c5) - 3.0) < EPSIOLON);
		}
		catch(CoordinateException e){
			e.printStackTrace();
			throw new RuntimeException("Cartesian Coordinate falied with exception in testGetDistanceVAlid");
		}
	}
	
	@Test(expected = ArithmeticException.class)
	public void testGetDistanceInValid(){
		try{
			Coordinate max = new CartesianCoordinate(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
			Coordinate min = new CartesianCoordinate(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
			max.getCartesianDistance(min);
		}
		catch(CoordinateException e){
			e.printStackTrace();
			throw new RuntimeException("function testGetDistanceInValid faild with wrong exception");
		}
	}
	

}
