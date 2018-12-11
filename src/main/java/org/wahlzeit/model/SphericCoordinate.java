package org.wahlzeit.model;

import static org.junit.Assert.*;
import org.junit.*;
import org.wahlzeit.model.Coordinate;
import org.wahlzeit.model.AssertCoordinateInvariants;

/*
* SphericCoordinate
*
* version 1.0
*
* Date: 28.10.2018
*
* Copyright notice: AGPLv3
* 
* This class handles Coordinates. The have to be initialized with reasonable values (Infinity and NaN is not accepted).
* If an overflow occurs in any calculation an ArithmeticException is thrown.
*/
public class SphericCoordinate extends AbstractCoordinate {
	
	private final double phi;
	private final double theta;
	private final double radius;
	
	private final static double EPSILON = 1e-14;
	
	/**
	 * @methodtype constructor
	 */
	private SphericCoordinate(double phi, double theta, double radius) throws CoordinateException{
		
		//preconditions
		AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);
		
		
		
		this.phi = phi;
		this.theta = theta;
		this.radius = radius;
		
		AssertCoordinateInvariants.assertThreeAssignments(phi, theta, radius, this.phi, this.theta, this.radius);
		AssertCoordinateInvariants.assertCoordinate(this.phi, this.theta, this.radius);
	}
	
	
	public static SphericCoordinate getSphericCoordinate(double phi, double theta, double radius) throws CoordinateException{
		SphericCoordinate c = new SphericCoordinate(phi, theta, radius);
		if(SphericCoordinate.existsInCoordinateValObjMap(c)){
			return (SphericCoordinate) getCoordinateInMap(c.hashCode());
		} else{
			insertInCoordinateHashMap(c);
			return c;
		}
	}
	
	
	
	/**
	 * @methodtype get
	 */
	public double getPhi(){
		return phi;
	}
	
	/**
	 * @methodtype get
	 */
	public double getTheta(){
		return theta;
	}
	
	/**
	 * @methodtype get
	 */
	public double getRadius(){
		return radius;
	}
	
	
	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		CartesianCoordinate c;
		try{
			AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);

		
			double x = radius * Math.sin(theta) * Math.cos(phi);
			double y = radius * Math.sin(theta) * Math.sin(phi);
			double z = radius * Math.cos(theta);
			c =  CartesianCoordinate.getCartesianCoordinate(x,y,z);
		
		
			Assert.assertNotNull(c); 
			AssertCoordinateInvariants.assertCoordinate(c.getX(), c.getY(), c.getZ());
		}
		catch(CoordinateException e){
			throw new RuntimeException("Internal Error: Severe error in asCartesian! This message indicates a bug and not wrong usage!");
		}

		return c;
	}

	@Override
	public double getCartesianDistance(Coordinate c) throws IllegalArgumentException,CoordinateException{
		if(c == null)
			throw new IllegalArgumentException("getCartesianDistance must not be called with null");
		return this.asCartesianCoordinate().getCartesianDistance(c);
	
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		SphericCoordinate s;
	
		try{
			//preconditions
			AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);

		
			s = this;
		
			//postconditions
			Assert.assertNotNull(s); 
			AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);
		}
		//transform checked Error into unchecked error
		catch(CoordinateException e){
			e.printStackTrace();
			throw new RuntimeException("Internal Error: This indicates a bug in asSphericCoordinate -->see stacktrace");
		}
		return s;
	}

	@Override
	public double getCentralAngle(Coordinate c) throws IllegalArgumentException,CoordinateException{
		//preconditions
		if(c == null)
			throw new IllegalArgumentException("getCentralAngle must not be called with null");
		CartesianCoordinate buf = c.asCartesianCoordinate();
		AssertCoordinateInvariants.assertCoordinate(buf.getX(), buf.getY(), buf.getZ());
		AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);

		
		
		double ret =  this.asCartesianCoordinate().getCentralAngle(c);
		
		//postconditions
		AssertCoordinateInvariants.assertSingleDoubleValue(ret);
		return ret;

	}

	
	/*
	 * theoraticly for value objects the comparision is performed by
	 * "==" and the function should not be necessary any more.
	 * Practicly there can be still an calculation error and that is why
	 * this function should be kept.
	 */
	private boolean doIsEqual(SphericCoordinate c) throws CoordinateException{
		
		AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);
		AssertCoordinateInvariants.assertSphericCoordinate(this.phi, this.theta, this.radius);
		
		boolean ret;
		
		
		if(c == null){
			ret =  false;
		} else{
			if( Math.abs(phi - c.phi) < EPSILON && Math.abs(theta - c.theta) < EPSILON && Math.abs(radius-c.radius) < EPSILON)
				ret =  true;
			else
				ret = false;
		}
		
		
		AssertCoordinateInvariants.assertIfThenCheck(c == null, false, ret);
		AssertCoordinateInvariants.assertIfThenCheck(Math.abs(c.getPhi() - phi) >= EPSILON, false, ret);
		AssertCoordinateInvariants.assertIfThenCheck(Math.abs(c.getTheta() - theta) >= EPSILON, false, ret);
		AssertCoordinateInvariants.assertIfThenCheck(Math.abs(c.getRadius() - radius) >= EPSILON, false, ret);

		
		return ret;
	}
	
	
	/*
	 * theoraticly for value objects the comparision is performed by
	 * "==" and the function should not be necessary any more.
	 * Practicly there can be still an calculation error and that is why
	 * this function should be kept.
	 */
	@Override
	public boolean isEqual(Coordinate c) {
		if(c == null)
			return false;
		try{
			return this.doIsEqual(c.asSphericCoordinate());
		}
		//transform checked Exception into unchecked Exception because this error is cause by internal faults and
		// does not indicate a misusage of the function.
		catch(CoordinateException e){
			e.printStackTrace();
			throw new RuntimeException("internal Error in isEqual. See stacktrace");
		}
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	@Override
	public String toString(){
		return "SphericCoordinate: (" + phi + "," + theta + "," + radius + ")";
	}

}
