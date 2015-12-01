package characters;

import java.awt.Color;
import java.awt.Graphics;

/**
 * the point of this is that things are drawable.
 * 
 * @author element
 *
 */
public abstract class Character {
	public Color color;
	public int width;
	public int height;
	public int x_pos;
	public int y_pos;
	public int origional_x_pos;
	public int origional_y_pos;

	public Character() {
		this.color = Color.black;
		this.width = 0;
		this.height = 0;
		this.x_pos = 0;
		this.y_pos = 0;
		this.origional_x_pos = 0;
		this.origional_y_pos = 0;
	}

	/**
	 * copy constructor
	 */
	public Character(Character c) {
		this.x_pos = c.x_pos;
		this.y_pos = c.y_pos;
		this.height = c.height;
		this.width = c.width;
		this.color = c.color;
		this.origional_x_pos = c.x_pos;
		this.origional_y_pos = c.y_pos;
	}

	public abstract void draw(Graphics g);
}
