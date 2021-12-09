package com.mycompany.a4;
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
	public void  move(int width, int height, int elapsedTime, boolean isSpider)
	{
//		Point oldLocation= getLocation();
//		Point newLocation = new Point();
		//Since heading starts on the y axis where 90 normally is subtract the heading's value from 90 to obtain theta
		float theta = 90 - this.getHeading();
		double distance = this.getSpeed()*(elapsedTime/1000.0);
		double deltaY=distance*Math.sin(Math.toRadians(theta));
		double deltaX=distance*Math.cos(Math.toRadians(theta));
		
		
		if( isSpider)
		{
			//Ensure the new location is in bounds for the width and height of the world
			//1. check if x is going to exceed the rightmost direction  (right width)
			//2. check if x is going to exceed the leftmost direction (below 0)
			if(this.getLocation().getX()<0 )
			{
				System.out.print("Exceeded the X direction" + this.getLocation().getX());
				this.rotate(180);
				System.out.print("Changed heading to 180");
			}else if( this.getLocation().getX()>width*2 )
			{
				System.out.print("Exceeded the X direction >width("+width+") " + this.getLocation().getX());
				this.rotate(180);
				System.out.print("Changed heading to 180");
			}
			//1. check if y is going to exceed the topmost direction (below 0)
			//2. check if y is going to exceed the bottommost direction (above height)
			if(this.getLocation().getY()<0) 
			{
				System.out.print("Exceeded the Y direction < 0" + this.getLocation().getY());
				this.rotate(180);
				System.out.print("Changed heading to 180");

			}else if( this.getLocation().getY()>height*2)
			{
				System.out.print("Exceeded the Y direction > height("+height+") " + this.getLocation().getY());
				this.rotate(180);
				System.out.print("Changed heading to 180");
			}
			
			//Ensure the new location is in bounds for the 1000x1000 world
			//Check that your heading does not cause you to exceed the bounds in the x direction
			//1. check if x is going to exceed the rightmost direction
			//2. check if x is going to exceed the leftmost direction
//			if(this.getLocation().getX()<=0 && getHeading()<359 && getHeading()>180 || this.getLocation().getX()>=width*2 && getHeading()>0 && getHeading()<180 )
//			{
//				theta = 180 - heading;	//flips the theta's direction
//				deltaX=Math.cos(Math.toRadians(theta)*speed);
//				this.rotate(180);
//				this.translate((float)deltaX, (float)deltaY);
////				this.getLocation().setX(((float)(oldLocation.getX()+deltaX)));
//				
//			}
//			//Check that your heading does not cause you to exceed the bounds in the y direction
//			//1. check if y is going to exceed the topmost direction
//			//2. check if y is going to exceed the bottommost direction
//			if(this.getLocation().getY()<=0 && getHeading()<270 && getHeading()>90 || this.getLocation().getY()>height*2 && getHeading()>270 && getHeading()<90 )
//			{
//				theta = 180 -heading;	//flips the theta's direction
//				deltaY=Math.sin(Math.toRadians(theta)*speed);
//				this.rotate(180);
//				this.translate((float)deltaX, (float)deltaY);
////				this.getLocation().setY(((float)(oldLocation.getY()+deltaY)));
//			}			
		}
		else
		{
			//Translate the ant to this new location
			this.translate((float)deltaX, (float)deltaY);
			System.out.println("\n Translated by: "+ deltaX + ", "+ deltaY);
		}
		
		
	}

	@Override
	public String toString() {
		return super.toString()+" speed=" + speed + " heading=" + heading ;
	}
	
	

}
