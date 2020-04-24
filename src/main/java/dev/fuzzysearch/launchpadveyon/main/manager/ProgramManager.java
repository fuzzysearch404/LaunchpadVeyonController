package dev.fuzzysearch.launchpadveyon.main.manager;

import java.util.ArrayList;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.*;

import dev.fuzzysearch.launchpadveyon.events.LaunchpadButtonEventDispatcher;
import dev.fuzzysearch.launchpadveyon.events.LaunchpadPadEventDispatcher;
import dev.fuzzysearch.launchpadveyon.launchpad.control.LaunchpadLightManager;
import dev.fuzzysearch.launchpadveyon.veyon.VeyonActionType;
import dev.fuzzysearch.launchpadveyon.veyon.models.Device;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.Pad;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

/**
 * Contains program level resources, that can be
 * accessed in any class.
 * 
 * @author Roberts Ziediņš
 *
 */
public class ProgramManager {
	
	/* Determines if the program is running just with the virtual Launchpad
	* if physical Launchpad is not available.
	*/
	private boolean launchpadConnected = false;
	
	// LP4J API
	private MidiLaunchpad midiLaunchpad;
	private LaunchpadClient launchpadClient;
	
	// Event dispatchers
	private LaunchpadPadEventDispatcher padEventDispatcher = new LaunchpadPadEventDispatcher();
	private LaunchpadButtonEventDispatcher buttonEventDispatcher = new LaunchpadButtonEventDispatcher();
	
	// Launchpad's light management
	private LaunchpadLightManager lightManager;
	
	// Loaded Veyon devices
	private ArrayList<Device> loadedDevices;
	
	// Current selected action
	private VeyonActionType currentAction = DEFAULT_VEYON_ACTION_TYPE;
	
	// Veyon's process - e.g. active remote screen view
	private Process activeVeyonProcess;
	private Device activeVeyonDevice;
	
	// Singleton
	private static ProgramManager instance = null;
	
	
	private ProgramManager() {
		this.lightManager = new LaunchpadLightManager();
	}
	
	// Getters and Setters
	
	public boolean isLaunchpadConnected() {
		return launchpadConnected;
	}

	public void setLaunchpadConnected(boolean launchpadConnected) {
		this.launchpadConnected = launchpadConnected;
	}
	
	public MidiLaunchpad getMidiLaunchpad() {
		return midiLaunchpad;
	}

	public void setMidiLaunchpad(MidiLaunchpad launchpad) {
		this.midiLaunchpad = launchpad;
	}

	public LaunchpadClient getLaunchpadClient() {
		return launchpadClient;
	}

	public void setLaunchpadClient(LaunchpadClient launchpadClient) {
		this.launchpadClient = launchpadClient;
	}

	public LaunchpadPadEventDispatcher getPadEventDispatcher() {
		return padEventDispatcher;
	}

	public void setPadEventDispatcher(LaunchpadPadEventDispatcher padEventDispatcher) {
		this.padEventDispatcher = padEventDispatcher;
	}

	public LaunchpadButtonEventDispatcher getButtonEventDispatcher() {
		return buttonEventDispatcher;
	}

	public void setButtonEventDispatcher(LaunchpadButtonEventDispatcher buttonEventDispatcher) {
		this.buttonEventDispatcher = buttonEventDispatcher;
	}

	public LaunchpadLightManager getLightManager() {
		return lightManager;
	}

	public void setLightManager(LaunchpadLightManager lightManager) {
		this.lightManager = lightManager;
	}

	public ArrayList<Device> getLoadedDevices() {
		return loadedDevices;
	}

	public void setLoadedDevices(ArrayList<Device> devices) {
		this.loadedDevices = devices;
	}
	
	public VeyonActionType getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(VeyonActionType currentAction) {
		this.currentAction = currentAction;
	}

	public Process getActiveVeyonProcess() {
		return activeVeyonProcess;
	}

	public void setActiveVeyonProcess(Process activeVeyonProcess) {
		this.activeVeyonProcess = activeVeyonProcess;
	}

	public Device getActiveVeyonDevice() {
		return activeVeyonDevice;
	}

	public void setActiveVeyonDevice(Device activeVeyonDevice) {
		this.activeVeyonDevice = activeVeyonDevice;
	}

	/**
	 * Returns {@link Device} mapped by {@link Pad}.
	 * If the device is not present, returns null.
	 * 
	 * @param pad to check
	 * @return device object
	 */
	public Device getDeviceByPad(Pad pad) {
		if(loadedDevices != null) {
			for(Device d: loadedDevices) {
				if(d.getPad().equals(pad))
					return d;
			}
		}
		
		return null;
	}

	/**
	 * Checks if any of the loaded {@link Device}
	 * objects are represented on provided {@link Pad} 
	 * 
	 * @param pad to check.
	 * @return true if pad represents a device (device is loaded on the pad)
	 */
	public boolean padRepresentsLoadedDevice(Pad pad) {
		if(loadedDevices == null) return false;
		
		for(Device d: loadedDevices) {
			Pad devicePad = d.getPad();
			if(devicePad.getX() == pad.getX() && devicePad.getY() == pad.getY())
				return true;
		}
		
		return false;
	}
	

	public static ProgramManager getInstance() { 
        if (instance == null) 
            instance = new ProgramManager(); 
  
        return instance; 
    }

}
