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


import org.junit.*;
import org.wahlzeit.model.AssertCoordinateInvariants;

public class CartesianCoordinate extends AbstractCoordinate{
	
	private double x;
	private double y;
	private double z;
	
	private final static double EPSILON = 1e-14;
	
	/**
	 * @methodtype constructor
	 */
	public CartesianCoordinate(double x, double y, double z){
		
		//preconditions
		AssertCoordinateInvariants.assertCoordinate(x, y, z);
	
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		//postconditions
		AssertCoordinateInvariants.assertThreeAssignments(x, y, z, this.x, this.y, this.z);
		AssertCoordinateInvariants.assertCoordinate(this.x, this.y, this.z);

	}
	
	/**
	 * @methodtype constructor
	 */
	public CartesianCoordinate(Coordinate c){
		
		//check preconditions
		Assert.assertNotNull(c);
		Assert.assertNotNull(c.asCartesianCoordinate());
		CartesianCoordinate cartPre = c.asCartesianCoordinate();
		AssertCoordinateInvariants.assertCoordinate(cartPre.x, cartPre.y, cartPre.z);
		
		
		CartesianCoordinate cc = c.asCartesianCoordinate();
		this.x = cc.x;
		this.y = cc.y;
		this.z = cc.z;
		
		//postcondtions
		CartesianCoordinate cartPost = c.asCartesianCoordinate();
		AssertCoordinateInvariants.assertThreeAssignments(x, y, z, cartPost.x, cartPost.y, cartPost.z);
		
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
	
	
	
	/**
	 * @methodtype set
	 */
	private void setCoordinates(double x,double y, double z){
		
		//preconditions
		AssertCoordinateInvariants.assertCoordinate(x, y, z);
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		//postconditions
		AssertCoordinateInvariants.assertCoordinate(this.x,this.y , this.z);
		AssertCoordinateInvariants.assertThreeAssignments(x, y, z, this.x, this.y, this.z);

	}
	
	
	
	/**
	 * @methodtype set
	 */
	@Deprecated
	private void setX(double x){
		AssertCoordinateInvariants.assertSingleDoubleValue(x);
		this.x = x;
	}
	
	/**
	 * @methodtype set
	 */
	@Deprecated
	private void setY(double y){
		AssertCoordinateInvariants.assertSingleDoubleValue(x);
		this.y = y;
	}
	
	/**
	 * @methodtype set
	 */
	@Deprecated
	private void setZ(double z){
		AssertCoordinateInvariants.assertSingleDoubleValue(x);
		this.z = z;
	}
	
	/**
	 * @methodtype boolean-query
	 */
	private boolean doIsEqual(CartesianCoordinate c){
		
		//preconditions
		AssertCoordinateInvariants.assertCoordinate(x, y, z);
		AssertCoordinateInvariants.assertCoordinate(this.x, this.y, this.z);
		
		boolean ret;
		
		if(c == null){
			ret =  false;
		}
		else{
			if(Math.abs(c.getX() - x) < EPSILON &&  Math.abs(c.getY() - y) < EPSILON && Math.abs(c.getZ() - z) < EPSILON ){
				ret =  true;
			} else{
				ret = false;
			}
		}
		
		//postconditions
		AssertCoordinateInvariants.assertIfThenCheck(c == null, false, ret);
		AssertCoordinateInvariants.assertIfThenCheck(Math.abs(c.getX() - x) >= EPSILON, false, ret);
		AssertCoordinateInvariants.assertIfThenCheck(Math.abs(c.getY() - y) >= EPSILON, false, ret);
		AssertCoordinateInvariants.assertIfThenCheck(Math.abs(c.getZ() - z) >= EPSILON, false, ret);
				
		
		return ret;
		
	}
	
	/**
	 * @methodtype boolean-query
	 */
	public boolean equals(Object obj){
		
		//precondition and postcondition check in doIsEqual

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
		AssertCoordinateInvariants.assertCoordinate(c.x, c.y, c.z);
		
		
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
		
		double ret = Math.sqrt(sum);
		
		AssertCoordinateInvariants.assertSingleDoubleValue(sum);
		
		return ret;
	}

	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		// TODO Auto-generated method stub
		
		//preconditions
		AssertCoordinateInvariants.assertCoordinate(this.x, this.y, this.z);
		
		CartesianCoordinate c = new CartesianCoordinate(x,y,z);
		
		//postconditions
		Assert.assertNotNull(c); 
		AssertCoordinateInvariants.assertCoordinate(c.x, c.y, c.z);
		return c;
	}

	@Override
	public double getCartesianDistance(Coordinate c) {
		if(c == null)
			throw new IllegalArgumentException("getCartesianDistance must be called with a non null value");
		
		return doGetDistance(c.asCartesianCoordinate());
	}
	
	private double getRadius(double x,double y, double z){
		
		//preconditions
		AssertCoordinateInvariants.assertCoordinate(x, y, z);
		AssertCoordinateInvariants.assertCoordinate(this.x, this.y, this.z);
		
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
		
		double yz = xy + zz;
		if(Math.abs(yz - zz - xy) >= EPSILON)
			throw new ArithmeticException("overflow in calc in asSphericCoordinate");
		
		double r = Math.sqrt(yz);
		
		//postconditions
		AssertCoordinateInvariants.assertSingleDoubleValue(r);
		AssertCoordinateInvariants.assertCoordinate(this.x, this.y, this.z);
		
		return r;
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		
		
		AssertCoordinateInvariants.assertCoordinate(x, y, z);

		
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
		
		SphericCoordinate ret = new SphericCoordinate(phi, theta, r);
		
		Assert.assertNotNull(ret);
		AssertCoordinateInvariants.assertSphericCoordinate(phi, theta, r);
		
		return ret;
		
	}

	@Override
	public double getCentralAngle(Coordinate c) {
		//preconditions
		if(c == null)
			throw new IllegalArgumentException("Coordinate for getCentralAngle must not be null");
		
		CartesianCoordinate buf = c.asCartesianCoordinate();
		AssertCoordinateInvariants.assertCoordinate(buf.x, buf.y, buf.z);
		AssertCoordinateInvariants.assertCoordinate(this.x, this.y, this.z);
		
		
		
		
		CartesianCoordinate cc = c.asCartesianCoordinate();
		double dx = cc.x - this.y;
		double dy = cc.y - this.y;
		double dz = cc.z - this.z;
		
		double c_wiki = getRadius(dx, dy, dz);
		double result = 2 * Math.asin(c_wiki/2);
		
		AssertCoordinateInvariants.assertSingleDoubleValue(result);
		AssertCoordinateInvariants.assertCoordinate(this.x, this.y, this.z);
		
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

