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
	static public void assertSingleDoubleValue(double d) throws IllegalArgumentException{
		//checks for infinity and NaN.
		if(! Double.isFinite(d)){
			throw new IllegalArgumentException("double must not be NaN or pos. or neg. infinity");
		}
	}
	
	static public void assertCoordinate(double x, double y, double z) throws CoordinateException{
		try{
			assertSingleDoubleValue(x);
			assertSingleDoubleValue(y);
			assertSingleDoubleValue(z);
		}
		catch(IllegalArgumentException e){
			throw new CoordinateException("At least one coordinate value ist not valid(NaN,pos. or neg. infinity");
		}
	}
	
	static public void assertThreeAssignments(double x, double y, double z,double a1,double a2, double a3) throws CoordinateException{
		//I do not use assertEquals because this is deprecated
		if(x != a1 || y != a2 || z != a3){
			throw new CoordinateException("Internal error: Assignment of coordinates ist not correct.");
		}
	
	}
	
	static public void assertIfThenCheck(boolean if_condition,boolean then_condition_should,boolean then_condition_is) throws CoordinateException{
		if(if_condition){
			if(then_condition_should != then_condition_is){
				throw new CoordinateException("internal error: condition check failed!");
			}
		}
		
	}
	
	
	static public void assertSphericCoordinate(double phi, double theta,double r) throws CoordinateException{
		assertCoordinate(phi, theta, r);
		
		if(    !(phi >= -Math.PI && phi <= Math.PI) ||
				!(theta >= -Math.PI && theta <= Math.PI) || 
				!(r >= 0)){
			throw new CoordinateException("At least one of the SphericCoordinate is not in valid range");
		}
			

	}
}
