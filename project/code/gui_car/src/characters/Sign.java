package characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Sign extends Character {
	public enum SignType {
		STOP, SPEED;
	}

	public SignType type;
	public int speed_limit;

	public Sign(int x_pos, int y_pos, SignType type) {
		super();

		this.width = 100;
		this.height = 100;

		this.type = type;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.speed_limit = 50;
	}

	/**
	 * copy constructor
	 */
	public Sign(Sign s) {
		super(s);

		this.type = s.type;
		this.speed_limit = s.speed_limit;
	}

	@Override
	public void draw(Graphics g) {
		switch (this.type) {
		case STOP:
			// background circle
			g.setColor(Color.red);
			g.fillOval(x_pos, y_pos, width, height);

			// stop lettering
			g.setColor(Color.white);
			g.setFont(new Font("Serif", Font.BOLD, 24));
			g.drawString("STOP", x_pos + width / 10 + width / 20, y_pos + height / 2 + height / 16);

			break;
		case SPEED:
			// outer border
			g.setColor(Color.black);
			g.fillRect(this.x_pos, this.y_pos, this.width, this.height);

			// inner sign
			g.setColor(Color.white);
			g.fillRect(x_pos + width / 10, y_pos + height / 10, width * 8 / 10, height * 8 / 10);

			// speed limit numbers
			g.setColor(Color.black);
			g.setFont(new Font("Serif", Font.BOLD, 32));
			g.drawString("" + this.speed_limit, x_pos + width * 2 / 10, y_pos + height / 2 + height / 16);

			break;
		}
	}
}
