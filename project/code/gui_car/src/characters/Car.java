package characters;

import java.awt.Color;
import java.awt.Graphics;

public class Car extends Character {
	public enum Facing {
		LEFT, RIGHT;
	}

	public Facing facing;
	public int speed;

	public Car() {
		super();
		reset();
	}

	public Car(int x_pos, int y_pos) {
		this();
		this.color = Color.red;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.origional_x_pos = x_pos;
		this.origional_y_pos = y_pos;
	}

	/**
	 * copy constructor
	 */
	public Car(Car car) {
		super(car);

		this.facing = car.facing;
		this.speed = car.speed;
	}

	public void reset() {
		this.color = Color.BLUE;
		this.width = 120;
		this.height = 60;
		this.x_pos = 0;
		this.y_pos = 0;
		this.speed = 0;
		this.facing = Facing.RIGHT;
	}

	/**
	 * ask the car to draw itself
	 */
	@Override
	public void draw(Graphics g) {
		// draw the body of the car
		g.setColor(this.color);
		g.fillRect(this.x_pos, this.y_pos, this.width, this.height);

		if (Facing.RIGHT == this.facing) {
			// draw the windshield
			g.setColor(Color.black);
			g.fillRect(this.x_pos + this.width / 2 + this.width / 16, this.y_pos + this.height / 10, this.width / 3,
					this.height * 4 / 5);
		} else {
			// draw the windshield
			g.setColor(Color.black);
			g.fillRect(this.x_pos + this.width / 16, this.y_pos + this.height / 10, this.width / 3,
					this.height * 4 / 5);
		}
	}
}
