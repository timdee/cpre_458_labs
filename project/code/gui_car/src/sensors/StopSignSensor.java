package sensors;

import java.util.ArrayList;

import characters.Sign;
import characters.Sign.SignType;
import compute.CarPanelState;

public class StopSignSensor extends Sensor {
	public ArrayList<Sign> signs;
	public ArrayList<Double> distances;

	public StopSignSensor(CarPanelState state) {
		super(state);
	}

	@Override
	protected void compute() {
		this.signs = new ArrayList<Sign>();
		this.distances = new ArrayList<Double>();

		for (Sign sign : this.car_panel_state.signs) {
			if (sign.type == SignType.STOP && is_within_range(this.car_panel_state.main_car, sign)) {
				// if within range of sensor
				signs.add(sign);

				// add in parallel the distance to the sign
				distances.add(compute_distance(this.car_panel_state.main_car, sign));
			}
		}
	}
}
