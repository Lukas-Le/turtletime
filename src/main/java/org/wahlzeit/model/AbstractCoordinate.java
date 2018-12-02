/*
* AbstractCoordinate
*
* version 1.0
*
* Date: 28.10.2018
*
* Copyright notice: AGPLv3
*/


package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate{
	
	
	@Override
	public abstract CartesianCoordinate asCartesianCoordinate();
	
	@Override
	public double getCartesianDistance(Coordinate c){
		CartesianCoordinate cc = this.asCartesianCoordinate();
		return cc.getCartesianDistance(c);
	}
	
	@Override
	public abstract SphericCoordinate asSphericCoordinate();
	
	@Override
	public double getCentralAngle(Coordinate c){
		CartesianCoordinate cc = this.asCartesianCoordinate();
		return cc.getCentralAngle(c);
	}
	
	@Override
	public boolean isEqual(Coordinate c){
		CartesianCoordinate cc = this.asCartesianCoordinate();
		return cc.isEqual(c);
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if(o instanceof Coordinate){
			return isEqual((Coordinate) o);
		}
		return false;
	}
	
}	
