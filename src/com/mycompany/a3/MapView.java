package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {

	private Game game;					//game object needed to pause in the gameworld
	private GameWorld gw;				//gameworld contains the move methods 
	private boolean selectAllowed = false;	//Assumed nothing is able to be selected by default
	public MapView(Game game) {
		setLayout(new FlowLayout());
		getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(255,0,0)));//give container red border
		this.game=game;
	}
	
	public void setSelectAllowed(boolean select)
	{
		selectAllowed=select;
	}
	
	//only allowed to select objects when the game is paused
	public boolean getSelectAllowed()
	{
		return selectAllowed;
	}
	
	public MapView(Game game,GameWorld gw ) {
		setLayout(new FlowLayout());
		getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(255,0,0)));//give container red border
		this.game=game;
		this.gw=gw;
	}

	//Event fired when the pointer is pressed
	@Override
	public void pointerPressed(int x, int y) {
		if(getSelectAllowed() == true)
		{
			System.out.println("Pointer event fired at spot "+x+"  "+y);
			//new location where pointer pressed where the object will be moved to
			int newX= x- this.getAbsoluteX();
			int newY = y-this.getAbsoluteY();
			Point newPoint= new Point(newX,newY);
			
			//Update the pointer Coordinates to be relative to the parent component
			x=x-getParent().getAbsoluteX();
			y=y-getParent().getAbsoluteY();
			Point pPtrRelParent = new Point(x,y);
			Point pPtrCmpParent = new Point(getX(),getY());
//			Fixed chosenObject = null;
			
			//If the position button has been selected and an object as been selected.
			//this means this pointer pressed for was for the new location
			IIterator it = gw.getCollection().getIterator();
			while(it.hasNext())
			{
				GameObject go= it.getNext();
				if(go instanceof Fixed)
				{
					Fixed fixedObject = (Fixed)go;
					if(fixedObject.isSelected() && gw.getPosition() )
					{
						System.out.println();
						System.out.println("Object is selected about about to move "+fixedObject);
						System.out.println();
						fixedObject.setLocation(newPoint.getX(), newPoint.getY());
						gw.setPosition(false);//reset position to false now that we have moved the object
						fixedObject.setSelected(false);	//reset the selection
						this.repaint();
					}
				}
			}
			
			
			//check if mouse pointer is in another selectable object
			it = gw.getCollection().getIterator();
			while(it.hasNext())
			{
				GameObject go= it.getNext();
				if(go instanceof Fixed)
				{
					Fixed fixedObject = (Fixed)go;
					if(fixedObject.contains(pPtrRelParent,pPtrCmpParent) )
					{
						fixedObject.setSelected(true);
						System.out.println();
						System.out.println("setSelected "+fixedObject);
						System.out.println();
					}
					else	//unselect all the other objects
					{
						fixedObject.setSelected(false);
						System.out.println("unSelect "+fixedObject);
					}
				}
			}
			
		repaint();	//draw the updated items on screen	
		}
		else
		{
			//reset all objects to unselected and turns off position if pause is not enabled
			System.out.println();
			System.out.print("Pointer pressed fired. But pause mode was not enabled");
			System.out.println();
			IIterator it = gw.getCollection().getIterator();
			while(it.hasNext())
			{
				GameObject go= it.getNext();
				if(go instanceof Fixed)
				{
					Fixed fixedObject = (Fixed)go;
						fixedObject.setSelected(false);
				}
			}
		}
		  
		repaint();	//draw the updated items on screen
	}
	
	//Prepares the game to be put back into playmode
	//1. Unselects all the fixed objects
	//2. Sets the position command to false
	public void setPlayMode(boolean play)
	{
		gw.setPosition(false);//reset position to false now that we have moved the object
		if (play == true)
		{
			IIterator it = gw.getCollection().getIterator();
			while(it.hasNext())
			{
				GameObject go= it.getNext();
				if(go instanceof Fixed)
				{
					Fixed fixedObject = (Fixed)go;
						fixedObject.setSelected(false);
				}
			}
			repaint();	//draw the updated items on screen
		}
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// Displays the state of all game objects in the game world
		if (observable instanceof GameWorld)
		{
			GameWorld gameworld= (GameWorld)observable;
			System.out.println("\n");
			IIterator iterator = gameworld.getGameObjectCollection().getIterator();
			while(iterator.hasNext())
			{
				System.out.print("\n"+(GameObject)iterator.getNext());
			}	
		}
		repaint();			//calls paint which in turn re-paints the world
	}

	//Pain draws each of the game objects in the mapview using each gameobjects' draw()
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);				//Always needed
		Point pCmpRelPrnt = new Point(getX(),getY());
		IIterator it = gw.getCollection().getIterator();	//get the collection from gameworld then  get an iterator on it
		while(it.hasNext())
		{
			it.getNext().draw(g, pCmpRelPrnt);
		}
		
	}

}
