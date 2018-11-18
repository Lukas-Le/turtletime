/*
* Location
*
* version 1.0
*
* Date: 28.10.2018
*
* Copyright notice: AGPLv3
*/
package org.wahlzeit.model;
import org.wahlzeit.model.*;

public class Location {
	public Coordinate coordinate;
	
	/*
	 * A location must have a coordinate according to the class-diagram. Therefore no empty constructor is written.
	 * Still this code decides to accept it if the user sets it to null manually.
	 */
	
	/**
	 * @methodtype constructor
	 */
	public Location(Coordinate coordinate){
		this.coordinate = coordinate;
	}
	
	/**
	 * @methodtype boolean-query
	 */
	public boolean equals(Object obj){
		if(obj instanceof Location && this.coordinate != null){
			return this.coordinate.isEqual( ((Location)obj).coordinate);
		} else{
			return false;
		}
	}
	
	
	
	/**
	 * @methodtype set
	 */
	public void setCoordinate(Coordinate coordinate){
		this.coordinate = coordinate;
	}
	
	/**
	 * @methodtype get
	 */
	public Coordinate getCoordinate(){
		return coordinate;
	}
	
}
