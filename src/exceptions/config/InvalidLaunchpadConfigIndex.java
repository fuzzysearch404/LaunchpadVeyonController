package exceptions.config;

public class InvalidLaunchpadConfigIndex extends ConfigException {

	private static final long serialVersionUID = 8331047098614152421L;

	public InvalidLaunchpadConfigIndex(String message) {
		super(message);
	}
	
	public InvalidLaunchpadConfigIndex(String message, Throwable cause) {
		super(message, cause);
	}

}
