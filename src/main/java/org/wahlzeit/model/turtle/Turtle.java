package org.wahlzeit.model.turtle;

public class Turtle {
	
	private TurtleType type = null;
	
	private String color;
	private String breed;
	private int age;
	
	private int id;
	private static int num_ids = 0;
	public Turtle(TurtleType type){
		this.id = num_ids++;
		this.type = type;
	}
	
	public final TurtleManager turtleManager = TurtleManager.getInstance();
	
	/**
	 * @methodtype set
	 */
	public void setColor(String color){
		TurtleAssertions.assertNotNull(color);
		this.color = color;
	}
	
	/**
	 * @methodtype set
	 */
	public void setBreed(String breed){
		TurtleAssertions.assertNotNull(breed);
		this.breed = breed;
	}
	
	/**
	 * @methodtype set
	 */
	public void setAge(int age){
		if(age < 0)
			throw new IllegalArgumentException("age must be >= 0");
		this.age = age;
	}
	
	/**
	 * @methodtype get
	 */
	public String getColor(){
		return color;
	}
	
	/**
	 * @methodtype get
	 */
	public String getBreed(){
		return breed;
	}
	
	/**
	 * @methodtype get
	 */
	public int getAge(){
		return age;
	}
	
	/**
	 * @methodtype get
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * @methodtype get
	 */
	public TurtleType getType(){
		return type;
	}
}
