package dev.fuzzysearch.launchpadveyon.config;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.GUI_EDIT_PAD_FXML_PATH;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.GUI_EDIT_PAD_TITLE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import dev.fuzzysearch.launchpadveyon.app.controllers.PadConfigurationWindowController;
import dev.fuzzysearch.launchpadveyon.app.dialogs.ErrorAlert;
import dev.fuzzysearch.launchpadveyon.config.exceptions.ConfigException;
import dev.fuzzysearch.launchpadveyon.config.exceptions.ProgramUnconfiguredException;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.veyon.models.Device;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * Class that manages the pad editing process.
 * 
 * @author Roberts Ziediņš
 *
 */
public class PadEditor {

	private Device device;
	private Pad pad;
	private ProgramManager manager = ProgramManager.getInstance();
	
	/**
	 * Constructs and starts the pad editing process.
	 * 
	 * @param device - previous {@link Device} on the pad
	 * @param pad - {@link Pad} to edit
	 */
	public PadEditor(Device device, Pad pad) {
		this.device = device;
		this.pad = pad;
		edit();
	}
	
	private void edit() {
		constructEditWindow();
	}
	
	/**
	 * Creates the actual JavaFX window,
	 * that is going to be displayed to the
	 * user. Also closes the previous
	 * popup window, if it exists.
	 */
	private void constructEditWindow() {
		Stage stage = new Stage();
		FileInputStream fxmlStream;
		try {
			fxmlStream = new FileInputStream(GUI_EDIT_PAD_FXML_PATH);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}
		
		FXMLLoader loader = new FXMLLoader();
		PadConfigurationWindowController controller = new PadConfigurationWindowController(this);
		loader.setController(controller);
		
		VBox root;
		try {
			root = loader.load(fxmlStream);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		stage.setScene(new Scene(root));
		stage.setTitle(GUI_EDIT_PAD_TITLE);
		manager.setPopupStage(stage);
        stage.show();   
	}
	
	/**
	 * Edits the current pad configuration
	 * 
	 * @param ipAddress - the new IP address
	 * for the new device
	 */
	public void editPad(String ipAddress) {
		ConfigurationFileParser parser = null;
		try {
			parser = new ConfigurationFileParser();
			parser.addNewDevice(pad, ipAddress);
		} catch (ProgramUnconfiguredException | IOException e) {
			e.printStackTrace();
			new ErrorAlert("Unable to configure", "Could update configuration"
        			+ "file or create a new one. Make sure program has permissions to read/"
        			+ "write files.");
			return;
		}
		catch (ConfigException e) {
			e.printStackTrace();
			new ErrorAlert("Unable to configure", "The current JSON configuration file"
					+ " has invalid syntax or is corrupted.");
		}
		
		manager.getLightManager().lightUpPadsForConfiguration();
	}
	
	/**
	 * Deletes the current pad configuration.
	 */
	public void deletePad() {
		ConfigurationFileParser parser = null;
		
		try {
			parser = new ConfigurationFileParser();
			parser.deleteDevice(pad);
		} catch (ProgramUnconfiguredException | IOException e) {
			e.printStackTrace();
			new ErrorAlert("Unable to configure", "Could update configuration"
        			+ "file or create a new one. Make sure program has permissions to read/"
        			+ "write files.");
			return;
		} catch (ConfigException e) {
			e.printStackTrace();
			new ErrorAlert("Unable to configure", "The current JSON configuration file"
					+ " has invalid syntax or is corrupted.");
			return;
		}
		
		manager.getLightManager().lightUpPadsForConfiguration();
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Pad getPad() {
		return pad;
	}

	public void setPad(Pad pad) {
		this.pad = pad;
	}
	
}
