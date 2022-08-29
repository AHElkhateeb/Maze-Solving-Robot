package Lab4;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class robot_TurningWithGyro extends  robot_SimpleTurning {
	
	private int offest = 0 ;
	

	public robot_TurningWithGyro()
	{
		super();
	}

	@Override
	public void turn(float degrees) {
		// TODO Auto-generated method stub
		EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S3);
		SampleProvider gyro = gyroSensor.getAngleMode();
		int sampleSizegryo = gyro.sampleSize();
		float[] gyroSamples = new float [sampleSizegryo];   
	    float angle = 0.0f;
	    boolean flag = true;
		
		while (!Button.ENTER.isDown()) 
			{
				
			while (angle < degrees && flag)
			{
				gyro.fetchSample(gyroSamples,offest);
				angle = gyroSamples[0];
				getRightMotor().backward();
				getLeftMotor().forward();
			}
			
	        getRightMotor().stop();
	        getLeftMotor().stop();
	        flag = false;
			
		    }
		
	}
	
	public int getOffest() {
		return offest;
	}

	public void setOffest(int offest) {
		this.offest = offest;
	}

}
