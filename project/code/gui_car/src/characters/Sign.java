package characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Sign extends Character {
	public enum SignType {
		STOP, SPEED;
	}

	public SignType type;

	public Sign() {
		super();
		this.type = SignType.STOP;
	}

	@Override
	public void draw(Graphics g) {
		switch (this.type) {
		case STOP:
			g.setColor(Color.red);

			Polygon octogon = new Polygon();
			for (int i = 0; i < 8; i++) {
				octogon.addPoint(this.x_pos + (this.width / 6) * i % 3, this.y_pos + (this.height / 6) * i % 3);
			}

			g.fillPolygon(octogon);
			break;
		case SPEED:
			g.setColor(Color.WHITE);
			g.fillRect(this.x_pos, this.y_pos, this.width, this.height);
			break;
		}
	}
}
