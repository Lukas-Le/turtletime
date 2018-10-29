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

public class Coordinate {
	
	private double x;
	private double y;
	private double z;
	
	/**
	 * @methodtype constructor
	 */
	public Coordinate(double x, double y, double z){
		setX(x);
		setY(y);
		setZ(z);
	}
	
	/**
	 * @methodtype constructor
	 */
	public Coordinate(Coordinate c){
		if(c!=null){
			setX(c.getX());
			setY(c.getY());
			setZ(c.getZ());
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
	public void setCoordinates(double x,double y, double z){
		setX(x);
		setY(y);
		setZ(z);
	}
	
	/**
	 * @methodtype set
	 */
	public void setX(double x){
		if(!check_double(x)){
			throw new IllegalArgumentException("input of double must not be NaN or neg. or pos. infinity");
		}
		this.x = x;
	}
	
	/**
	 * @methodtype set
	 */
	public void setY(double y){
		if(!check_double(x)){
			throw new IllegalArgumentException("input of double must not be NaN or neg. or pos. infinity");
		}
		this.y = y;
	}
	
	/**
	 * @methodtype set
	 */
	public void setZ(double z){
		if(!check_double(x)){
			throw new IllegalArgumentException("input of double must not be NaN or neg. or pos. infinity");
		}
		this.z = z;
	}
	
	/**
	 * @methodtype boolean-query
	 */
	public boolean isEqual(Coordinate c){
		if(c == null){
			return false;
		}
		
		if(c.getX() == x && c.getY() == y && c.getZ() == z){
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
	public double getDistance(Coordinate c){
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
}

