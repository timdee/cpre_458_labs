package scheduler;

import java.util.ArrayList;
import java.util.List;

public class HVDF implements SchedulingAlgorithm {

	@Override
	public List<List<Task>> schedule(List<Task> tasks, int n_processors) {
		ArrayList<List<Task>> scheduled_tasks_list = new ArrayList<List<Task>>();
		ArrayList<Task> scheduled_tasks = new ArrayList<Task>();

		for (int i = 0; i < tasks.size(); i++) {
			scheduled_tasks.add(new Task(tasks.get(tasks.size() - 1 - i)));
		}

		// find the largest value task, put it first
		for (int i = 0; i < scheduled_tasks.size(); i++) {
			for (int j = i + 1; j < scheduled_tasks.size(); j++) {
				// sort the largest value thing first
				if (value_density(scheduled_tasks.get(i)) < value_density(scheduled_tasks.get(j))) {
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

	@Override
	public boolean is_feasible_schedule(List<Task> tasks, int n_processors) {
		// for best effort scheduling, we don't care if it is feasible
		return true;
	}

	/**
	 * returns the value density of a task
	 * 
	 * [value] / [remaining_computation_time]
	 */
	private double value_density(Task task) {
		return value(task) / ((double) task.computation_time_remaining);
	}

	/**
	 * return the value of a task
	 */
	private double value(Task task) {
		// value depends on the type of task
		// give aperiodic tasks high value
		double value = 0.0;

		if (task.nature == Task.Nature.APERIODIC) {
			value = 75;
		} else {
			// the action of the task should influence its value
			switch (task.action) {
			case READ_OTHER_CAR_SENSOR:
				value += 10;
			case READ_CONE_SENSOR:
				value += 10;
			case READ_SPEED_SIGN_SENSOR:
				value += 10;
			case READ_LANE_SENSOR:
				value += 10;
			case READ_STOP_SIGN_SENSOR:
				value += 10;
				break;
			default:
				value += 10;
			}
		}

		return value;
	}
}
