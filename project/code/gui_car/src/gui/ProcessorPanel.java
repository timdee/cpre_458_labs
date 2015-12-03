package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import characters.Labels;
import characters.Sign;
import characters.TaskTable;
import compute.ProcessingState;
import scheduler.EDF;
import scheduler.Task;

public class ProcessorPanel extends JPanel {
	private static final long serialVersionUID = 1L;


	private ProcessingState state;
	
	public ProcessorPanel() {
		super();

		// TODO add new panels with the different processor parts

		// set properties of the thing
		setOpaque(true);
		setBackground(Color.WHITE);
		// set the width and height


		// initialize the state
		this.state = new ProcessingState(new EDF(),1);

		// make a new main car
		//state.main_car = new MainCar(w, h);

		// make a new road
		//state.road = new Road(w, h);
	}

	public ProcessingState getState() {
		return this.state;
	}

	public void setState(ProcessingState state) {
		this.state = state;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawSchedulerQueueTasks(g);
		drawTaskTables(g);
		drawLabels(g);
		draw_signs(g);
	}


	/**
	 * draw the tasks in the schedule que
	 */
	private void drawSchedulerQueueTasks(Graphics g) {
		for (Task t : this.state.scheduler_task_queue) {
			t.taskBlock.draw(g);

		}

	}



	/**
	 * draw the task tables
	 */
	private void drawTaskTables(Graphics g) {
		for (TaskTable t : this.state.taskHolders) {
			t.draw(g);

		}

	}

	/**
	 * draw the labels for the processor panel
	 */
	private void drawLabels(Graphics g) {
		for (Labels l : this.state.labels) {
			l.draw(g);

		}

	}
	
	/**
	 * draw the sign array
	 *
	 * @param g
	 */
	private void draw_signs(Graphics g) {
		for (Sign c : state.signs) {
			c.draw(g);
		}
	}
}
