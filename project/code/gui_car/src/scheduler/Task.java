package scheduler;

import characters.Cone;
import characters.MainCar;
import compute.CarPanelController;
import compute.CarPanelState;
import compute.ProcessingController;
import sensors.SensorData;

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
		int actuator_comp_time = 50;
		int actuator_deadline = 100;
		Nature actuator_nature = Task.Nature.APERIODIC;
		int actuator_movement_amount = 80;

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
			CarPanelState car_panel_state = this.car_panel_controller.car_panel.getState();
			SensorData sensor_data = this.car_panel_controller.get_sensor_data();

			int move_up = 0;
			int move_down = 0;
			
			System.out.println("cone sensor:\t" + sensor_data.cone_sensor.cones);

			// what to do to avoid hitting the cone
			// if the bottom of the car will hit the cone, move up
			for (Cone cone : sensor_data.cone_sensor.cones) {
				if (will_collide(car_panel_state.main_car, cone)) {
					// check if the top of the cone will hit the car
					if ((cone.y_pos < car_panel_state.main_car.y_pos + car_panel_state.main_car.height)
							&& (cone.y_pos > car_panel_state.main_car.y_pos)) {
						// the bottom of the car will hit, so move up
						move_down++;
					} else {
						// the top of the car will hit, so move down
						// this.processing_controller.add_task(task);
						move_up++;
					}
				}
			}

			// if we need to move decide what task we need to insert
			if (move_up + move_down > 0) {
				// see if more obstacles would be avoided by moving up or down
				if (move_down > move_up) {
					Task task = new Task(actuator_comp_time, 0, actuator_deadline, actuator_nature,
							Task.Action.MOVE_DOWN_CAR, this.processing_controller, this.car_panel_controller,
							actuator_movement_amount);
					this.processing_controller.add_task(task);
				} else {
					Task task = new Task(actuator_comp_time, 0, actuator_deadline, actuator_nature,
							Task.Action.MOVE_UP_CAR, this.processing_controller, this.car_panel_controller,
							actuator_movement_amount);
					this.processing_controller.add_task(task);
				}
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

	private boolean will_collide(MainCar main_car, Cone c) {
		// calculate the overlap
		boolean overlap = false;

		int car_top = main_car.y_pos;
		int car_bottom = main_car.y_pos + main_car.height;
		int character_top = c.y_pos;
		int character_bottom = c.y_pos + c.height;

		// if character top is inbetween car top and bottom then collision
		if ((character_top < car_bottom) && (character_top > car_top)) {
			overlap = true;
		}

		// if car bottom is between cone top and bottom, then collision
		if ((character_bottom < car_bottom) && (character_bottom > car_top)) {
			overlap = true;
		}

		//System.out.println(overlap);
		return overlap;
	}
}
