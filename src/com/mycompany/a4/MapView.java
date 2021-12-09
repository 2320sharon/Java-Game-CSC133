package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {

	private Transform worldToND,ndToDisplay,theVTM;
	private float winLeft , winRight, winTop, winBottom;
	private Game game;					//game object needed to pause in the gameworld
	private GameWorld gw;				//gameworld contains the move methods 
	private boolean selectAllowed = false;	//Assumed nothing is able to be selected by default
	public MapView(Game game) {
		setLayout(new FlowLayout());
		getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(255,0,0)));//give container red border
		this.game=game;
		winLeft=0;
		winBottom =0;
	}
	
	public void setWinRight(float value)
	{
		 winRight=value;
	}
	
	public void setWinTop(float value)
	{
		winTop=value;
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
		winLeft=0;
		winBottom =0;
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
//						fixedObject.setLocation(newPoint.getX(), newPoint.getY());
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
	
	private Transform buildWorldToNDXform(float winWidth, float winHeight, float winLeft, float winBottom)
	{
		Transform tmpXform=Transform.makeIdentity();
		tmpXform.scale((1/winWidth), (1/winHeight) );
		tmpXform.translate(-winLeft, -winBottom);
		return tmpXform;
	}

	private Transform buildNDToDisplayXform(float displayWidth, float displayHeight)
	{
		Transform tmpXform=Transform.makeIdentity();
		tmpXform.translate(0,displayHeight);
		tmpXform.scale(displayWidth, -displayHeight );
		return tmpXform;
	}

	
	//Pain draws each of the game objects in the mapview using each gameobjects' draw()
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);				//Always needed
		
		//calculate the winWidth and winHeight
		float winHeight=winTop-winBottom;
		float winWidth=winRight-winLeft;
		
		//construct Viewing Transform Matrix
		worldToND = buildWorldToNDXform(winWidth,winHeight,winLeft,winBottom);
		System.out.print("\nthis.getWidth(),this.getHeight()"+this.getWidth()+" , "+this.getHeight());
		ndToDisplay = buildNDToDisplayXform(this.getWidth(),this.getHeight());
		theVTM = ndToDisplay.copy();
		theVTM.concatenate(worldToND);
		
		//concatenate the VTM onto g's current transformation (also apply the local transformation)
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		gXform.translate(getAbsoluteX(),getAbsoluteY()); //local origin xform part 2
	
		gXform.concatenate(theVTM);						//theVTM xform
		gXform.translate(-getAbsoluteX(),-getAbsoluteY()); //local origin xform part 1
		
		g.setTransform(gXform);
		
		//each gameObject draws itself using the g(which contains the theVTM)
		Point pCmpRelPrnt = new Point(this.getX(),this.getY());
		Point pCmpRelScrn = new Point(getAbsoluteX(),getAbsoluteY());
		IIterator it = gw.getCollection().getIterator();	//get the collection from gameworld then  get an iterator on it
		while(it.hasNext())
		{
			it.getNext().draw(g, pCmpRelPrnt,pCmpRelScrn);
		}
		
		g.resetAffine();		//reset the graphics object
		
	}

}
