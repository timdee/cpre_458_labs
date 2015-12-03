package characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import scheduler.Scheduler;

public class Sign extends Character {
	public enum SignType {
		STOP, SPEED, SCHEDULE, SINGLE_PROCESSOR;
	}

	public SignType type;
	public int speed_limit;
	public String scheduler;
	private final int LINEHEIGHT = 2;

	public Sign(int x_pos, int y_pos, SignType type) {
		super();

		this.width = 100;
		this.height = 100;

		this.type = type;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.speed_limit = 50;

		this.origional_x_pos = x_pos;
		this.origional_y_pos = y_pos;
	}
	
	public Sign(int x_pos, int y_pos, SignType type,String scheduler) {
		super();

		this.width = 100;
		this.height = 100;

		this.type = type;
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.speed_limit = 50;

		this.origional_x_pos = x_pos;
		this.origional_y_pos = y_pos;
		
		this.scheduler = scheduler;
	}

	/**
	 * copy constructor
	 */
	public Sign(Sign s) {
		super(s);

		this.type = s.type;
		this.speed_limit = s.speed_limit;

		this.origional_x_pos = s.x_pos;
		this.origional_y_pos = s.y_pos;
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
		case SCHEDULE:
			// outer border
			g.setColor(Color.BLACK);
			g.fillOval(this.x_pos, this.y_pos, this.width*3-20, this.height*3-20);

			// inner sign
			g.setColor(Color.RED);
			g.fillOval(x_pos + (width*3-20) / 10, y_pos + (height*3-20) / 10, width * 12/5-20, height * 12/5-20);

			// speed limit numbers
			g.setColor(Color.WHITE);
			g.setFont(new Font("Serif", Font.BOLD, 32));
			g.drawString("Scheduler", x_pos + width -25 , y_pos + height / 2 + height / 16+80);
			
			// speed limit numbers
			g.setColor(Color.WHITE);
			g.setFont(new Font("Serif", Font.BOLD, 32));
			g.drawString(""+this.scheduler, x_pos + width +10 , y_pos + height / 2 + height / 16+110);

			break;
			
		case SINGLE_PROCESSOR:
			// outer border
			g.setColor(Color.RED);
			g.fillRect(this.x_pos, this.y_pos, this.width*4, this.height*2);

			// inner sign
			g.setColor(Color.white);
			g.fillRect(x_pos + width / 5, y_pos + height / 5, width * 18/ 5, height * 8 / 5);

			// speed limit numbers
			//g.setColor(Color.black);
			//g.setFont(new Font("Serif", Font.BOLD, 32));
			//g.drawString("" + this.speed_limit, x_pos + width * 2 / 10, y_pos + height / 2 + height / 16);]
			g.setColor(Color.BLACK);
			g.fillRect(this.x_pos + this.width / 2 + this.width / 16, this.y_pos + this.height / 10+150, 300,
	                LINEHEIGHT);

			break;
		}
	}
}
