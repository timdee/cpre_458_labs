package sensors;

import java.util.ArrayList;

import characters.Car;
import compute.CarPanelState;

public class OtherCarSensor extends Sensor {
	public ArrayList<Car> other_cars;
	public ArrayList<Double> distances;

	public OtherCarSensor(CarPanelState state) {
		super(state);
	}

	@Override
	protected void compute() {
		this.other_cars = new ArrayList<Car>();
		this.distances = new ArrayList<Double>();
		
		for (Car car : this.car_panel_state.other_cars) {
			if (is_within_range(this.car_panel_state.main_car, car)) {
				// if within range of sensor
				other_cars.add(car);

				// add in parallel the distance to the sign
				distances.add(compute_distance(this.car_panel_state.main_car, car));
			}
		}
	}
}
