package sensors;

import compute.CarPanelState;

/**
 * contains all of the sensors. This is a single storage spot for all data given
 * by sensors on the car. Each sensor knows how to obtain its data from the
 * CarPanelState.
 *
 */
public class SensorData {
	public SpeedSensor speed_sensor;
	public StopSignSensor stop_sign_sensor;
	public OtherCarSensor other_car_sensor;

	public SensorData(CarPanelState state) {
		// construct all sensors
		this.speed_sensor = new SpeedSensor(state);
		this.stop_sign_sensor = new StopSignSensor(state);
		this.other_car_sensor = new OtherCarSensor(state);
	}
}
