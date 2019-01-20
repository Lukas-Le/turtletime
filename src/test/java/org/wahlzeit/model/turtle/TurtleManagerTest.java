package org.wahlzeit.model.turtle;

import org.junit.*;
public class TurtleManagerTest {

	TurtleManager turtleManager;
	
	@Before
	public void init(){
		turtleManager = TurtleManager.getInstance();
		Assert.assertNotNull(turtleManager);
	}
	
	//check whether 2 created Turtle have the same type
	@Test
	public void createTurtleTest(){
		Turtle t = turtleManager.createTurtle("Sea Turtle");
		Turtle t2 = turtleManager.createTurtle("Sea Turtle");
		Assert.assertNotNull(t);
		Assert.assertNotNull(t2);
		TurtleType tt1 = t.getType();
		TurtleType tt2 = t2.getType();
		Assert.assertNotNull(tt1);
		Assert.assertNotNull(tt2);
		Assert.assertEquals(tt1.type, tt2.type);
	}
	
	@Test
	public void createTurtleTestWithDifferentTypes(){
		Turtle t = turtleManager.createTurtle("Sea Turtle");
		Turtle t2 = turtleManager.createTurtle("Land Turtle");
		Assert.assertNotNull(t);
		Assert.assertNotNull(t2);
		TurtleType tt1 = t.getType();
		TurtleType tt2 = t2.getType();
		Assert.assertNotNull(tt1);
		Assert.assertNotNull(tt2);
		Assert.assertNotEquals(tt1.type, tt2.type);
	}
	
}
