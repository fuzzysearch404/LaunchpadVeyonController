package dev.fuzzysearch.launchpadveyon.launchpad.listeners;

import dev.fuzzysearch.launchpadveyon.events.LaunchpadButtonEventDispatcher;
import dev.fuzzysearch.launchpadveyon.events.LaunchpadPadEventDispatcher;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
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
	
	private LaunchpadPadEventDispatcher padEventDispatcher = 
			ProgramManager.getInstance().getPadEventDispatcher();
	private LaunchpadButtonEventDispatcher buttonEventDispatcher =
			ProgramManager.getInstance().getButtonEventDispatcher();

    @Override
    public void onPadPressed(Pad pad, long timestamp) {
        padEventDispatcher.dispatch(pad, "PHYSICAL LAUNCHPAD");
    }
    
    @Override
    public void onButtonPressed(Button button, long timestamp) {
    	buttonEventDispatcher.dispatch(button, "PHYSICAL LAUNCHPAD");
    }

}
