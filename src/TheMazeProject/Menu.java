package TheMazeProject;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;

public class Menu {
	
	// Variables
	int Colors [] = {Color.BLUE,Color.GREEN,Color.RED,Color.YELLOW,Color.ORANGE,Color.WHITE,Color.BLACK};
	String ColorsNames[] = {"Blue","Green","Red","Yellow","Orange","White","Black"};
	int CursorPos;
	final int NUM_OF_COLORS = 7;
	
	// Constructors
	
	public Menu()
	{
		this.CursorPos = 0;
		Display(this.ColorsNames,this.CursorPos);
	}
	
	// Methods
	
	private void Display(String ColorsNames[], int CursorPos)
	{
       for(int i = 0 ; i< NUM_OF_COLORS ; i++)
       {
    	   if(i==CursorPos)
    	   {
    		   LCD.drawString(">> "+ColorsNames[i],1,i);
    	   }
    	   else
    	   {
    		   LCD.drawString("   "+ColorsNames[i],1,i);
    		   
    	   }
       }
	}
	
	public void Display()
	{
		Display(this.ColorsNames,this.CursorPos);
	}
	public int getColor()
	{
		return this.Colors[CursorPos];

		
	}
	
	public String getColorName()
	{
		return this.ColorsNames[CursorPos];
		
	}
	
	public void MoveUp()
	{
		if (CursorPos>0)
		this.CursorPos--;
		Display(this.ColorsNames, this.CursorPos);
	}
	
	public void MoveDown()
	{
		if (CursorPos<NUM_OF_COLORS-1)
		this.CursorPos++;
		Display(this.ColorsNames, this.CursorPos);
	}
	
	}
