package com.mycompany.a4;

import java.util.Vector;

public class GameObjectCollection implements ICollection{

	private Vector<GameObject> gameObjects; //used to hold all the gameObjects during the course of a game
	
	//Creates an instance of gameObjects
	public GameObjectCollection() {
		gameObjects = new Vector<GameObject>();	
	}

	/*add(GameObject gameobj)
	 * Adds a gameobject to the GameObjectCollection
	 */
	@Override
	public void add(GameObject gameobj) {
		//Add the GameObject to the vector
		gameObjects.add(gameobj);
	}

	/*getIterator()
	 * Used to get an iterator to access elements of GameObjectCollection
	 */
	@Override
	public IIterator getIterator() {
		return new GameCollectionIterator();
	}
	
	//Private Inner Class: GameCollectionIterator
	//Can only be accessed by GameObjectCollection and is used to access the elements in the GameObjectCollection
	private class GameCollectionIterator implements IIterator{

		private int currElementIndex;
		 
		public GameCollectionIterator() {
			currElementIndex=-1;
		}
		
		@Override
		public boolean hasNext() {
			if(gameObjects.size() <= 0) return false;
			if(currElementIndex == gameObjects.size()-1) return false;
			return true;
		}

		@Override
		public GameObject getNext() {
			currElementIndex++;
			return (gameObjects.elementAt(currElementIndex));
		}
		
	}

}
