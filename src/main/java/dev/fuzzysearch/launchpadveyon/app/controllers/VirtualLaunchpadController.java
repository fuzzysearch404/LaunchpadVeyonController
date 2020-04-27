package dev.fuzzysearch.launchpadveyon.app.controllers;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.Pad;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import dev.fuzzysearch.launchpadveyon.app.dialogs.ErrorAlert;
import dev.fuzzysearch.launchpadveyon.config.ConfigurationFileParser;
import dev.fuzzysearch.launchpadveyon.config.exceptions.ConfigException;
import dev.fuzzysearch.launchpadveyon.events.LaunchpadButtonEventDispatcher;
import dev.fuzzysearch.launchpadveyon.events.LaunchpadPadEventDispatcher;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.CONFIG_FILE_DEFAULT_FILE_NAME;;

/**
 * JavaFX FXML controller class that implements
 * interactivity for the virtual Launchpad's scene.
 *
 * @author Roberts Ziediņš
 *
 */
public class VirtualLaunchpadController {
	
	@FXML
	private AnchorPane mainContainer;
	
	@FXML
	private CheckMenuItem editmenuitem;
	
	private LaunchpadPadEventDispatcher padDispatcher = 
			ProgramManager.getInstance().getPadEventDispatcher();
	private LaunchpadButtonEventDispatcher buttonDispatcher = 
			ProgramManager.getInstance().getButtonEventDispatcher();
	
	/**
	 * This event is fired whenever a Pad
	 * is pressed on the virtual Launchpad.
	 * 
	 * @param m - {@link MouseEvent} mouse event payload
	 */
	@FXML
	protected void launchpadPadPressedEvent(MouseEvent m) {
		Node node = (Node) m.getSource();
		Pad pad = decodePadID(node.getId());
		
		padDispatcher.dispatch(pad, "VIRTUAL LAUNCHPAD");
	}
	
	/**
	 * This event is fired whenever a Button
	 * is pressed on the virtual Launchpad.
	 * 
	 * @param m - {@link MouseEvent} mouse event payload
	 */
	@FXML
	protected void launchpadButtonPressedEvent(MouseEvent m) {
		Node node = (Node) m.getSource();
		Button button = decodeButtonID(node.getId());
		
		buttonDispatcher.dispatch(button, "VIRTUAL LAUNCHPAD");
	}
	
	/**
	 * Converts FXML {@link Node} ID
	 * to {@link Launchpad}'s {@link Pad} object.
	 * 
	 * The ID contains the following:
	 * p - prefix for the ID (stands for Pad)
	 * x - X pad coordinate on the Launchpad.
	 * y - Y pad coordinate on the Launchpad.
	 * 
	 * Example: p03 - pad for x:0, y:3
	 * 
	 * @param id - ID to decode
	 * @return {@link Pad} the matching pad
	 * 
	 * @see #encodePadID(Pad)
	 */
	public static Pad decodePadID(String id) {
		int x = Integer.parseInt("" + id.charAt(1));
		int y = Integer.parseInt("" + id.charAt(2));
		
		return Pad.at(x, y);
	}
	
	/**
	 * Converts {@link Launchpad}'s {@link Pad}
	 * to FXML {@link Node} object ID.
	 * 
	 * The ID contains the following:
	 * p - prefix for the ID (stands for Pad)
	 * x - X pad coordinate on the Launchpad.
	 * y - Y pad coordinate on the Launchpad.
	 * 
	 * Example: p03 - pad for x:0, y:3
	 * 
	 * @param pad - {@link Pad} to encode
	 * @return {@link String} - the generated ID
	 * 
	 * @see #decodePadID(String)
	 */
	public static String encodePadID(Pad pad) {
		return "p" + pad.getX() + pad.getY();
	}
	
	/**
	 * Converts  FXML {@link Node} ID
	 * to {@link Launchpad}'s {@link Button} object.
	 * 
	 * @param id - ID to decode
	 * @return {@link Button} the matching button
	 * 
	 * @see #decodeButtonID(String)
	 */
	public static Button decodeButtonID(String id) {
		switch (id) {
		case "user1":
			return Button.USER_1;
		case "user2":
			return Button.USER_2;
		case "stop":
			return Button.STOP;
		case "up":
			return Button.UP;
		case "down":
			return Button.DOWN;
		default:
			throw new IllegalArgumentException("Unexpected value: " + id);
		}
	}
	
	/**
	 * Converts {@link Launchpad}'s {@link Button}
	 * to FXML {@link Node} object ID.
	 * 
	 * @param button - {@link Button} to encode
	 * @return {@link String} - the generated ID
	 * 
	 * @see #encodeButtonID(Button)
	 */
	public static String encodeButtonID(Button button) {
		switch (button) {
		case USER_1:
			return "user1";
		case USER_2:
			return "user2";
		case STOP:
			return "stop";
		case UP:
			return "up";
		case DOWN:
			return "down";
		default:
			throw new IllegalArgumentException("Unexpected value: " + button);
		}
	}
	
	/**
	 * Sets a color for a certain pad on the
	 * virtual Launchpad.
	 * 
	 * @param id - ID of the desired pad
	 * @param color - {@link Paint} of the desired paint
	 * 
	 * @see #encodePadID(Pad) Pad ID generation
	 */
	public void setPadColor(String id, Paint color) {
		Shape shape = (Shape) mainContainer.lookup('#' + id);
		if(shape != null) shape.setFill(color);
	}
	
	/**
	 * Sets a color for a certain pad on the
	 * virtual Launchpad.
	 * 
	 * @param pad - {@link Pad} the desired pad
	 * @param color - {@link Paint} of the desired paint
	 */
	public void setPadColor(Pad pad, Paint color) {
		Shape shape = (Shape) mainContainer.lookup('#' + encodePadID(pad));
		if(shape != null) shape.setFill(color);
	}
	
	/**
	 * Sets a color for a certain button on the
	 * virtual Launchpad.
	 * 
	 * @param id - ID of the desired button
	 * @param color - {@link Paint} of the desired paint
	 * 
	 * @see #encodeButtonID(Pad) Button ID generation
	 */
	public void setButtonColor(String id, Paint color) {
		Shape shape = (Shape) mainContainer.lookup('#' + id);
		if(shape != null) shape.setFill(color);
	}
	
	/**
	 * Sets a color for a certain button on the
	 * virtual Launchpad.
	 * 
	 * @param pad - {@link Button} the desired button
	 * @param color - {@link Paint} of the desired paint
	 */
	public void setButtonColor(Button button, Paint color) {
		Shape shape = (Shape) mainContainer.lookup('#' + encodeButtonID(button));
		if(shape != null) shape.setFill(color);
	}
	
	/**
	 * This event is fired whenever user
	 * clicks the menu button that
	 * enables/disables the edit mode.
	 */
	@FXML
	protected void configurationModeEvent() {
		ProgramManager manager = ProgramManager.getInstance();
		manager.setEditMode((manager.isEditMode())? false: true);
		
		if(manager.isEditMode()) {
			manager.destroyCurrentVeyonProcess();
			manager.getLightManager().lightUpPadsForConfiguration();
		}
		else {
			manager.closePopupStage();
			manager.getLightManager().lightUpPadsByDevices();
		}
	}
	
	/**
	 * This event is fired whenever user
	 * clicks the menu button that
	 * imports custom user configuration.
	 */
	@FXML
	protected void importConfigurationEvent() {
		ProgramManager manager = ProgramManager.getInstance();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open configuration file");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON file", "*.json"));
		File file = fileChooser.showOpenDialog(manager.getMainStage());
		
		if(file != null && file.canRead()) {
			ConfigurationFileParser parser;
			try {
				parser = new ConfigurationFileParser(file);
				parser.configure();
				parser.dumpJSONToConfigurationFile();
				ProgramManager.getInstance().getLightManager().lightUpPadsByDevices();
			} catch (IOException | ConfigException e) {
				e.printStackTrace();
				new ErrorAlert("Configuration failed", "Invalid configuration file. Check"
						+ " if the configuration file has valid syntax.");
			}
			
			// This switches edit mode off
			if(manager.isEditMode()) {
				manager.setEditMode(false);
				editmenuitem.setSelected(false);
			}
		}
	}
	
	/**
	 * This event is fired whenever user
	 * clicks the menu button that
	 * exports custom user configuration.
	 */
	@FXML
	protected void exportConfigurationEvent() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save configuration file");
		fileChooser.setInitialFileName(CONFIG_FILE_DEFAULT_FILE_NAME);
		fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON file", "*.json"));
		File file = fileChooser.showSaveDialog(ProgramManager.getInstance().getMainStage());
		
		if(file != null) {
			ConfigurationFileParser parser;
			try {
				parser = new ConfigurationFileParser();
				parser.writeJSONtoFile(file, true);
			} catch (IOException | ConfigException e) {
				e.printStackTrace();
				new ErrorAlert("Export failed", "Could not create configuration"
						+ " file copy in the specified location.");
			}
		}
	}
	
	/**
	 * This event is fired whenever user
	 * clicks the menu button that
	 * quits the program.
	 */
	@FXML
	protected void appExitEvent() {
		ProgramManager.getInstance().shutdownProgram();
	}
	
	/**
	 * This event is fired whenever user
	 * clicks the menu button that
	 * opens information about program
	 * in the default browser.
	 */
	@FXML
	protected void aboutEvent() {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    try {
				Desktop.getDesktop().browse(new URI("https://github.com/fuzzysearch404/LaunchpadVeyonController"));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
				new ErrorAlert("Unavailable", "Sorry, this option is not available.");
			}
		}
	}

}
