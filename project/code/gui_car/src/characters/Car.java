package characters;

import java.awt.Color;
import java.awt.Graphics;

public class Car extends Character {
	public int speed;

	public Car() {
		super();
		reset();
	}

	/**
	 * copy constructor
	 */
	public Car(Car car) {
		super(car);
	}

	public void reset() {
		this.color = Color.BLUE;
		this.width = 100;
		this.height = 200;
		this.x_pos = 0;
		this.y_pos = 0;
		this.speed = 0;
	}

	/**
	 * ask the car to draw itself
	 */
	@Override
	public void draw(Graphics g) {
		// draw the body of the car
		g.setColor(this.color);
		g.fillRect(this.x_pos, this.y_pos, this.width, this.height);

		// draw the windshield
		g.setColor(Color.black);
		g.fillRect(this.x_pos + this.width / 2 + this.width / 16, this.y_pos + this.height / 10, this.width / 3,
				this.height * 4 / 5);
	}
}
