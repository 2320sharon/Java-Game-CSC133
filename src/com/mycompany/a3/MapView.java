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
	private boolean selected = false;	//asumed nothing is selected by default
	public MapView(Game game) {
		setLayout(new FlowLayout());
		getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(255,0,0)));//give container red border
		this.game=game;
	}
	
	public MapView(Game game,GameWorld gw ) {
		setLayout(new FlowLayout());
		getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(255,0,0)));//give container red border
		this.game=game;
		this.gw=gw;
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
