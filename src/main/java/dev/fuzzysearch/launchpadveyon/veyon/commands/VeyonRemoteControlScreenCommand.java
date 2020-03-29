package dev.fuzzysearch.launchpadveyon.veyon.commands;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.*;

import dev.fuzzysearch.launchpadveyon.models.veyon.Device;
import dev.fuzzysearch.launchpadveyon.utils.ProcessExecutor;

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
	
	public VeyonRemoteControlScreenCommand(Device device) {
		super(device);
	}
	
	@Override
	protected void createProcessExecutor() {
		processExecutor = new ProcessExecutor(commandBody + device.getIpAdreess());
	}

}
