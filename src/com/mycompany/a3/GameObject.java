package com.mycompany.a3;
import java.util.ArrayList;

import com.codename1.charts.models.Point ;
import com.codename1.charts.util.ColorUtil ;
import com.codename1.ui.Graphics;
import com.codename1.ui.List;


public abstract class GameObject  implements IDrawable, ICollider{
	private int color;
	private int size;
	private Point location ;
	ArrayList<ICollider> collidingList = new ArrayList<ICollider>();
	public ICollider lastCollidedObj;
	
	public GameObject(int size)
	{
		this.size=size;	
	}
	public abstract void draw(Graphics g, Point pCmpRelPrnt);

	//Returns the location as a new Point
	public Point getLocation() {
		return (new Point(location.getX(),location.getY()));
	}
	
    public void setLocation(float x, float y)
	{ 
		this.location=new Point(x,y);
	}
    
    public ArrayList<ICollider> getCollidingList(){
    	return collidingList;
    }
	
	public int getSize() {return size;}
	public int getColor() {return color;}
	public void setColor(int red, int green, int blue) { this.color=ColorUtil.rgb(red, green, blue);}

	/*
	 * Returns a string representation of the GameObject including the color,location, and size.
	 */
	public String toString() {
		String colorString = " Color: " + "[" + ColorUtil.red(getColor()) + ","
				+ ColorUtil.green(getColor()) + ","
				+ ColorUtil.blue(getColor()) + "] ";
		float locationX=Math.round((getLocation().getX()*10)/10);
		float locationY=Math.round((getLocation().getY()*10)/10);
		String locationString=" Location: ("+locationX+" , "+locationY+" )";
		String sizeString=" Size: "+getSize();
		String fullString=locationString+colorString+sizeString;
		return fullString;
	}
	
	@Override
	public boolean collidesWith(ICollider collideObject) {
		float RightmostObject1=this.getLocation().getX()+(this.getSize()/2);
		float LeftmostObject1=this.getLocation().getX()-(this.getSize()/2);
		float TopmostObject1=this.getLocation().getY()+(this.getSize()/2);
		float BottommostObject1=this.getLocation().getY()-(this.getSize()/2);
		
		GameObject gameCollideObject= (GameObject) collideObject;
		
		float RightmostObject2= gameCollideObject.getLocation().getX()+(gameCollideObject.getSize()/2);
		float LeftmostObject2=gameCollideObject.getLocation().getX()-(gameCollideObject.getSize()/2);
		float TopmostObject2=gameCollideObject.getLocation().getY()+(gameCollideObject.getSize()/2);
		float BottommostObject2=gameCollideObject.getLocation().getY()-(gameCollideObject.getSize()/2);
		
		
//		if((RightmostObject1 < LeftmostObject2 || LeftmostObject1 > RightmostObject2 ) && ((TopmostObject2<BottommostObject1 || TopmostObject1 < BottommostObject2)))
		if(RightmostObject1 < LeftmostObject2 || LeftmostObject1 > RightmostObject2 ) 
		{
			return false;
		}
		else if (TopmostObject2<BottommostObject1 || TopmostObject1 < BottommostObject2)
		{
			return false;
		}
		else	//if neither of the previous conditions are true then the objects are not colliding
		{
			return true;
		}
		
	}

}
