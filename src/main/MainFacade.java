package main;

import javax.sound.midi.MidiUnavailableException;

import exceptions.config.ProgramUncofiguredException;
import launchpad.listeners.MainLaunchpadListener;
import main.manager.ProgramManager;
import net.thecodersbreakfast.lp4j.midi.MidiDeviceConfiguration;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;
import setup.LaunchpadConfigParser;

public class MainFacade {

	public void run() throws MidiUnavailableException {
		MidiLaunchpad launchpad = new MidiLaunchpad(MidiDeviceConfiguration.autodetect());
		ProgramManager.getInstance(launchpad);
		
		// Resets the Launchpad's buffers, so the lights turn off if there were on before.
		ProgramManager.getInstance().getLaunchpadClient().reset();
		
		initConfiguration();
		
		setLaunchpadListeners();
	}
	
	private void initConfiguration() {
		try {
			new LaunchpadConfigParser();
		} catch (ProgramUncofiguredException e) {
			// Skip the configuration, but keep program alive...
			e.printStackTrace();
		}
	}
	
	private void setLaunchpadListeners() {
		ProgramManager.getInstance().getLaunchpad().setListener(new MainLaunchpadListener());
	}
}
