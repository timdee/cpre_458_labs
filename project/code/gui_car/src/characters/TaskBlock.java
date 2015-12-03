package characters;

import scheduler.Task;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Created by user on 11/30/15.
 */
public class TaskBlock extends Character {


    public Task.Nature nature;
   // public int speed;
    public Task task;

    private final int STARTING_X_POS_PERIODIC = 130;
    private final int STARTING_X_POS_APERIODIC = 50;
    private final int STARTING_Y_POS_PERIODIC = 130;
    private final int STARTING_Y_POS_APERIODIC = 50;

    private int x_pos;
    private int y_pos;

    public TaskBlock() {
        super();
        reset();
    }

    public TaskBlock( Task task) {
        this();
        this.task = task;
        nature = task.nature;
        //checks whether the task is periodic or aperiodic
        if(nature == Task.Nature.PERIODIC) {
            this.color = Color.red;
            this.x_pos = STARTING_X_POS_PERIODIC;
            this.y_pos = STARTING_Y_POS_PERIODIC;
        }else if (nature == Task.Nature.APERIODIC) {
            this.color = Color.BLUE;
            this.x_pos = STARTING_X_POS_APERIODIC;
            this.y_pos = STARTING_Y_POS_APERIODIC;
        }
    }

    /**
     * copy constructor
     */
    public TaskBlock(TaskBlock taskBlock) {
        super(taskBlock);

    }

    public void reset() {
        this.color = Color.RED;
        this.width = 120;
        this.height = 60;
        this.x_pos = 0;
        this.y_pos = 0;
        //this.speed = 0;
        nature = Task.Nature.PERIODIC;
    }

    /**
     * ask the car to draw itself
     */
    @Override
    public void draw(Graphics g) {
        // draw the body of the car
        g.setColor(this.color);
        g.fillRect(this.x_pos, this.y_pos, this.width, this.height);

        if (nature == Task.Nature.PERIODIC) {
            // draw the windshield
            g.setColor(Color.RED);
            g.fillRect(this.x_pos + this.width / 2 + this.width / 16, this.y_pos + this.height / 10, (this.width / 3)+task.computation_time_origional,
                    this.height * 4 / 5);
        } else {
            // draw the windshield
            g.setColor(Color.BLUE);
            g.fillRect(this.x_pos + this.width / 16, this.y_pos + this.height / 10, (this.width / 3)+task.computation_time_origional,
                    this.height * 4 / 5);
        }

    }
}
