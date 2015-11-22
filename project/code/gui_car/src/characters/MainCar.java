package characters;

import java.awt.Color;

public class MainCar extends Car {
	public MainCar(int width, int height) {
		super();

		this.color = Color.BLUE;
		this.height = (int) (height * .1);
		this.width = this.height * 2;
		this.x_pos = width / 40;
		this.y_pos = (height / 2) + this.height / 2;
		this.speed = 1;
	}
}
