package dev.fuzzysearch.launchpadveyon.veyon.commands;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.models.veyon.Device;
import dev.fuzzysearch.launchpadveyon.veyon.VeyonActionType;

/**
 * Factory class for {@link VeyonCommand} generation.
 * 
 * @author Roberts Ziediņš
 *
 */
public class VeyonCommandFactory {
	
	/**
	 * Constructs a {@link VeyonCommand} for specific {@link Device}
	 * according to global {@link VeyonActionType} located
	 * in {@link ProgramManager}.
	 * 
	 * @param device Veyon device to create command context for.
	 * @return {@link VeyonCommand} the created command.
	 */
	public static VeyonCommand getVeyonCommand(Device device) {
		switch(ProgramManager.getInstance().getCurrentAction()) {
			case SCREEN_CONTROL:
				return new VeyonRemoteControlScreenCommand(device);
			case SCREEN_VIEW:
				return new VeyonViewScreenCommand(device);
			default:
				return new VeyonViewScreenCommand(device);
		}
		
	}

}
