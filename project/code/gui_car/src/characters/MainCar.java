package characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MainCar extends Car {

	public MainCar() {
		this(2000, 500);
	}

	public MainCar(int width, int height) {
		super();

		this.color = Color.BLUE;
		this.height = (int) (height * .1);
		this.width = this.height * 2;
		this.x_pos = width / 20;
		this.y_pos = (height / 2) + this.height / 2;
		this.speed = 1;
	}

	/**
	 * copy constructor
	 */
	public MainCar(MainCar mc) {
		this.color = Color.BLUE;
		this.height = mc.height;
		this.width = mc.width;
		this.x_pos = mc.x_pos;
		this.y_pos = mc.y_pos;

		this.speed = mc.speed;
	}

	/**
	 * draws the speed of the car somewhere on the screen
	 */
	public void draw_speed(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.PLAIN, 75));
		g.drawString("" + this.speed, 100, 100);
	}
}
