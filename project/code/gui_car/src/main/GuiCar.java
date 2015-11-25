package main;

import gui.MainFrame;
import scheduler.EDF;
import scheduler.SchedulingAlgorithm;

/**
 * this class is designed to be the main class to run everything. It's only
 * responsibility is to start the gui window.
 */
public class GuiCar {

	public static void main(String[] args) {
		// set up the scheduling algorithm
		SchedulingAlgorithm scheduling_algorithm = new EDF();

		// start the gui window
		new MainFrame(scheduling_algorithm);
	}
}
