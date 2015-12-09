package simulation;

import characters.Car;
import characters.Cone;
import characters.Labels;
import characters.Sign;
import characters.TaskTable;
import compute.CarPanelController;
import compute.ProcessingController;
import compute.ProcessorPanelController;
import scheduler.Task;
import scheduler.Task.Nature;
import sensors.ConeSensor;
import sensors.OtherCarSensor;
import sensors.SensorData;
import sensors.SpeedSignSensor;
import sensors.StopSignSensor;

/**
 * This class will control both the car_panel and the processor panel.
 * 
 * This is where the simulation logic happens.
 * 
 * @author element
 *
 */
public class Simulate implements Runnable {
	private CarPanelController car_panel_controller;
	private ProcessorPanelController processor_panel_controller;
	private ProcessingController processing_controller;

	private int width;
	private int height;

	public Simulate(CarPanelController car_panel_controller, ProcessorPanelController processor_panel_controller,
			ProcessingController processing_controller, int width, int height) {
		this.car_panel_controller = car_panel_controller;
		this.processor_panel_controller = processor_panel_controller;
		this.processing_controller = processing_controller;

		this.width = width;
		this.height = height;
	}

	@Override
	public void run() {
		// test functions
		// test_car_panel_controller(this.car_panel_controller);

		// create obstacles
		setup_obstacles_demo();

		// create processor panel parts
		setup_processor_panel();

		// start the car moving
		this.car_panel_controller.set_target_speed(45);

		// set up all the periodic tasks
		setup_periodic_tasks();

		// every so often, hand off the processing state to the
		// processor_panel_controller
		while (true) {
			try {
				// update every 10 ms (100 fps)
				Thread.sleep(3);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// System.out.println(this.car_panel_controller.get_sensor_data().cone_sensor.distances);
			// provide the SensorData to the ProcessingController
			this.processing_controller.set_sensor_data(this.car_panel_controller.get_sensor_data());

			// provide the processing state to the processor panel
			this.processor_panel_controller.set_processing_state(this.processing_controller.get_state());

			// we want the simulation to keep going. Oce we get to about 8000,
			// reset the simulation.
			// setup_obstacles();

			// test code
			// test_sensor_data();
			// System.out.println(this.car_panel_controller.get_sensor_data().cone_sensor.cones);
		}
	}

	// set up all cones, signs, and other cars
	private void setup_obstacles_demo() {
		int sign_y = this.height / 8;
		int simulations = 20;

		// create all the signs
		int sign_interval = 3000;
		Sign s = new Sign(sign_interval, sign_y, Sign.SignType.SPEED);
		s.speed_limit = 45;
		for (int i = 0; i < simulations; i++) {
			this.car_panel_controller.submit_sign(s);

			s = new Sign(sign_interval * (i + 2), sign_y, Sign.SignType.SPEED);
			s.speed_limit = 45 + (i * 5);
		}

		// create all the cones
		int cone_interval = 1800;// 2000;
		Cone cone = new Cone(cone_interval - 500, (height / 2) + 25);
		for (int i = 0; i < simulations * 5 / 2; i++) {
			this.car_panel_controller.submit_cone(cone);

			cone = new Cone(cone_interval * (i + 2), (height / 2) + 25);
		}

		// create all the other cars
		int car_interval = 4000;
		Car car = new Car(car_interval, (height / 2) - 100);
		for (int i = 0; i < simulations; i++) {
			car.facing = Car.Facing.LEFT;
			car.speed = -20;
			this.car_panel_controller.submit_car(car);

			car = new Car(car_interval * (i + 2), (height / 2) - 100);
		}
	}

	// set up all cones, signs, and other cars
	private void setup_obstacles() {
		int sign_y = this.height / 8;

		// create all the signs
		Sign s = new Sign(300, sign_y, Sign.SignType.SPEED);
		s.speed_limit = 50;
		this.car_panel_controller.submit_sign(s);

		s = new Sign(2000, sign_y, Sign.SignType.SPEED);
		s.speed_limit = 70;
		this.car_panel_controller.submit_sign(s);

		s = new Sign(4000, sign_y, Sign.SignType.STOP);
		this.car_panel_controller.submit_sign(new Sign(4000, sign_y, Sign.SignType.STOP));

		s = new Sign(6000, sign_y, Sign.SignType.SPEED);
		s.speed_limit = 30;
		this.car_panel_controller.submit_sign(new Sign(6000, sign_y, Sign.SignType.SPEED));

		// create all the cones
		Cone cone = new Cone(700, height / 2);
		this.car_panel_controller.submit_cone(cone);

		cone = new Cone(2700, height / 2 + height / 16);
		this.car_panel_controller.submit_cone(cone);

		cone = new Cone(4700, height / 2 + height * 2 / 16);
		this.car_panel_controller.submit_cone(cone);

		// create all the other cars
		Car car = new Car(1400, height / 2);
		car.facing = Car.Facing.LEFT;
		car.speed = -20;
		this.car_panel_controller.submit_car(car);

		car = new Car(4000, height / 2);
		car.facing = Car.Facing.LEFT;
		car.speed = -40;
		this.car_panel_controller.submit_car(car);

		car = new Car(5400, height / 2);
		car.facing = Car.Facing.LEFT;
		car.speed = -20;
		this.car_panel_controller.submit_car(car);

		car = new Car(8400, height / 2);
		car.facing = Car.Facing.LEFT;
		car.speed = -20;
		this.car_panel_controller.submit_car(car);
	}

	// set up processor panel
	private void setup_processor_panel() {

		// table for periodic tasks
		TaskTable taskTable = new TaskTable(20, 127, 650);
		this.processing_controller.get_state().submit_task_table(taskTable);
		// table for aperiodic tasks
		taskTable = new TaskTable(20, 252, 650);
		this.processing_controller.get_state().submit_task_table(taskTable);
		// table for processor queue
		taskTable = new TaskTable(1000, 215, 250);
		this.processing_controller.get_state().submit_task_table(taskTable);

		// label for scheduler queue
		Labels label = new Labels(205, 40, "Scheduler Queue");
		this.processing_controller.get_state().submit_labels(label);
		// label for periodic tasks
		label = new Labels(5, 55, "Periodic");
		this.processing_controller.get_state().submit_labels(label);
		// label for aperiodic tasks
		label = new Labels(5, 165, "Aperiodic");
		this.processing_controller.get_state().submit_labels(label);
		// label for the processor queue
		label = new Labels(1125, 40, "Processor Queue");
		this.processing_controller.get_state().submit_labels(label);
		// label for processor
		label = new Labels(1560, 40, "Processor");
		this.processing_controller.get_state().submit_labels(label);
		// scheduler sign
		Sign sign = new Sign(775, 5, Sign.SignType.SCHEDULE, "EDF");
		this.processing_controller.get_state().submit_signs(sign);
		// processor sign
		sign = new Sign(1400, 60, Sign.SignType.SINGLE_PROCESSOR);
		this.processing_controller.get_state().submit_signs(sign);

	}

	/**
	 * set up all the periodic tasks. This is all I need to do because the
	 * aperiodic tasks will be triggered based on the results of the periodic
	 * tasks. For instance, when a periodic task which reads sensor data
	 * finsihes, an aperiodic task which preforms a task dependtant on that
	 * sensor data will be triggered.
	 * 
	 * this automatic triggering happens within the processing controller.
	 */
	private void setup_periodic_tasks() {
		int sensor_comp_time = 500;
		int sensor_period = 2000;
		int sensor_deadline = sensor_period;
		Nature nature = Task.Nature.PERIODIC;

		// add a task to read each of the sensor's data
		// read cone sensor
		this.processing_controller.add_task(new Task(sensor_comp_time, sensor_period, sensor_deadline, nature,
				Task.Action.READ_CONE_SENSOR, this.processing_controller, this.car_panel_controller, 0));

		this.processing_controller.add_task(new Task(sensor_comp_time, sensor_period, sensor_deadline, nature,
				Task.Action.READ_OTHER_CAR_SENSOR, this.processing_controller, this.car_panel_controller, 0));

		this.processing_controller.add_task(new Task(sensor_comp_time, sensor_period, sensor_deadline, nature,
				Task.Action.READ_SPEED_SIGN_SENSOR, this.processing_controller, this.car_panel_controller, 0));

		// this.processing_controller.add_task(new Task(sensor_comp_time,
		// sensor_period, sensor_deadline, nature,
		// Task.Action.READ_STOP_SIGN_SENSOR, this.processing_controller,
		// this.car_panel_controller, 0));

		this.processing_controller.add_task(new Task(sensor_comp_time, sensor_period * 2, sensor_deadline, nature,
				Task.Action.READ_LANE_SENSOR, this.processing_controller, this.car_panel_controller, 0));
	}

	/**
	 * tests the sensor data to make sure its correct
	 */
	private void test_sensor_data() {
		SensorData sensor_data = this.car_panel_controller.get_sensor_data();
		StringBuilder sb = new StringBuilder();

		// cone sensor
		ConeSensor cone_sensor = sensor_data.cone_sensor;

		sb.append("cone_sensor\n");
		sb.append(cone_sensor.cones.toString() + "\n");
		sb.append(cone_sensor.distances.toString() + "\n\n");

		// other car sensor
		OtherCarSensor other_car_sensor = sensor_data.other_car_sensor;

		sb.append("other_car_sensor\n");
		sb.append(other_car_sensor.other_cars.toString() + "\n");
		sb.append(other_car_sensor.distances.toString() + "\n\n");

		// speed sign sensor
		SpeedSignSensor speed_sign_sensor = sensor_data.speed_sign_sensor;

		sb.append("speed_sign_sensor\n");
		sb.append(speed_sign_sensor.signs.toString() + "\n");
		sb.append(speed_sign_sensor.distances.toString() + "\n\n");

		// stop sign sensor
		StopSignSensor stop_sign_sensor = sensor_data.stop_sign_sensor;

		sb.append("stop_sign_sensor\n");
		sb.append(stop_sign_sensor.signs.toString() + "\n");
		sb.append(stop_sign_sensor.distances.toString() + "\n\n");

		System.out.println(sb);
	}

	// preforms test functions
	// this is also an example of how to use the car controller
	private void test_car_panel_controller(CarPanelController car_controller) {
		int obstacle_start_x = width / 2;
		int obstacle_start_y = height / 4;

		// submit other car, sign, cones
		Car car = (new Car(obstacle_start_x - 300, obstacle_start_y));
		car.facing = Car.Facing.LEFT;
		car.speed = 15;
		car_controller.submit_car(car);

		car_controller.submit_sign(new Sign(obstacle_start_x - 600, obstacle_start_y, Sign.SignType.STOP));

		car_controller.submit_sign(new Sign(obstacle_start_x - 900, obstacle_start_y, Sign.SignType.SPEED));

		car_controller.submit_cone(new Cone(obstacle_start_x, obstacle_start_y));

		// move the car
		car_controller.set_target_speed(20);
		car_controller.move_up(50);

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		car_controller.set_target_speed(40);
		car_controller.move_down(100);

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		car_controller.move_up(50);
		car_controller.set_target_speed(0);
		car_controller.finish();
	}
}
