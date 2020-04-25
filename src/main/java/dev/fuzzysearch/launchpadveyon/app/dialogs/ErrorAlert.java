package dev.fuzzysearch.launchpadveyon.app.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class for JavaFX error message constructing.
 * 
 * @author Roberts Ziediņš
 *
 */
public class ErrorAlert {

	/**
	 * Creates and shows JavaFX error message dialog.
	 * 
	 * @param header - error header
	 * @param content - error content
	 */
	public ErrorAlert(String header, String content) {
		construct(header, content);
	}
	
	private void construct(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();
	}
}
