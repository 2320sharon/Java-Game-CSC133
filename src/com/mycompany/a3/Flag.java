package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

//Flag is used to create a series of path markers that the ant must walk to in sequential order
//Flag is fixed so it cannot change its size, location, or color after being created
public class Flag extends Fixed {
	private int sequenceNumber;

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	//The flag is only able to set its sequence Number when it is created
	private void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	//Prevents anyone from changing the flag's color after creation
	@Override
	public void setColor(int red, int green, int blue) { }
	
	//Prevents anyone from changing the flag's color after creation
	@Override
	public String toString() {
		return "Flag :"+super.toString()+" seqNum= "+ getSequenceNumber();
	}
	
	/*Flag(int FlagNum, float xLoc, float yLoc)
	 * Creates a flag with SequenceNumber = flagNum at the location specified by (xLoc,yLoc). The flag is initialized with color purple
	 * and size = 10. The size, color and location of the flag cannot be changed after creation.
	 * 
	 * @param flagNum : int used to set the SequenceNumber of the flag
	 * @param xLoc : float used to set the location of the flag on the x-axis
	 * @param yLoc : float used to set the location of the flag on the y-axis
	 */
	public Flag(int FlagNum, float xLoc, float yLoc) 
	{
		//Makes the Flag's size 10 
		//Place the Flag within the map
		//Flag cannot change its location after creation
		super(60, xLoc ,yLoc );
		
		//Sets the flag's SeqNumber
		setSequenceNumber(FlagNum);
			
		//Sets the Flag's color to purple
		super.setColor(255, 0, 255);
		
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		//Draw the flag as a filled Isosceles triangle
		//Top point
		float x=(pCmpRelPrnt.getX()+super.getLocation().getX());
		float y=(pCmpRelPrnt.getY()+super.getLocation().getY() + super.getSize()/2);
		Point upperPoint =new Point(x,y);
				
		//lower left point
		x=(pCmpRelPrnt.getX()+super.getLocation().getX() - super.getSize()/2);
		y=(pCmpRelPrnt.getY()+super.getLocation().getY() - super.getSize()/2);
		Point lowerLeftPoint = new Point(x,y);
				
		//lower right point
		x= (pCmpRelPrnt.getX()+super.getLocation().getX() + super.getSize()/2 );
		y= (pCmpRelPrnt.getY()+super.getLocation().getY() - super.getSize()/2);
		Point lowerRightPoint= new Point(x,y);
				
		int xPoints[]=new int[] {(int)upperPoint.getX(),(int)lowerLeftPoint.getX(),(int)lowerRightPoint.getX()};
		int yPoints[]=new int[] {(int)upperPoint.getY(),(int)lowerLeftPoint.getY(),(int)lowerRightPoint.getY()};
		//number of points in a triangle is 3 hence numPoints =3
		int numPoints =3;
				
		g.setColor(getColor());
		g.fillPolygon(xPoints, yPoints, numPoints);
		
		//put the text on top of the flag
		//the text is black
		g.setColor(ColorUtil.BLACK);
		//text is in the middle of the flag and subtract the font size to get it in the proper location
		int xText= (int)(pCmpRelPrnt.getX()+super.getLocation().getX()-(g.getFont().getPixelSize() / 2));
		int yText=(int)(pCmpRelPrnt.getY()+super.getLocation().getY()- (g.getFont().getPixelSize() / 2));
		g.drawString(Integer.toString(this.getSequenceNumber()), xText, yText);
		
	}


	@Override
	public void handleCollision(ICollider collideObject) {
		// TODO Auto-generated method stub
		
	}

	
	
}
