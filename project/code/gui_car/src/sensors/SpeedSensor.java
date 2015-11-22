package sensors;

import compute.CarPanelState;

public class SpeedSensor extends Sensor {

	public SpeedSensor(CarPanelState state) {
		super(state);
	}

	public int getCarSpeed() {
		return this.car_panel_state.main_car.speed;
	}
}
