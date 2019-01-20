package org.wahlzeit.model.turtle;

import org.junit.*;

public class TurtleTypeTest {

	
	@Test
	public void testTurtleTypeConstructor(){
		TurtleType tt = new TurtleType("TerryPratchettTurtle");
		Assert.assertNotNull(tt);
		
	}
	
	
	@Test
	public void testInheritance(){
		TurtleType parent = new TurtleType("Turtle");
		TurtleType land = new TurtleType("Land Turtle");
		TurtleType sea = new TurtleType("Sea Turtle");
		
		parent.addChild(land);
		parent.addChild(sea);
		
		Assert.assertEquals(land.getParent(), sea.getParent());
		Assert.assertTrue(parent.isChild(land));
		Assert.assertTrue(parent.isChild(sea));
		
	}
}
