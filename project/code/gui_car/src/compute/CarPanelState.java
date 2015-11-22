package compute;

import java.util.ArrayList;

import characters.Car;
import characters.Cone;
import characters.Sign;

/**
 * holds all variables related to the state of the car pannel. Modifying this
 * will affect the car panel.
 * 
 * @author element
 *
 */
public class CarPanelState {
	public Car main_car;

	public ArrayList<Cone> obstacles;
	public ArrayList<Car> other_cars;
	public ArrayList<Sign> signs;

	public CarPanelState() {
		reset();
	}

	/**
	 * copy constructor
	 */
	public CarPanelState(CarPanelState state) {
		this.main_car = new Car(state.main_car);

		this.obstacles = new ArrayList<Cone>(state.obstacles);
		this.other_cars = new ArrayList<Car>(state.other_cars);
		this.signs = new ArrayList<Sign>(state.signs);
	}

	private void reset() {
		main_car = new Car();
		obstacles = new ArrayList<Cone>();
		other_cars = new ArrayList<Car>();
		signs = new ArrayList<Sign>();
	}
}
