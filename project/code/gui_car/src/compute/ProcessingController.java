package compute;

import scheduler.SchedulingAlgorithm;
import scheduler.Task;
import sensors.SensorData;

/**
 * The mission of this task is to update the processing state that will be given
 * to the gui representation of the processor.
 * 
 * controls the back end processing.
 * 
 * This includes: 1) scheduler 2) task queues
 * 
 */
public class ProcessingController implements Runnable {
	public final long time_increment = 10L;

	private volatile ProcessingState processing_state;

	private volatile SensorData sensor_data;

	public ProcessingController(SchedulingAlgorithm scheduling_algorithm,
			SchedulingAlgorithm overload_scheduling_algorithm, int n_processors) {
		this.processing_state = new ProcessingState(scheduling_algorithm, overload_scheduling_algorithm, n_processors);
		sensor_data = null;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// update every 10 ms (100 fps)
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ask the state to update itself
			processing_state.update(time_increment);
		}
	}

	/**
	 * return the state of the processing.
	 * 
	 * @return
	 */
	public ProcessingState get_state() {
		return this.processing_state;
	}

	public void set_sensor_data(SensorData sensor_data) {
		this.sensor_data = sensor_data;
	}

	/**
	 * adds a task to the processing state.
	 * 
	 * if the task is periodic, add it every time its period has expired
	 */
	public void add_task(Task task) {
		this.processing_state.add_task(task);
	}
}
