package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;

import compute.CarPanelController;
import compute.ProcessingController;
import compute.ProcessorPanelController;
import scheduler.SchedulingAlgorithm;
import simulation.Simulate;

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
	public MainFrame(SchedulingAlgorithm scheduling_algorithm, SchedulingAlgorithm overload_scheduling_algorithm,
			int n_processors) {
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
		ProcessorPanelController processor_controller = new ProcessorPanelController(processor_panel);
		ProcessingController processing_controller = new ProcessingController(scheduling_algorithm,
				overload_scheduling_algorithm, n_processors);

		Thread car_controller_thread = new Thread(car_controller);
		Thread processor_controller_thread = new Thread(processor_controller);
		Thread processing_controller_thread = new Thread(processing_controller);

		// added by Brent
		processor_panel.setState(processing_controller.get_state());

		// adjust settings of this frame
		this.setSize(this.width, this.height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// start these threads, but they don't act atonamously. Simulate thread
		// will use them to perform the simulation.
		car_controller_thread.start();
		processor_controller_thread.start();
		processing_controller_thread.start();

		// START SIMULATION THREAD
		Simulate simulator = new Simulate(car_controller, processor_controller, processing_controller, width, height);
		Thread simulator_thread = new Thread(simulator);
		simulator_thread.start();
	}
}
