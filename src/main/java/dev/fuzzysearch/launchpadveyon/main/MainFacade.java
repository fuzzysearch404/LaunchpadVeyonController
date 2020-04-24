package dev.fuzzysearch.launchpadveyon.main;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_COMMAND_EXECUTABLE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_HELP;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_HELP_SUCCESSFUL_CONTAINS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.MidiUnavailableException;

import dev.fuzzysearch.launchpadveyon.config.ConfigurationFileParser;
import dev.fuzzysearch.launchpadveyon.config.exceptions.ProgramUnconfiguredException;
import dev.fuzzysearch.launchpadveyon.config.exceptions.VeyonUnavailableException;
import dev.fuzzysearch.launchpadveyon.launchpad.listeners.MainLaunchpadListener;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import net.thecodersbreakfast.lp4j.api.LaunchpadException;
import net.thecodersbreakfast.lp4j.midi.MidiDeviceConfiguration;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

public class MainFacade {

	public void run() throws VeyonUnavailableException {
		if(!ensureVeyonisAvailable())
			throw new VeyonUnavailableException("This system's environment"
					+ "does not have Veyon CLI available");
		
		System.out.println("[Init]: Creating ProgramManager");
		ProgramManager manager = ProgramManager.getInstance();

		System.out.println("[Init]: Detecting and setting up physical Launchpad");
		try {
			MidiLaunchpad launchpad = new MidiLaunchpad(MidiDeviceConfiguration.autodetect());
			manager.setMidiLaunchpad(launchpad);
			manager.setLaunchpadClient(launchpad.getClient());
			
			setLaunchpadListeners();
			
			System.out.println("[Init]: Physical Launchpad detected");
			manager.setLaunchpadConnected(true);
			
			// Resets the Launchpad's buffers, so the lights turn off if there were on before.
			manager.getLaunchpadClient().reset();
		} catch(MidiUnavailableException | LaunchpadException e) {
			System.err.println("Unable to detect the physical Launchpad. "
					+ "Entering virtual Launchpad only mode...");
		}
		
		System.out.println("[Init]: Reading the configuration and preparing the data");
		initConfiguration();
	}
	
	private void initConfiguration() {
		try {
			new ConfigurationFileParser();
		} catch (ProgramUnconfiguredException e) {
			// Skip the configuration, but keep program alive...
			e.printStackTrace();
		}
	}
	
	private void setLaunchpadListeners() {
		ProgramManager.getInstance().getMidiLaunchpad().setListener(new MainLaunchpadListener());
	}
	
	/**
	 * Checks if the Veyon CLI (command line interface) 
	 * is available on the current environment by
	 * building a test {@link Process}.
	 * 
	 * @return boolean condition if 
	 * the Veyon CLIis accessible.
	 */
	private boolean ensureVeyonisAvailable() {
		Process process;
		
		try {
			process = Runtime.getRuntime().exec(VEYON_CLI_COMMAND_EXECUTABLE + ' ' + VEYON_CLI_HELP);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String line = "";
		String fullOutput = "";
		
		try {
			while ((line=buf.readLine()) != null) {
				fullOutput += line;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			return false;
		}
		
		return (fullOutput.contains(VEYON_CLI_HELP_SUCCESSFUL_CONTAINS))? true: false;
	}
}
