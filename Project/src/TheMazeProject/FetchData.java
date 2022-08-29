package TheMazeProject;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class FetchData {
	
	EV3ColorSensor ColorSensor ;
	EV3UltrasonicSensor DistanceSensor;
	SampleProvider Distance;
	SensorMode ColorID;
	float[] ColorIdSamples;
	float[] DistanceSamples;
	MotionController Controller;
	
	
	
			public MotionController getController() {
		return Controller;
	}

			public FetchData(MotionController Controller)
			{

				InitializeColorSensor();
				InitializeEV3UltrasonicSensor();
				this.Controller = Controller;

				
			}
			
			//public double getDis
			
			private void InitializeColorSensor()
			{
				 this.ColorSensor = new EV3ColorSensor(SensorPort.S1);   // Create new color sensor object
				 this.ColorID = ColorSensor.getColorIDMode();                // Create sensor mode object
				 int sampleSizeID = ColorID.sampleSize();                           // get the sample size for fetching 
				 this.ColorIdSamples = new float[sampleSizeID];                  // initialize new array for the samples
		
			}
			
			private void InitializeEV3UltrasonicSensor()
			{
				this.DistanceSensor = new EV3UltrasonicSensor(SensorPort.S4);  // Create new distance sensor object
				this.Distance = DistanceSensor.getDistanceMode();                   // Create sensor mode object
				int sampleSizeDis = Distance.sampleSize();                                    // get the sample size for fetching
				this.DistanceSamples = new float[sampleSizeDis];                           // initialize new array for the samples
				
			}
			
			public double[] CheckWalls(MotionController Controller)
			{
				int Index = -1;
				int offset = 0 ;
				int NUMBEROFWALLS = 4;
				float WallDistance = 25f;
				double Walls[] = new double[NUMBEROFWALLS];
				for(int CheckedWalls=0 ; NUMBEROFWALLS > CheckedWalls ; CheckedWalls++ )
				{
					float Orientation = this.Controller.Orientation;
					Index =  this.Controller.getIndex(Orientation);
					if(Index==-1) {continue;}
					if(CheckedWalls==2) {Walls[Index]=1; Controller.turn(90); continue;}
					Distance.fetchSample(DistanceSamples, offset);
					LCD.drawString(""+DistanceSamples[0]*100, 2, 6);
					if(DistanceSamples[0]*100<=WallDistance) {Walls[Index]=1;}
					Controller.turn(90);
					
				}
				return Walls;
			}
			
			public int[] CheckColors(int SelectedColorID)
			{
				int offset = 0 ;
				int Speed = 100;
				int NUMBEROFWALLS = 4;
				float WallDistanceMin = 3.3f;
				float WallDistanceMax = 15f;
				int Colors[] = new int[NUMBEROFWALLS];
				for(int CheckedWalls=0 ; NUMBEROFWALLS > CheckedWalls ; CheckedWalls++ )
				{
					Distance.fetchSample(DistanceSamples, offset);
					if(DistanceSamples[0]*100>=WallDistanceMax) {Controller.turn(90);continue;}
					while(DistanceSamples[0]*100>=WallDistanceMin)
					{
						Distance.fetchSample(DistanceSamples, offset);
						Controller.MoveForward(Speed);
					}
					
					ColorID.fetchSample(ColorIdSamples, offset);
					Colors[CheckedWalls]=(int)ColorIdSamples[0];
					if(Colors[CheckedWalls]==SelectedColorID) {Sound.setVolume(70);Sound.playTone(500, 600); return null;}
					
					while(DistanceSamples[0]*100<=WallDistanceMax)
					{
						Distance.fetchSample(DistanceSamples, offset);
						Controller.MoveBackward(Speed);
					}

					Controller.turn(90);
					
				}
				
				return Colors;
			}

}
