package com.mycompany.a3;

/*abstract Fixed class extends GameObject
 * This class is used for objects that cannot move
 * */


public abstract class Fixed extends GameObject implements ISelectable {
	
	//selected: true if object is currently selected
	private boolean selected;
	
	//Pass the size of the object to the parent GameObject
	//Fixed objects can only set their location on creation
	public Fixed(int size,float x, float y)
	{
		super(size);
		super.setLocation(x, y);
	}
	
	public void setSelected(boolean value)
	{
		selected=value;
	}
	
	public boolean isSelected()
	{
		return selected;
	}

}
