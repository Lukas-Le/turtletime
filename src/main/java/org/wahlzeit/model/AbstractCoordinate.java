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
	public abstract double getCartesianDistance(Coordinate c);
	
	@Override
	public abstract SphericCoordinate asSphericCoordinate();
	
	@Override
	public abstract double getCentralAngle(Coordinate c);
	
	@Override
	public abstract boolean isEqual(Coordinate c);
	
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
