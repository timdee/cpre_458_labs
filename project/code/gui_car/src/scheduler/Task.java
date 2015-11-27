package scheduler;

import compute.CarPanelController;
import compute.CarPanelState;
import compute.ProcessingController;

/**
 * Represents a task to be completed by a processor
 * 
 * @author element
 *
 */
public class Task {
	// how the task sould be run (just once) or (at period for ever)
	public enum Nature {
		PERIODIC, APERIODIC;
	}

	// says what action should be conducted upon task completion
	public enum Action {
		NONE, SET_CAR_SPEED, MOVE_UP_CAR, MOVE_DOWN_CAR, READ_CONE_SENSOR, READ_OTHER_CAR_SENSOR, READ_SPEED_SIGN_SENSOR, READ_STOP_SIGN_SENSOR;
	}

	public int deadline;
	public int computation_time_remaining;
	public int computation_time_origional;
	public int period;

	public Nature nature;
	public Action action;

	public ProcessingController processing_controller;
	public CarPanelController car_panel_controller;

	// either the car speed or elevation depending on the task.
	public int set_point;

	public Task(int computation_time, int period, int deadline, Nature nature, Action action,
			ProcessingController processing_controller, CarPanelController car_panel_controller, int set_point) {
		this.computation_time_remaining = computation_time;
		this.computation_time_origional = computation_time;
		this.period = period;
		this.deadline = deadline;

		this.nature = nature;
		this.action = action;

		this.processing_controller = processing_controller;
		this.car_panel_controller = car_panel_controller;

		this.set_point = set_point;
	}

	public Task(Task task) {
		this.computation_time_remaining = task.computation_time_remaining;
		this.computation_time_origional = task.computation_time_origional;
		this.period = task.period;
		this.deadline = task.deadline;

		this.nature = task.nature;
		this.action = task.action;

		this.processing_controller = task.processing_controller;
		this.car_panel_controller = task.car_panel_controller;

		this.set_point = task.set_point;
	}

	/**
	 * preform the action specified on the task.
	 * 
	 * submit aperiodic tasks based on the sensor data.
	 */
	public void preform_action() {
		switch (this.action) {
		case SET_CAR_SPEED:
			this.car_panel_controller.set_target_speed(this.set_point);
			break;
		case MOVE_UP_CAR:
			this.car_panel_controller.move_up(this.set_point);
			break;
		case MOVE_DOWN_CAR:
			this.car_panel_controller.move_down(this.set_point);
			break;
		case READ_CONE_SENSOR:
			System.out.println("read cone sensor");
			// determine if the car is going to hit the cone, and what to do to
			// avoid it
			// overlap is relative to car
			CarPanelState car_panel_state = this.car_panel_controller.car_panel.getState();
			// upper_overlap = car_panel_state.main_car.y_pos -;
			// lower_overlap = this.car_panel_controller.car_panel.getState().;

			// if the bottom of the car will hit the cone, move up
			if (true) {

			} else {
				// the top of the car will hit, so move down
				// this.car_panel_controller.move_down(amount);
			}
			break;
		case READ_OTHER_CAR_SENSOR:
			System.out.println("read other car sensor");

			break;
		case READ_SPEED_SIGN_SENSOR:
			System.out.println("read speed sign sensor");

			break;
		case READ_STOP_SIGN_SENSOR:
			System.out.println("read stop sign sensor");

			break;
		}
	}
}
