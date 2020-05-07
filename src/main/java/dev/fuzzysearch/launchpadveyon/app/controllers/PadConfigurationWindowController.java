package dev.fuzzysearch.launchpadveyon.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import dev.fuzzysearch.launchpadveyon.config.PadEditor;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.veyon.models.Device;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * JavaFX FXML controller class that implements
 * interactivity for the pad configuration scene.
 * 
 * @author Roberts Ziediņš
 *
 */
public class PadConfigurationWindowController implements Initializable {
	
	private PadEditor padEditor;
	
	@FXML
	private TextField ipinputfield;
	@FXML
	private Text inputerror;
	@FXML
	private Button deletebutton;
	
	public PadConfigurationWindowController(PadEditor padEditor) {
		this.padEditor = padEditor;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Device device = padEditor.getDevice();
		if(device != null) {
			ipinputfield.setText(device.getIpAddress());
		}
		else {
			deletebutton.setDisable(true);
		}
		
		ipinputfield.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.ENTER))
					configurationSaveEvent();
			}
		});
	}
	
	/**
	 * This event is fired whenever user
	 * clicks button that saves the pad
	 * configuration.
	 */
	@FXML
	protected void configurationSaveEvent() {
		String inputIpAddress = ipinputfield.getText();
		
		if(inputIpAddress.isBlank()) {
			inputerror.setVisible(true);
			return;
		}
		
		padEditor.editPad(inputIpAddress);
		ProgramManager.getInstance().closePopupStage();
	}
	
	/**
	 * This event is fired whenever user
	 * clicks button that deletes
	 * pad configuration.
	 */
	@FXML
	protected void configurationDeleteEvent() {
		padEditor.deletePad();
		ProgramManager.getInstance().closePopupStage();
	}

}
