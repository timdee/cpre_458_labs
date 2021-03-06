package sensors;

import compute.CarPanelState;

/**
 * contains all of the sensors. This is a single storage spot for all data given
 * by sensors on the car. Each sensor knows how to obtain its data from the
 * CarPanelState.
 *
 */
public class SensorData {
	public StopSignSensor stop_sign_sensor;
	public SpeedSignSensor speed_sign_sensor;
	public OtherCarSensor other_car_sensor;
	public ConeSensor cone_sensor;
	public LaneSensor lane_sensor;

	public SensorData(CarPanelState state) {
		// construct all sensors
		this.stop_sign_sensor = new StopSignSensor(state);
		this.other_car_sensor = new OtherCarSensor(state);
		this.cone_sensor = new ConeSensor(state);
		this.speed_sign_sensor = new SpeedSignSensor(state);
		this.lane_sensor = new LaneSensor(state);
	}
}
