package org.wahlzeit.model;

public interface Coordinate {
	
	public CartesianCoordinate asCartesianCoordinate();
	public double getCartesianDistance(Coordinate c) throws IllegalArgumentException,CoordinateException;
	public SphericCoordinate asSphericCoordinate();
	public double getCentralAngle(Coordinate c) throws IllegalArgumentException,CoordinateException;
	public boolean isEqual(Coordinate c);
	
	public boolean equals(Object o);
	
	
}