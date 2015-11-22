package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;

import compute.CarPanelController;

/**
 * contains the car and processor panels.
 * 
 * @author element
 *
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

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
		this.add(car_panel);
		this.add(new ProcessorPanel());

		// start any necessary threads
		CarPanelController car_controller = new CarPanelController(car_panel);
		Thread car_controller_thread = new Thread(car_controller);
		car_controller_thread.start();

		// adjust settings of this frame
		this.setSize(this.width, this.height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		// comment out to not do test functions
		test(car_controller);
	}

	// preforms test functions
	private void test(CarPanelController car_controller) {
		car_controller.finish();
	}
}
