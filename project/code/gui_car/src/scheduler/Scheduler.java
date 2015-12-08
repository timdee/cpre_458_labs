package scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents a scheduler. It will take in a scheduling algorithm in
 * the constructor. It will then schedule task sents with this scheduling
 * algorithm.
 * 
 * @author element
 *
 */
public class Scheduler {
	private SchedulingAlgorithm scheduling_algorithm;

	public Scheduler(SchedulingAlgorithm scheduling_algorithm) {
		this.scheduling_algorithm = scheduling_algorithm;
	}

	/**
	 * returns the task set in scheduled order. null if not schedulable.
	 */
	public ArrayList<List<Task>> schedule(List<Task> tasks, int n_processors) {
		ArrayList<Task> array_list_tasks = new ArrayList<Task>(tasks);

		// if the schedule is feasible, schedule it. Otherwise return null.
		if (this.scheduling_algorithm.is_feasible_schedule(tasks, n_processors)) {
			return (ArrayList<List<Task>>) this.scheduling_algorithm.schedule(array_list_tasks, n_processors);
		} else {
			return null;
		}
	}
}
