package scheduler;

import java.util.ArrayList;
import java.util.List;

public class EDF implements SchedulingAlgorithm {
	/**
	 * this edf only works in the single processor sense right now
	 */
	@Override
	public List<List<Task>> schedule(List<Task> tasks, int n_processors) {
		ArrayList<List<Task>> scheduled_tasks_list = new ArrayList<List<Task>>();
		ArrayList<Task> scheduled_tasks = new ArrayList<Task>();

		for (int i = 0; i < tasks.size(); i++) {
			scheduled_tasks.add(new Task(tasks.get(tasks.size() - 1 - i)));
		}

		// find the earliest deadline, put it first
		for (int i = 0; i < scheduled_tasks.size(); i++) {
			for (int j = i + 1; j < scheduled_tasks.size(); j++) {
				// sorted smallest thing first
				if (scheduled_tasks.get(i).deadline > scheduled_tasks.get(j).deadline) {
					Task temp = scheduled_tasks.get(i);
					scheduled_tasks.set(i, scheduled_tasks.get(j));
					scheduled_tasks.set(j, temp);
				}
			}
		}

		// System.out.println(scheduled_tasks);

		scheduled_tasks_list.add(scheduled_tasks);

		return scheduled_tasks_list;
	}

	/**
	 * return true of the schedule is feasible
	 * 
	 * EDF scheduleability check:
	 * 
	 * sum of the utilization < 1
	 */
	@Override
	public boolean is_feasible_schedule(List<Task> tasks, int n_processors) {
		double sum = 0;

		for (Task task : tasks) {
			// aperiodic tasks will have period equal to 0
			if (task.nature == Task.Nature.PERIODIC) {
				sum += ((double) task.computation_time_remaining) / ((double) task.period);
			} else {
				// TODO decide what to do with aperiodic tasks
				// take remaining computation time by deadline?
				double time_until_deadline = ((double) task.deadline)
						- task.processing_controller.get_state().total_time;

				sum += ((double) task.computation_time_remaining) / time_until_deadline;
			}
		}

		// System.out.println(sum <= 1);
		return sum <= 1;
	}
}
