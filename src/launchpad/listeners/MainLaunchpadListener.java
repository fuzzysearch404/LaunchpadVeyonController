package launchpad.listeners;

import main.manager.ProgramManager;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.LaunchpadListenerAdapter;
import net.thecodersbreakfast.lp4j.api.Pad;

public class MainLaunchpadListener extends LaunchpadListenerAdapter {
	
	private LaunchpadClient client = ProgramManager.getInstance().getLaunchpadClient();
	private static boolean[][] buttons;
	private static final byte BUTTONS_COUNT_X = 8;
	private static final byte BUTTONS_COUNT_Y = 8;
	
	public MainLaunchpadListener() {
		buttons = new boolean[BUTTONS_COUNT_X][BUTTONS_COUNT_Y];
		for(int x = 0; x < BUTTONS_COUNT_X; x++) {
			for(int y = 0; y < BUTTONS_COUNT_Y; y++) {
				buttons[x][y] = false;
			}
		}
	}

    @Override
    public void onPadPressed(Pad pad, long timestamp) {
        System.out.println("Pad pressed : " + pad);
        buttons[pad.getX()][pad.getY()] = true;
        deactivateInactive(pad);
    }
    
    @Override
    public void onButtonPressed(Button button, long timestamp) {
    	System.out.println("Button pressed: " + button);
    	client.setButtonLight(button, Color.GREEN, BackBufferOperation.COPY);
    }
    
    private void deactivateInactive(Pad pad) {
    	int x = pad.getX(); int y = pad.getY();
    	
    	for(int _x = 0; _x < BUTTONS_COUNT_X; _x++) {
			for(int _y = 0; _y < BUTTONS_COUNT_Y; _y++) {
				if(buttons[_x][_y] && (_x != x || _y != y)) {
					client.setPadLight(Pad.at(_x, _y), Color.BLACK, BackBufferOperation.NONE);
					buttons[_x][_y] = false;
				}
				
			}
    	}
    }

}
