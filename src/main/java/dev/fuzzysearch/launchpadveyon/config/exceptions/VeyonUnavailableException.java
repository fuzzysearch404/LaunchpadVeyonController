package dev.fuzzysearch.launchpadveyon.config.exceptions;

/**
 * Class for exception that is thrown if
 * the Veyon's CLI is not available
 * on the current  environment.
 * 
 * @author Roberts Ziediņš
 *
 */
public class VeyonUnavailableException extends ConfigException {

	private static final long serialVersionUID = 7258840267450745225L;

	public VeyonUnavailableException(String message) {
		super(message);
	}
	
	public VeyonUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
