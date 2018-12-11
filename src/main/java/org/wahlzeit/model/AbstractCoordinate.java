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

import java.util.Objects;
import java.util.HashMap;

public abstract class AbstractCoordinate implements Coordinate{
	
	
	/* Transform the Coordinate classes into value object classes */
	private final static HashMap<Integer,Coordinate> coordinateValObjMap = new HashMap();
	
	
	protected static boolean existsInCoordinateValObjMap(Coordinate c){
		return coordinateValObjMap.containsKey(c.hashCode());
	}
	
	/*
	 * @ return: returns true if created. If it already exists it returns false
	 */
	protected static boolean insertInCoordinateHashMap(Coordinate c){
		if(!existsInCoordinateValObjMap(c)){
			coordinateValObjMap.put(c.hashCode(), c);
			return true;
		} else{
			return false;
		}
	}
	
	protected static Coordinate getCoordinateInMap(int hashCode){
		Coordinate inMap = coordinateValObjMap.get(hashCode);
		if(inMap == null)
			throw new IllegalArgumentException("Coordinate in not in Map!");
		return inMap;
	}
	
	public abstract int hashCode();
	
	
	
	
	@Override
	public abstract CartesianCoordinate asCartesianCoordinate();
	
	@Override
	public double getCartesianDistance(Coordinate c) throws IllegalArgumentException,CoordinateException{
		CartesianCoordinate cc = this.asCartesianCoordinate();
		return cc.getCartesianDistance(c);
	}
	
	@Override
	public abstract SphericCoordinate asSphericCoordinate();
	
	@Override
	public double getCentralAngle(Coordinate c) throws IllegalArgumentException,CoordinateException{
		CartesianCoordinate cc = this.asCartesianCoordinate();
		return cc.getCentralAngle(c);
	}
	
	
	
	
	/*
	 * Even though isEqual should be theoraticly possible by "==" comparison, 
	 * in practicse it is difficult to avoid duplicates with a little calculation error.
	 * Therefore EVERY element would have to be compared with an epsilon error.
	 * Thats why the comparision isEqual is still kept.
	 */
	
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
