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
		TurtleType northSea = new TurtleType("North Sea Turtle");
		
		parent.addChild(land);
		parent.addChild(sea);
		
		sea.addChild(northSea);
		
		Assert.assertEquals(land.getParent(), sea.getParent());
		Assert.assertTrue(parent.isChild(land));
		Assert.assertTrue(parent.isChild(sea));
		
		
		Assert.assertTrue(land.isSubtype(parent));
		Assert.assertTrue(sea.isSubtype(parent));
		Assert.assertTrue(parent.isSubtype(parent));
		Assert.assertTrue(northSea.isSubtype(sea));
		Assert.assertTrue(northSea.isSubtype(parent));
		
	}
}
