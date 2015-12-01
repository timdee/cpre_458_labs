package sensors;

import characters.Character;
import characters.MainCar;
import compute.CarPanelState;

public abstract class Sensor {
	protected CarPanelState car_panel_state;

	// the number of pixels away the sensor can detect an object
	protected int range;

	public Sensor(CarPanelState state) {
		this.car_panel_state = state;
		this.range = 1500;

		compute();
	}

	/**
	 * computes the distance between two charaters
	 * 
	 * @param c_1
	 * @param c_2
	 * @return
	 */
	// protected double compute_distance(Character c_1, Character c_2) {
	// int x_dist = Math.abs(c_1.x_pos - c_2.x_pos);
	// int y_dist = Math.abs(c_1.y_pos - c_2.y_pos);
	//
	// return Math.sqrt(x_dist ^ 2 + y_dist ^ 2);
	// // return x_dist;
	// }

	/**
	 * computing distance is a little different for the main car. Distance
	 * referrs to distance of the object in front of the car.
	 * 
	 * @param c_1
	 * @param c_2
	 * @return
	 */
	protected double compute_distance(MainCar c_1, Character c_2) {
		//int x_dist = c_2.x_pos - (c_1.total_moved + c_1.x_pos + c_1.width);
		int x_dist = c_2.origional_x_pos - (c_1.total_moved + c_1.x_pos + c_1.width);
		
		return (x_dist > 0) ? x_dist : 0;
	}

	/**
	 * determines whether c_1 is within range of c_2
	 * 
	 * @param c_1
	 * @param c_2
	 * @return
	 */
	protected boolean is_within_range(MainCar c_1, Character c_2) {
		return ((compute_distance(c_1, c_2) < range) && (compute_distance(c_1, c_2) > 0));
	}

	/**
	 * computes all of the instance variables
	 * 
	 * @return
	 */
	protected abstract void compute();
}
