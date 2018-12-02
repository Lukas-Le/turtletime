/*
* AssertCoordinateInvariants
*
* version 1.0
*
* Date: 2.12.2018
*
* Copyright notice: AGPLv3
*/


package org.wahlzeit.model;

import static org.junit.Assert.*;
import org.junit.*;

public class AssertCoordinateInvariants {
	
	/*
	 * check for valid double. Useful for setter functions
	 */
	static public void assertSingleDoubleValue(double d){
		//checks for infinity and NaN.
		Assert.assertTrue( Double.isFinite(d));
	}
	
	static public void assertCoordinate(double x, double y, double z){
		assertSingleDoubleValue(x);
		assertSingleDoubleValue(y);
		assertSingleDoubleValue(z);
	}
	
	static public void assertThreeAssignments(double x, double y, double z,double a1,double a2, double a3){
		//I do not use assertEquals because this is deprecated
		Assert.assertTrue(x == a1);
		Assert.assertTrue(y == a2);
		Assert.assertTrue(z == a3);
	}
	
	static public void assertIfThenCheck(boolean if_condition,boolean then_condition_should,boolean then_condition_is){
		if(if_condition){
			Assert.assertEquals(then_condition_should, then_condition_is);
		}
		
	}
	
	
	static public void assertSphericCoordinate(double phi, double theta,double r){
		assertCoordinate(phi, theta, r);
		
		
		
		Assert.assertTrue(phi >= -Math.PI && phi <= Math.PI);
		Assert.assertTrue(theta >= -Math.PI && theta <= Math.PI);
		Assert.assertTrue(r >= 0);

	}
}
