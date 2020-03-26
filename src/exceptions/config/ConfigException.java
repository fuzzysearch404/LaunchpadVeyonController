package exceptions.config;

public class ConfigException extends Exception{

	private static final long serialVersionUID = 400848246155039430L;

	public ConfigException(String message) {
		super(message);
	}
	
	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
