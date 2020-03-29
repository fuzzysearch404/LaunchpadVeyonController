package dev.fuzzysearch.launchpadveyon.launchpad.control;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.COLOR_DEVICE_LOADED;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.models.veyon.Device;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * Class for managing {@link Launchpad}'s lights.
 *
 * @author Roberts Ziediņš
 *
 */
public class LaunchpadLightManager {

	/**
	 * Lights up lights on {@link Launchpad} according to
	 * list of devices at the {@link ProgramManager}.
	 */
	public void lightUpByDevices() {
		for(Device d: ProgramManager.getInstance().getDevices()) {
			ProgramManager.getInstance().getLaunchpadClient().setPadLight(
					Pad.at(
							d.getPad().getX(),
							d.getPad().getY()
							),
					COLOR_DEVICE_LOADED, BackBufferOperation.NONE
			);
		}
		
	}
	
}
