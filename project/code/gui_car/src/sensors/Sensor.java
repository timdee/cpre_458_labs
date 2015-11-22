package sensors;

import compute.CarPanelState;

public abstract class Sensor {
	protected CarPanelState car_panel_state;

	public Sensor(CarPanelState state) {
		this.car_panel_state = new CarPanelState(state);
	}
}
