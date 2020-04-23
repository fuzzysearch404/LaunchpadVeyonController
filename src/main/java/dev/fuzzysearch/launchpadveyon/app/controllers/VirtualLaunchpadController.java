package dev.fuzzysearch.launchpadveyon.app.controllers;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.Pad;

import dev.fuzzysearch.launchpadveyon.events.LaunchpadPadEventDispatcher;

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
	private LaunchpadPadEventDispatcher padDispatcher = 
			ProgramManager.getInstance().getPadEventDispatcher();
	
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

}
