package dev.fuzzysearch.launchpadveyon.veyon.commands;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_COMMAND_EXECUTABLE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_REMOTEACCESS_CONTROL;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_REMOTEACCESS_MODULE;

import dev.fuzzysearch.launchpadveyon.veyon.models.Device;
import dev.fuzzysearch.launchpadveyon.veyon.utils.VeyonProcessExecutor;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * Remote access module {@link VeyonCommand} to launch
 * {@link Process} for remote control of a remote computer. 
 * 
 * @author Roberts Ziediņš
 *
 */
public class VeyonRemoteControlScreenCommand extends VeyonCommand {

	private final String commandBody = VEYON_CLI_COMMAND_EXECUTABLE + ' '
			+ VEYON_CLI_REMOTEACCESS_MODULE + ' '
			+ VEYON_CLI_REMOTEACCESS_CONTROL + ' ';
	
	public VeyonRemoteControlScreenCommand(Pad pad, Device device) {
		super(pad, device);
	}
	
	@Override
	protected void createProcessExecutor() {
		processExecutor = new VeyonProcessExecutor(commandBody + device.getIpAdreess(), pad);
	}

}
