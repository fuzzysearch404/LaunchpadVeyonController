package dev.fuzzysearch.launchpadveyon.lights;

import javafx.scene.paint.Paint;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

/**
 * Custom class that holds color objects
 * for both Launchpads - {@link MidiLaunchpad}
 * and the physical Launchpad.
 * 
 * @author Roberts Ziediņš
 *
 */
public class LaunchpadColor {

	private ColorType type;
	private Color midiLaunchpadColor;
	private Paint physicalLaunchpadColor;
	
	public LaunchpadColor(ColorType type, Color midiLaunchpadColor, Paint virtualLaunchpadColor) {
		this.type = type;
		this.midiLaunchpadColor = midiLaunchpadColor;
		this.physicalLaunchpadColor = virtualLaunchpadColor;
	}
	
	public ColorType getType() {
		return type;
	}
	
	public void setType(ColorType type) {
		this.type = type;
	}
	
	public Color getMidiLaunchpadColor() {
		return midiLaunchpadColor;
	}
	
	public void setMidiLaunchpadColor(Color midiLaunchpadColor) {
		this.midiLaunchpadColor = midiLaunchpadColor;
	}
	
	public Paint getVirtualLaunchpadColor() {
		return physicalLaunchpadColor;
	}
	
	public void setVirtualLaunchpadColor(Paint physicalLaunchpadColor) {
		this.physicalLaunchpadColor = physicalLaunchpadColor;
	}
	
}
