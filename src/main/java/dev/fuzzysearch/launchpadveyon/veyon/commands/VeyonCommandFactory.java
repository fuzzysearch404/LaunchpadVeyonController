package dev.fuzzysearch.launchpadveyon.veyon.commands;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.veyon.VeyonActionType;
import dev.fuzzysearch.launchpadveyon.veyon.models.Device;
import net.thecodersbreakfast.lp4j.api.Pad;

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
	public static VeyonCommand getVeyonCommand(Pad pad, Device device) {
		switch(ProgramManager.getInstance().getCurrentAction()) {
			case SCREEN_CONTROL:
				return new VeyonRemoteControlScreenCommand(pad, device);
			case SCREEN_VIEW:
				return new VeyonViewScreenCommand(pad, device);
			default:
				return new VeyonViewScreenCommand(pad, device);
		}
		
	}

}
