package dev.fuzzysearch.launchpadveyon.main;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_COMMAND_EXECUTABLE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_HELP;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_HELP_SUCCESSFUL_CONTAINS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.MidiUnavailableException;

import dev.fuzzysearch.launchpadveyon.exceptions.config.ProgramUnconfiguredException;
import dev.fuzzysearch.launchpadveyon.exceptions.config.VeyonUnavailableException;
import dev.fuzzysearch.launchpadveyon.launchpad.control.LaunchpadLightManager;
import dev.fuzzysearch.launchpadveyon.launchpad.listeners.MainLaunchpadListener;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import net.thecodersbreakfast.lp4j.midi.MidiDeviceConfiguration;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;
import dev.fuzzysearch.launchpadveyon.setup.LaunchpadConfigParser;

public class MainFacade {

	public void run() throws MidiUnavailableException, VeyonUnavailableException {
		if(!ensureVeyonisAvailable())
			throw new VeyonUnavailableException("This system's environment"
					+ "does not have Veyon CLI available");
		
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
