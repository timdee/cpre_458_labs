package sensors;

import java.util.ArrayList;

import characters.Cone;
import compute.CarPanelState;

public class ConeSensor extends Sensor {
	public ArrayList<Cone> cones;
	public ArrayList<Double> distances;

	public ConeSensor(CarPanelState state) {
		super(state);
	}

	@Override
	protected void compute() {
		this.cones = new ArrayList<Cone>();
		this.distances = new ArrayList<Double>();

		for (Cone cone : this.car_panel_state.obstacles) {
			if (is_within_range(this.car_panel_state.main_car, cone)) {
				// if within range of sensor
				cones.add(cone);

				// add in parallel the distance to the sign
				distances.add(compute_distance(this.car_panel_state.main_car, cone));
			}
		}
	}
}
