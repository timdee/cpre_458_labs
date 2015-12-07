package compute;

import gui.ProcessorPanel;

/**
 * this class controls the processor panel. The Idea behind this is that
 * anything the car panel needs to do will be controled by this class.
 * 
 * This provides a nice interface for interacting with the gui.
 * 
 * Needs to be able to perform all the functions of the tasks.
 * 
 * Will also update the positions / colors of processor parts.
 * 
 * This will need to run on its own thread.
 */
public class ProcessorPanelController implements Runnable {
	private ProcessorPanel processor_panel;
	private ProcessingState processing_state;


	public ProcessorPanelController(ProcessorPanel processor_panel) {
		this.processor_panel = processor_panel;
	}

	@Override
	public void run() {
		int oldLength = 0;
		while (true) {
			try {
				// update every 10 ms (100 fps)
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// TODO do something with the processing state. Move the processing
			// panel toward the current processing state.
			// TODO need to cause the panel to update (visually) based on the
			// state
			// TODO
			
			set_processing_state(this.processor_panel.getState());
			if(this.processor_panel.getState().taskBlocks.size() != oldLength){
				// cause the processor panel to be repainted
				processor_panel.repaint();
				processor_panel.revalidate();
				oldLength = this.processor_panel.getState().taskBlocks.size();
			}
			
		}
	}

	/**
	 * sets the state of the processing. This class controlls how the processing
	 * panel moves toward this state.
	 */
	public void set_processing_state(ProcessingState state) {
		this.processing_state = state;
	}
}
