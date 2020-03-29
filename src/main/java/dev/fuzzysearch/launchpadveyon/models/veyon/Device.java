package dev.fuzzysearch.launchpadveyon.models.veyon;

import dev.fuzzysearch.launchpadveyon.models.launchpad.LaunchpadPad;
import net.thecodersbreakfast.lp4j.api.Launchpad;

/**
 * This class represents Veyon's devices 
 * and them on the Novation {@link Launchpad}
 * pads.
 * 
 * @author Roberts Ziediņš
 *
 */
public class Device {
	
	private String ipAdreess;
	private LaunchpadPad pad;
	
	public Device(String ipAdress) {
		this.ipAdreess = ipAdress;
	}

	public Device(String ipAdreess, LaunchpadPad pad) {
		setIpAdreess(ipAdreess);
		setPad(pad);
	}

	public String getIpAdreess() {
		return ipAdreess;
	}

	public void setIpAdreess(String ipAdreess) {
		this.ipAdreess = ipAdreess;
	}

	public LaunchpadPad getPad() {
		return pad;
	}

	public void setPad(LaunchpadPad pad) {
		this.pad = pad;
	}

}
