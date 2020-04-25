package dev.fuzzysearch.launchpadveyon.config;

import dev.fuzzysearch.launchpadveyon.lights.ColorType;
import dev.fuzzysearch.launchpadveyon.lights.LaunchpadColor;
import dev.fuzzysearch.launchpadveyon.veyon.VeyonActionType;
import javafx.scene.paint.Paint;
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
	
	/*
	 * ###########################################################################
	 * ###########################################################################
	 *                           CONFIGURATION FILES
	 * ###########################################################################
	 * ###########################################################################
	 *
	 * Description: Settings related to user configuration files.
	 */

	// Default directory for the configuration file
	public final static String CONFIG_FILE_DEFAULT_FILE_DIRECTORY = "./files/";
	// Default file name for the configuration file
	public final static String CONFIG_FILE_DEFAULT_FILE_NAME = "Config.json";
	
	// Default full path for the configuration file
	public final static String CONFIG_FILE_DEFAULT_FULL_FILE_PATH = 
			CONFIG_FILE_DEFAULT_FILE_DIRECTORY + CONFIG_FILE_DEFAULT_FILE_NAME;
	
	
	/*
	 * ###########################################################################
	 * ###########################################################################
	 *                          	  LAUNCHPAD
	 * ###########################################################################
	 * ###########################################################################
	 */
	
	/*
	 * ###########################################################################
	 *                          	    LAYOUT
	 * ###########################################################################
	 * Description: Settings related to Launchpad's layout.
	 * 
	 * 
	 *                  Top buttons
	 * 	     +---+---+---+---+---+---+---+---+ 
	 *       |   |   |   |   |   |   |   |	 |
	 *       +---+---+---+---+---+---+---+---+ 
	 *         
	 *                     Pads
	 *         0   1   2   3   4   5   6   7   X ->
	 *       +---+---+---+---+---+---+---+---+  +---+
	 *     0 |   |   |   |   |   |   |   |   |  | 	|
	 *       +---+---+---+---+---+---+---+---+  +---+
	 *     1 |   |   |   |   |   |   |   |   |  | 	|
	 *       +---+---+---+---+---+---+---+---+  +---+
	 *     2 |   |   |   |   |   |   |   |   |  | 	|
	 *       +---+---+---+---+---+---+---+---+  +---+
	 *     3 |   |   |   |   |   |   |   |   |  | 	|
	 *       +---+---+---+---+---+---+---+---+  +---+
	 *     4 |   |   |   |   |   |   |   |   |  | 	|
	 *       +---+---+---+---+---+---+---+---+  +---+
	 *     5 |   |   |   |   |   |   |   |   |  | 	|
	 *       +---+---+---+---+---+---+---+---+  +---+
	 *     6 |   |   |   |   |   |   |   |   |  | 	|
	 *       +---+---+---+---+---+---+---+---+  +---+
	 *     7 |   |   |   |   |   |   |   |   |  | 	|
	 *       +---+---+---+---+---+---+---+---+  +---+
	 *     Y                             Right side buttons  
	 *     |
	 *     ˅ 
	 */
	
	
	// Lowest X index of a pad
	public final static int LAUNCHPAD_PAD_MIN_X = 0;
	// Lowest X index of a pad
	public final static int LAUNCHPAD_PAD_MIN_Y = 0;
	
	// Highest X index of a pad
	public final static int LAUNCHPAD_PAD_MAX_X = 7;
	// Highest Y index of a pad
	public final static int LAUNCHPAD_PAD_MAX_Y = 7;
	
	// Total rows
	public final static int LAUNCHPAD_PAD_X_ROWS = LAUNCHPAD_PAD_MAX_X + 1;
	// Total columns
	public final static int LAUNCHPAD_PAD_Y_COLLUMNS = LAUNCHPAD_PAD_MAX_Y + 1;
	
	/*
	 * ###########################################################################
	 *                         COLOR AND LIGHTING SETTINGS
	 * ###########################################################################
	 * 
	 * Description: Settings for program's context visualization on both Launchpads.
	 */ 
	
	// Black color (no color)
	public final static LaunchpadColor LP_COLOR_DEFAULT = 
			new LaunchpadColor(ColorType.DEFAULT, Color.BLACK, Paint.valueOf("#a4a4a4"));
	
	// DEVICE PADS
	
	// Shows that the Veyon device is loaded on the Launchpad
	public final static LaunchpadColor LP_COLOR_DEVICE_LOADED = 
			new LaunchpadColor(ColorType.DEVICE_LOADED, Color.AMBER, Paint.valueOf("#fadb2a"));
	// Shows that the Veyon device is currently selected
	public final static LaunchpadColor LP_COLOR_DEVICE_ACTIVE =
			new LaunchpadColor(ColorType.DEVICE_ACTIVE, Color.GREEN, Paint.valueOf("#00d11f"));
	// Shows that the Veyon device was previously not accessible
	public final static LaunchpadColor LP_COLOR_DEVICE_FAILED =
			new LaunchpadColor(ColorType.DEVICE_FAILED, Color.RED, Paint.valueOf("#f72d2d"));
	// Shows that the used Pads are being edited
	public final static LaunchpadColor LP_COLOR_DEVICE_EDIT =
			new LaunchpadColor(ColorType.DEVICE_EDIT, Color.RED, Paint.valueOf("#f72d2d"));
	// Shows that the empty Pads are being edited
	public final static LaunchpadColor LP_COLOR_DEVICE_ADD =
			new LaunchpadColor(ColorType.DEVICE_ADD, Color.BLACK, Paint.valueOf("#26d7ff"));
	
	// MENU BUTTONS CONTEXT
	
	// Shows that the action is not selected
	public final static LaunchpadColor LP_COLOR_ACTION_CONTEXT_INACTIVE = 
			new LaunchpadColor(ColorType.CONTEXT_INACTIVE, Color.ORANGE, Paint.valueOf("#fca821"));
	// Shows that the action is selected
	public final static LaunchpadColor LP_COLOR_ACTION_CONTEXT_ACTIVE =
			new LaunchpadColor(ColorType.CONTEXT_ACTIVE, Color.GREEN, Paint.valueOf("#00d11f"));
	// Color for remote access stop button
	public final static LaunchpadColor LP_COLOR_ACTION_CONTEXT_STOP =
			new LaunchpadColor(ColorType.CONTEXT_STOP, Color.of(2, 0), Paint.valueOf("#d60f26"));
	
	// BRIGHTNESS
	
	// Default Launchpad's brightness intensity
	public final static int PHYSICAL_LP_DEFAULT_LIGHT_BRIGHTNESS = 13;
	
	/*
	 * ###########################################################################
	 *                      	  PAD BLINKING SETTINGS
	 * ###########################################################################
	 * 
	 * Description: Settings for pad blinking rates.
	 */
	
	// Default value for the error indication blinking delay (min. - 200)
	public final static int DEFAULT_ERROR_BLINK_MILLISECONDS = 350;
	// Default value for the error indication blinking iterations (min. - 1)
	public final static int DEFAULT_ERROR_BLINK_TIMES = 3;
	
	/*
	 * ###########################################################################
	 *                            CONTEXT BUTTON MAPPINGS
	 * ###########################################################################
	 * 
	 * Description: Settings for Launchpad's button mappings for certain contexts.
	 */ 
	
	// VEYON REMOTE ACCESS
	
	// Button for switching to "view" mode
	public final static Button VEYON_REMOTEACCESS_VIEW_BUTTON = Button.USER_1;
	// Button for switching to "control" mode
	public final static Button VEYON_REMOTEACCESS_CONTROL_BUTTON = Button.USER_2;
	// Button for stopping current remote access
	public final static Button VEYON_REMOTEACCESS_STOP_BUTTON = Button.STOP;
	
	// BRIGHTNESS 
	
	// Button for increasing light brightness on the Launchpad
	public final static Button LAUNCHPAD_BRIGHTNESS_UP = Button.UP;
	// Button for decreasing light brightness on the Launchpad
	public final static Button LAUNCHPAD_BRIGHTNESS_DOWN = Button.DOWN;
	
	/*
	 * ###########################################################################
	 * ###########################################################################
	 *                                 VEYON
	 * ###########################################################################
	 * ###########################################################################
	 *
	 * Description: Settings related to program's integration with Veyon.
	 */
	
	// Default command context type
	public final static VeyonActionType DEFAULT_VEYON_ACTION_TYPE = VeyonActionType.SCREEN_VIEW;
	
	/*
	 * ###########################################################################
	 *                               CLI COMMNADS
	 * ###########################################################################
	 * 
	 * Description: Settings for defining Veyon's command line interface commands.
	 */ 
	
	// Main executable name
	public final static String VEYON_CLI_COMMAND_EXECUTABLE = "veyon-cli";
	
	// Help module
	public final static String VEYON_CLI_HELP = "help";
	
	// Remote access module
	public final static String VEYON_CLI_REMOTEACCESS_MODULE = "remoteaccess";
	// Remote access view mode - to view a remote computer's screen
	public final static String VEYON_CLI_REMOTEACCESS_VIEW = "view";
	// Remote access control mode - to control a remote computer's screen
	public final static String VEYON_CLI_REMOTEACCESS_CONTROL = "control";
	
	/*
	 * ###########################################################################
	 *                           COMMNON COMMAND OUTPUTS
	 * ###########################################################################
	 * 
	 * Description: Defines what Veyon's command interface commonly returns
	 * when the commands are executed.
	 */
	
	/*
	 *  This is what the help command's output usually contains, 
	 *  it is used to check if Veyon's CLI is available on the current environment
	 */
	public final static String VEYON_CLI_HELP_SUCCESSFUL_CONTAINS = "Available modules:";
	// Output if the process failed
	public final static String VEYON_CLI_FAIL = "[FAIL]";
	// Output if the process was successful
	public final static String VEYON_CLI_OK = "[OK]";
	
	/*
	 * ###########################################################################
	 * ###########################################################################
	 *                         	   GRAPHICAL INTERFACE
	 * ###########################################################################
	 * ###########################################################################
	 */
	
	/*
	 * ###########################################################################
	 *                           	    PROPERTIES
	 * ###########################################################################
	 * 
	 * Description: Defines various JavaFX settings.
	 */
	
	public final static String GUI_MAIN_TITLE = "Launchpad Veyon Controller";
	
	/*
	 * ###########################################################################
	 *                           		FXML FILES
	 * ###########################################################################
	 * 
	 * Description: Defines where the FXML files for JavaFX are
	 * stored on the system.
	 */
	
	// The root directory for the files
	public final static String GUI_FXML_ROOT_DIR = "./scenes/";
	
	// Main - Virtual Launchpad Scene FXML file.
	public final static String GUI_MAIN_FXML_PATH = GUI_FXML_ROOT_DIR + "main.fxml";

}
