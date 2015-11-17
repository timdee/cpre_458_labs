package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import characters.Car;

public class CarPanel extends JPanel {
	private static final long serialVersionUID = -7055815099049883098L;

	private int width;
	private int height;
	private Car main_car;

	private ArrayList<Character> obstacles;
	private ArrayList<Character> other_cars;

	public CarPanel(int w, int h) {
		// call parent constructor
		super();

		// set properties of the thing
		setOpaque(true);
		setBackground(Color.GREEN);

		// set the width and height
		this.width = w;
		this.height = h;

		// make a new main car
		this.main_car = new Car();

		main_car.color = Color.BLUE;
		main_car.height = (int) (this.height * .1);
		main_car.width = main_car.height / 2;
		main_car.x_pos = ((int) ((this.width / 2) + width * .05));
		main_car.y_pos = this.height - main_car.height - (int) (this.height * .05);

		// TODO make obstacles
		this.obstacles = new ArrayList<Character>();

		// TODO make other_cars
		this.other_cars = new ArrayList<Character>();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// call methods to preform different drawing operations
		draw_background(g);
		draw_road(g);
		draw_obstacles(g);
		draw_other_cars(g);
		draw_car(g, main_car);
	}

	/**
	 * draw the background color
	 */
	private void draw_background(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, width, height);
	}

	/**
	 * draw the road on which the car will drive
	 */
	private void draw_road(Graphics g) {
		// draw the vertical road
		g.setColor(Color.DARK_GRAY);
		g.fillRect(((int) ((width * .8) / 2)), 0, ((int) (width * .2)), height);

		// draw the horizontal road
		g.fillRect(0, ((int) ((height - (width * .2)) / 2)), width, ((int) (width * .2)));

		// TODO draw vertical lines

		// TODO draw horizontal lines
	}

	/**
	 * draw the obstacles array
	 * 
	 * @param g
	 */
	private void draw_obstacles(Graphics g) {
		// TODO
	}

	/**
	 * draw the obstacles array
	 * 
	 * @param g
	 */
	private void draw_other_cars(Graphics g) {
		// TODO
	}

	/**
	 * draws the car based on parameters in the car class. This is the main
	 * character.
	 * 
	 * @param g
	 */
	private void draw_car(Graphics g, Car c) {
		g.setColor(c.color);
		g.fillRect(c.x_pos, c.y_pos, c.width, c.height);
	}
}
