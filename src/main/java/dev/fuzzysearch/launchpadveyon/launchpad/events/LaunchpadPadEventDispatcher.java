package dev.fuzzysearch.launchpadveyon.launchpad.events;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.models.veyon.Device;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonCommand;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonCommandFactory;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * Converts {@link Launchpad}'s {@link Pad}s event data
 * context to this program's method calls to
 * ensure program's overall functionality.
 * 
 * @author Roberts Ziediņš
 *
 */
public class LaunchpadPadEventDispatcher {
	
	public void dispatch(Pad pad, long timestamp) {
		System.out.println("[" + timestamp + "] Pad pressed event : " + pad);
        
		manageLights(pad);
        
        Device device = ProgramManager.getInstance().getDeviceByPad(pad);
        
        /* If there is no mapped Device on the Pad, we
         * destroy the active process, if there is one
         * and return.
         */
        if(device == null) {
        	destroyCurrentVeyonProcess();
        	return;
        }
        
        VeyonCommand veyonCommand = getVeyonCommand(pad, device);
        veyonCommand.execute();
	}
	
	private void destroyCurrentVeyonProcess() {
		Process process = ProgramManager.getInstance().getActiveVeyonProcess();
    	if(process != null)
    		process.destroy();
	}

	private void manageLights(Pad pad) {
		ProgramManager.getInstance().getLightManager().setSelected(pad);
	}
	
	private VeyonCommand getVeyonCommand(Pad pad, Device device) {
		return VeyonCommandFactory.getVeyonCommand(pad, device);
	}

}
