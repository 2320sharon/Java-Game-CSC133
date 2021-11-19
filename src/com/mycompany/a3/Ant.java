/**
 * 
 */
package com.mycompany.a3;
import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

/**
 * Ant class
 * An ant is controlled by the player via the steer method from its interface Isteerable
 * The ant has a color of blue and a size of 10, with a max speed of 10, and a food consumption rate of 2 on creation.

 */
public class Ant extends Moveable implements Isteerable , ICollider {

	private static Ant ant;					//private ant to ensure singleton
	private int maximumSpeed;			//the max speed the ant can move CANNOT be modified
	private int foodLevel;				//the amount of food the ant currently has
	private int foodConsumptionRate;	//the amount of food the ant consumes per clock tick
	private int lastFlagReached;		//the highest sequential flag the ant has reached
	private int healthLevel;			//the ant's current health out of 10
	private GameWorld gw;
	
	/**Ant()
	 * Creates an ant with a color of blue, size= 10, at the  same location as the first flag, maxSpeed=10, initial speed = 5,
	 *  initial health level= 10, initial heading= 0, intital food level= 50, and a food consumption rate =2
	 * 
	 * The ant cannot change size, maxspeed, or food consumption rate after creation.
	 * 
	 * @param  initialLocation :a Point containing the location of the first flag
	 */
	private Ant(Point initialLocation, GameWorld gameworld) {
		//Set the ant's initial heading to 0 and size to 90
		super(90);
		super.setHeading(0);
		//set the Ant's color to blue
		super.setColor(0, 0, 255);
		
		//set the Ant's initial location to that of the location passed in which is the first flag
		super.setLocation(initialLocation.getX(),initialLocation.getX());
		setLastFlagReached(1);
		
		//set the ant's  initial speed to 100
		super.setSpeed(100);
		
		//set the ant's  Maximum speed to 100
		setMaximumSpeed(100);
		
		//start the ant off with health of 10
		setHealthLevel(10);
		
		//the ant starts off with 50 food 
		setFoodLevel(50);
		
		//the ant consumes 2 peices of food each clock tick
		setFoodConsumptionRate(2);
		
		//Need a copy of the gameworld object to use for handling collisions
		this.gw=gameworld;
	}
	
	/**getAnt(Point initialLocation)
	 *  If an instance of Ant does not exist creates an ant with a color of blue, size= 10, at the
	 *  same location as the first flag, maxSpeed=10, initial speed = 5,
	 *  initial health level= 10, initial heading= 0, intital food level= 50, and a food consumption rate =2.
	 *  If an instance of Ant does exist returns the current instance of ant.
	 * 
	 * The ant cannot change size, maxspeed, or food consumption rate after creation.
	 * 
	 * @param  initialLocation :a Point containing the location of the first flag
	 */
	public static Ant getAnt(Point initialLocation, GameWorld gw) {
		if(ant == null )
		{
			ant =new Ant(initialLocation,gw);
		}
		return ant;
	}
	
	/**getAnt()
	 *  If an instance of Ant does not exist returns null. A call to getAnt(Point initialLocation) must be
	 *  made before calling this function as ant must be initialized to the same location as the first flag.
	 * 
	 * The ant cannot change size, maxspeed, or food consumption rate after creation.
	 */
	public static Ant getAnt() {
		if(ant == null ) //call to Ant getAnt(Point initialLocation) must be made before calling getAnt()
		{
			return null;
		}
		return ant;
	}
	
	public static Ant destroyAnt() {
		ant = null; //destroy the ant
		return ant;
	}
	
	public int getMaximumSpeed() {
		return maximumSpeed;
	}

	private void setMaximumSpeed(int maximumSpeed) {
		this.maximumSpeed = maximumSpeed;
	}

	public int getFoodLevel() {
		return foodLevel;
	}

	public void setFoodLevel(int foodLvl) {
		this.foodLevel = foodLvl;
	}

	public int getFoodConsumptionRate() {
		return foodConsumptionRate;
	}

	private void setFoodConsumptionRate(int foodConsumptionRate) {
		this.foodConsumptionRate = foodConsumptionRate;
	}

	public int getLastFlagReached() {
		return lastFlagReached;
	}

	public void setLastFlagReached(int lastFlagReached) {
		this.lastFlagReached = lastFlagReached;
	}

	public int getHealthLevel() {
		return healthLevel;
	}

	public void setHealthLevel(int healthLvl) {
		this.healthLevel = healthLvl;
	}

	
	/**steer(char direction)
	* Changes the heading of the ant by 5 towards the left or right direction.
	* 
	*  This method keeps the heading within 0 to 360 degrees and adjusts the heading according to the direction given.
	*
	* @param  :direction 'l': adjusts heading to left by 5 degrees or 'r' adjusts heading to right by 5 degrees
	*/
	@Override
	public void steer(char direction) {
		if (direction == 'l') //adjust the heading so its going left
		{
			int newHeading = getHeading()-15;
			if(newHeading <= 0 )
			{
				//forces the heading to always be between 0 to 360 degrees
				int adjustedHeading= 360+newHeading;
				System.out.print("\nYou adjusted too far to the left. Adjusting course from "+getHeading() +" to "+ adjustedHeading);
				setHeading(adjustedHeading);
			}
			else
			{
			setHeading(newHeading);
			System.out.print("\n Adjusted heading by 15 to the left");
			}
		}
		if (direction == 'r') 
		{
			int newHeading = getHeading()+15;
		//check if new heading will go outside of 0 to 360 degree bound
			if (newHeading >= 360)
			{
				//forces the heading to always be between 0 to 360 degrees
				int adjustedHeading= newHeading-360;
				System.out.print("\nYou adjusted too far to the right. Adjusting course from "+getHeading() +" to "+ adjustedHeading);
				setHeading(adjustedHeading);
			}
			else
			{
			setHeading(newHeading);
			System.out.print("\n Adjusted heading by 15 to the right");
			}
		}
		}
	
	/**move()
	* Moves the ant to a new location based on the ant's current heading and speed.
	*/
	@Override
	public void move(int width, int height, int elapsedTime) {
		this.setHeading(getHeading());
		super.move(width,height,elapsedTime);
		System.out.println("\nThe ant moved! To location: ("+Math.round((super.getLocation().getX()*10)/10)+" , "+Math.round((super.getLocation().getY()*10)/10) +") heading: "+super.getHeading());
	}
	
	/**setSpeed(int speed)
	 * Creates an ant with a color of blue, size= 10, at the  same location as the first flag, maxSpeed=10, initial speed = 5,
	 *  initial health level= 10, initial heading= 0, intital food level= 50, and a food consumption rate =2
	 * 
	 * The ant cannot change size, maxspeed, or food consumption rate after creation.
	 * 
	 * @param  speed :int indicating the speed the ant should change to
	 */
	@Override
	public void setSpeed(int speed) 
	{
		//speed can only be betweem 0 and 10
		if (speed < 0)
			speed =0 ;
		else if(speed > 100)
			speed =100 ;
		
		//maxSpeed is based on the ant's current health level
		int maxSpeed=(maximumSpeed - (5*(10-getHealthLevel())));
		//The ant has 0 speed if either its health or food level is 0
		if (getHealthLevel()==0 || getFoodLevel()==0 )
		{
			super.setSpeed(0);
			System.out.print("\n The ant's speed is 0 due to 0 health or food level");
		}
		else if (super.getSpeed() < speed ) //if the new speed is more than the old speed
		{
			if (getHealthLevel() == 10)
			{
				System.out.print("\n Ant is at full health and can go to max speed 10" );
				super.setSpeed(speed);
			}
			else if(getHealthLevel() < 10 && getHealthLevel() > 0 && speed < maxSpeed)	
			{
				//the ant's current speed is less than the health level and the new speed is <= to health level then it can increase
				super.setSpeed(speed);
				System.out.print("\n The ant's speed was increased to "+ super.getSpeed());
			}
//			else if(getHealthLevel() < 10 && getHealthLevel() > 0 && speed < getSpeed())	//the speed cannot be more than the healthLevel
//			{
//				super.setSpeed(getSpeed()-10 );
//				System.out.print("\n The ant's speed was decreased due to health level");
//			}
			//if none of these are true then do not change the speed
			
		}
		else if(super.getSpeed() > speed ) //if the new speed is less than the old speed
		{
			if (speed >= maxSpeed)
			{
				super.setSpeed(maxSpeed);
				System.out.print("\n The ant's speed was decreased due to health level");
			}
			else if (speed < maxSpeed)// the ant's new speed is < health so it can slow down
			{
				super.setSpeed(speed);
				System.out.print("\n The ant's speed was decreased to "+ super.getSpeed());
			}
			
		}
		else if (getSpeed() == getMaximumSpeed())
		{
			System.out.print("\n The ant is already at max speed"+getSpeed()+" and cannot go any faster!");
			super.setSpeed(getMaximumSpeed());
		}
		else if (speed == 0)
		{
			System.out.print("\n The ant is already at "+getSpeed()+" speed and cannot brake anymore!");
		}
		else
		{
			System.out.print("\n SPEED "+speed);
		}
	}

	@Override
	public String toString() {
		return "Ant: "+super.toString() + " MaximumSpeed=" + getMaximumSpeed() + " FoodLevel=" + getFoodLevel()
				+ " FoodConsumptionRate=" + getFoodConsumptionRate() + " LastFlagReached="
				+ getLastFlagReached() +" HealthLevel=" + getHealthLevel();
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		//Draw a black filled circle for ant 
		float x=(pCmpRelPrnt.getX()+super.getLocation().getX() - super.getSize()/2);
		float y=(pCmpRelPrnt.getY()+super.getLocation().getY() - super.getSize()/2);
		int r = super.getSize() / 2; // size indicates diameter, so divide it by 2 to get the radius		
		g.setColor(getColor());
		g.fillArc((int)x,(int)y, r*2, r*2, 0, 360);
		
	}



	@Override
	public void handleCollision(ICollider collideObject) {
		if (collideObject instanceof Spider)
		{
			gw.collideSpider();
		}
		else if (collideObject instanceof FoodStation)
		{
			FoodStation food = (FoodStation) collideObject;
			gw.collideFoodStation(food);
		}
		else if (collideObject instanceof Flag)
		{
			Flag flag = (Flag) collideObject;
			gw.collideFlag(flag);
		}
		
	}
	
	
	

}
