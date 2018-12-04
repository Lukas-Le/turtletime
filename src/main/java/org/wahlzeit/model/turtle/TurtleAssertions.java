package org.wahlzeit.model.turtle;

public class TurtleAssertions {
	public static void assertNotNull(Object o) throws IllegalArgumentException{
		if(o == null)
			throw new IllegalArgumentException("Object must not be null");
	}
}
