package TheMazeProject;

import MazebotSim.MazebotSimulation;

public class SimulatedMain {

	public static void main(String[] args) {
		
		
		MazebotSimulation sim = new MazebotSimulation("Mazes/maze_1_3by4.png", 1.4,  1.05);
		sim.setRobotPosition(0.525, 0.175, 90);
		sim.startSimulation();
			
		MazeSolverMain.main(new String[0]);
				
		sim.stopSimulation();
	}

}
