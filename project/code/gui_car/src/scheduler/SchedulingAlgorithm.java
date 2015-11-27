package scheduler;

import java.util.List;

public interface SchedulingAlgorithm {
	/**
	 * returns a schedule of all tasks for n processors. Each processor has a
	 * different list in the task set.
	 * 
	 * @param tasks
	 * @return
	 */
	public List<List<Task>> schedule(List<Task> tasks, int n_processors);
}
