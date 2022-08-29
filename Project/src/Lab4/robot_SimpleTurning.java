package Lab4;

import TheMazeProject.Turner;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.utility.Timer;
import lejos.utility.TimerListener;

public class robot_SimpleTurning implements Turner {
	


	private EV3LargeRegulatedMotor rightMotor;
	private EV3LargeRegulatedMotor leftMotor;
	
	
	public robot_SimpleTurning()
	{
		Sound.setVolume(50);
		Sound.playTone(400, 500);
		this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);    // Create new right motor object 
	    this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.B); 
	}

	@Override
	public void turn(float degrees) {
		
		boolean flag = true;
		
		while (!Button.ENTER.isDown()) {  
			
		if(flag)
		{
		this.rightMotor.rotate((int) (61.5*3*degrees/27.5),true);
		this.leftMotor.rotate(-(int) (61.5*3*degrees/27.5));
		}
		      
        this.rightMotor.stop();
        this.leftMotor.stop();
		flag = false;
		}
	}
	
	
	public EV3LargeRegulatedMotor getRightMotor() {
		return this.rightMotor;
	}
	public EV3LargeRegulatedMotor getLeftMotor() {
		return this.leftMotor;
	}

}
