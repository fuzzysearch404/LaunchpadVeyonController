package dev.fuzzysearch.launchpadveyon.launchpad.listeners;

import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.LaunchpadListenerAdapter;
import net.thecodersbreakfast.lp4j.api.Pad;

public class MainLaunchpadListener extends LaunchpadListenerAdapter {
	
	private LaunchpadClient client = ProgramManager.getInstance().getLaunchpadClient();
	
	public MainLaunchpadListener() {

	}

    @Override
    public void onPadPressed(Pad pad, long timestamp) {
        System.out.println("Pad pressed : " + pad);
        ProgramManager.getInstance().getLightManager().setSelected(pad);
    }
    
    @Override
    public void onButtonPressed(Button button, long timestamp) {
    	System.out.println("Button pressed: " + button);
    	client.setButtonLight(button, Color.GREEN, BackBufferOperation.COPY);
    }

}
