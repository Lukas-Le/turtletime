package org.wahlzeit.model.turtle;

import java.util.HashMap;
import java.util.Map;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.model.PhotoManager;
import org.wahlzeit.services.ObjectManager;

public class TurtleManager extends ObjectManager{
	
	//like in photoManager (which extends ObjectManager, too)
	protected Map<Integer, Turtle> turtleCache = new HashMap<Integer, Turtle>();
	protected Map<Integer, TurtleType> turtleTypeCache = new HashMap<Integer, TurtleType>();
	
	
	protected static final TurtleManager instance = new TurtleManager();
	
	public static final TurtleManager getInstance() {
		return instance;
	}
	
	public Turtle createTurtle(String turtleType){
		return new Turtle(new TurtleType(turtleType));
	}
}
