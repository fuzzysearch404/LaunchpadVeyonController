package dev.fuzzysearch.launchpadveyon.launchpad.control;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.*;

import java.util.HashMap;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.models.veyon.Device;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.Pad;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

/**
 * Class for managing {@link MidiLaunchpad}'s lights.
 *
 * @author Roberts Ziediņš
 *
 */
public class LaunchpadLightManager {
	
	private HashMap<Pad, Color> pads;
	private Pad selectedPad;
	
	public LaunchpadLightManager() {
		initPadsMap();
	}

	/**
	 * Lights up lights on {@link Launchpad} according to
	 * list of devices at the {@link ProgramManager}.
	 */
	public void lightUpByDevices() {
		for(Device d: ProgramManager.getInstance().getLoadedDevices()) {
			int x = d.getPad().getX();
			int y = d.getPad().getY();
			Pad pad = Pad.at(x, y);
			
			setPadLight(pad, COLOR_DEVICE_LOADED, BackBufferOperation.COPY);
		}
		
	}
	
	/**
	 * Lights up the selected pad to color that
	 * represents that the selected pad is active.
	 */
	public void setSelected(Pad pad) {
		if(selectedPad != null) {
			if(ProgramManager.getInstance().padRepresentsLoadedDevice(selectedPad)) {
				setPadLight(selectedPad, COLOR_DEVICE_LOADED, BackBufferOperation.COPY);
			}
			else if(ProgramManager.getInstance().padRepresentsFailedDevice(selectedPad)) {
				setPadLight(selectedPad, COLOR_DEVICE_FAILED, BackBufferOperation.COPY);
			}
			else {
				setPadLight(selectedPad, COLOR_DEFAULT, BackBufferOperation.COPY);
			}
		}
		
		selectedPad = pad;
		setPadLight(pad, COLOR_DEVICE_ACTIVE, BackBufferOperation.COPY);
		
	}
	
	/**
	 * This method sets the pad light to provided {@link Color}
	 * and also registers the color to the pad map.
	 */
	private void setPadLight(Pad pad, Color color, BackBufferOperation operation) {
		ProgramManager.getInstance().getLaunchpadClient().setPadLight(pad, color, operation);
		pads.replace(pad, color);
	}
	
	private void initPadsMap() {
		pads = new HashMap<Pad, Color>(LAUNCHPAD_PAD_X_ROWS * LAUNCHPAD_PAD_Y_COLLUMNS);
		
		for(int x = 0; x < LAUNCHPAD_PAD_X_ROWS; x++) {
			for(int y = 0; y < LAUNCHPAD_PAD_Y_COLLUMNS; y++) {
				pads.put(Pad.at(x, y), COLOR_DEFAULT);
			}
		}
	}
	
}
