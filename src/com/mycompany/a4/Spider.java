package com.mycompany.a4;
import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

//Spider is a GameObject that extends the Moveable class
//Spider is the enemy of the ant and on collision with an ant decreases the ant's health
public class Spider extends Moveable {

	//local coordinates
	private Point top, bottomLeft, bottomRight;
	/* Spider()
	 * Creates a spider in a random location with a random speed between 5 and 10, with a size chosen between 10 and 50. 
	 * The spider is red and this color cannot be changed. The spider's size also cannot be changed after creation
	 * The Spider's heading is chosen randomly between 0 and 359
	*/
	public Spider(int base, int height) {
		//@TODO May have to change this logic since size and base and height are related
		//may not have to change since we can just scale the triangle to the correct size
		super((new Random()).nextInt(10)+20);
		//scale the spider here based on the size
		
		//Calculates the Spider's points based on the height and base
		top=new Point(0,(float) ((float)height/2.0));
		bottomLeft = new Point((float)(-base/2),(float)(-height/2));
		bottomRight = new Point((float)(base/2),(float)(-height/2));
		
		Random randNum = new Random();
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
	public void move(int width, int height, int elapsedTime,boolean isSpider)
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
						//@TODO might be broken
						this.rotate(-5);
					}
					else
					{
					setHeading(newHeading);
					//@TODO might be broken
					this.rotate(-5);
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
							//@TODO might be broken
							this.rotate(5);
						}
						else
						{
						setHeading(newHeading);
						//@TODO might be broken
						this.rotate(5);
						System.out.print("\n Adjusted heading by 5 to the right");
						}
				}	
				
				super.move(width,height,elapsedTime,true);
				System.out.println("\nThe spider moved! To location: ("+Math.round((super.getLocation().getX()*10)/10)+" , "+Math.round((super.getLocation().getY()*10)/10) +") heading: "+super.getHeading());	
	}

	@Override
	public String toString() {
		return "Spider: " + super.toString()+"\n top"+ top + " bottomLeft: "+ "bottomRight: "+ bottomRight;
	}


	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		//Draw the spider is an unfilled Isosceles triangle
		g.setColor(super.getColor());
		
		//Append the spider's LT's to the xform in the Graphics object. But first move the drawing coordinates
		// so that the local origin coincides with the screen origin. After the LT's are applied, move the drawing
		//coordinates back
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		//Make a copy of the gXform
		Transform gOrigXform = gXform.copy();
		
		//Since java is row majored. This operation which is normally last in row majored is applied first
		gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
		gXform.translate(getTranslate().getTranslateX(), getTranslate().getTranslateY());
		gXform.concatenate(getRotate());
		gXform.scale(getScale().getScaleX(),getScale().getScaleY());
		//Translate back to origin
		gXform.translate(-getTranslate().getTranslateX(), -getTranslate().getTranslateY());
		
		//reset the transform
		g.setTransform(gXform);
		
		//Top point
		float x=(pCmpRelPrnt.getX()+top.getX());
		float y=(pCmpRelPrnt.getY()+top.getY());
		Point upperPoint =new Point(x,y);
		System.out.println("\nSpider Top: "+upperPoint.getX() + " , "+ upperPoint.getY() );
		
		//lower left point
		x=(pCmpRelPrnt.getX()+bottomLeft.getX() );
		y=(pCmpRelPrnt.getY()+bottomLeft.getY() );
		Point lowerLeftPoint = new Point(x,y);
		System.out.println("\nSpider lowerLeftPoint: "+lowerLeftPoint.getX() + " , "+ lowerLeftPoint.getY() );
		
		//lower right point
		x= (pCmpRelPrnt.getX()+bottomRight.getX());
		y= (pCmpRelPrnt.getY()+bottomRight.getY());
		Point lowerRightPoint= new Point(x,y);
		System.out.println("\nSpider lowerRightPoint: "+lowerRightPoint.getX() + " , "+ lowerRightPoint.getY()  );
		
		int xPoints[]=new int[] {(int)upperPoint.getX(),(int)lowerLeftPoint.getX(),(int)lowerRightPoint.getX()};
		int yPoints[]=new int[] {(int)upperPoint.getY(),(int)lowerLeftPoint.getY(),(int)lowerRightPoint.getY()};
		//number of points in a triangle is 3 hence numPoints =3
		int numPoints =3;
		
		g.drawPolygon(xPoints, yPoints, numPoints);
		
		//reset the transform to its original form
		g.setTransform(gOrigXform);
		
	}

	@Override
	public void handleCollision(ICollider collideObject) {
	}
}
