package dev.fuzzysearch.launchpadveyon.veyon.models;

import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * This class represents Veyon's devices 
 * and them on the {@link Launchpad}
 * pads.
 * 
 * @author Roberts Ziediņš
 *
 */
public class Device {
	
	private String ipAddress;
	private Pad pad;
	
	public Device(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Device(String ipAddress, Pad pad) {
		setIpAddress(ipAddress);
		setPad(pad);
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Pad getPad() {
		return pad;
	}

	public void setPad(Pad pad) {
		this.pad = pad;
	}

}
