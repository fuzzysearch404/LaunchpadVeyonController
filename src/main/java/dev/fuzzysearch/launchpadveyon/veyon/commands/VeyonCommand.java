package dev.fuzzysearch.launchpadveyon.veyon.commands;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.veyon.models.Device;
import dev.fuzzysearch.launchpadveyon.veyon.utils.VeyonProcessExecutor;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * An abstract template for Veyon command creation.
 * <a href="https://docs.veyon.io/en/latest/admin/cli.html">Veyon's command line interface docs</a>
 * 
 * @author Roberts Ziediņš
 *
 */
public abstract class VeyonCommand {
	
	protected Pad pad;
	protected Device device;
	protected VeyonProcessExecutor processExecutor;
	
	/**
	 * Constructs the command for specific device.
	 * 
	 * @param device for command's specific context.
	 */
	public VeyonCommand(Device device) {
		this.device = device;
		this.pad = device.getPad();
		
		createProcessExecutor();
	}
	
	/* We can't do this in template class because we don't know
	*  the command's body yet.
	*/
	protected abstract void createProcessExecutor();
	
	/**
	 * Executes the constructed command on a new {@link Thread}
	 * that creates a new {@link Process}. If there already was
	 * a process running before, it gets destroyed first to
	 * accomplish switching between commands on Launchpad
	 * with a single window open on computer's screen.
	 */
	public void execute() {
		ProgramManager manager = ProgramManager.getInstance();
		
		manager.destroyCurrentVeyonProcess();
		manager.setActiveVeyonDevice(device);
		
		Thread thread = new Thread(processExecutor);
		thread.start();
        
		try {
			processExecutor.getLatch().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        manager.setActiveVeyonProcess(processExecutor.getProcess());
	}
	
}
