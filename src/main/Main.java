package main;

import javax.sound.midi.MidiUnavailableException;

import net.thecodersbreakfast.lp4j.api.LaunchpadException;

public class Main {

	public static void main(String[] args) {
		MainFacade app = new MainFacade();
		try {
			app.run();
		} catch (MidiUnavailableException | LaunchpadException e) {
			System.err.println("Unable to detect the Launchpad. Exiting...");
		}
		
	}
}
