package dev.fuzzysearch.launchpadveyon.main.manager;

import java.util.ArrayList;

import dev.fuzzysearch.launchpadveyon.launchpad.control.LaunchpadLightManager;
import dev.fuzzysearch.launchpadveyon.models.veyon.Device;
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
	
	private MidiLaunchpad launchpad;
	private LaunchpadClient launchpadClient;
	private LaunchpadLightManager lightManager;
	private ArrayList<Device> loadedDevices;
	private ArrayList<Device> failedDevices;
	
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
	
	public ArrayList<Device> getFailedDevices() {
		return failedDevices;
	}

	public void setFailedDevices(ArrayList<Device> failedDevices) {
		this.failedDevices = failedDevices;
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
	
	/**
	 * Checks if any of the failed {@link Device}
	 * objects are represented on provided {@link Pad} 
	 * 
	 * @param pad to check.
	 * @return true if pad represents a device (device is represented as failed on the pad)
	 */
	public boolean padRepresentsFailedDevice(Pad pad) {
		if(failedDevices == null) return false;
		
		for(Device d: failedDevices) {
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
