package sensors;

import characters.MainCar;
import compute.CarPanelState;

/**
 * detects whether or not the MainCar is within its lane
 * 
 * @author element
 *
 */
public class LaneSensor extends Sensor {
	public boolean within_lane;

	public LaneSensor(CarPanelState state) {
		super(state);
	}

	@Override
	protected void compute() {
		// determine if the car is within the lane
		this.within_lane = true;
		MainCar mc = this.car_panel_state.main_car;

		if (Math.abs(mc.origional_y_pos - mc.y_pos) > 20) {
			this.within_lane = false;
		}
	}

}
