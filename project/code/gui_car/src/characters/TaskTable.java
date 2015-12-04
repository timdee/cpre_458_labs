package characters;

import scheduler.Task;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Created by user on 11/30/15.
 */
public class TaskTable extends Character {


    private final int LINEHEIGHT = 2;

    public TaskTable() {
        super();
        reset();
    }

    public TaskTable(int x_pos, int y_pos) {
        this();
        this.color = Color.BLACK;
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    /**
     * copy constructor
     */
    public TaskTable(TaskTable taskTable) {
        super(taskTable);
    }

    public void reset() {
        this.color = Color.BLACK;
        this.width = 120;
        this.height = 60;
        this.x_pos = 0;
        this.y_pos = 0;
    }

    /**
     * ask the line to drawitself
     */
    @Override
    public void draw(Graphics g) {
        // draw the line
        g.setColor(this.color);
        g.fillRect(this.x_pos + this.width / 2 + this.width / 16, this.y_pos + this.height / 10, 400,
                LINEHEIGHT);


    }
}
