package exceptions.config;

/**
 * Class for exception that is thrown if
 * the program's configuration failed.
 * 
 * @author Roberts Ziediņš
 *
 */
public class ProgramUncofiguredException extends ConfigException {

	private static final long serialVersionUID = -1600122038682707050L;

	public ProgramUncofiguredException(String message) {
		super(message);
	}
	
	public ProgramUncofiguredException(String message, Throwable cause) {
		super(message, cause);
	}

}
