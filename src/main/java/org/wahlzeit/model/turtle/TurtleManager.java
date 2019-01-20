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
	protected Map<String, TurtleType> turtleTypeCache = new HashMap<String, TurtleType>();
	
	
	protected static final TurtleManager instance = new TurtleManager();
	
	public static final TurtleManager getInstance() {
		return instance;
	}
	
	public Turtle createTurtle(String turtleType){
		TurtleType tt;
		if(turtleTypeCache.containsKey(turtleType)){
			tt = turtleTypeCache.get(turtleType);
		}else{
			tt = new TurtleType(turtleType);
			turtleTypeCache.put(turtleType,tt);
		}
		Turtle t = new Turtle(tt);
		turtleCache.put(t.getId(), t);
		return t;
	}
	
	public TurtleType getTurtleType(String type){
		if(turtleTypeCache.containsKey(type)){
			return turtleTypeCache.get(type);
		}
		return null;
	}
}
