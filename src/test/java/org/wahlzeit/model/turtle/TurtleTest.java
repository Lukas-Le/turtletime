package org.wahlzeit.model.turtle;

import org.junit.*;

public class TurtleTest {
	
	@Test
	public void testTurtleConstructor(){
		TurtleType tt = new TurtleType("Magic Turtle");
		Assert.assertNotNull(tt);
		Turtle t = new Turtle(tt);
		Assert.assertNotNull(t);
		Assert.assertNotNull(t.getType());
	}
	
	@Test
	public void testSetterAndGetter(){
		Turtle t = new Turtle(new TurtleType("Fancy Turtle"));
		Assert.assertNotNull(t);
		
		t.setAge(15);
		t.setBreed("ancient turtle");
		t.setColor("grey");
		
		Assert.assertEquals(t.getBreed(), "ancient turtle");
		Assert.assertEquals(t.getAge(), 15);
		Assert.assertEquals(t.getColor(), "grey");
		
	}
}
