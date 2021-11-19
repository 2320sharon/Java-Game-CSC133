package com.mycompany.a3;

/*abstract Fixed class extends GameObject
 * This class is used for objects that cannot move
 * */


public abstract class Fixed extends GameObject {
	
	//Pass the size of the object to the parent GameObject
	//Fixed objects can only set their location on creation
	public Fixed(int size,float x, float y)
	{
		super(size);
		super.setLocation(x, y);
	}
	
	//Cannot change the location after the fixed object is created
	@Override
	public void setLocation(float x, float y)
	{
	}

}
