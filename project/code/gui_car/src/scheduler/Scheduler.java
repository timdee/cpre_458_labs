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
	private SchedulingAlgorithm overload_scheduling_algorithm;

	public Scheduler(SchedulingAlgorithm scheduling_algorithm, SchedulingAlgorithm overload_scheduling_algorithm) {
		this.scheduling_algorithm = scheduling_algorithm;
		this.overload_scheduling_algorithm = overload_scheduling_algorithm;
	}

	/**
	 * returns the task set in scheduled order. null if not schedulable.
	 */
	public ArrayList<List<Task>> schedule(List<Task> tasks, int n_processors) {
		ArrayList<Task> array_list_tasks = new ArrayList<Task>(tasks);

		// discard tasks which will already missed their deadline
		discard_late_tasks(array_list_tasks);

		// if the schedule is feasible, schedule it. Otherwise return null.
		if (this.scheduling_algorithm.is_feasible_schedule(tasks, n_processors)) {
			return (ArrayList<List<Task>>) this.scheduling_algorithm.schedule(array_list_tasks, n_processors);
		} else {
			System.out.println("OVERLOAD");
			// if there is no feasible schedule, use the overload scheduling
			// algorithm
			return (ArrayList<List<Task>>) this.overload_scheduling_algorithm.schedule(array_list_tasks, n_processors);
		}
	}

	/**
	 * discard all tasks which have already missed their deadline
	 */
	private void discard_late_tasks(ArrayList<Task> tasks) {
		// remove all tasks which have missed their deadline
		for (int i = tasks.size() - 1; i > -1; i--) {
			// if a task will miss its deadline
			int current_time = (int) tasks.get(i).processing_controller.get_state().total_time;

			if ((current_time + tasks.get(i).computation_time_remaining) > (tasks.get(i).deadline)) {
				tasks.remove(i);
			}
		}
	}
}
