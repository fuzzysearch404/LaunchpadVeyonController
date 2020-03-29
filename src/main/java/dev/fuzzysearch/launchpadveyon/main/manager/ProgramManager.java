package dev.fuzzysearch.launchpadveyon.main.manager;

import java.util.ArrayList;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.*;

import dev.fuzzysearch.launchpadveyon.launchpad.control.LaunchpadLightManager;
import dev.fuzzysearch.launchpadveyon.models.veyon.Device;
import dev.fuzzysearch.launchpadveyon.veyon.VeyonActionType;
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
	
	// LP4J API
	private MidiLaunchpad launchpad;
	private LaunchpadClient launchpadClient;
	
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
	
	private ProgramManager(MidiLaunchpad launchpad) {
		this.lightManager = new LaunchpadLightManager();
		this.launchpad = launchpad;
		this.launchpadClient = this.launchpad.getClient();
	}
	
	// Getters and Setters
	
	public MidiLaunchpad getLaunchpad() {
		return launchpad;
	}

	public void setLaunchpad(MidiLaunchpad launchpad) {
		this.launchpad = launchpad;
	}

	public LaunchpadClient getLaunchpadClient() {
		return launchpadClient;
	}

	public void setLaunchpadClient(LaunchpadClient launchpadClient) {
		this.launchpadClient = launchpadClient;
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
	
	public static ProgramManager getInstance(MidiLaunchpad launchpad) { 
        if (instance == null) 
            instance = new ProgramManager(launchpad); 
  
        return instance; 
    }

}
