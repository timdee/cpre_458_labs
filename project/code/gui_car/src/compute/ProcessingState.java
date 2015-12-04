package compute;

import java.util.ArrayList;
import java.util.List;

import characters.Labels;
import characters.Sign;
import characters.TaskBlock;
import characters.TaskTable;
import scheduler.Processor;
import scheduler.Scheduler;
import scheduler.SchedulingAlgorithm;
import scheduler.Task;
import scheduler.Task.Action;
import scheduler.Task.Nature;

/**
 * captures all information about the processing state.
 * 
 * This includes:
 * 
 * 1) scheduler task queue 2) scheduler 4) processors task queues 5) processors
 * 
 * @author element
 *
 */
public class ProcessingState {
	public ArrayList<Task> scheduler_task_queue;
	private Scheduler scheduler;

	private ArrayList<Processor> processors;
	
	public ArrayList<TaskTable> taskHolders;
	public ArrayList<Labels> labels;
	public ArrayList<TaskBlock> taskBlocks;
	public ArrayList<Sign> signs;

	public ProcessingState(SchedulingAlgorithm scheduling_algorithm, int n_processors) {
		this.scheduler_task_queue = new ArrayList<Task>();
		this.scheduler = new Scheduler(scheduling_algorithm);
		
		this.taskHolders = new ArrayList<TaskTable>();
		this.taskBlocks = new ArrayList<TaskBlock>();
		this.labels = new ArrayList<Labels>();
		this.signs = new ArrayList<Sign>();

		// set up processors
		this.processors = new ArrayList<Processor>();

		for (int i = 0; i < n_processors; i++) {
			processors.add(new Processor());
		}
	}

	/**
	 * add a task to the processing state. new tasks go in the scheduler queue
	 */
	public void add_task(Task task) {
		this.scheduler_task_queue.add(task);
	}

	/**
	 * updates task data with the amount of time that has passed. preforms all
	 * necessary actions based on the time.
	 * 
	 * @param time
	 */
	public void update(long time) {
		// schedule tasks into the processor queues
		run_scheduler();

		// run the processors for the specified time
		update_processors(time);
	}

	private void run_scheduler() {
		// run the scheduler on the task queue for the number of processors
		ArrayList<List<Task>> schedule = scheduler.schedule(scheduler_task_queue, this.processors.size());

		// based on what the schedule returns, we can populate the processor
		// task queues
		int processor_n = 0;

		for (List<Task> task_list : schedule) {
			// for each task in this task list
			for (Task task : task_list) {
				processors.get(processor_n).task_queue.add(task);
			}

			processor_n++;
		}

		// clear the task queue
		this.scheduler_task_queue = new ArrayList<Task>();
	}

	private void update_processors(long time) {
		// preform updates in an incremental fashion
		for (int i = 0; i < time; i++) {
			// if a task has completed, run the scheduler at the point it
			// completes.
			// remember the only tasks who's computation time is decreasing are
			// the ones in the processors.
			for (Processor p : processors) {
				// first, decrement the computation time of the thing in this
				// processor
				p.task.computation_time_remaining--;

				if (p.task.computation_time_remaining <= 0) {
					// periodic tasks need to be added back
					if (p.task.nature == Task.Nature.PERIODIC) {
						// add the same task back in if it is periodic
						this.scheduler_task_queue.add(new Task(p.task.computation_time_origional, p.task.period,
								p.task.deadline, p.task.nature, p.task.action, p.task.processing_controller,
								p.task.car_panel_controller, p.task.set_point));
					}

					// preform the complete action of the task when it is
					// finished
					p.task.preform_action();

					// if the task has finished, get the next thing out of the
					// queue
					// set the current processor task to the smallest thing in
					// the queue
					if (p.task_queue.isEmpty() == false) {
						// get the next task in the queuue
						p.task = p.task_queue.get(0);

						// remove this task from the queue
						p.task_queue.remove(0);
					} else {
						// insert a dummy task
						p.task = new Task(1, 0, 0, Nature.APERIODIC, Action.NONE, null, null, 0);
					}
				}
			}
		}
	}
	

	/*****************************************************
	 * provide methods to submit obstacles
	 *
	 * once an ostacle goes off screen, it will reset to the end.
	 *
	 * This means obstacles will come like a they were on a circular conveyer
	 * belt.
	 *
	 * ***************************************************
	 */
	/**
	 * adds a cone at the point specified in the code
	 */
	public void submit_task_table(TaskTable taskTable) {
		this.taskHolders.add(taskTable);

	}

	/**
	 * adds a cone at the point specified in the code
	 */
	public void submit_labels(Labels label) {
		this.labels.add(label);

	}
	
	/**
	 * adds a sign at the point specified in the code
	 */
	public void submit_signs(Sign sign) {
		this.signs.add(sign);

	}
}
