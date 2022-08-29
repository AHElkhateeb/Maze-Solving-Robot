package Lab4;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class robot_TurnP extends robot_TurningWithGyro {
	
	private final float  P = 2.0f;
	public robot_TurnP()
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
				
			while (degrees-angle >= 0.1 && flag)
			{
				gyro.fetchSample(gyroSamples,getOffest());
				angle = gyroSamples[0];
				float error = (degrees-angle);
				getRightMotor().setSpeed(error*this.P);
				getLeftMotor().setSpeed(error*this.P);
				getRightMotor().backward();
				getLeftMotor().forward();
			}
			
	        getRightMotor().stop();
	        getLeftMotor().stop();
	        flag = false;
			
		    }
		
	}

}
