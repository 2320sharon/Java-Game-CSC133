package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface ISelectable {

	void setSelected (boolean selected);
	boolean isSelected();
	boolean contains(Point pPtrRelParent, Point pCmpRelParent);
	void draw(Graphics g, Point pCmpRelParent );
	
}
