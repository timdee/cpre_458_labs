package characters;

import java.awt.Color;
import java.awt.Graphics;

public class Cone extends Character {
	public Cone(int x_pos, int y_pos) {
		this.color = Color.orange;
		this.width = 60;
		this.height = 60;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.origional_x_pos = x_pos;
		this.origional_y_pos = y_pos;
	}

	/**
	 * copy constructor
	 */
	public Cone(Cone c) {
		this.color = c.color;
		this.width = c.width;
		this.height = c.height;
		this.x_pos = c.x_pos;
		this.y_pos = c.y_pos;
		this.origional_x_pos = x_pos;
		this.origional_y_pos = y_pos;
	}

	@Override
	public void draw(Graphics g) {
		// cone consists of a rounded rectange and two circles
		// rounded rectangle
		g.setColor(Color.orange);
		g.fillRoundRect(this.x_pos, this.y_pos, this.width, this.height, 35, 35);

		// first circle
		g.setColor(Color.white);
		g.fillOval(this.x_pos + this.width / 8, this.y_pos + this.height / 8, this.width * 3 / 4, this.height * 3 / 4);

		// second circle
		g.setColor(Color.orange);
		g.fillOval(this.x_pos + this.width / 4 + this.width / 24, this.y_pos + this.height / 4 - this.height / 24,
				this.width / 2, this.height / 2);
	}
}
