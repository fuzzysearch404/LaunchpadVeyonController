package dev.fuzzysearch.launchpadveyon.launchpad.listeners;

import dev.fuzzysearch.launchpadveyon.launchpad.events.LaunchpadButtonEventDispatcher;
import dev.fuzzysearch.launchpadveyon.launchpad.events.LaunchpadPadEventDispatcher;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.LaunchpadListenerAdapter;
import net.thecodersbreakfast.lp4j.api.Pad;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;

/**
 * Implements {@link LaunchpadListenerAdapter} that
 * allows to listen {@link MidiLaunchpad}'s events,
 * e.g. when {@link Button}s or {@link Pad}s get pressed or released.
 * 
 * @author Roberts Ziediņš
 *
 */
public class MainLaunchpadListener extends LaunchpadListenerAdapter {
	
	private LaunchpadPadEventDispatcher padEventDispatcher 
		= new LaunchpadPadEventDispatcher();
	private LaunchpadButtonEventDispatcher buttonEventDispatcher
		= new LaunchpadButtonEventDispatcher();
	
	public MainLaunchpadListener() {

	}

    @Override
    public void onPadPressed(Pad pad, long timestamp) {
        padEventDispatcher.dispatch(pad, timestamp);
    }
    
    @Override
    public void onButtonPressed(Button button, long timestamp) {
    	buttonEventDispatcher.dispatch(button, timestamp);
    }

}
