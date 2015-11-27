package scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * runs tasks one at a time
 * 
 * @author element
 *
 */
public class Processor {
	public Task task;
	public List<Task> task_queue;

	public Processor() {
		// create a dummy task
		this.task = new Task(0, 0, 0, Task.Nature.APERIODIC, Task.Action.NONE, null, null, 0);
		this.task_queue = new ArrayList<Task>();
	}
}
