package characters;

import java.awt.Color;
import java.awt.Graphics;

public class Road extends Character {
	// determines how fast the lines scroll
	public int line_speed;
	// determines how far the lines are offset
	public int line_offset;

	public Road(int width, int height) {
		super();

		this.color = Color.DARK_GRAY;
		this.height = (int) (height * .4);
		this.width = width;
		this.x_pos = -100;
		this.y_pos = height / 2 - this.height / 2;

		this.line_speed = 1;
		this.line_offset = 0;
	}

	/**
	 * copy constructor
	 */
	public Road(Road r) {
		super(r);

		this.line_speed = r.line_speed;
		this.line_offset = r.line_offset;
	}

	@Override
	public void draw(Graphics g) {
		draw_road_surface(g);
		draw_lines(g);
	}

	private void draw_road_surface(Graphics g) {
		// draw the vertical road
		g.setColor(this.color);
		// g.fillRect(((int) ((width * .8) / 2)), 0, ((int) (width * .2)),
		// height);

		// draw the horizontal road
		int road_height = (int) (height * .4);
		g.fillRect(this.x_pos, this.y_pos, width, this.height);
	}

	private void draw_lines(Graphics g) {
		// TODO draw vertical lines
		g.setColor(Color.YELLOW);

		// TODO draw horizontal lines
		int line_height = (int) (this.height / 10);
		int line_width = (int) (this.width / 15);
		int number_of_lines = 20;

		// draw many number of lines
		for (int i = 0; i < number_of_lines; i++) {
			g.fillRect(this.x_pos + this.line_offset + (i * (line_width * 15 / 10)), this.y_pos + this.height / 2 - line_height / 2, line_width,
					line_height);
		}
	}
}
