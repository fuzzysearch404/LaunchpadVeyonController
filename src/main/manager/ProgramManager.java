package main.manager;

import java.util.ArrayList;

import launchpad.control.LaunchpadLightManager;
import models.veyon.Device;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
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
	private ArrayList<Device> devices;
	
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

	public ArrayList<Device> getDevices() {
		return devices;
	}

	public void setDevices(ArrayList<Device> devices) {
		this.devices = devices;
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
