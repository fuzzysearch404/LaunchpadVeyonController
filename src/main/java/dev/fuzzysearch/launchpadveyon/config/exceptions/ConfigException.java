package dev.fuzzysearch.launchpadveyon.config.exceptions;

import dev.fuzzysearch.launchpadveyon.config.ConfigurationFileParser;

/**
 * Exception that is thrown when invalid configuration
 * that cannot be parsed by {@link ConfigurationFileParser}
 * is provided in cofiguration files.
 * 
 * @author Roberts Ziediņš
 *
 */
public class ConfigException extends Exception {

	private static final long serialVersionUID = 400848246155039430L;

	public ConfigException(String message) {
		super(message);
	}
	
	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
