package TheMazeProject;

import java.util.EventListener;
import java.util.Stack;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class MazeSolverMain{
	
	static MotionController Controller;
	static FetchData Fetcher;

	public static void main(String[] args) {

		 int Color = 0;
		int SPEED = 350;
		String ColorName = "";
		Controller = new MotionController();
		Fetcher = new FetchData(Controller);
		(Fetcher.getController()).getOffest();
		
		
		Menu menu = new Menu();
		
		while(!Button.ENTER.isDown()) {
			
			Button.waitForAnyPress(200);

				if(Button.DOWN.isDown())
				{
					menu.MoveDown();
				}
				if(Button.UP.isDown())
				{
					menu.MoveUp();
				}
				
					Color = menu.getColor();
					ColorName = menu.getColorName();
					menu.Display();
					
				}		
		
		   LCD.clear();
		   LCD.drawString("Selected Color is",1,1);
		   LCD.drawString(ColorName ,7,2);
		   Button.waitForAnyPress(2000);
			Sound.setVolume(50);
			Sound.playTone(400, 500);
		   Stack<Integer> StackForward  = new Stack<Integer>();
		   Stack<Integer> StackBackward  = new Stack<Integer>();
		   
		   while(!Button.ESCAPE.isDown())
		   {
			   double walls [] = Fetcher.CheckWalls(Controller);
			   LCD.drawString("WallsChecked" ,2,3);

			   if( ArraySum(walls)>3) 
			   { 
				   LCD.drawString("ColorCheck" ,2,4);
				   int colors[] = Fetcher.CheckColors(Color);
				   if(colors==null) {break;}
			   }
			   
			   // Start of the next move ----------------------------------------
			   
			   for(int i=0 ; i<walls.length ; i++)
			   {
				   if(walls[i]==0) {StackForward.push(i);}
				   
			   }
			   if(ArraySum(walls)==2) {StackBackward.push(-1);}
			   if(ArraySum(walls)==1) {StackBackward.push(-1);StackBackward.push(-1);}
			   if(ArraySum(walls)!=4)
			   {
				   StackBackward.push(StackForward.peek());
				   int PoppedvalueF = StackForward.pop();
				   Controller.turn(Controller.RotationDegree(Controller.getIndex(Controller.Orientation), PoppedvalueF));
				   LCD.drawString("TakeStep",2,5);
				   Controller.StepForward(SPEED);
				   LCD.drawString("        "+Controller.Orientation,2,5);
				   

			   }
			   else
			   {
				   int PoppedValueB = StackBackward.peek();
				   while(PoppedValueB!=-1)
				   {
				   PoppedValueB = StackBackward.pop();
				   if(PoppedValueB!=-1)
				   {
				   Controller.turn(Controller.RotationDegree(Controller.getIndex(Controller.Orientation), PoppedValueB));
				   Controller.StepBackward(SPEED);
				   }
				   }
				   Controller.turn(Controller.RotationDegree(Controller.getIndex(Controller.Orientation), StackForward.peek()));
				   StackBackward.push(StackForward.pop());
				   Controller.StepForward(SPEED);
			   }
			   
		   }
		   


	
		}
	
	public static int ArraySum(double [] walls)
	{
		int sum = 0;
		   for (int i=0;i<walls.length ; i++)
		   {
			   sum+=walls[i];
		   }
		   return sum;
		
	}
	
	

}