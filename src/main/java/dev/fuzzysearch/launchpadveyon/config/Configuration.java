package dev.fuzzysearch.launchpadveyon.config;

import net.thecodersbreakfast.lp4j.api.Color;

/**
 * Program's configuration class for easier
 * common variable management. The variables
 * usually should not be changed by the final user
 * and are program defaults.
 * 
 * @author Roberts Ziediņš
 *
 */
public final class Configuration {
	
	// Launchpad 
	public final static int LAUNCHPAD_PAD_MIN_X = 0;
	public final static int LAUNCHPAD_PAD_MIN_Y = 0;
	
	public final static int LAUNCHPAD_PAD_MAX_X = 7;
	public final static int LAUNCHPAD_PAD_MAX_Y = 7;
	
	public final static int LAUNCHPAD_PAD_X_ROWS = LAUNCHPAD_PAD_MAX_X + 1;
	public final static int LAUNCHPAD_PAD_Y_COLLUMNS = LAUNCHPAD_PAD_MAX_Y + 1;
	
	// Configuration files
	public final static String CONFIG_FILE_DEFAULT_FILE_DIRECTORY = "./files/";
	public final static String CONFIG_FILE_DEFAULT_FILE_NAME = "Config.json";
	
	public final static String CONFIG_FILE_DEFAULT_FULL_FILE_PATH = 
			CONFIG_FILE_DEFAULT_FILE_DIRECTORY + CONFIG_FILE_DEFAULT_FILE_NAME;
	
	// Launchpad inteface colors
	public final static Color COLOR_DEFAULT = Color.BLACK;
	
	public final static Color COLOR_DEVICE_LOADED = Color.AMBER;
	public final static Color COLOR_DEVICE_ACTIVE = Color.GREEN;
	public final static Color COLOR_DEVICE_FAILED = Color.RED;
	

}
