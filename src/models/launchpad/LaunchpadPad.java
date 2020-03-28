package models.launchpad;

import static config.Configuration.*;

import exceptions.launchpad.OutOfLaunchpadPadBoundsException;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * {@link Launchpad}'s API does not include
 * {@link Pad} creation, so we have to create
 * our implementation to register pads for
 * our program's use cases.
 * 
 * @author Roberts Ziediņš
 *
 */
public class LaunchpadPad {
	
	private int x;
	private int y;
	
	public LaunchpadPad(int x, int y) throws OutOfLaunchpadPadBoundsException {
		setX(x);
		setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) throws OutOfLaunchpadPadBoundsException {
		if(x < LAUNCHPAD_PAD_MIN_X || x > LAUNCHPAD_PAD_MAX_X)
			throw new OutOfLaunchpadPadBoundsException("Out of Launchpad x bounds. X: " + x);
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) throws OutOfLaunchpadPadBoundsException {
		if(y < LAUNCHPAD_PAD_MIN_Y || y > LAUNCHPAD_PAD_MAX_Y)
			throw new OutOfLaunchpadPadBoundsException("Out of Launchpad y bounds. Y: " + y);
		this.y = y;
	}

}
