/*
* Coordinate
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

package org.wahlzeit.model;

public class CartesianCoordinate implements Coordinate{
	
	private double x;
	private double y;
	private double z;
	
	private final static double EPSILON = 1e-14;
	
	/**
	 * @methodtype constructor
	 */
	public CartesianCoordinate(double x, double y, double z){
		setX(x);
		setY(y);
		setZ(z);
	}
	
	/**
	 * @methodtype constructor
	 */
	public CartesianCoordinate(Coordinate c){
		if(c!=null){
			CartesianCoordinate cc = c.asCartesianCoordinate();
			setX(cc.getX());
			setY(cc.getY());
			setZ(cc.getZ());
		}
	}
	
	/**
	 * @methodtype get
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * @methodtype get
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * @methodtype get
	 */
	public double getZ(){
		return z;
	}
	
	/*
	 * check for valid double. Useful for setter functions
	 */
	private boolean check_double(double d){
		//checks for infinity and NaN.
		return Double.isFinite(d);
	}
	
	/**
	 * @methodtype set
	 */
	private void setCoordinates(double x,double y, double z){
		setX(x);
		setY(y);
		setZ(z);
	}
	
	/**
	 * @methodtype set
	 */
	private void setX(double x){
		if(!check_double(x)){
			throw new IllegalArgumentException("input of double must not be NaN or neg. or pos. infinity");
		}
		this.x = x;
	}
	
	/**
	 * @methodtype set
	 */
	private void setY(double y){
		if(!check_double(x)){
			throw new IllegalArgumentException("input of double must not be NaN or neg. or pos. infinity");
		}
		this.y = y;
	}
	
	/**
	 * @methodtype set
	 */
	private void setZ(double z){
		if(!check_double(x)){
			throw new IllegalArgumentException("input of double must not be NaN or neg. or pos. infinity");
		}
		this.z = z;
	}
	
	/**
	 * @methodtype boolean-query
	 */
	private boolean doIsEqual(CartesianCoordinate c){
		if(c == null){
			return false;
		}
		
		if(Math.abs(c.getX() - x) < EPSILON &&  Math.abs(c.getY() - y) < EPSILON && Math.abs(c.getZ() - z) < EPSILON ){
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * @methodtype boolean-query
	 */
	public boolean equals(Object obj){
		if(obj instanceof Coordinate){
			return this.isEqual((Coordinate) obj);
		} else{
			return false;
		}
	}
	
	/*
	 * @input:  Coordinate c must not be null
	 * 
	 * @return: returns the Cartesian distance of the two coordinates
	 * 
	 * @info:   You do not need to worry about the order of the coordinates because
	 *          this.getDistance(c) equals to c.getDistance(this) if c!=null
	 * 
	 *          see https://javapapers.com/core-java/java-overflow-and-underflow/ for overflow/underflow discussion
	 * 
	 * @exception: throws ArithmeticException when any overflow occurs
	 *             throws IllegalArgumentException when Coordinate is null
	 */
	private double doGetDistance(CartesianCoordinate c){
		if(c == null){
			throw new IllegalArgumentException("Coordinate must not be null");
		}
		
		//subtract and check for neg. overflow
		Double x_diff = x - c.getX();
		Double y_diff = y - c.getY();
		Double z_diff = z - c.getZ();
		if(x_diff.isInfinite() || y_diff.isInfinite() || z_diff.isInfinite()){
			throw new ArithmeticException("negative overflow occured");
		}
		
		//multiply and check for overflow
		Double x_diff_multiplied = x_diff * x_diff;
		Double y_diff_multiplied = y_diff * y_diff;
		Double z_diff_multiplied = z_diff * z_diff;
		if(x_diff_multiplied.isInfinite() || y_diff_multiplied.isInfinite() || z_diff_multiplied.isInfinite()){
			throw new ArithmeticException("negative overflow occured");
		}
		
		//sum up and check for overflow
		Double sum = x_diff_multiplied + y_diff_multiplied + z_diff_multiplied;
		if(sum.isInfinite()){
			throw new ArithmeticException("positive overflow occured");
		}
		
		return Math.sqrt(sum);
	}

	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		// TODO Auto-generated method stub
		CartesianCoordinate c = new CartesianCoordinate(x,y,z);
		return c;
	}

	@Override
	public double getCartesianDistance(Coordinate c) {
		if(c == null)
			throw new IllegalArgumentException("getCartesianDistance must be called with a non null value");
		
		return doGetDistance(c.asCartesianCoordinate());
	}
	
	private double getRadius(double x,double y, double z){
		double xx = x * x;
		double yy = y * y;
		double zz = z * z;
		if(x != 0 && Math.abs(xx / x -x) >= EPSILON)
			throw new ArithmeticException("overflow in calc in asSphericCoordinate");
		
		if(y != 0 &&  Math.abs(yy / y -y) >= EPSILON)
			throw new ArithmeticException("overflow in calc in asSphericCoordinate");
		
		if(z != 0 &&  Math.abs(zz /z -z ) >= EPSILON)
			throw new ArithmeticException("overflow in calc in asSphericCoordinate");
		
			
		double xy = xx + yy;
		if( Math.abs(xy - xx - yy) >= EPSILON)
			throw new ArithmeticException("overflow in calc in asSphericCoordinate");
		
		double yz = yy + zz;
		if(Math.abs(yz - zz - yy) >= EPSILON)
			throw new ArithmeticException("overflow in calc in asSphericCoordinate");
		
		double r = Math.sqrt(yz);
		return r;
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		
		double r = getRadius(x, y, z);
		double theta;
		if(r != 0){
			theta = Math.acos(z/r);
		}
		else{
			theta = 0;
		}
		double phi;
		if(x != 0){
			phi = Math.atan(y/x);
		}
		else{
			phi = 0;
		}
		
		return new SphericCoordinate(phi, theta, r);
	}

	@Override
	public double getCentralAngle(Coordinate c) {
		if(c == null)
			throw new IllegalArgumentException("Coordinate for getCentralAngle must not be null");
		CartesianCoordinate cc = c.asCartesianCoordinate();
		double dx = cc.x - this.y;
		double dy = cc.y - this.y;
		double dz = cc.z - this.z;
		
		double c_wiki = getRadius(dx, dy, dz);
		double result = 2 * Math.asin(c_wiki/2);
		return result;
	}

	@Override
	public boolean isEqual(Coordinate c) {
		if(c == null)
			return false;
		CartesianCoordinate cc = c.asCartesianCoordinate();
		
		return this.doIsEqual(cc);
	}
}

