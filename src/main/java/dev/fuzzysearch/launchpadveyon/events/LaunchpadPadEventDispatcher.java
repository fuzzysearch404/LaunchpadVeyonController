package dev.fuzzysearch.launchpadveyon.events;

import dev.fuzzysearch.launchpadveyon.config.PadEditor;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonCommand;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonCommandFactory;
import dev.fuzzysearch.launchpadveyon.veyon.models.Device;
import javafx.application.Platform;
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
	
	public void dispatch(Pad pad, String launchpadName) {
		System.out.println("[" + launchpadName + "] Pad pressed event : " + pad);
        
		manageLights(pad);
        
		ProgramManager manager = ProgramManager.getInstance();
        Device device = manager.getDeviceByPad(pad);
        
        if(manager.isEditMode()) {
        	editPad(pad, device);
        	return;
        }
        
        /* If there is no mapped Device on the Pad, we
         * destroy the active process, if there is one
         * and return.
         */
        if(device == null) {
        	manager.destroyCurrentVeyonProcess();
        	return;
        }
        
        VeyonCommand veyonCommand = getVeyonCommand(pad, device);
        veyonCommand.execute();
	}

	private void manageLights(Pad pad) {
		ProgramManager.getInstance().getLightManager().setSelected(pad);
	}
	
	private VeyonCommand getVeyonCommand(Pad pad, Device device) {
		return VeyonCommandFactory.getVeyonCommand(pad, device);
	}
	
	private void editPad(Pad pad, Device device) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				new PadEditor(device, pad);
			}
		});	
	}

}
