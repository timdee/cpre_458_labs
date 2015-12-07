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
	private ArrayList<Task> periodic_tasks;
	private long total_time; // total time of the processor

	public ArrayList<TaskTable> taskHolders;
	public ArrayList<Labels> labels;
	public ArrayList<TaskBlock> taskBlocks;
	public ArrayList<Sign> signs;
	public ArrayList<TaskBlock> processorQueueTasks;
	public ArrayList<TaskBlock> processorTasks;
	private int numSchedulerPeriodic;
	private int numSchedulerAperiodic;
	private boolean safeToPaint;

	public ProcessingState(SchedulingAlgorithm scheduling_algorithm, int n_processors) {
		this.scheduler_task_queue = new ArrayList<Task>();
		this.scheduler = new Scheduler(scheduling_algorithm);

		this.taskHolders = new ArrayList<TaskTable>();
		this.taskBlocks = new ArrayList<TaskBlock>();
		this.labels = new ArrayList<Labels>();
		this.signs = new ArrayList<Sign>();
		this.processorQueueTasks = new ArrayList<TaskBlock>();
		this.processorTasks = new ArrayList<TaskBlock>();
		// set up processors
		this.processors = new ArrayList<Processor>();

		for (int i = 0; i < n_processors; i++) {
			processors.add(new Processor());
		}

		this.total_time = 0L;
		this.periodic_tasks = new ArrayList<Task>();
		
		this.numSchedulerPeriodic = 0;
		this.numSchedulerAperiodic = 0;
		this.safeToPaint = false;
	}

	/**
	 * add a task to the processing state. new tasks go in the scheduler queue
	 */
	public void add_task(Task task) {
		if (task.nature == Task.Nature.PERIODIC) {
			// add the same task back in if it is periodic
			this.periodic_tasks.add(task);
		}
		
		this.scheduler_task_queue.add(task);
		//this.taskBlocks.add(task.taskBlock);
		
		if(task.nature == Task.Nature.PERIODIC) this.numSchedulerPeriodic++;
		else if(task.nature == Task.Nature.APERIODIC) this.numSchedulerAperiodic++;
		
		if(task.nature == Task.Nature.PERIODIC && this.numSchedulerPeriodic >1){
			moveTasks(task);

		}else if(task.nature == Task.Nature.APERIODIC && this.numSchedulerAperiodic >1){
			moveTasks(task);
		}
	}

	/**
	 * updates task data with the amount of time that has passed. preforms all
	 * necessary actions based on the time.
	 * 
	 * @param time
	 */
	public void update(long time) {
		this.safeToPaint = false;
		// schedule tasks into the processor queues
		run_scheduler();

		// run the processors for the specified time
		update_processors(time);
		this.safeToPaint = true;
	}

	/**
	 * set only one task into the task queue of each processor. Leave this task
	 * in the scheduler task queue until it is pulled into the processor.
	 */
	private void run_scheduler() {
		// grab all the tasks out of the processor queues, add them to the
		// schedule
		for (Processor p : processors) {
			if (p.task_queue.isEmpty() == false) {
				scheduler_task_queue.add(p.task_queue.get(0));
				p.task_queue.remove(0);
			}
		}

		// run the scheduler on the task queue for the number of processors
		ArrayList<List<Task>> schedule = scheduler.schedule(scheduler_task_queue, this.processors.size());

		// determine if there is no viable schedule
		if (schedule == null) {
			// TODO find something better to do when no viable schedule is
			// returned
			// TODO like a backup scheduler which does overload handling
			return;
		}

		// for each processor, maintain one task in the queue, remove this task
		// from the scheduler task queue.
		int processor_n = 0;

		for (Processor p : processors) {
			// if a task exists for this processor
			if (schedule.get(processor_n).isEmpty() == false) {
				// add the task to the processor queue
				p.task_queue.add(schedule.get(processor_n).get(0));
				//update gui for processor queue
				if(this.processorQueueTasks != null){
					if(this.processorQueueTasks.size() == 0){
						this.processorQueueTasks.add(schedule.get(processor_n).get(0).taskBlock);
						this.processorQueueTasks.get(0).inProcessorQueue = true;
					}
				}
				// remove the task from the schedule task queue
				schedule.get(processor_n).remove(0);
			}
			processor_n++;
		}

		// put the rest of the scheduled tasks back into the scheduler task
		// queue
		// clear the task queue
		this.scheduler_task_queue = new ArrayList<Task>();
		this.numSchedulerPeriodic = 0;
		this.numSchedulerAperiodic = 0;

		// for all lists of task in schedule
		for (List<Task> task_list : schedule) {
			// for each task in this task list
			for (Task task : task_list) {
				this.scheduler_task_queue.add(task);
				if(task.nature == Task.Nature.PERIODIC) this.numSchedulerPeriodic++;
				else if(task.nature == Task.Nature.APERIODIC) this.numSchedulerAperiodic++;
				
				if(task.nature == Task.Nature.PERIODIC && this.numSchedulerPeriodic >1){
					moveTasks(task);

				}else if(task.nature == Task.Nature.APERIODIC && this.numSchedulerAperiodic >1){
					moveTasks(task);
				}
			}

		}
	}

	private void update_processors(long time) {
		// preform updates in an incremental fashion
		for (int i = 0; i < time; i++) {
			total_time++;
			add_periodic_tasks();

			// if a task has completed, run the scheduler at the point it
			// completes.
			// remember the only tasks who's computation time is decreasing are
			// the ones in the processors.
			for (Processor p : processors) {
				// first, decrement the computation time of the thing in this
				// processor
				p.task.computation_time_remaining--;

				if (p.task.computation_time_remaining <= 0) {
					// preform the complete action of the task when it is
					// finished
					p.task.preform_action();

					// if a task has completed, we might need to run the
					// scheduler
					run_scheduler();

					// if the task has finished, get the next thing out of the
					// queue
					// set the current processor task to the smallest thing in
					// the queue
					if (p.task_queue.isEmpty() == false) {
						// get the next task in the queuue
						p.task = p.task_queue.get(0);
						//take out of processor queue
						p.task.taskBlock.inProcessorQueue = false;
						
						if(this.processorTasks != null){
							if(this.processorTasks.size() > 0){
								this.processorTasks.remove(0);
							}
						}
						
						
						if(this.processorTasks != null){
							if(this.processorTasks.size() == 0){
								//put in processor
								this.processorTasks.add(p.task.taskBlock);
								this.processorTasks.get(0).inProcessorQueue = false;
								this.processorTasks.get(0).inProcessor = true;
							
							}
						}
						
						//remove task from the processor queue for gui
						if(this.processorQueueTasks.size()>0){
							this.processorQueueTasks.get(0).inProcessorQueue = false;
							this.processorQueueTasks.remove(0);
						}
						
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
	/**
	 * moves tasks to their perspective position on the task table
	 */
	public void moveTasks(Task task){
		if(task.nature == Task.Nature.PERIODIC){
			for(int i = this.scheduler_task_queue.size()-1; i > 0;i--){
				if(this.scheduler_task_queue.get(i).nature == Task.Nature.PERIODIC){
					this.scheduler_task_queue.get(i).taskBlock.x_pos+= task.taskBlock.actualWidth+5;
				}
			}
			
			
			
		}else if(task.nature == Task.Nature.APERIODIC){
			for(int i = this.scheduler_task_queue.size()-1; i > 0;i--){
				if(this.scheduler_task_queue.get(i).nature == Task.Nature.APERIODIC){
					this.scheduler_task_queue.get(i).taskBlock.x_pos+= task.taskBlock.actualWidth+5;
				}
			}
		}
	}

	/**
	 * this method looks for periodic tasks which have their period expired. If
	 * these tasks exist than these tasks are added back into the queue.
	 * 
	 * if the total_time is a multiple of any periodic task periods, add it back
	 * in.
	 */
	private void add_periodic_tasks() {
		// look though all periodic tasks
		for (int i = 0; i < this.periodic_tasks.size(); i++) {

			// if the periodic task needs to be re-added
			if ((this.total_time % periodic_tasks.get(i).period) == 0) {
				Task task = periodic_tasks.get(i);
				this.scheduler_task_queue
						.add(new Task(task.computation_time_origional, task.period, task.deadline, task.nature,
								task.action, task.processing_controller, task.car_panel_controller, task.set_point));
			}
		}
	}
	
	/**
	 * returns if it is safe to paint the processor gui
	 */
	public boolean safe_to_paint () {
		return this.safeToPaint;
	}
}
