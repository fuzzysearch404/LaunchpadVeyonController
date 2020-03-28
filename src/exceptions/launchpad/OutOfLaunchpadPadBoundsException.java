package exceptions.launchpad;

import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.LaunchpadException;

/**
 * Class for exception that is thrown
 * if any of the provided coordinates
 * that are representing {@link Pad} 
 * on the {@link Launchpad} are 
 * out of Launchpad's bounds.
 * 
 * @author Roberts Ziediņš
 *
 */
public class OutOfLaunchpadPadBoundsException extends LaunchpadException {

	private static final long serialVersionUID = 4277976914896956288L;
	
	public OutOfLaunchpadPadBoundsException(String message) {
		super(message);
	}
	
	public OutOfLaunchpadPadBoundsException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
