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
	
	private double phi;
	private double theta;
	private double radius;
	
	private final static double EPSILON = 1e-14;
	
	/**
	 * @methodtype constructor
	 */
	public SphericCoordinate(double phi, double theta, double radius){
		
		//preconditions
		AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);
		
		
		
		this.phi = phi;
		this.theta = theta;
		this.radius = radius;
		
		AssertCoordinateInvariants.assertThreeAssignments(phi, theta, radius, this.phi, this.theta, this.radius);
		AssertCoordinateInvariants.assertCoordinate(this.phi, this.theta, this.radius);
	}
	
	/**
	 * @methodtype constructor
	 */
	public SphericCoordinate(Coordinate c){
		
		
		Assert.assertNotNull(c);
		Assert.assertNotNull(c.asSphericCoordinate());
		SphericCoordinate cartPre = c.asSphericCoordinate();
		AssertCoordinateInvariants.assertSphericCoordinate(cartPre.phi, cartPre.theta, cartPre.radius);
		
		SphericCoordinate sp = c.asSphericCoordinate();

		this.phi = sp.phi;
		this.theta = sp.theta;
		this.radius = sp.radius;
		
		//postcondtions
		SphericCoordinate cartPost = c.asSphericCoordinate();
		AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);
		AssertCoordinateInvariants.assertThreeAssignments(phi,theta,radius,cartPost.phi,cartPost.theta,cartPost.radius);
		
		
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
	
	
	
	/**
	 * @methodtype set
	 */
	@Deprecated
	private void setPhi(double phi){
		AssertCoordinateInvariants.assertSingleDoubleValue(phi);
		this.phi = phi;
	}
	
	/**
	 * @methodtype set
	 */
	@Deprecated
	private void setTheta(double theta){
		AssertCoordinateInvariants.assertSingleDoubleValue(theta);
		this.theta = theta;
	}
	
	/**
	 * @methodtype set
	 */
	@Deprecated
	private void setRadius(double radius){
		AssertCoordinateInvariants.assertSingleDoubleValue(radius);
		this.radius = radius;
	}
	
	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		
		AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);

		
		double x = radius * Math.sin(theta) * Math.cos(phi);
		double y = radius * Math.sin(theta) * Math.sin(phi);
		double z = radius * Math.cos(theta);
		CartesianCoordinate c =  new CartesianCoordinate(x,y,z);
		
		
		Assert.assertNotNull(c); 
		AssertCoordinateInvariants.assertCoordinate(c.getX(), c.getY(), c.getZ());

		return c;
	}

	@Override
	public double getCartesianDistance(Coordinate c) {
		if(c == null)
			throw new IllegalArgumentException("getCartesianDistance must not be called with null");
		return this.asCartesianCoordinate().getCartesianDistance(c);
	
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		//preconditions
		AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);

		
		SphericCoordinate s =  new SphericCoordinate(phi,theta,radius);
		
		//postconditions
		Assert.assertNotNull(s); 
		AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, radius);
		return s;
	}

	@Override
	public double getCentralAngle(Coordinate c) {
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

	private boolean doIsEqual(SphericCoordinate c){
		
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
	
	@Override
	public boolean isEqual(Coordinate c) {
		if(c == null)
			return false;
		return this.doIsEqual(c.asSphericCoordinate());
	}
	
	

}
