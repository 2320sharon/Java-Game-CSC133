package com.mycompany.a4;
import java.util.ArrayList;

import com.codename1.charts.models.Point ;
import com.codename1.charts.util.ColorUtil ;
import com.codename1.ui.Graphics;
import com.codename1.ui.List;
import com.codename1.ui.Transform;


public abstract class GameObject  implements IDrawable, ICollider{
	private int color;
	private int size;
//	private Point location ;
	private Transform myRotate, myScale, myTranslate;
	ArrayList<ICollider> collidingList = new ArrayList<ICollider>();
	public ICollider lastCollidedObj;
	
	public GameObject(int size)
	{
		//initalize all the transform to identity matrices
		myRotate= Transform.makeIdentity();
		myScale= Transform.makeIdentity();
		myTranslate= Transform.makeIdentity();
		this.size=size;	
	}
	
	public void rotate(float degrees)
	{//pivot point (0,0) means the rotation will be applied about the screen origin
		myRotate.rotate((float)Math.toRadians(degrees), 0, 0);
	}
	
	public Transform getTranslate()
	{
		return myTranslate;
	}
	
	public Transform getRotate()
	{
		return myRotate;
	}
	
	public Transform getScale()
	{
		return myScale;
		
	}
	
	public void translate(float tx, float ty)
	{
		myTranslate.translate(tx,ty);
	}
	
	public void scale(float sx, float sy)
	{//scale is applied relative to the screen origin
		myScale.scale(sx, sy);
	}
	
	public abstract void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn);

	//Returns the location as a new Point
	public Point getLocation() {
		return (new Point(myTranslate.getTranslateX(),myTranslate.getTranslateY()));
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
