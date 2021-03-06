package dev.fuzzysearch.launchpadveyon.main;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.GUI_MAIN_FXML_PATH;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.GUI_MAIN_TITLE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_COMMAND_EXECUTABLE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_HELP;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_CLI_HELP_SUCCESSFUL_CONTAINS;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.MidiUnavailableException;

import dev.fuzzysearch.launchpadveyon.app.dialogs.ErrorAlert;
import dev.fuzzysearch.launchpadveyon.config.ConfigurationFileParser;
import dev.fuzzysearch.launchpadveyon.config.exceptions.ConfigException;
import dev.fuzzysearch.launchpadveyon.config.exceptions.ProgramUnconfiguredException;
import dev.fuzzysearch.launchpadveyon.config.exceptions.VeyonUnavailableException;
import dev.fuzzysearch.launchpadveyon.launchpad.listeners.MainLaunchpadListener;
import dev.fuzzysearch.launchpadveyon.lights.LaunchpadLightManager;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.thecodersbreakfast.lp4j.api.LaunchpadException;
import net.thecodersbreakfast.lp4j.midi.MidiDeviceConfiguration;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

public class MainFacade {
	
	private boolean noPhysicalLaunchpad = false;
	private boolean noConfigurationFile = false;
	private boolean invalidConfiguration = false;
	private boolean veyonUnavailable = false;

	public void run() throws VeyonUnavailableException {
		System.out.println("[Init]: Checking Veyon availability on this system");
		if(!ensureVeyonisAvailable()) {
			veyonUnavailable = true;
			throw new VeyonUnavailableException("This system's environment"
					+ " does not have Veyon CLI available");
		}
		
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
			noPhysicalLaunchpad = true;
			System.err.println("Unable to detect the physical Launchpad. "
					+ "Entering virtual Launchpad only mode...");
		}
		
		System.out.println("[Init]: Reading the configuration and preparing the data");
		initConfiguration();
	}
	
	private void initConfiguration() {
		try {
			ConfigurationFileParser configParser = new ConfigurationFileParser();
			configParser.configure();
		
		}
		// Skip the configuration, but keep program alive...
		catch (ProgramUnconfiguredException e) {
			noConfigurationFile = true;
			
			e.printStackTrace();
		} catch (ConfigException e) {
			invalidConfiguration = true;
			
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
	
	/**
	 * Loads the main JavaFX stage and lights up
	 * both Launchpad devices lights.
	 * 
	 * @param primary - primary stage from Main class
	 * @throws Exception - if JavaFX failed to load for any reason
	 */
	public void startJavaFXAndLightUpLaunchpads(Stage primary) throws Exception {
		if(veyonUnavailable) {
			new ErrorAlert("Veyon unavailable", "This program requires"
					+ " Veyon CLI on this system's environment");
			return;
		}
		
		ProgramManager.getInstance().setMainStage(primary);
		
		FileInputStream fxmlStream = new FileInputStream(GUI_MAIN_FXML_PATH);
		FXMLLoader loader = new FXMLLoader();
		VBox root = loader.load(fxmlStream);
		ProgramManager.getInstance().setVirtualLaunchpadController(loader.getController());
		primary.setScene(new Scene(root));
		primary.setTitle(GUI_MAIN_TITLE);
        primary.show();
        
        if(noConfigurationFile) {
        	new ErrorAlert("Unable to configure", "Could not find existing configuration"
        			+ "file or create a new one. Make sure program has permissions to read/"
        			+ "write files.");
        }
        if(invalidConfiguration) {
        	new ErrorAlert("Unable to configure", "The current JSON configuration file"
					+ " has invalid syntax or is corrupted.");
        }
        if(noPhysicalLaunchpad) {
        	new ErrorAlert("No physical Launchpad", "Could not detect MIDI Launchpad."
        			+ " Connect the Launchpad device and restart to use physical Launchpad."
        			+ " This time program will run on virtual Launchpad mode only.");
        }

        /** We could not do this before, because the VirtualLaunchpadController did not exist before
         *  and it is required for the light manager.
         */
        System.out.println("[Init]: Lighting up Launchpads");
        LaunchpadLightManager lightManager = ProgramManager.getInstance().getLightManager();
        lightManager.lightUpContextSwitchButtons();
		lightManager.lightUpPadsByDevices();
		lightManager.initBrightness();
	}
}
