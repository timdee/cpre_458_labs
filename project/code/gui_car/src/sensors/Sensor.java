package sensors;

import characters.Character;
import compute.CarPanelState;

public abstract class Sensor {
	protected CarPanelState car_panel_state;

	// the number of pixels away the sensor can detect an object
	protected int range;

	public Sensor(CarPanelState state) {
		this.car_panel_state = new CarPanelState(state);
		this.range = 1000;

		compute();
	}

	/**
	 * computes the distance between two charaters
	 * 
	 * @param c_1
	 * @param c_2
	 * @return
	 */
	protected double compute_distance(Character c_1, Character c_2) {
		int x_dist = Math.abs(c_1.x_pos - c_2.x_pos);
		int y_dist = Math.abs(c_1.y_pos - c_2.y_pos);

		return Math.sqrt(x_dist ^ 2 + y_dist ^ 2);
	}

	/**
	 * determines whether c_1 is within range of c_2
	 * 
	 * @param c_1
	 * @param c_2
	 * @return
	 */
	protected boolean is_within_range(Character c_1, Character c_2) {
		return (compute_distance(c_1, c_2) < range);
	}

	/**
	 * computes all of the instance variables
	 * 
	 * @return
	 */
	protected abstract void compute();
}
