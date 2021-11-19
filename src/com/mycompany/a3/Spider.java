package com.mycompany.a3;
import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

//Spider is a GameObject that extends the Moveable class
//Spider is the enemy of the ant and on collision with an ant decreases the ant's health
public class Spider extends Moveable {

	/* Spider()
	 * Creates a spider in a random location with a random speed between 5 and 10, with a size chosen between 10 and 50. 
	 * The spider is red and this color cannot be changed. The spider's size also cannot be changed after creation
	 * The Spider's heading is chosen randomly between 0 and 359
	*/
	public Spider(int width, int height) {
		//sets the spider's size randomly between 50 and 80
		super((new Random()).nextInt(30)+50);
		Random randNum = new Random();
		//Sets the spider location randomly location randomly between 1 and 999
		super.setLocation(randNum.nextFloat()*width+1,randNum.nextFloat()*height+1);
		
		//sets the spider's speed randomly between 140 and 150
		super.setSpeed(randNum.nextInt(150)+140);
		
		//the Spider's heading is chosen randomly between 0 and 359
		super.setHeading(randNum.nextInt(360));
		
		//sets the spiders color to red this cannot be changed later
		super.setColor(255,0,0);
		
	}
	
	
	//Spiders should not be able to change their color after creation
	@Override
	public void setColor(int r, int g, int b) 
	{}
	
	/*move()
	 * Randomly moves the spider left or right for every call to move() by changing the heading.
	 */
	@Override
	public void move(int width, int height, int elapsedTime)
	{
				//Choose a random number between 1 and 3
				Random randNum = new Random();
				int generatedRandom=1+randNum.nextInt(3);
				//if its 1 then set heading - 5 else set heading + 5
				if(generatedRandom == 1) {
					int newHeading = getHeading()-5;
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
					System.out.print("\n Adjusted heading by 5 to the left");
					}
				}
				else {
					super.setHeading(getHeading() + 5);
					int newHeading = getHeading()+5;
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
						System.out.print("\n Adjusted heading by 5 to the right");
						}
				}	
				
				super.move(width,height,elapsedTime);
				System.out.println("\nThe spider moved! To location: ("+Math.round((super.getLocation().getX()*10)/10)+" , "+Math.round((super.getLocation().getY()*10)/10) +") heading: "+super.getHeading());	
	}

	@Override
	public String toString() {
		return "Spider: " + super.toString();
	}


	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		//Draw the spider is an unfilled Isosceles triangle
		
		//Top point
		float x=(pCmpRelPrnt.getX()+super.getLocation().getX());
		float y=(pCmpRelPrnt.getY()+super.getLocation().getY() + super.getSize()/2);
		Point upperPoint =new Point(x,y);
		
		//lower left point
		x=(pCmpRelPrnt.getX()+super.getLocation().getX() - super.getSize()/2);
		y=(pCmpRelPrnt.getY()+super.getLocation().getY() - super.getSize()/2);
		Point lowerLeftPoint = new Point(x,y);
		
		//lower right point
		x= (pCmpRelPrnt.getX()+super.getLocation().getX() + super.getSize()/2 );
		y= (pCmpRelPrnt.getY()+super.getLocation().getY() - super.getSize()/2);
		Point lowerRightPoint= new Point(x,y);
		
		int xPoints[]=new int[] {(int)upperPoint.getX(),(int)lowerLeftPoint.getX(),(int)lowerRightPoint.getX()};
		int yPoints[]=new int[] {(int)upperPoint.getY(),(int)lowerLeftPoint.getY(),(int)lowerRightPoint.getY()};
		//number of points in a triangle is 3 hence numPoints =3
		int numPoints =3;
		
		g.setColor(getColor());
		g.drawPolygon(xPoints, yPoints, numPoints);
		
	}



	@Override
	public void handleCollision(ICollider collideObject) {
		// TODO Auto-generated method stub
		
	}
}
