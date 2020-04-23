package dev.fuzzysearch.launchpadveyon.veyon.models;

import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.Pad;

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
	private Pad pad;
	
	public Device(String ipAdress) {
		this.ipAdreess = ipAdress;
	}

	public Device(String ipAdreess, Pad pad) {
		setIpAdreess(ipAdreess);
		setPad(pad);
	}

	public String getIpAdreess() {
		return ipAdreess;
	}

	public void setIpAdreess(String ipAdreess) {
		this.ipAdreess = ipAdreess;
	}

	public Pad getPad() {
		return pad;
	}

	public void setPad(Pad pad) {
		this.pad = pad;
	}

}
