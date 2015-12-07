package characters;

import scheduler.Task;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Created by user on 11/30/15.
 */
public class TaskBlock extends Character {



    public Task.Nature nature;
   // public int speed;
    public Task task;

    private final int STARTING_X_POS_PERIODIC = 88;
    private final int STARTING_X_POS_APERIODIC = 88;
    private final int X_POS_PROCESSOR_QUEUE = 1125;
    private final int STARTING_Y_POS_PERIODIC = 70;
    private final int STARTING_Y_POS_APERIODIC = 195;
    private final int Y_POS_PROCESSOR_QUEUE = 158;

    public int x_pos;
    public int y_pos;
    private Task.Action action;
    public int actualWidth;
    public boolean inProcessor;
    public boolean inProcessorQueue;
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
        this.action = this.task.action;
        this.actualWidth = this.width+(task.computation_time_origional / 5);
        this.inProcessor = false;
        this.inProcessorQueue = false;
        
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
        this.action = Task.Action.READ_CONE_SENSOR;
        this.actualWidth = this.width;
        this.inProcessor = false;
        this.inProcessorQueue = false;
    }

    /**
     * ask the car to draw itself
     */
    @Override
    public void draw(Graphics g) {
       

        if (nature == Task.Nature.PERIODIC && this.inProcessorQueue == false) {
        	// draw the body of the car
            g.setColor(Color.BLACK);
            g.fillRect(this.x_pos-1, this.y_pos-1, this.actualWidth+2, this.height+2);
            // draw the body of task
            g.setColor(Color.RED);
            g.fillRect(this.x_pos, this.y_pos, this.actualWidth,
                    this.height);
          
            //show action of the task
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, 8));
            g.drawString(""+this.action.toString(), this.x_pos+5, this.y_pos+30);
        } else if (nature == Task.Nature.APERIODIC && this.inProcessorQueue == false) {
        	// draw the body of the car
            g.setColor(Color.BLACK);
            g.fillRect(this.x_pos-1, this.y_pos-1, this.actualWidth+2, this.height+2);
            // draw body of the task
            g.setColor(Color.BLUE);
            g.fillRect(this.x_pos, this.y_pos, this.actualWidth
            		,
                    this.height);
            //show action of the task
            g.setColor(Color.WHITE);
            g.setFont(new Font("Serif", Font.BOLD, 8));
            g.drawString(""+this.action.toString(), this.x_pos+5, this.y_pos+30);
        }else if (nature == Task.Nature.PERIODIC && this.inProcessorQueue) {
        	
        	//set coordinates
        	this.x_pos = this.X_POS_PROCESSOR_QUEUE;
        	this.y_pos = this.Y_POS_PROCESSOR_QUEUE;
        	
        	// draw the body of the car
            g.setColor(Color.BLACK);
            g.fillRect(this.x_pos-1, this.y_pos-1, this.actualWidth+2, this.height+2);
        
        	 // draw the body of task
            g.setColor(Color.RED);
            g.fillRect(this.x_pos, this.y_pos, this.actualWidth,
                    this.height);
          
            //show action of the task
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, 8));
            g.drawString(""+this.action.toString(), this.x_pos+5, this.y_pos+30);
            
        }else if (nature == Task.Nature.APERIODIC && this.inProcessorQueue) {
        	//set coordinates
        	this.x_pos = this.X_POS_PROCESSOR_QUEUE;
        	this.y_pos = this.Y_POS_PROCESSOR_QUEUE;
        	
        	// draw the body of the car
            g.setColor(Color.BLACK);
            g.fillRect(this.x_pos-1, this.y_pos-1, this.actualWidth+2, this.height+2);
        
        	 // draw the body of task
            g.setColor(Color.BLUE);
            g.fillRect(this.x_pos, this.y_pos, this.actualWidth,
                    this.height);
          
            //show action of the task
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, 8));
            g.drawString(""+this.action.toString(), this.x_pos+5, this.y_pos+30);
            
        }
        

    }
}
