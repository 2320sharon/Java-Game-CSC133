package com.mycompany.a4;

import java.util.Random;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;

import com.codename1.charts.models.Point;

public class GameWorld extends Observable {
	private int lives ;
	private static int clock ; 
	private int width;
	private int height;
	private boolean positionSet;
	private boolean sound;
	private boolean isSoundEnabled;
	private GameObjectCollection gameObjCollection;
	
	//Sounds used in the gameworld
	private Sound spiderCollision;
	private Sound flagCollision;
	private Sound foodStationCollision;
	private BGSound backgroundSound;
	
	/* GameWorld()
	 * initializes the GameWorld with 3 lives and clock to 0.
	 */
	GameWorld(){
		setLives(3);
		setClock(0);
		//@TODO Change back to false
		setSound(false);
//		setSound(true);
	}
	
	
	/* init()
	 * Creates all the gameObjects in the gameWorld.
	 * 
	 * //Specifically creates at least 4 flags, an ant controllable by the player at the location of the first flag, at least two food stations,
	 *  and at least two spiders. It adds all these objects the gameObjCollection.
	 */
	public void init() {
		//Create at least 4 flags 
		//Make the flag object
		//Apply transforms
		//apply translate
		//apply scale according to size
		Random randNum= new Random();
		float winWidth = this.getWidth()/2;
		float winHeight =this.getHeight()/2;
		
		//Flag scaling factor
		float flagScaleFactor = (float) 1.2;
		int flagHeight=(int)winHeight/10;
		int flagWidth=(int)winWidth/10;
		
		//Spider scaling factors and height & widths
		float spiderScaleFactor = (float) 1;
		int spiderHeight=(int)winHeight/12;
		int spiderWidth=(int)winWidth/12;
		//Flag one location for the ant
		int flag1LocX=(int)(winWidth/1.2);
		int flag1LocY=(int)(winHeight/1.6);
		
		//locations for the flags
		int flag2LocX=(int)(winWidth/3);
		int flag2LocY=(int)(winHeight/1.5);
		
		int flag3LocX=(int)(winWidth/2);
		int flag3LocY= (int)(winHeight/5);
		
		int flag4LocX=(int)(winWidth/1.2);
		int flag4LocY= (int)(winHeight/10);
		
		Flag flag1 =new Flag(1,flagWidth,flagHeight);
		flag1.translate(flag1LocX, flag1LocY);
		flag1.scale(flagScaleFactor , flagScaleFactor);
		
		Flag flag2 = new Flag(2,flagWidth,flagHeight);
		flag2.translate(flag2LocX,  flag2LocY);
		flag2.scale(flagScaleFactor , flagScaleFactor);
		
		Flag flag3 =new Flag(3,flagWidth,flagHeight);
		flag3.translate(flag3LocX,flag3LocY);
		flag3.scale(flagScaleFactor , flagScaleFactor);
		
		Flag flag4 = new Flag(4,flagWidth,flagHeight);
		flag4.translate(flag4LocX, flag4LocY);
		flag4.scale(flagScaleFactor , flagScaleFactor);
		
		//create the Ant at position of flag one
		Ant player = Ant.destroyAnt();
		player = Ant.getAnt(this);
		player.translate(flag1LocX, flag1LocY);
		player.scale(3, 3);
		
		//Create two spiders
		//public Spider(int base, int height, int x, int y) //x and y do nothing
//		Spider spider1 = new Spider((int)winHeight/5,(int)winHeight/5,this.getWidth(), this.getHeight());
		Spider spider1 = new Spider(spiderWidth,spiderHeight);
		spider1.translate((int)winWidth/8, (int)winHeight/5);
		spider1.scale(spiderScaleFactor,spiderScaleFactor);
		
		Spider spider2 = new Spider(spiderWidth,spiderHeight);
		spider2.translate((int)winWidth/10, (int)winHeight/4);
		spider2.scale(spiderScaleFactor, spiderScaleFactor);
		
		
		//Create 2 foodStations
		//public FoodStation(int winWidth, int winHeight) {
		FoodStation foodStation1= new FoodStation(); 
		foodStation1.translate((int)winWidth/8, (int)winHeight/5);
		foodStation1.translate( (float)(winWidth/((new Random()).nextInt(10) +1.2)), (float)(winHeight/((new Random()).nextInt(10) +1.2)));
		foodStation1.scale((float)1,(float) 1);
		FoodStation foodStation2= new FoodStation();
		foodStation2.translate((int)winWidth/4, (int)winHeight/2);
		foodStation2.translate( (float)(winWidth/((new Random()).nextInt(10) +1.2)), (float)(winHeight/((new Random()).nextInt(10) +1.2)));
		foodStation2.scale((float)1,(float) 1);
		
		//Put all the GameObjects into the GameObjectCollection
		this.gameObjCollection=new GameObjectCollection();
		
		System.out.println("New game object collection: "+ this.gameObjCollection);
		this.gameObjCollection.add(player);
		this.gameObjCollection.add(spider1);
		this.gameObjCollection.add(spider2);
		this.gameObjCollection.add(flag1);
		this.gameObjCollection.add(flag2);
		this.gameObjCollection.add(flag3);
		this.gameObjCollection.add(flag4);
		this.gameObjCollection.add(foodStation1);
		this.gameObjCollection.add(foodStation2);
		
		updateAllObservers();
		
	}
	
	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getLives() {
		return lives;
	}

	private void setLives(int lives) {
		this.lives = lives;
	}

	public int getClock() {
		return clock;
	}
	
	public void setSound(boolean soundVal)
	{
		sound=soundVal;
		updateAllObservers();
	}
	
	public boolean getSound()
	{
		return sound;
	}
	
	public void setSoundEnabled(boolean soundVal)
	{
		isSoundEnabled=soundVal;
		updateAllObservers();
	}
	
	public boolean getisSoundEnabled()
	{
		return isSoundEnabled;
	}
	
	public GameObjectCollection getGameObjectCollection()
	{
		return this.gameObjCollection;
	}
	
	/*updateAllObservers()
	 *  Updates all observers that a change has occurred causing them to re-render their views
	 */
	public void updateAllObservers()
	{
		this.setChanged();	//Lets observers know a change has occurred
		this.notifyObservers();
	}
	
	
	//Allows the user to pause the game to move the positions of fixed object
	public void setPosition(boolean pos)
	{
		positionSet=pos;
	}

	public boolean getPosition()
	{
		return positionSet;
	}
	
	private void setClock(int clock) {
		this.clock = clock;
	}
	
	/*gameOver()
	 *  If the player has more than 0 lives then this method re-initializes the game world.
	 *  If the player has 0 lives then displays game over message and exits the game.
	 */
	private void gameOver() {
		if (getLives() > 0) 
		{
			System.out.print("\n GO AGAIN Lives left:  "+getLives() );
			System.out.print("\nYou lost one life! You have "+ getLives() + " lives left!");
//			gameObjCollection=new GameObjectCollection();//clear all the previous gameObjects
			init();
		}
		else
		{
			System.out.print("\n YOU LOST Lives left:  "+getLives() );
			System.out.print("\nGame over you failed! ");
			Dialog.show("Game over","Game over you failed!","Ok",null);
			Display.getInstance().exitApplication();
		}
	}
	
	public void changeBGSound(boolean playSound) {
	if (backgroundSound == null )
	{
		createSounds();
	}
	
	if (playSound)
		backgroundSound.play();
	else
		backgroundSound.pause();
}
	
	/*createSounds()bird
	* Creates the sound objects
	 */
	public void createSounds()
	{
		spiderCollision= new Sound("SQUEAK.wav");
		flagCollision = new Sound("FLAGSOUND.wav");
		foodStationCollision = new Sound("DONKEY.wav");
		backgroundSound = new BGSound("BACKGROUND.wav");
	}
	
	/* accelerate()
	 * Increases the speed of ant by one.
	 * 
	 * All the constraints on speed are handled within the setSpeed method
	 */
	public void accelerate() 
	{
		Ant antPlayer = Ant.getAnt() ;
		if(antPlayer != null)
		{	antPlayer.setSpeed((antPlayer.getSpeed()+5));
			System.out.print("\nAnt Accelerated to speed: "+antPlayer.getSpeed() );
		}
	}
	
	
	/* brake()
	 * Decreases the speed of ant by one.
	 * 
	 * All the constraints on speed are handled within the setSpeed method
	 */
	public void brake() 
	{	
		Ant antPlayer =  Ant.getAnt();
		if(antPlayer != null)
		{	antPlayer.setSpeed((antPlayer.getSpeed()-5));
			System.out.print("\nAnt braked to speed: "+antPlayer.getSpeed() );
		}
	}
		
	/*turn(char direction)
	 * Turns the ant either left or right
	 * 
	 * @param direction : character either 'l'= left or 'r'=right 
	 */
	public void turn(char direction) {

		Ant antPlayer = Ant.getAnt() ;
		if(antPlayer != null)
		{
			if (direction == 'l')
				 antPlayer.steer('l');
			else 
				 antPlayer.steer('r');
		}
	
	}
	
	/*public GameObjectCollection getCollection()
	 *  returns the gameobjectcollection so other classes can iterate through the collection
	 */
	public GameObjectCollection getCollection() {
		if (this.gameObjCollection == null)
		{
			System.out.print("The collection is empty and is null");
			return this.gameObjCollection;
		}
		return this.gameObjCollection;
	}
	
	/* tick(int ticks)
	 * This method increments the clock by 1. All moveable objects are moved.
	 * If the ant has non-zero health level, food level, and speed then it can move.
	 *  After it moves its foodLevel is decreased by the foodConsumptionRate
	 */
	public void tick(int ticks) {
		setClock(this.getClock() + ticks);
		System.out.print("\nOne clock tick occurred \n clock is now:  "+getClock());
		
		IIterator iterator = this.gameObjCollection.getIterator();
		while(iterator.hasNext())
		{
			GameObject iteratorcopy =(GameObject)iterator.getNext();
			if (iteratorcopy instanceof Ant)
				{
					Ant antPlayer= (Ant)iteratorcopy;
					if (antPlayer.getFoodLevel()<=0 || antPlayer.getHealthLevel() <=0 )
					{
						//Lose a life if ant's foodlevel <= 0
						setLives(getLives()-1);
						System.out.println("FoodLevel hit 0. You lost a life.");
						gameOver();
					}
					else if (antPlayer.getFoodLevel() != 0 && antPlayer.getHealthLevel() != 0 )
					{
						antPlayer.move(getWidth()/2,getHeight()/2,ticks,false);
						if(this.getClock() % 1000 == 0)	//ant loses food every second
							{antPlayer.setFoodLevel(antPlayer.getFoodLevel()-antPlayer.getFoodConsumptionRate());}
					}
					
				}
			else if (iteratorcopy instanceof Spider)
			{
				Spider spider= (Spider)iteratorcopy;
				spider.move(getWidth()/2,getHeight()/2,ticks,true);
			}
		}
		
	//collision handling (only check if ant collides with other objects)

		 		Ant antPlayer = Ant.getAnt();	//this is where nulls are happening
				IIterator otherObjectsIterator = this.gameObjCollection.getIterator();
				while(otherObjectsIterator.hasNext())
				{
					ICollider collidingObject = (ICollider)otherObjectsIterator.getNext();
					ArrayList<ICollider> collidingList = antPlayer.getCollidingList();
					ICollider potentialCollision = antPlayer.lastCollidedObj;
					
					if(!((GameObject)collidingObject instanceof Ant) && antPlayer.collidesWith(collidingObject) )
					{
						System.out.println("Yes Collision: "+collidingObject);
						System.out.println(antPlayer);
//						antPlayer.handleCollision(collidingObject);
						//check if ant has already collided with any object in collidingList
						if(!collidingList.isEmpty())
						{
							if (collidingList.contains(collidingObject))
							{
								System.out.print("Do not handle collision because it is already in the list");
							}
							else if(!collidingList.contains(collidingObject))
							{
								System.out.print("Handle collision"+collidingObject );
								antPlayer.handleCollision(collidingObject);
							}
						}
						else
						{
							System.out.print("Added to list: "+collidingObject );
							collidingList.add(collidingObject);
							antPlayer.handleCollision(collidingObject);
							
						}
					}
					else if (!((GameObject)collidingObject instanceof Ant) && !antPlayer.collidesWith(collidingObject) ) 
					{//if the object is no longer colliding with the ant then remove from list
						System.out.println("No collision: "+collidingObject);
						if (collidingList.contains(collidingObject))
						{
							//if ant is not current colliding with the object in the list then remove it unless it is ant
							System.out.print("Removing object from the list: "+collidingObject);
							collidingList.remove(collidingObject);
						}
					}
//				}
//			}
		}
		updateAllObservers();//this causes objects to be drawn on mapview since mapview's update calls repaint()
	}
	
	/*collideFoodStation()
	 * Collision between ant and a full food station.
	 * 
	 * When the ant collides with a non-empty food station its food level is increased by the capacity of the food station.
	 * The food station's capacity is dropped to 0 and its color is faded to a lighter shade of green. A new food station is 
	 * created to replace the empty one.
	 */
	public void collideFoodStation(FoodStation foodstation) {
		if (this.getSound()==true)
		{
			foodStationCollision.play();		//play the sound for the food station collision
		}
		Ant antPlayer = Ant.getAnt();
		IIterator it = gameObjCollection.getIterator();
		System.out.println(it.getNext());
		if(antPlayer != null)
		{
			if (foodstation != null && foodstation.getCapacity() != 0 )
			{
				//increase ant food level by the capacity of the food station
				antPlayer.setFoodLevel(foodstation.getCapacity()+ antPlayer.getFoodLevel());
				System.out.print("\nAnt has eaten food and now has foodLevel: "+ antPlayer.getFoodLevel());
				//decrease the food station's capacity to 0
				foodstation.setCapacity(0);
				//reduce the color of the food station
				foodstation.setColor(0,210,0);
			}
			else
				System.out.print("\nThere are no non-empty foodStations");
			//create a new food station to replace the empty one
			System.out.print("\nwidth"+this.getWidth()+" height: "+this.getHeight());
			FoodStation newfoodstation= new FoodStation();
			System.out.print("\nnewfoodstation"+newfoodstation);;
			if (this.gameObjCollection == null)
			{
				System.out.print("\ngameObjectCollectionCopy"+this.gameObjCollection);
			}
			this.gameObjCollection.add(newfoodstation);
		}
		updateAllObservers();
	}
	
	/* collideSpider()
	 * Collision between ant and a spider.
	 * 
	 * When the ant collides with food station its health level is dropped by one and its speed will change accordingly.The ant's color is
	 * also faded to a lighter blue. If the ant reaches 0 health then the player loses a life.
	 * The details of the speed change are handled within the ant's setSpeed(). 
	 */
	public void collideSpider() {
		 if (this.getSound()==true)
			{
			 spiderCollision.play();		//play the sound for the spider collision
			}
		 	Ant antPlayer = Ant.getAnt();
		 	System.out.println("antPlayer.getHealthLevel()"+antPlayer.getHealthLevel());
			antPlayer.setHealthLevel(antPlayer.getHealthLevel()-1);
			System.out.print("\nThe ant's health after spider collision: "+antPlayer.getHealthLevel());
			//fade the color of the ant
			int newColor = antPlayer.getHealthLevel() *20; //max of 200
			//Color can be between 0 to 255 so
			antPlayer.setColor(0, 0, 55+newColor);
				
			//reduce ant speed, so that it's speed can never be more than its health
			if (antPlayer.getHealthLevel()<antPlayer.getSpeed())
			{
				antPlayer.setSpeed(100 - (5*(10-antPlayer.getHealthLevel())));
				System.out.print("\nThe ant's speed after spider collision: "+antPlayer.getSpeed());
			}
			//If ant health == 0 then speed =0 and player loses a life
			if (antPlayer.getHealthLevel()<=0)
			{
				antPlayer.setSpeed(0);
				setLives(getLives()-1);
				//gameOver() either ends the game or reinitializes the GameObjects
				gameOver();
			}
		updateAllObservers();
	}	
	
	/* collideFlag(int flagNum) 
	 * Simulates a collision between ant and a flag of number flagNum.
	 * 
	 * When the ant collides with flag it either increments its LastFlagReached by one or leaves it unchanged if the flagNum was not sequential
	 * to the last flag reached by the ant. If the flagNum is 9 and all the flags before 9 have been reached in sequential order then the player wins!
	 * 
	 *  @param flagNum : int indicating which flag the ant has collided with.
	 */
  public void collideFlag(Flag flag) 
	{
	  if (this.getSound()==true)
		{
		  System.out.println("Flag sound should play");
		  flagCollision.play();		//play the sound for the flag collision
		}
	  int flagNum = flag.getSequenceNumber();
	  Ant antPlayer = Ant.getAnt();
		if(antPlayer != null)
		{
			System.out.print("\n Collided with flag:"+flagNum);
			
			if((antPlayer.getLastFlagReached()+1) == flagNum)
			{
				antPlayer.setLastFlagReached(flagNum);
				System.out.print("\n Ant lastFlag encountered is now:"+antPlayer.getLastFlagReached());
				if(flagNum==4)
				{
					Dialog.show("You win","Game Over You win! Total time: "+ this.getClock(),"Ok",null);
					Display.getInstance().exitApplication();
				}
			}
			else
				System.out.print("\n Collided with flag:"+flagNum+" was not the next flag after "+antPlayer.getLastFlagReached() );
		}
		updateAllObservers();	//this causes objects to be drawn on mapview since mapview's update calls repaint()
	}//end of collideFlag(int flagNum)

}//end of the GameWorld Class
