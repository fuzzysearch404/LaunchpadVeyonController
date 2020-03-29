package dev.fuzzysearch.launchpadveyon.launchpad.listeners;

import dev.fuzzysearch.launchpadveyon.launchpad.events.LaunchpadPadEventDispatcher;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
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
	
	private LaunchpadClient client = ProgramManager.getInstance().getLaunchpadClient();
	private LaunchpadPadEventDispatcher padEventDispatcher 
		= new LaunchpadPadEventDispatcher();
	
	public MainLaunchpadListener() {

	}

    @Override
    public void onPadPressed(Pad pad, long timestamp) {
        padEventDispatcher.dispatch(pad, timestamp);
    }
    
    @Override
    public void onButtonPressed(Button button, long timestamp) {
    	System.out.println("Button pressed: " + button);
    	client.setButtonLight(button, Color.GREEN, BackBufferOperation.COPY);
    }

}
