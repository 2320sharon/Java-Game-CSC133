package com.mycompany.a4;

//Used to create a collection of GameObjects
public interface ICollection {
	public void add(GameObject gameobj);
	public IIterator getIterator();
}
