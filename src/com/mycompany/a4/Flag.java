package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

//Flag is used to create a series of path markers that the ant must walk to in sequential order
//Flag is fixed so it cannot change its size, location, or color after being created
public class Flag extends Fixed {
	//local coordinates
	private Point top, bottomLeft, bottomRight;
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
	public Flag(int FlagNum, int base , int height) 
	{
		//Flag cannot change its location after creation
		//Makes the Flag's size 80 
		super(80);
		
		//Calculates the Spider's points based on the height and base
		top=new Point(0,(float) ((float)height/2.0));
		bottomLeft = new Point((float)(-base/2),(float)(-height/2));
		bottomRight = new Point((float)(base/2),(float)(-height/2));
		
		//Sets the flag's SeqNumber
		setSequenceNumber(FlagNum);
			
		//Sets the Flag's color to purple
		super.setColor(255, 0, 255);
		
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		
		//Draw the spider is an unfilled Isosceles triangle
		g.setColor(super.getColor());
				
		//Append the spider's LT's to the xform in the Graphics object. But first move the drawing coordinates
		// so that the local origin coincides with the screen origin. After the LT's are applied, move the drawing
		//coordinates back
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform); //loads gXform into g
		//Make a copy of the gXform
		Transform gOrigXform = gXform.copy();
				
		//Since java is row majored. This operation which is normally last in row majored is applied first
		gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());//LT part 2
		gXform.translate(getTranslate().getTranslateX(), getTranslate().getTranslateY());
		gXform.concatenate(getRotate());
		gXform.scale(getScale().getScaleX(),getScale().getScaleY());	
		//Translate back to origin
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
		
		//Set the transformation using transformed gXform
		g.setTransform(gXform);
				
      //Top point
		float x=(pCmpRelPrnt.getX()+top.getX());
		float y=(pCmpRelPrnt.getY()+top.getY());
		Point upperPoint =new Point(x,y);
				
		//lower left point
		x=(pCmpRelPrnt.getX()+bottomLeft.getX() );
		y=(pCmpRelPrnt.getY()+bottomLeft.getY() );
		Point lowerLeftPoint = new Point(x,y);
				
		//lower right point
		x= (pCmpRelPrnt.getX()+bottomRight.getX());
		y= (pCmpRelPrnt.getY()+bottomRight.getY());
		Point lowerRightPoint= new Point(x,y);
				
		int xPoints[]=new int[] {(int)upperPoint.getX(),(int)lowerLeftPoint.getX(),(int)lowerRightPoint.getX()};
		int yPoints[]=new int[] {(int)upperPoint.getY(),(int)lowerLeftPoint.getY(),(int)lowerRightPoint.getY()};
		//number of points in a triangle is 3 hence numPoints =3
		int numPoints =3;
		
		//Draw the flag as a filled Isosceles triangle		
		if (this.isSelected() == true)
			g.drawPolygon(xPoints, yPoints, numPoints);
		else
			g.fillPolygon(xPoints, yPoints, numPoints);
		
		g.getTransform(gXform); //loads gXform into g
		//Now do transformation on the text
		gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
		gXform.scale(1,-1);
		//Translate back to origin
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
				
		//Set the transformation so we can write the text in the transformed form
		g.setTransform(gXform);
		
		//put the text on top of the food station
		//the text is black
		g.setColor(ColorUtil.BLACK);
		g.drawString(Integer.toString(this.getSequenceNumber()),(int)(upperPoint.getX()-25),(int)(upperPoint.getY()-38));
		//reset the transform to its original form
		g.setTransform(gOrigXform);
		
	}


	@Override
	public void handleCollision(ICollider collideObject) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean contains(Point pPtrRelParent, Point pCmpRelParent) {
		System.out.println("Flag is at: "+ pPtrRelParent.getX() + " , "+pPtrRelParent.getY() );
		//Pointer's Coordinates
		int pointerX=(int)pPtrRelParent.getX();
		int pointerY=(int)pPtrRelParent.getY();
		
		//location of flag relative to mapview
		int xLoc=(int)(pCmpRelParent.getX() + this.getLocation().getX());
		int yLoc=(int)(pCmpRelParent.getY() + this.getLocation().getY());
		
		System.out.println("Pointer is at: "+pointerX + " , "+pointerY );
		System.out.println("Flag is "+this.getSequenceNumber()+ " at: "+xLoc + " , "+yLoc );
		System.out.println("Size of flag is"+ this.getSize());
		//is the pointer within the flag
		if((((pointerX >= xLoc) && (pointerX <= xLoc + this.getSize()/2)) || ((pointerX < xLoc) && (pointerX >= xLoc - this.getSize()/2) ))  && (((pointerY < yLoc) && (pointerY >= yLoc - this.getSize()/2) )||(pointerY>= yLoc) && (pointerY <= yLoc + this.getSize()/2)) )
			return true;
		else
			return false;
	}

	
	
}
