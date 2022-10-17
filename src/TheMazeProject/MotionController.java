package TheMazeProject;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class MotionController implements Turner {
	
	private EV3LargeRegulatedMotor rightMotor;
	private EV3LargeRegulatedMotor leftMotor;
	private EV3GyroSensor gyroSensor ;
	private final float  P = 10.0f;
	private int offest = 0 ;
	float Orientation = 0.0f;
	
	public MotionController()
	{
		this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);    // Create new right motor object 
	    this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.B); 
		this.gyroSensor = new EV3GyroSensor(SensorPort.S3);
	}
	
	@Override
	public void turn(float degrees) {
		// TODO Auto-generated method stub
		SampleProvider gyro = gyroSensor.getAngleMode();
		int sampleSizegryo = gyro.sampleSize();
		float[] gyroSamples = new float [sampleSizegryo];   
		gyro.fetchSample(gyroSamples,getOffest());	
		float angle = gyroSamples[0];
		this.Orientation = angle;
			while (((degrees+this.Orientation)-angle>0.1 || (degrees+this.Orientation)-angle<-0.1))
			{
				gyro.fetchSample(gyroSamples,getOffest());
				angle = gyroSamples[0];
				float error = (Math.abs((degrees+this.Orientation-angle)));
				this.rightMotor.setSpeed((int)(error*this.P));
				this.leftMotor.setSpeed((int)(error*this.P));
				if(degrees>0)
				{
				  this.rightMotor.backward();
				  this.leftMotor.forward();
				}
				else if(degrees<0)
				{
					  this.rightMotor.forward();
					  this.leftMotor.backward();
				}
				else {this.Orientation = angle; break;}
			}
			
			rightMotor.stop();
			leftMotor.stop();
	        this.Orientation = angle;
	}
	

	public int getOffest() {
		return offest;
	}

	public void setOffest(int offest) {
		this.offest = offest;
	}
	
	public void MoveForward(int speed)
	{
		  this.rightMotor.setSpeed(speed);
		  this.leftMotor.setSpeed(speed);
		  this.rightMotor.backward();
		  this.leftMotor.backward();
	}
	
	public void MoveForward( int speed, int angle)
	{
		  this.rightMotor.setSpeed(speed);
		  this.leftMotor.setSpeed(speed);
		  this.rightMotor.rotate(-angle,true);
		  this.leftMotor.rotate(-angle);
		
	}
	
	public void MoveBackward(int speed)
	{
		  this.rightMotor.setSpeed(speed);
		  this.leftMotor.setSpeed(speed);
		  this.rightMotor.forward();
		  this.leftMotor.forward();
	}
	
	public void MoveBackward(int speed,int angle)
	{
		  this.rightMotor.setSpeed(speed);
		  this.leftMotor.setSpeed(speed);
		  this.rightMotor.rotate(angle,true);
		  this.leftMotor.rotate(angle);
		
	}
	
	public void StepForward(int speed)
	{
		
		SampleProvider gyro = gyroSensor.getAngleMode();
		int sampleSizegryo = gyro.sampleSize();
		float[] gyroSamples = new float [sampleSizegryo];   

		   this.rightMotor.setSpeed(speed);
		   this.leftMotor.setSpeed(speed);
		  
		  this.rightMotor.rotate(-729*3,true);
		  this.leftMotor.rotate(-729*3);
		  
		  gyro.fetchSample(gyroSamples,getOffest());	
		  float angle = gyroSamples[0];
		  float degrees = -angle+this.Orientation;
			if(degrees>0) {degrees+=1;}
			if(degrees<0) {degrees-=1;}
          turn(degrees);
	}
	
	public void StepBackward(int speed)
	{
		SampleProvider gyro = gyroSensor.getAngleMode();
		int sampleSizegryo = gyro.sampleSize();
		float[] gyroSamples = new float [sampleSizegryo];   
		
		
		  this.rightMotor.setSpeed(speed);
		  this.leftMotor.setSpeed(speed);
		 
		  this.rightMotor.rotate(729*3,true);
		  this.leftMotor.rotate(729*3);

		  gyro.fetchSample(gyroSamples,getOffest());	
		  float angle = gyroSamples[0];
		  float degrees = -angle+this.Orientation;
			if(degrees>0) {degrees+=1;}
			if(degrees<0) {degrees-=1;}
          turn(degrees);
          
	}
	public void Stop()
	{
		  this.rightMotor.stop();
		  this.leftMotor.stop();
	}
	
	public int getIndex(float Orientation)
	{
		//float PositiveOrientation = Math.abs(Orientation);
		Orientation = Orientation%360;
		Orientation = (int)Orientation;
		if((Orientation<=4 && Orientation>=-4)||(Orientation>=356)||(Orientation<=-356)) {return 0;}
		if((Orientation<=94 && Orientation>=86 )|| (Orientation>=-274 && Orientation <= -266)) {return 1;}
		if((Orientation<=184 && Orientation>=176) || (Orientation>=-184 && Orientation <= -176)) {return 2;}
		if((Orientation<=274 && Orientation >= 266) || (Orientation>=-94 && Orientation<=-86 ))  {return 3;}
		else {return -1;}
	}
	
	public int RotationDegree(int CurrentIndex, int Index)
	{
		int C = Index - CurrentIndex;
		if(C== 1 || C==-3 ) {return 90;}
		if(C== 2 || C==-2) {return 180;}
		if(C== 3 || C==-1) {return -90;}
		else {return 0;}
	}

}
