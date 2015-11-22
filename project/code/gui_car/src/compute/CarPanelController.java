package compute;

import gui.CarPanel;
import sensors.SensorData;

/**
 * this class controls the car panel. The Idea behind this is that anything the
 * car panel needs to do will be controled by this class.
 * 
 * This provides a nice interface for interacting with the gui.
 * 
 * Needs to be able to perform all the functions of the tasks.
 * 
 * Will also update the positions / colors of obstacles.
 * 
 * This will need to run on its own thread.
 */
public class CarPanelController implements Runnable {
	CarPanel car_panel;
	CarPanelState target_state;

	private volatile boolean running;

	public CarPanelController(CarPanel car_panel) {
		this.car_panel = car_panel;
		this.target_state = new CarPanelState(car_panel.getState());

		this.running = true;
	}

	@Override
	public void run() {
		while (running) {
			try {
				// update every 10 ms (100 fps)
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}

			update_panel();
		}
	}

	/**
	 * the idea is that we're always moving toward the target_state. Each update
	 * will make incremental steps toward the target state if it is different
	 * from the current state. Interactions from outsize the thread will be able
	 * to change the target state.
	 */
	private void update_panel() {
		// update the targets if necessary
		update_target_state();

		// call methods to perform various specific updates
		move_car();
		move_obstacles();
		move_other_cars();

		// cause the panel and its children to repaint
		this.car_panel.repaint();
		this.car_panel.revalidate();
	}

	private void update_target_state() {

	}

	private void move_car() {
		CarPanelState state = this.car_panel.getState();

		// if x_pos is differing
		if ((state.main_car.x_pos - target_state.main_car.x_pos) != 0) {
			if ((state.main_car.x_pos - target_state.main_car.x_pos) < 0) {
				// x_pos is smaller than it should be
				state.main_car.x_pos++;
			} else {
				// x_pos is bigger than it shoudl be
				state.main_car.x_pos--;
			}
		}

		// if y_pos is differing
		if ((state.main_car.y_pos - target_state.main_car.y_pos) != 0) {
			if ((state.main_car.y_pos - target_state.main_car.y_pos) < 0) {
				// y_pos is smaller than it should be
				state.main_car.y_pos++;
			} else {
				// y_pos is bigger than it shoudl be
				state.main_car.y_pos--;
			}
		}

		// if color differing
		if ((state.main_car.color != target_state.main_car.color)) {
			state.main_car.color = target_state.main_car.color;
		}

		this.car_panel.setState(state);
	}

	/**
	 * move all obstacles if they require movement
	 */
	private void move_obstacles() {
		// TODO
	}

	/**
	 * move any other cars
	 */
	private void move_other_cars() {
		// TODO
	}

	public void stop() {
		this.running = false;
	}

	/*****************************************************
	 * provide methods to update the position of the car
	 * ***************************************************
	 */
	/**
	 * slow down
	 */
	public void slow_down() {

	}

	/**
	 * speed up
	 */
	public void speed_up() {

	}

	/**
	 * move up
	 */
	public void move_up() {

	}

	/**
	 * move down
	 */
	public void move_down() {

	}

	public void finish() {
		this.target_state.main_car.x_pos = 2000 - this.target_state.main_car.width * 2;
	}

	/*****************************************************
	 * provide methods to get status information for car
	 * ***************************************************
	 */
	/**
	 * get speed
	 */
	public int get_speed() {
		CarPanelState state = this.car_panel.getState();
		return state.main_car.speed;
	}

	/**
	 * read all of the sensors on the car
	 */
	public SensorData get_sensor_data() {
		// build and return sensor data
		return new SensorData(this.car_panel.getState());
	}
}
