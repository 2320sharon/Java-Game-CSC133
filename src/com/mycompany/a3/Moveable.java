package com.mycompany.a3;
import com.codename1.charts.models.Point ;				//used for moving location's of GameObjects

/*abstract class Moveable extends GameObject
 * This class inherits from GameObject.
 * Used for objects that can move.
 */
public abstract class Moveable extends GameObject {
	private int speed;			//the current speed at which the object is moving
	private int heading;		//the current heading (between 0 - 360) at which the object is pointed
	
	//Pass the size of the GameObject to the parent gameobject
	Moveable(int size)
	{
		super(size);
	}
	
	public void setHeading(int heading) 
	{this.heading=heading;}
	
	public int getHeading() 
	{return heading;}
	
	public void setSpeed(int speed) 
	{this.speed=speed;}
	
	public int getSpeed() 
	{return speed;}
	
	/* move()
	 * Used to move the object within the confines of the world.
	 * 
	 * Move() checks to make sure the object's new location does not exceed the bounds of the world and adjusts the object's location if it does. 
	 * */
	public void  move(int width, int height, int elapsedTime)
	{
		Point oldLocation= getLocation();
		Point newLocation = new Point();
		//Since heading starts on the y axis where 90 normally is subtract the heading's value from 90 to obtain theta
		float theta = 90 - this.getHeading();
		double distance = this.getSpeed()*(elapsedTime/1000.0);
		double deltaY=distance*Math.sin(Math.toRadians(theta));
		double deltaX=distance*Math.cos(Math.toRadians(theta));
		
		//update the new location and convert the double to float
		newLocation.setX(((float)(oldLocation.getX()+deltaX)));
		newLocation.setY(((float)(oldLocation.getY()+deltaY)));
		
		//Ensure the new location is in bounds for the width and height of the world
		//1. check if x is going to exceed the rightmost direction  (right width)
		//2. check if x is going to exceed the leftmost direction (below 0)
		if(newLocation.getX()-(this.getSize()/2)<0 )
		{
			newLocation.setX(this.getSize()/2);	
		}else if( newLocation.getX()+(this.getSize()/2)>width )
		{
			newLocation.setX(width-this.getSize()/2);	//puts the center on the border of the world
		}
		//1. check if y is going to exceed the topmost direction (below 0)
		//2. check if y is going to exceed the bottommost direction (above height)
		if(newLocation.getY()<0) 
		{
			newLocation.setY(this.getSize()/2);
		}else if( newLocation.getY()+(this.getSize()/2)>height)
		{
			newLocation.setY(height-this.getSize()/2);
		}
		//Officially set the location
		setLocation((float)newLocation.getX(),(float)newLocation.getY());
		
	}

	@Override
	public String toString() {
		return super.toString()+" speed=" + speed + " heading=" + heading ;
	}
	
	

}
