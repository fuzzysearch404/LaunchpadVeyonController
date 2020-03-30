package dev.fuzzysearch.launchpadveyon.main;

import javax.sound.midi.MidiUnavailableException;

import dev.fuzzysearch.launchpadveyon.exceptions.config.ProgramUnconfiguredException;
import dev.fuzzysearch.launchpadveyon.launchpad.control.LaunchpadLightManager;
import dev.fuzzysearch.launchpadveyon.launchpad.listeners.MainLaunchpadListener;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import net.thecodersbreakfast.lp4j.midi.MidiDeviceConfiguration;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;
import dev.fuzzysearch.launchpadveyon.setup.LaunchpadConfigParser;

public class MainFacade {

	public void run() throws MidiUnavailableException {
		MidiLaunchpad launchpad = new MidiLaunchpad(MidiDeviceConfiguration.autodetect());
		ProgramManager manager = ProgramManager.getInstance(launchpad);
		LaunchpadLightManager lightManager = manager.getLightManager();
		
		// Resets the Launchpad's buffers, so the lights turn off if there were on before.
		manager.getLaunchpadClient().reset();
		
		initConfiguration();
		
		setLaunchpadListeners();
		
		lightManager.lightUpModeSelectButtons();
		lightManager.lightUpPadsByDevices();
		lightManager.initBrightness();
	}
	
	private void initConfiguration() {
		try {
			new LaunchpadConfigParser();
		} catch (ProgramUnconfiguredException e) {
			// Skip the configuration, but keep program alive...
			e.printStackTrace();
		}
	}
	
	private void setLaunchpadListeners() {
		ProgramManager.getInstance().getLaunchpad().setListener(new MainLaunchpadListener());
	}
}
