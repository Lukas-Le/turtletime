package org.wahlzeit.model.turtle;

import java.awt.List;
import java.util.HashSet;
import java.util.Set;

public class TurtleType {
	
	public String type;
	
	private TurtleType parent = null;
	private Set<TurtleType> children = new HashSet<TurtleType>();
	
	public TurtleType(String type){
		this.type = type;
	}
	
	
	
	public void setParent(TurtleType parent){
		if(this.parent != null && this.parent.type.compareTo(parent.type) == 0)
			throw new IllegalArgumentException("no multi inheritance allowed");
		this.parent = parent;
	}
	
	public TurtleType getParent(){
		return parent;
	}
	
	public void addChild(TurtleType t){
		children.add(t);
		t.setParent(this);
	}
	
	public boolean isSubtype(TurtleType t){
		TurtleType p = this;
		while(p != null){
			if(p.type.compareTo(t.type) == 0)
				return true;
			p = p.parent;
		}
		return false;
	}
	
	public boolean isChild(TurtleType t){
		setParent(t);
		return children.contains(t);
	}

}
