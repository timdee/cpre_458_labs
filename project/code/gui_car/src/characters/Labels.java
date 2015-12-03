package characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Created by user on 12/1/15.
 */
public class Labels extends Character {

    private String labelMessage;
    public Labels(int x_pos, int y_pos, String message) {
        super();

        this.width = 20;
        this.height = 100;
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.labelMessage = message;
    }

    /**
     * copy constructor
     */
    public Labels(Sign s) {
        super(s);
    }

    @Override
    public void draw(Graphics g) {

                // outer border
                //g.setColor(Color.black);
                //g.fillRect(this.x_pos, this.y_pos, this.width, this.height);

                // inner sign
                //g.setColor(Color.white);
                //g.fillRect(x_pos + width / 10, y_pos + height / 10, width * 8 / 10, height * 8 / 10);

                // speed limit numbers
                g.setColor(Color.black);
                g.setFont(new Font("Serif", Font.BOLD, 18));
                g.drawString("" + this.labelMessage, x_pos,y_pos);



    }
}

