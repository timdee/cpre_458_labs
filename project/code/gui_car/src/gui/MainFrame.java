package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;

import characters.Car;
import characters.Cone;
import characters.Sign;
import compute.CarPanelController;

/**
 * contains the car and processor panels.
 * 
 * @author element
 *
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	// do not change size anymore... it might screw things up
	public final int width = 2000;
	public final int height = 600;

	/**
	 * constructor which knows how to create this Frame.
	 */
	public MainFrame() {
		// call parent constructor
		super("Real Time Car Simulation");

		// set the layout of the thing
		this.setLayout(new GridLayout(0, 1));

		// add the panels to this frame
		CarPanel car_panel = new CarPanel(this.width, this.height);
		ProcessorPanel processor_panel = new ProcessorPanel();
		this.add(car_panel);
		this.add(processor_panel);

		// start any necessary threads
		CarPanelController car_controller = new CarPanelController(car_panel);
		Thread car_controller_thread = new Thread(car_controller);
		car_controller_thread.start();

		// adjust settings of this frame
		this.setSize(this.width, this.height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		// START SIMULATION THREAD

		// comment out to not do test functions
		test(car_controller);
	}

	// preforms test functions
	// this is also an example of how to use the car controller
	private void test(CarPanelController car_controller) {
		int obstacle_start_x = width / 2;
		int obstacle_start_y = height / 4;

		// submit other car, sign, cones
		Car car = (new Car(obstacle_start_x - 300, obstacle_start_y));
		car.facing = Car.Facing.LEFT;
		car.speed = 15;
		car_controller.submit_car(car);

		car_controller.submit_sign(new Sign(obstacle_start_x - 600, obstacle_start_y, Sign.SignType.STOP));

		car_controller.submit_sign(new Sign(obstacle_start_x - 900, obstacle_start_y, Sign.SignType.SPEED));

		car_controller.submit_cone(new Cone(obstacle_start_x, obstacle_start_y));

		// move the car
		car_controller.set_target_speed(20);
		car_controller.move_up(50);

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		car_controller.set_target_speed(40);
		car_controller.move_down(100);

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		car_controller.move_up(50);
		car_controller.set_target_speed(0);
		car_controller.finish();
	}
}
