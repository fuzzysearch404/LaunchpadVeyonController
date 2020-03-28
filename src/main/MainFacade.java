package main;

import javax.sound.midi.MidiUnavailableException;

import exceptions.config.ProgramUncofiguredException;
import launchpad.listeners.MainLaunchpadListener;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.midi.MidiDeviceConfiguration;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;
import setup.LaunchpadConfigParser;

public class MainFacade {

	public void run() throws MidiUnavailableException {
		MidiLaunchpad launchpad;
		
		launchpad = new MidiLaunchpad(MidiDeviceConfiguration.autodetect());
		
		LaunchpadClient client = launchpad.getClient();
		// Resets the Launchpad's buffers, so the lights turn off.
		client.reset();
		
		try {
			LaunchpadConfigParser cfg = new LaunchpadConfigParser(client);
		} catch (ProgramUncofiguredException e) {
			e.printStackTrace();
		}
		
		launchpad.setListener(new MainLaunchpadListener(client));
	}
}
