package exceptions.config;

/**
 * Class for exception that is thrown if
 * the program's configuration failed.
 * 
 * @author Roberts Ziediņš
 *
 */
public class ProgramUnconfiguredException extends ConfigException {

	private static final long serialVersionUID = -1600122038682707050L;

	public ProgramUnconfiguredException(String message) {
		super(message);
	}
	
	public ProgramUnconfiguredException(String message, Throwable cause) {
		super(message, cause);
	}

}
