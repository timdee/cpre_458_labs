package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * contains the car and processor panels.
 * 
 * @author element
 *
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public final int width = 900;
	public final int height = 900;

	/**
	 * constructor which knows how to create this Frame.
	 */
	public MainFrame() {
		// call parent constructor
		super("Real Time Car Simulation");

		// set the layout of the thing
		this.setLayout(new GridLayout(1, 0));

		// add the panels to this frame
		this.add(new CarPanel(this.width, this.height));
		this.add(new ProcessorPanel());

		// adjust settings of this frame
		this.setSize(this.width, this.height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
