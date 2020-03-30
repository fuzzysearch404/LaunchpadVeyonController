package dev.fuzzysearch.launchpadveyon.config;

import dev.fuzzysearch.launchpadveyon.veyon.VeyonActionType;
import net.thecodersbreakfast.lp4j.api.Button;
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
	
	// Launchpad interface colors
	public final static Color COLOR_DEFAULT = Color.BLACK;
	
	public final static Color COLOR_DEVICE_LOADED = Color.AMBER;
	public final static Color COLOR_DEVICE_ACTIVE = Color.GREEN;
	public final static Color COLOR_DEVICE_FAILED = Color.RED;
	
	public final static Color COLOR_ACTION_CONTEXT_INACTIVE = Color.ORANGE;
	public final static Color COLOR_ACTION_CONTEXT_ACTIVE = Color.GREEN;
	public final static Color COLOR_ACTION_CONTEXT_STOP = Color.of(2, 0);
	
	// Launchpad interface settings
	public final static int DEFAULT_LIGHT_BRIGHTNESS = 13;
	
	public final static int DEFAULT_ERROR_BLINK_MILLISECONDS = 350;
	public final static int DEFAULT_ERROR_BLINK_TIMES = 3;
	
	// Launchpad button mappings
	public final static Button VEYON_REMOTEACCESS_VIEW_BUTTON = Button.USER_1;
	public final static Button VEYON_REMOTEACCESS_CONTROL_BUTTON = Button.USER_2;
	public final static Button VEYON_REMOTEACCESS_STOP_BUTTON = Button.STOP;
	
	public final static Button LAUNCHPAD_BRIGHTNESS_UP = Button.UP;
	public final static Button LAUNCHPAD_BRIGHTNESS_DOWN = Button.DOWN;
	
	// Veyon setup
	public final static VeyonActionType DEFAULT_VEYON_ACTION_TYPE = VeyonActionType.SCREEN_VIEW;
	
	// Veyon CLI commands setup
	public final static String VEYON_CLI_COMMAND_EXECUTABLE = "veyon-cli";
	
	public final static String VEYON_CLI_REMOTEACCESS_MODULE = "remoteaccess";
	public final static String VEYON_CLI_REMOTEACCESS_VIEW = "view";
	public final static String VEYON_CLI_REMOTEACCESS_CONTROL = "control";
	
	// Veyon CLI common command output texts
	public final static String VEYON_CLI_FAIL = "[FAIL]";
	public final static String VEYON_CLI_OK = "[OK]";
	

}
