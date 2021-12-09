package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface ISelectable {

	void setSelected (boolean selected);
	boolean isSelected();
	boolean contains(Point pPtrRelParent, Point pCmpRelParent);
	void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn);
	
}
