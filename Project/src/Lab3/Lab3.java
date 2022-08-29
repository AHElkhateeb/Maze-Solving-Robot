/*
 * Lab 3 solution included task 3.3 for the bouns 
 * Students : Ossama ahmed - Ahmed Elkhateeb
 * */


package Lab3;


// Import files -------------------------------------------

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
/**
 * FIXME: Unnecessary import
 */
import lejos.hardware.Button;

public class Lab3 {

	public static void main(String[] args) {

		// Task 3.1 " Variables " ---------------------------------------------------------------------------------------
		
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);   // Create new color sensor object
		SensorMode colorID = colorSensor.getColorIDMode();                // Create sensor mode object
		int sampleSizeID = colorID.sampleSize();                           // get the sample size for fetching 
		float[] colorIdSamples = new float[sampleSizeID];                  // initialize new array for the samples

		// Task 3.2 " Variables " ---------------------------------------------------------------------------------------------
		
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S4);  // Create new distance sensor object
		SampleProvider distance = distanceSensor.getDistanceMode();                   // Create sensor mode object
		int sampleSizeDis = distance.sampleSize();                                    // get the sample size for fetching
		float[] distanceSamples = new float[sampleSizeDis];                           // initialize new array for the samples
		
		// Task 3.3 " Variables " ----------------------------------------------------------------------------------------------
		
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);    // Create new right motor object 
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);     // Create new left motor object 
		rightMotor.setSpeed(200);                                                       // Initial speed setting for motors
		leftMotor.setSpeed(200);
		
		// General Variables -----------------------------------------------------------------------------------------------------

		String color = "";
		boolean obstacleFound = false;
		/**
		 * FIXME: maxDistance, minDistance, offset, x and y coordinate
		 * should rather be constants here!
		 */
		int maxDistance = 8;                                                           // Max distance for robot to use the 200 speed settings in cm
		float minDistance = 3.3f;                                                      // Min distance for robot to move in cm
		
		/**
		 * FIXME: Bad Naming: Mixed camel casing and snake casing
		 * FIXME: This does not work: When calling colorIdSamples[0], only 
		 *  the value which is currently in the array at position 0 is copied
		 *  to the variable color_ID. You need to do this in every loop iteration
		 *  again!
		 */
		int color_ID = (int)(colorIdSamples[0]);
		float Distance_cm = distanceSamples[0] * 100 ;                                 // Distance read from the sensor in cm
		int offset = 0 ;
		int x_Coordinate_Origin = 2;
		int y_Coordinate_Origin = 2;
		
		// Starting Tune to indicate successful code booting --------------------------------
		Sound.setVolume(50);
		Sound.playTone(400, 500);

		
		while (!Button.ENTER.isDown()) {                                              // Infinite loop till the enter button is pressed
			
			colorID.fetchSample(colorIdSamples, offset);
			distance.fetchSample(distanceSamples, offset);
			
			/*
			 * FIXME Distance_cm must be read from the distanceSamples array 
			 *   in every iteration
			 */
			if (Distance_cm > maxDistance) 
				/*
				 * FIXME inconsistent indentation - sometimes opening bracket is at
				 *  end of line, sometimes in a new line. If it is in a new line, 
				 *  the opening bracket should be on the same indentation level as the 
				 *  previous line. Try select all code and press ctrl+I for auto format
				 */
				{           
					rightMotor.setSpeed(200);                                        // if distance is larger than maxDistance use 200 speed
					leftMotor.setSpeed(200);
				} 
			else if (Distance_cm <= maxDistance)
				{               
					rightMotor.setSpeed(100);                                        // if distance is less than maxDistance use 100 speed
					leftMotor.setSpeed(100);
				}

			if (Distance_cm <= minDistance)
				{
					rightMotor.stop();                                               // if distance is less than minDistance Stop! and set obstacle found flag to true
					leftMotor.stop();
					obstacleFound = true;
				} 
			else
				{
					obstacleFound = false;                                            // if distance is larger than minDistance set obstacle found flag to false
				}

			if (obstacleFound == false) 
				{
					rightMotor.backward();                                                    // Move robot if no obstacle detected
					leftMotor.backward();
				}
		

			switch (color_ID) // Switch over the color ID to identify the color by the provided Color
								// constants
			{
			case Color.BLACK:
				color = "BLACK";
				break;
			case Color.BLUE:
				color = "BLUE";
				break;
			case Color.GREEN:
				color = "GREEN";
				break;
			case Color.RED:
				color = "RED";
				break;
			case Color.YELLOW:
				color = "YELLOW";
				break;
			case Color.BROWN:
				color = "BROWN";
				break;
			case Color.CYAN:
				color = "CYAN";
				break;
			case Color.DARK_GRAY:
				color = "DARK GRAY";
				break;
			case Color.GRAY:
				color = "GRAY";
				break;
			case Color.LIGHT_GRAY:
				color = "LIGHT GRAY";
				break;
			case Color.MAGENTA:
				color = "MAGENTA";
				break;
			case Color.ORANGE:
				color = "ORANGE";
				break;
			case Color.PINK:
				color = "PINK";
				break;
			case Color.WHITE:
				color = "WHITE";
				break;

			default:
				LCD.drawString("None", x_Coordinate_Origin, y_Coordinate_Origin);
				break;
			}
			
			/*
			 * FIXME: Clarity: Do LCD.clear() before printing instead of overwriting with whitespaces
			 */
			LCD.drawString(color+"        ", x_Coordinate_Origin, y_Coordinate_Origin);                           // Display color to LCD
			LCD.drawString(Distance_cm+"                    ", x_Coordinate_Origin, y_Coordinate_Origin+1);       // Display distance to LCD
		}
	}
}
