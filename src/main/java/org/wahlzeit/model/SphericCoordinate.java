package org.wahlzeit.model;

import org.wahlzeit.model.Coordinate;
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
public class SphericCoordinate implements Coordinate {
	
	private double phi;
	private double theta;
	private double radius;
	
	private final static double EPSILON = 1e-14;
	
	/**
	 * @methodtype constructor
	 */
	public SphericCoordinate(double phi, double theta, double radius){
		setPhi(phi);
		setTheta(theta);
		setRadius(radius);
	}
	
	/**
	 * @methodtype constructor
	 */
	public SphericCoordinate(Coordinate c){
		if(c!=null){
			SphericCoordinate sp = c.asSphericCoordinate();
			if(sp == null){
				throw new IllegalArgumentException("Coordinate can not be parsed corretly. Internal error!");
			}
			setPhi(sp.getPhi());
			setTheta(sp.getTheta());
			setTheta(sp.getRadius());
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
	
	

	private boolean check_double(double d){
		//checks for infinity and NaN.
		return Double.isFinite(d);
	}
	
	/**
	 * @methodtype set
	 */
	private void setPhi(double phi){
		if(!check_double(phi)){
			throw new IllegalArgumentException("input of double must not be NaN or neg. or pos. infinity");
		}
		this.phi = phi;
	}
	
	/**
	 * @methodtype set
	 */
	private void setTheta(double theta){
		if(!check_double(theta)){
			throw new IllegalArgumentException("input of double must not be NaN or neg. or pos. infinity");
		}
		this.theta = theta;
	}
	
	/**
	 * @methodtype set
	 */
	private void setRadius(double radius){
		if(!check_double(radius)){
			throw new IllegalArgumentException("input of double must not be NaN or neg. or pos. infinity");
		}
		this.radius = radius;
	}
	
	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		double x = radius * Math.sin(theta) * Math.cos(phi);
		double y = radius * Math.sin(theta) * Math.sin(phi);
		double z = radius * Math.cos(theta);
		return new CartesianCoordinate(x,y,z);
	}

	@Override
	public double getCartesianDistance(Coordinate c) {
		if(c == null)
			throw new IllegalArgumentException("getCartesianDistance must not be called with null");
		return this.asCartesianCoordinate().getCartesianDistance(c);
	
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		
		return new SphericCoordinate(phi,theta,radius);
	}

	@Override
	public double getCentralAngle(Coordinate c) {
		if(c == null)
			throw new IllegalArgumentException("getCentralAngle must not be called with null");
		return this.asCartesianCoordinate().getCentralAngle(c);

	}

	private boolean doIsEqual(SphericCoordinate c){
		if(c == null)
			return false;
		if( Math.abs(phi - c.phi) < EPSILON && Math.abs(theta - c.theta) < EPSILON && Math.abs(radius-c.radius) < EPSILON)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean isEqual(Coordinate c) {
		if(c == null)
			return false;
		return this.doIsEqual(c.asSphericCoordinate());
	}
	
	@Override
	public boolean equals(Object c) {
		if(c == null)
			return false;
		if(c instanceof Coordinate){
			return this.isEqual((Coordinate) c);
		}
		else{
			return false;
		}
	}

}
