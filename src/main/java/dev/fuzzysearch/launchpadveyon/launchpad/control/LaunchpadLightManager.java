package dev.fuzzysearch.launchpadveyon.launchpad.control;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.*;

import java.util.HashMap;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.models.veyon.Device;
import dev.fuzzysearch.launchpadveyon.veyon.VeyonActionType;
import dev.fuzzysearch.launchpadveyon.veyon.commands.VeyonCommand;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.Pad;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

/**
 * Class for managing {@link MidiLaunchpad}'s lights.
 *
 * @author Roberts ZiediÅ†Å¡
 *
 */
public class LaunchpadLightManager {
	
	private HashMap<Pad, Color> padsColors;
	private HashMap<Button, Color> buttonsColors;
	private Pad selectedPad;
	
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
	 * only once.
	 */
	public void lightUpByDevices() {
		LaunchpadClient client = ProgramManager.getInstance().getLaunchpadClient();
		
		for(Device d: ProgramManager.getInstance().getLoadedDevices()) {
			int x = d.getPad().getX();
			int y = d.getPad().getY();
			Pad pad = Pad.at(x, y);
			
			client.setPadLight(pad, COLOR_DEVICE_LOADED, BackBufferOperation.COPY);
			padsColors.replace(pad, COLOR_DEVICE_LOADED);
		}
		
	}
	
	/**
	 * Lights up {@link Button} lights on {@link Launchpad}
	 * that are indicating switching between {@link VeyonCommand}s.
	 */
	public void lightUpModeSelectButtons() {
		ProgramManager manager = ProgramManager.getInstance();
		LaunchpadClient client = manager.getLaunchpadClient();
		VeyonActionType activeType = manager.getCurrentAction();
		
		if(activeType.equals(VeyonActionType.SCREEN_VIEW)) {
			client.setButtonLight(VEYON_REMOTEACCESS_VIEW_BUTTON, COLOR_ACTION_CONTEXT_ACTIVE
					,BackBufferOperation.COPY);
			buttonsColors.replace(VEYON_REMOTEACCESS_VIEW_BUTTON, COLOR_ACTION_CONTEXT_ACTIVE);
			
			client.setButtonLight(VEYON_REMOTEACCESS_CONTROL_BUTTON, 
					COLOR_ACTION_CONTEXT_INACTIVE, BackBufferOperation.COPY);
			buttonsColors.replace(VEYON_REMOTEACCESS_CONTROL_BUTTON, COLOR_ACTION_CONTEXT_INACTIVE);
		} 
		else if(activeType.equals(VeyonActionType.SCREEN_CONTROL)) {
			client.setButtonLight(VEYON_REMOTEACCESS_CONTROL_BUTTON, 
					COLOR_ACTION_CONTEXT_ACTIVE, BackBufferOperation.COPY);
			buttonsColors.replace(VEYON_REMOTEACCESS_CONTROL_BUTTON, COLOR_ACTION_CONTEXT_ACTIVE);
			
			client.setButtonLight(VEYON_REMOTEACCESS_VIEW_BUTTON, COLOR_ACTION_CONTEXT_INACTIVE
					,BackBufferOperation.COPY);
			buttonsColors.replace(VEYON_REMOTEACCESS_VIEW_BUTTON, COLOR_ACTION_CONTEXT_INACTIVE);
		}
		
		client.setButtonLight(VEYON_REMOTEACCESS_STOP_BUTTON, COLOR_ACTION_CONTEXT_STOP, BackBufferOperation.COPY);
		buttonsColors.replace(VEYON_REMOTEACCESS_STOP_BUTTON, COLOR_ACTION_CONTEXT_STOP);
		
	}
	
	/**
	 * Lights up the selected pad to color that
	 * represents that the selected pad is active.
	 */
	public void setSelected(Pad pad) {
		if(selectedPad != null) {
			if(ProgramManager.getInstance().padRepresentsLoadedDevice(selectedPad)) {
				setPadLight(selectedPad, COLOR_DEVICE_LOADED, BackBufferOperation.COPY);
			}
			else {
				setPadLight(selectedPad, COLOR_DEFAULT, BackBufferOperation.COPY);
			}
		}
		
		selectedPad = pad;
		setPadLight(pad, COLOR_DEVICE_ACTIVE, BackBufferOperation.COPY);
		
	}
	
	/**
	 * This method sets the {@link Pad} light to provided {@link Color}
	 * and also registers the color to the pad map.
	 */
	public void setPadLight(Pad pad, Color color, BackBufferOperation operation) {
		ProgramManager.getInstance().getLaunchpadClient().setPadLight(pad, color, operation);
		padsColors.replace(pad, color);
	}
	
	/**
	 * This method sets the {@link Button} light to provided {@link Color}
	 * and also registers the color to the pad map.
	 */
	public void setButtonLight(Button button, Color color, BackBufferOperation operation) {
		ProgramManager.getInstance().getLaunchpadClient().setButtonLight(button, color, operation);
		buttonsColors.replace(button, color);
	}
	
	private void initButtonMap() {
		buttonsColors = new HashMap<Button, Color>(2);
		
		buttonsColors.put(VEYON_REMOTEACCESS_VIEW_BUTTON, COLOR_DEFAULT);
		buttonsColors.put(VEYON_REMOTEACCESS_CONTROL_BUTTON, COLOR_DEFAULT);
		buttonsColors.put(VEYON_REMOTEACCESS_STOP_BUTTON, COLOR_DEFAULT);
	}
	
	private void initPadsMap() {
		padsColors = new HashMap<Pad, Color>(LAUNCHPAD_PAD_X_ROWS * LAUNCHPAD_PAD_Y_COLLUMNS);
		
		for(int x = 0; x < LAUNCHPAD_PAD_X_ROWS; x++) {
			for(int y = 0; y < LAUNCHPAD_PAD_Y_COLLUMNS; y++) {
				padsColors.put(Pad.at(x, y), COLOR_DEFAULT);
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
	public void blinkPad(Pad pad, Color color, Color color2, Color resetColor, int times, int millis) {
		BlinkPad bp = new BlinkPad(pad, color, color2, resetColor, times, millis);
		Thread t = new Thread(bp);
		t.start();
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
		private Color color;
		private Color color2;
		private Color resetColor;
		private int times;
		private int millis;
		
		private BlinkPad(Pad pad, Color color, Color color2, Color resetColor, int times, int millis) {
			this.pad = pad;
			this.color = color;
			this.color2 = color2;
			this.resetColor = resetColor;
			setTimes(times);
			setMillis(millis);
		}

		@Override
		public void run() {
			LaunchpadClient client = ProgramManager.getInstance().getLaunchpadClient();
			
			for(int i = 0; i < times; i++) {
				client.setPadLight(pad, color, BackBufferOperation.NONE);
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				client.setPadLight(pad, color2, BackBufferOperation.NONE);
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
			
			// Finally set to the reset color
			client.setPadLight(pad, resetColor, BackBufferOperation.NONE);
			
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
