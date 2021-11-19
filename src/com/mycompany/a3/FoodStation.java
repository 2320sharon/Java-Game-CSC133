package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

//FoodStation is used to hold the food that can be consumed by the ant
//FoodStation is fixed so it cannot change its size or location after being created
public class FoodStation extends Fixed {

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
	public FoodStation(int width, int height) {
//		Random randNum= new Random();
//		//Makes the FoodStation's size a random value between 60 and 80
		//Place the FoodStation in a random spot within the map
		//to prevent FoodStation's being placed on the map border place them within 999x999 square
		super((new Random()).nextInt(80)+20,((new Random()).nextFloat()* width)+1, ((new Random()).nextFloat()*height)+1);
		
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
	public void draw(Graphics g, Point pCmpRelPrnt) {
		//Draw the food station as a filled rectangle with text on top
		float x=(pCmpRelPrnt.getX()+super.getLocation().getX() - super.getSize()/2);
		float y=(pCmpRelPrnt.getY()+super.getLocation().getY() - super.getSize()/2);

		g.setColor(getColor());
		if (this.isSelected() == true)
			g.drawRect((int)x, (int)y, getSize(), getSize());
		else
			g.fillRect((int)x, (int)y, getSize(), getSize());
		
		//put the text on top of the food station
		//the text is black
		g.setColor(ColorUtil.BLACK);
		//text is in the middle of the flag and subtract the font size to get it in the proper location
		int xText= (int)(pCmpRelPrnt.getX()+super.getLocation().getX()-(g.getFont().getPixelSize() / 2));
		int yText=(int)(pCmpRelPrnt.getY()+super.getLocation().getY()- (g.getFont().getPixelSize() / 2));
		g.drawString(Integer.toString(this.getCapacity()), xText , yText -10);
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
