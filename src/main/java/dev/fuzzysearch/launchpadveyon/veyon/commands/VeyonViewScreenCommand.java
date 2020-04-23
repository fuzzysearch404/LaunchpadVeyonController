package dev.fuzzysearch.launchpadveyon.veyon.commands;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_COMMAND_EXECUTABLE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_REMOTEACCESS_MODULE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_REMOTEACCESS_VIEW;

import dev.fuzzysearch.launchpadveyon.veyon.models.Device;
import dev.fuzzysearch.launchpadveyon.veyon.utils.VeyonProcessExecutor;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * Remote access module {@link VeyonCommand} to launch
 * {@link Process} for remote view of a remote computer. 
 * 
 * @author Roberts Ziediņš
 *
 */
public class VeyonViewScreenCommand extends VeyonCommand {
	
	private final String commandBody = VEYON_CLI_COMMAND_EXECUTABLE + ' '
			+ VEYON_CLI_REMOTEACCESS_MODULE + ' '
			+ VEYON_CLI_REMOTEACCESS_VIEW + ' ';
	
	public VeyonViewScreenCommand(Pad pad, Device device) {
		super(pad, device);
	}

	@Override
	protected void createProcessExecutor() {
		processExecutor = new VeyonProcessExecutor(commandBody + device.getIpAdreess(), pad);
	}
	
}
