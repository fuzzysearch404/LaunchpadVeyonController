package dev.fuzzysearch.launchpadveyon.launchpad.events;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_REMOTEACCESS_CONTROL_BUTTON;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_REMOTEACCESS_VIEW_BUTTON;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_REMOTEACCESS_STOP_BUTTON;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.COLOR_DEVICE_LOADED;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.models.veyon.Device;
import dev.fuzzysearch.launchpadveyon.veyon.VeyonActionType;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonCommand;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonCommandFactory;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonRemoteControlScreenCommand;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonViewScreenCommand;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * Converts {@link Launchpad}'s {@link Button}s event data
 * context to this program's method calls to
 * ensure program's overall functionality.
 * 
 * @author Roberts Ziediņš
 *
 */
public class LaunchpadButtonEventDispatcher {
	
	public void dispatch(Button button, long timestamp) {
		System.out.println("[" + timestamp + "] Pad pressed event : " + button);
		
		switchVeyonModes(button);
		stopRemoteAccess(button);
	}
	
	/**
	 * Switches between {@link VeyonCommand} types
	 * to be executed.
	 * 
	 * @param button to check if it is mapped for a command.
	 */
	private void switchVeyonModes(Button button) {
		if(button.equals(VEYON_REMOTEACCESS_VIEW_BUTTON))
			switchToRemoteAccessView();
		else if(button.equals(VEYON_REMOTEACCESS_CONTROL_BUTTON))
			switchToRemoteAccessControl();
	}
	
	/**
	 * Switches to remote access view mode -
	 * All {@link Pad} are now mapped with
	 * {@link VeyonViewScreenCommand} commands.
	 * 
	 * If there is an active Veyon remote screen control,
	 * it gets terminated and instead opens remote screen view.
	 */
	private void switchToRemoteAccessView() {
		ProgramManager manager = ProgramManager.getInstance();
		Process activeProcess = manager.getActiveVeyonProcess();
		
		if(activeProcess != null 
				&& activeProcess.isAlive()
				&& manager.getCurrentAction() == VeyonActionType.SCREEN_CONTROL) {
			ProgramManager.getInstance().setCurrentAction(VeyonActionType.SCREEN_VIEW);
			Device activeDevice = manager.getActiveVeyonDevice();
			
			VeyonCommandFactory.getVeyonCommand(activeDevice.getPad(), activeDevice).execute();
		}
		else
			manager.setCurrentAction(VeyonActionType.SCREEN_VIEW);
		
		manager.getLightManager().lightUpModeSelectButtons();
	}
	
	/**
	 * Switches to remote access control mode -
	 * All {@link Pad} are now mapped with
	 * {@link VeyonRemoteControlScreenCommand} commands.
	 * 
	 * * If there is an active Veyon remote screen view,
	 * it gets terminated and instead opens remote screen control.
	 */
	private void switchToRemoteAccessControl() {
		ProgramManager manager = ProgramManager.getInstance();
		Process activeProcess = manager.getActiveVeyonProcess();
		
		if(activeProcess != null 
				&& activeProcess.isAlive()
				&& manager.getCurrentAction() == VeyonActionType.SCREEN_VIEW) {
			ProgramManager.getInstance().setCurrentAction(VeyonActionType.SCREEN_CONTROL);
			Device activeDevice = manager.getActiveVeyonDevice();
			
			VeyonCommandFactory.getVeyonCommand(activeDevice.getPad(), activeDevice).execute();
		}
		else
			manager.setCurrentAction(VeyonActionType.SCREEN_CONTROL);
		
		manager.getLightManager().lightUpModeSelectButtons();
	}
	
	/**
	 * Kills the current active Veyon process.
	 * 
	 * @param button to check if it is mapped for this operation.
	 */
	private void stopRemoteAccess(Button button) {
		if(button.equals(VEYON_REMOTEACCESS_STOP_BUTTON)) {
			ProgramManager manager = ProgramManager.getInstance();
			
			// Destroy the Veyon process.
			Process process = manager.getActiveVeyonProcess();
			if(process != null)
				process.destroy();
			
			// Finally set Pad light back to loaded.
			Pad pad = manager.getActiveVeyonDevice().getPad();
			manager.getLightManager().setPadLight(pad, COLOR_DEVICE_LOADED, BackBufferOperation.COPY);
		}
	}
}
