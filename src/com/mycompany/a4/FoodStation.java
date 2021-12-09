package com.mycompany.a4;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

//FoodStation is used to hold the food that can be consumed by the ant
//FoodStation is fixed so it cannot change its size or location after being created
public class FoodStation extends Fixed {
	private Point lowerLeft;
	//Capacity is the amount of food the foodstation holds
	private int capacity;
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/* FoodStation()
	 * Creates a FoodStation in a random location within the game's map, sets the color to green, sets the FoodStation's size a random
	 * value between 10 and 50 and makes the capacity proportional to the size.
	 * The food station's size and location cannot be changed after creation.
	 */
	public FoodStation() {
//		//Makes the FoodStation's size a random value between 20 and 100
		super((new Random()).nextInt(80)+20);
		//Initialize local point
		int size = super.getSize();
		lowerLeft=new Point(-size/2, -size/2);	
		
		//The amount of food held by FoodStation is proportional to the size 
		setCapacity(this.getSize());
		//Sets the FoodStation's color to green
		super.setColor(0, 255, 0);
	}
	
	@Override
	public String toString() 
	{
		return("FoodStation: "+ super.toString()+" capacity= "+getCapacity());
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		//Draw the food station as a filled rectangle with text on top
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
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
				
		//Set the transformation using transformed gXform
		g.setTransform(gXform);
		
		//Draw the rectangle for the food station
		float x=(pCmpRelPrnt.getX()+lowerLeft.getX() );
		float y=(pCmpRelPrnt.getY()+lowerLeft.getY() );
		
		if (this.isSelected() == true)
			g.drawRect((int)x, (int)y, getSize(), getSize());
		else
			g.fillRect((int)x, (int)y, getSize(), getSize());
		
		g.getTransform(gXform); //loads gXform into g
		//Now do transformation on the text
		gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());		//LT part 2		
		gXform.scale(1,-1);											//mirror the text
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());		//LT part 1

		//Set the transformation so we can write the text in the transformed form
		g.setTransform(gXform);
		
		//put the text on top of the food station
		//the text is black
		g.setColor(ColorUtil.BLACK);
//		g.drawString(Integer.toString(this.getCapacity()),(int)(lowerLeft.getX())+270,(int)(lowerLeft.getY()+120));
		g.drawString(Integer.toString(this.getCapacity()),(int)(x)+this.getSize()/5,(int)(y)+this.getSize()/4 +5);
		
		//reset the transform to its original form
		g.setTransform(gOrigXform);
		
	}

	@Override
	public boolean contains(Point pPtrRelParent, Point pCmpRelParent) {
		//Pointer's Coordinates
		int pointerX=(int)pPtrRelParent.getX();
		int pointerY=(int)pPtrRelParent.getY();
		
		//location of foodstation relative to mapview
		int xLoc=(int)(pCmpRelParent.getX() + this.getLocation().getX());
		int yLoc=(int)(pCmpRelParent.getY() + this.getLocation().getY());
		
		System.out.println("Pointer is at: "+pointerX + " , "+pointerY );
		System.out.println("FoodStation "+this.getCapacity()+ " is at: "+xLoc + " , "+yLoc );
		
		//is the pointer within the foodstation
		if((((pointerX >= xLoc) && (pointerX <= xLoc + this.getSize()/2)) || ((pointerX < xLoc) && (pointerX >= xLoc - this.getSize()/2) ))  && (((pointerY < yLoc) && (pointerY >= yLoc - this.getSize()/2) )||(pointerY>= yLoc) && (pointerY <= yLoc + this.getSize()/2)) )
			return true;
		
		return false;
	}

	@Override
	public void handleCollision(ICollider collideObject) {
		// TODO Auto-generated method stub
		
	}
	
	

}
