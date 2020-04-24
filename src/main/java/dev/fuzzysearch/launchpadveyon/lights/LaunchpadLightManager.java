package dev.fuzzysearch.launchpadveyon.lights;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.LAUNCHPAD_PAD_X_ROWS;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LAUNCHPAD_PAD_Y_COLLUMNS;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LP_COLOR_ACTION_CONTEXT_ACTIVE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LP_COLOR_ACTION_CONTEXT_INACTIVE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LP_COLOR_ACTION_CONTEXT_STOP;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LP_COLOR_DEFAULT;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LP_COLOR_DEVICE_ACTIVE;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LP_COLOR_DEVICE_ADD;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LP_COLOR_DEVICE_EDIT;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LP_COLOR_DEVICE_FAILED;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.LP_COLOR_DEVICE_LOADED;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.PHYSICAL_LP_DEFAULT_LIGHT_BRIGHTNESS;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_REMOTEACCESS_CONTROL_BUTTON;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_REMOTEACCESS_STOP_BUTTON;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.VEYON_REMOTEACCESS_VIEW_BUTTON;

import java.util.HashMap;
import java.util.Map.Entry;

import dev.fuzzysearch.launchpadveyon.app.controllers.VirtualLaunchpadController;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.veyon.VeyonActionType;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonCommand;
import dev.fuzzysearch.launchpadveyon.veyon.utils.VeyonProcessExecutor;
import javafx.scene.paint.Paint;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Brightness;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.Pad;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

/**
 * Class for managing {@link MidiLaunchpad}'s lights.
 *
 * @author Roberts Ziediņš
 *
 */
public class LaunchpadLightManager {
	
	private HashMap<Pad, LaunchpadColor> padsColors;
	private HashMap<Button, LaunchpadColor> buttonsColors;
	private Pad selectedPad;
	private int brightness;
	
	public LaunchpadLightManager() {
		initPadsMap();
		initButtonMap();
	}

	/**
	 * Lights up {@link Pad} lights on {@link Launchpad} 
	 * according to list of devices at the {@link ProgramManager}.
	 * 
	 * This method does not use 
	 * {@link #setPadLight(Pad, Color, BackBufferOperation)} method
	 * to speed up this process by acquiring {@link LaunchpadClient}
	 * and {@link VirtualLaunchpadController} only once.
	 */
	public void lightUpPadsByDevices() {
		ProgramManager manager = ProgramManager.getInstance();
		LaunchpadClient midiLaunchpadClient = manager.getLaunchpadClient();
		VirtualLaunchpadController virtualLaunchpadController = manager.getVirtualLaunchpadController();
		
		Paint deviceLoadedPaint = LP_COLOR_DEVICE_LOADED.getVirtualLaunchpadColor();
		Paint emptyPaint = LP_COLOR_DEFAULT.getVirtualLaunchpadColor();
		Color deviceLoadedColor = LP_COLOR_DEVICE_LOADED.getMidiLaunchpadColor();
		Color emptyColor = LP_COLOR_DEFAULT.getMidiLaunchpadColor();
		
		for(Entry<Pad, LaunchpadColor> e: padsColors.entrySet()) {
			Pad pad = e.getKey();
			if(manager.padRepresentsLoadedDevice(pad)) {
				if(manager.isLaunchpadConnected())
					midiLaunchpadClient.setPadLight(pad, deviceLoadedColor, BackBufferOperation.COPY);
				
				virtualLaunchpadController.setPadColor(pad, deviceLoadedPaint);
				
				padsColors.replace(pad, LP_COLOR_DEVICE_LOADED);
			}
			else {
				if(manager.isLaunchpadConnected())
					midiLaunchpadClient.setPadLight(pad, emptyColor, BackBufferOperation.COPY);
				
				virtualLaunchpadController.setPadColor(pad, emptyPaint);
				
				padsColors.replace(pad, LP_COLOR_DEFAULT);
			}
		}
		
	}
	
	/**
	 * Lights up {@link Pad} lights on {@link Launchpad} 
	 * according to list of devices at the {@link ProgramManager}.
	 */
	public void lightUpPadsForConfiguration() {
		ProgramManager manager = ProgramManager.getInstance();
		LaunchpadClient midiLaunchpadClient = manager.getLaunchpadClient();
		VirtualLaunchpadController virtualLaunchpadController = manager.getVirtualLaunchpadController();
		
		Paint deviceEditPaint = LP_COLOR_DEVICE_EDIT.getVirtualLaunchpadColor();
		Paint deviceAddPaint = LP_COLOR_DEVICE_ADD.getVirtualLaunchpadColor();
		Color deviceEditColor = LP_COLOR_DEVICE_EDIT.getMidiLaunchpadColor();
		Color deviceAddColor = LP_COLOR_DEVICE_ADD.getMidiLaunchpadColor();
		
		for(Entry<Pad, LaunchpadColor> e: padsColors.entrySet()) {
			Pad pad = e.getKey();
			if(manager.padRepresentsLoadedDevice(pad)) {
				if(manager.isLaunchpadConnected())
					midiLaunchpadClient.setPadLight(pad, deviceEditColor, BackBufferOperation.COPY);
				
				virtualLaunchpadController.setPadColor(pad, deviceEditPaint);
				
				padsColors.replace(pad, LP_COLOR_DEVICE_FAILED);
			}
			else {
				if(manager.isLaunchpadConnected())
					midiLaunchpadClient.setPadLight(pad, deviceAddColor, BackBufferOperation.COPY);
				
				virtualLaunchpadController.setPadColor(pad, deviceAddPaint);
				
				padsColors.replace(pad, LP_COLOR_DEVICE_LOADED);
			}
		}
		
	}
	
	/**
	 * Lights up {@link Button} lights on {@link Launchpad}
	 * that are indicating switching between {@link VeyonCommand}s.
	 */
	public void lightUpContextSwitchButtons() {
		ProgramManager manager = ProgramManager.getInstance();
		LaunchpadClient midiLaunchpadClient = manager.getLaunchpadClient();
		VirtualLaunchpadController virtualLaunchpadController = manager.getVirtualLaunchpadController();
		VeyonActionType activeType = manager.getCurrentAction();
		
		if(activeType.equals(VeyonActionType.SCREEN_VIEW)) {
			if(manager.isLaunchpadConnected())
				midiLaunchpadClient.setButtonLight(VEYON_REMOTEACCESS_VIEW_BUTTON, 
						LP_COLOR_ACTION_CONTEXT_ACTIVE.getMidiLaunchpadColor(), BackBufferOperation.COPY);
			
			virtualLaunchpadController.setButtonColor(VEYON_REMOTEACCESS_VIEW_BUTTON,
					LP_COLOR_ACTION_CONTEXT_ACTIVE.getVirtualLaunchpadColor());
			
			buttonsColors.replace(VEYON_REMOTEACCESS_VIEW_BUTTON, LP_COLOR_ACTION_CONTEXT_ACTIVE);
			
			if(manager.isLaunchpadConnected())
				midiLaunchpadClient.setButtonLight(VEYON_REMOTEACCESS_CONTROL_BUTTON, 
						LP_COLOR_ACTION_CONTEXT_INACTIVE.getMidiLaunchpadColor(), BackBufferOperation.COPY);
			
			virtualLaunchpadController.setButtonColor(VEYON_REMOTEACCESS_CONTROL_BUTTON,
					LP_COLOR_ACTION_CONTEXT_INACTIVE.getVirtualLaunchpadColor());
			
			buttonsColors.replace(VEYON_REMOTEACCESS_CONTROL_BUTTON, LP_COLOR_ACTION_CONTEXT_INACTIVE);
		} 
		else if(activeType.equals(VeyonActionType.SCREEN_CONTROL)) {
			if(manager.isLaunchpadConnected())
				midiLaunchpadClient.setButtonLight(VEYON_REMOTEACCESS_CONTROL_BUTTON, 
						LP_COLOR_ACTION_CONTEXT_ACTIVE.getMidiLaunchpadColor(), BackBufferOperation.COPY);
			
			virtualLaunchpadController.setButtonColor(VEYON_REMOTEACCESS_CONTROL_BUTTON,
					LP_COLOR_ACTION_CONTEXT_ACTIVE.getVirtualLaunchpadColor());
			
			buttonsColors.replace(VEYON_REMOTEACCESS_CONTROL_BUTTON, LP_COLOR_ACTION_CONTEXT_ACTIVE);
			
			if(manager.isLaunchpadConnected())
				midiLaunchpadClient.setButtonLight(VEYON_REMOTEACCESS_VIEW_BUTTON,
						LP_COLOR_ACTION_CONTEXT_INACTIVE.getMidiLaunchpadColor() ,BackBufferOperation.COPY);
			
			virtualLaunchpadController.setButtonColor(VEYON_REMOTEACCESS_VIEW_BUTTON,
					LP_COLOR_ACTION_CONTEXT_INACTIVE.getVirtualLaunchpadColor());
			
			buttonsColors.replace(VEYON_REMOTEACCESS_VIEW_BUTTON, LP_COLOR_ACTION_CONTEXT_INACTIVE);
		}
		
		if(manager.isLaunchpadConnected())
			midiLaunchpadClient.setButtonLight(VEYON_REMOTEACCESS_STOP_BUTTON, 
					LP_COLOR_ACTION_CONTEXT_STOP.getMidiLaunchpadColor(), BackBufferOperation.COPY);
		
		virtualLaunchpadController.setButtonColor(VEYON_REMOTEACCESS_STOP_BUTTON,
				LP_COLOR_ACTION_CONTEXT_STOP.getVirtualLaunchpadColor());
		
		buttonsColors.replace(VEYON_REMOTEACCESS_STOP_BUTTON, LP_COLOR_ACTION_CONTEXT_STOP);
		
	}
	
	/**
	 * Lights up the selected pad to color that
	 * represents that the selected pad is active.
	 */
	public void setSelected(Pad pad) {
		ProgramManager manager = ProgramManager.getInstance();
		
		boolean isEditMode = manager.isEditMode();
		
		// First set previous selected pad to old color
		if(selectedPad != null) {
			boolean padRepresentsDevice = manager.padRepresentsLoadedDevice(selectedPad);
			if(!isEditMode && padRepresentsDevice)
				setPadLight(selectedPad, LP_COLOR_DEVICE_LOADED, BackBufferOperation.COPY);
			else if(padRepresentsDevice)
				setPadLight(selectedPad, LP_COLOR_DEVICE_EDIT, BackBufferOperation.COPY);
			else
				setPadLight(selectedPad, LP_COLOR_DEVICE_ADD, BackBufferOperation.COPY);
		}
		// Then set the color of the new selected pad
		if(manager.isEditMode() || manager.padRepresentsLoadedDevice(pad)) {
			selectedPad = pad;
			setPadLight(pad, LP_COLOR_DEVICE_ACTIVE, BackBufferOperation.COPY);
		}
		
	}
	
	/**
	 * Sets {@link #selectedPad} to null.
	 * This is useful if {@link VeyonProcessExecutor}
	 * sets the {@link Pad} light as "device failed",
	 * then the next pressed pad does not clear the
	 * light color, that represents the error.
	 */
	public void removeSelectedPad() {
		selectedPad = null;
	}
	
	/**
	 * This method sets the {@link Pad} light to provided {@link Color}
	 * and also registers the color to the pad map.
	 */
	public void setPadLight(Pad pad, LaunchpadColor color, BackBufferOperation operation) {
		ProgramManager manager = ProgramManager.getInstance();
		
		if(manager.isLaunchpadConnected())
			ProgramManager.getInstance().getLaunchpadClient().setPadLight(pad, color.getMidiLaunchpadColor(), operation);
		
		manager.getVirtualLaunchpadController().setPadColor(pad, color.getVirtualLaunchpadColor());
		
		padsColors.replace(pad, color);
	}
	
	/**
	 * This method sets the {@link Button} light to provided {@link Color}
	 * and also registers the color to the pad map.
	 */
	public void setButtonLight(Button button, LaunchpadColor color, BackBufferOperation operation) {
		ProgramManager.getInstance().getLaunchpadClient().setButtonLight(button, color.getMidiLaunchpadColor(), operation);
		buttonsColors.replace(button, color);
	}
	
	/**
	 * Sets the {@link MidiLaunchpad}'s {@link Brightness}
	 * and registers the value of light intensity
	 * in this class.
	 * 
	 * @param newBrightness - value of {@link Brightness}
	 */
	private void setBrigtness(int newBrightness) {
		if(newBrightness >= Brightness.MAX_VALUE)
			this.brightness = Brightness.MAX_VALUE;
		else if(newBrightness <= Brightness.MIN_VALUE)
			this.brightness = Brightness.MIN_VALUE;
		else
			this.brightness = newBrightness;
		
		ProgramManager manager = ProgramManager.getInstance();
		if(manager.isLaunchpadConnected())
			manager.getLaunchpadClient().setBrightness(Brightness.of(brightness));
	}
	
	/**
	 * Sets the {@link MidiLaunchpad}'s 
	 * initial {@link Brightness} at the program's 
	 * startup.
	 */
	public void initBrightness() {
		setBrigtness(PHYSICAL_LP_DEFAULT_LIGHT_BRIGHTNESS);
	}
	
	
	/**
	 * Increments the {@link MidiLaunchpad}'s 
	 * {@link Brightness} value if possible. 
	 * Otherwise sets to the max value.
	 */
	public void setBrigtnessUp() {
		setBrigtness(brightness + 1);
	}
	
	/**
	 * Lowers the {@link MidiLaunchpad}'s 
	 * {@link Brightness} value if possible. 
	 * Otherwise sets to the min value.
	 */
	public void setBrigtnessDown() {
		setBrigtness(brightness - 1);
	}
	
	private void initButtonMap() {
		buttonsColors = new HashMap<Button, LaunchpadColor>(2);
		
		buttonsColors.put(VEYON_REMOTEACCESS_VIEW_BUTTON, LP_COLOR_DEFAULT);
		buttonsColors.put(VEYON_REMOTEACCESS_CONTROL_BUTTON, LP_COLOR_DEFAULT);
		buttonsColors.put(VEYON_REMOTEACCESS_STOP_BUTTON, LP_COLOR_DEFAULT);
	}
	
	private void initPadsMap() {
		padsColors = new HashMap<Pad, LaunchpadColor>(LAUNCHPAD_PAD_X_ROWS * LAUNCHPAD_PAD_Y_COLLUMNS);
		
		for(int x = 0; x < LAUNCHPAD_PAD_X_ROWS; x++) {
			for(int y = 0; y < LAUNCHPAD_PAD_Y_COLLUMNS; y++) {
				padsColors.put(Pad.at(x, y), LP_COLOR_DEFAULT);
			}
		}
	}
	
	/**
	 * Blinks {@link Launchpad}'s {@link Pad} 
	 * in a two certain {@link Color}s.
	 * Blinking - switching between two colors 
	 * for X times with Y time delay. 
	 * 
	 * @param pad to change colors to
	 * @param color first blinking color
	 * @param color2 second blinking color
	 * @param resetColor final color to set at the end of loop
	 * @param times how many times to blink between colors
	 * @param millis blinking delay in milliseconds
	 */
	public void blinkPad(Pad pad, LaunchpadColor color, LaunchpadColor color2, LaunchpadColor resetColor, int times, int millis) {
		BlinkPad bp = new BlinkPad(pad, color, color2, resetColor, times, millis);
		Thread thread = new Thread(bp);
		thread.start();
	}
	
	/**
	 * Small utility class that implements {@link Pad}
	 * blinking in a new {@link Thread}.
	 * 
	 * @author Roberts Ziediņš
	 *
	 */
	private static class BlinkPad implements Runnable {
		
		private Pad pad;
		private LaunchpadColor color;
		private LaunchpadColor color2;
		private LaunchpadColor resetColor;
		private int times;
		private int millis;
		
		private BlinkPad(Pad pad, LaunchpadColor color, LaunchpadColor color2, LaunchpadColor resetColor, int times, int millis) {
			this.pad = pad;
			this.color = color;
			this.color2 = color2;
			this.resetColor = resetColor;
			setTimes(times);
			setMillis(millis);
		}

		@Override
		public void run() {
			ProgramManager manager = ProgramManager.getInstance();
			LaunchpadClient client = manager.getLaunchpadClient();
			VirtualLaunchpadController virtualLaunchpadController = manager.getVirtualLaunchpadController();
			
			for(int i = 0; i < times; i++) {
				if(manager.isLaunchpadConnected())
					client.setPadLight(pad, color.getMidiLaunchpadColor(), BackBufferOperation.NONE);
				
				virtualLaunchpadController.setPadColor(pad, color.getVirtualLaunchpadColor());
				
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				if(manager.isLaunchpadConnected())
					client.setPadLight(pad, color2.getMidiLaunchpadColor(), BackBufferOperation.NONE);
				
				virtualLaunchpadController.setPadColor(pad, color2.getVirtualLaunchpadColor());
				
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
			
			// Finally set to the reset color
			if(manager.isLaunchpadConnected())
				client.setPadLight(pad, resetColor.getMidiLaunchpadColor(), BackBufferOperation.NONE);
			
			virtualLaunchpadController.setPadColor(pad, resetColor.getVirtualLaunchpadColor());
			
		}
		
		private void setTimes(int times) {
			this.times = (times > 0)? times: 3;
		}
		
		/* 
		* Smaller delay could result errors or it could trigger
		* people with epileptic seizures and with even smaller delay 
		* the effect would not be seen by human eye anyway.
		*/
		private void setMillis(int millis) {
			this.millis = (millis >= 200)? millis: 200;
		}
		
	}
	
}
