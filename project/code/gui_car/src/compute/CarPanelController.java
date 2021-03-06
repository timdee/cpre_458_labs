package compute;

import characters.Car;
import characters.Cone;
import characters.Sign;
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
	public CarPanel car_panel;
	public volatile CarPanelState target_state;

	// increase speed every this number of increments
	public final static int speed_increment = 15;
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
		move_signs();
		move_road();

		// cause the panel and its children to repaint
		this.car_panel.repaint();
		this.car_panel.revalidate();
	}

	private void update_target_state() {

	}

	/**
	 * move the main car
	 * 
	 */
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

		// if speed is differing
		if ((state.main_car.speed - target_state.main_car.speed) != 0) {
			if ((state.main_car.speed - target_state.main_car.speed) < 0) {
				// y_pos is smaller than it should be
				state.main_car.speed++;
			} else {
				// y_pos is bigger than it shoudl be
				state.main_car.speed--;
			}
		}

		// if color differing
		if ((state.main_car.color != target_state.main_car.color)) {
			state.main_car.color = target_state.main_car.color;
		}

		// update the total distance moved by the main car based on its speed
		state.main_car.total_moved += state.main_car.speed / this.speed_increment;

		//System.out.println(state.main_car.x_pos);

		this.car_panel.setState(state);
	}

	/**
	 * Cones Signs and other cars move differently than the main car.
	 * 
	 * Their movement depends on the speed of the main car. Plus their own
	 * movement in the case of other cars.
	 */
	/**
	 * move all obstacles if they require movement
	 */
	private void move_obstacles() {
		CarPanelState state = this.car_panel.getState();

		// for each cone
		for (int i = 0; i < state.obstacles.size(); i++) {
			// move toward the car at the car's speed
			state.obstacles.get(i).x_pos -= state.main_car.speed / speed_increment;
		}

		this.car_panel.setState(state);
	}

	/**
	 * move any other cars
	 */
	private void move_other_cars() {
		CarPanelState state = this.car_panel.getState();

		// for each cone
		for (int i = 0; i < state.other_cars.size(); i++) {
			// move toward the car at the car's speed. This accounts for the
			// main_car movement
			state.other_cars.get(i).x_pos -= state.main_car.speed / speed_increment;
			this.target_state.other_cars.get(i).x_pos -= state.main_car.speed / speed_increment;

			// now add in movement of this car
			// if x_pos is differing
			if ((state.other_cars.get(i).x_pos - target_state.other_cars.get(i).x_pos) != 0) {
				if ((state.other_cars.get(i).x_pos - target_state.other_cars.get(i).x_pos) < 0) {
					// x_pos is smaller than it should be
					state.other_cars.get(i).x_pos++;
				} else {
					// x_pos is bigger than it shoudl be
					state.other_cars.get(i).x_pos--;
				}
			}

			// if y_pos is differing
			if ((state.other_cars.get(i).y_pos - target_state.other_cars.get(i).y_pos) != 0) {
				if ((state.other_cars.get(i).y_pos - target_state.other_cars.get(i).y_pos) < 0) {
					// y_pos is smaller than it should be
					state.other_cars.get(i).y_pos++;
				} else {
					// y_pos is bigger than it shoudl be
					state.other_cars.get(i).y_pos--;
				}
			}
		}

		this.car_panel.setState(state);
	}

	/**
	 * move any other cars
	 */
	private void move_signs() {
		CarPanelState state = this.car_panel.getState();

		// for each cone
		for (int i = 0; i < state.signs.size(); i++) {
			// move toward the car at the car's speed
			state.signs.get(i).x_pos -= state.main_car.speed / speed_increment;
		}

		this.car_panel.setState(state);
	}

	/**
	 * move the road
	 */
	private void move_road() {
		CarPanelState state = this.car_panel.getState();

		// if away from target state, move toward target state
		if ((state.road.line_speed - target_state.road.line_speed) != 0) {
			if ((state.road.line_speed - target_state.road.line_speed) < 0) {
				// x_pos is smaller than it should be
				state.road.line_speed++;
			} else {
				// x_pos is bigger than it should be
				state.road.line_speed--;
			}
		}

		// update lines to be in the proper spot based on line speed
		state.road.line_offset = (state.road.line_offset + (state.road.line_speed / speed_increment)) % (2000 / 10);

		this.car_panel.setState(state);
	}

	public void stop() {
		this.running = false;
	}

	/*****************************************************
	 * provide methods to submit obstacles
	 * 
	 * once an ostacle goes off screen, it will reset to the end.
	 * 
	 * This means obstacles will come like a they were on a circular conveyer
	 * belt.
	 * 
	 * ***************************************************
	 */
	/**
	 * adds a cone at the point specified in the code
	 */
	public void submit_cone(Cone cone) {
		CarPanelState state = this.car_panel.getState();

		state.obstacles.add(cone);
		this.target_state.obstacles.add(new Cone(cone));

		this.car_panel.setState(state);
	}

	/**
	 * adds a sign at the point specified in the code
	 */
	public void submit_sign(Sign sign) {
		CarPanelState state = this.car_panel.getState();

		state.signs.add(sign);
		this.target_state.signs.add(new Sign(sign));

		this.car_panel.setState(state);
	}

	/**
	 * adds a car at the point specified in the code
	 */
	public void submit_car(Car car) {
		CarPanelState state = this.car_panel.getState();

		state.other_cars.add(car);

		// set the car to move toward our car
		this.target_state.other_cars.add(new Car(car));
		target_state.other_cars.get(target_state.other_cars.size() - 1).x_pos = -1 * car.width;

		this.car_panel.setState(state);
	}

	/*****************************************************
	 * provide methods to update the position of the car
	 * ***************************************************
	 */
	/**
	 * sets the target speed of the car. target_speed corresponds to the speed
	 * limit as depicted on the signs.
	 */
	public void set_target_speed(int target_speed) {
		this.target_state.main_car.speed = target_speed;
		this.target_state.road.line_speed = -1 * target_speed;
	}

	/**
	 * move up
	 */
	public void move_up(int amount) {
		this.target_state.main_car.y_pos -= amount;
	}

	/**
	 * move down
	 */
	public void move_down(int amount) {
		this.target_state.main_car.y_pos += amount;
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
