package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import characters.Car;
import characters.Cone;
import characters.MainCar;
import characters.Road;
import characters.Sign;
import compute.CarPanelState;

public class CarPanel extends JPanel {
	private static final long serialVersionUID = -7055815099049883098L;

	private CarPanelState state;

	private int width;
	private int height;

	public CarPanel(int w, int h) {
		// call parent constructor
		super();

		// set properties of the thing
		setOpaque(true);
		setBackground(Color.GREEN);

		// set the width and height
		this.width = w;
		this.height = h;

		// initialize the state
		this.state = new CarPanelState();

		// make a new main car
		state.main_car = new MainCar(w, h);

		// make a new road
		state.road = new Road(w, h);
	}

	public CarPanelState getState() {
		return this.state;
	}

	public void setState(CarPanelState state) {
		this.state = state;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// call methods to preform different drawing operations
		draw_background(g);
		state.road.draw(g);
		draw_obstacles(g);
		draw_other_cars(g);
		draw_signs(g);
		state.main_car.draw(g);
		state.main_car.draw_speed(g);
	}

	/**
	 * draw the background color
	 */
	private void draw_background(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, width, height);
	}

	/**
	 * draw the obstacles array
	 * 
	 * @param g
	 */
	private void draw_obstacles(Graphics g) {
		for (Cone c : state.obstacles) {
			c.draw(g);
		}
	}

	/**
	 * draw the obstacles array
	 * 
	 * @param g
	 */
	private void draw_other_cars(Graphics g) {
		for (Car c : state.other_cars) {
			c.draw(g);
		}
	}

	/**
	 * draw the sign array
	 * 
	 * @param g
	 */
	private void draw_signs(Graphics g) {
		for (Sign c : state.signs) {
			c.draw(g);
		}
	}
}
