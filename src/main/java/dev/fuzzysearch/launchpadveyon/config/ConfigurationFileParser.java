package dev.fuzzysearch.launchpadveyon.config;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import dev.fuzzysearch.launchpadveyon.config.exceptions.ConfigException;
import dev.fuzzysearch.launchpadveyon.config.exceptions.ProgramUnconfiguredException;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import dev.fuzzysearch.launchpadveyon.veyon.models.Device;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * Class for user's configuration file parsing
 * at the initialization phase of the startup.
 * 
 * @author Roberts ZiediÅ†Å¡
 *
 */
public class ConfigurationFileParser {

	private ArrayList<Device> deviceList = new ArrayList<Device>();
	private String strPath;
	private String json;

	public ConfigurationFileParser() throws ProgramUnconfiguredException {
		this.strPath = CONFIG_FILE_DEFAULT_FULL_FILE_PATH;

		configure();
	}
	
	public ConfigurationFileParser(String pathToConfig)
			throws ProgramUnconfiguredException {
		this.strPath = pathToConfig;

		configure();
	}

	/**
	 * The main method of this class that executes
	 * all the configuration phases.
	 * 
	 * @throws ProgramUnconfiguredException if the
	 * configuration failed.
	 */
	private void configure() throws ProgramUnconfiguredException {
		try {
			readConfigurationFile();
		} catch (IOException e) {
			throw new ProgramUnconfiguredException("Could not find config file or could not read it.", e);
		}

		ProgramManager.getInstance().setLoadedDevices(readJSON());
		
		if(deviceList.isEmpty())
			throw new ProgramUnconfiguredException("The configuration file was parsed, but no devices were configured.");
	}

	/**
	 * Reads the loaded JSON {@link String} to
	 * a {@link JSONObject} and extracts the
	 * configuration data from it and converts
	 * it to a {@link ArrayList} of {@link Device}.
	 * 
	 * Configuration file body:
	 * The JSON file must consist of object
	 * with this structure:
	 * 
	 * row_number (1-7):
	 * 		collumn_number (1-7):
	 * 			-ip: The IP address
	 * 			(and optionally the TCP port) 
	 * 			or name of the device.
	 * 
	 * Configuration file example:
	 * 
	 * {
	 * 	"0": {
	 * 		"0":{
	 * 			"ip":"8.8.8.8"
	 * 		},
	 * 		"1":{
	 * 			"ip":"8.8.8.8"
	 * 		}
	 * 	},
	 * 	"2" {
	 * 		"5":{
	 * 			"ip":"8.8.8.8"
	 * 		}
	 * 	}
	 * }
	 * 
	 * This will load data for Launchpad's
	 * row 1 collumns 1 and 2 and row 3
	 * collum 6. (indexes start from 0)
	 
    	    +---+---+---+---+---+---+---+---+ 
	        |   |   |   |   |   |   |   |   |
	        +---+---+---+---+---+---+---+---+ 
	          0   1   2   3   4   5   6   7   X ->
	        +---+---+---+---+---+---+---+---+  +---+
	      0 | D | D |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      1 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      2 |   |   |   |   |   | D |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      3 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      4 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      5 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      6 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+
	      7 |   |   |   |   |   |   |   |   |  |   |
	        +---+---+---+---+---+---+---+---+  +---+ 
	      Y          D = Loaded device
	      |
	      ˅ 
	 * 
	 * @return the loaded {@link Device} objects.
	 */
	private ArrayList<Device> readJSON() {
		JSONObject jsonObj = new JSONObject(json);
		Iterator<String> rowKeys = jsonObj.keys();

		// Rows
		while (rowKeys.hasNext()) {
			String key = rowKeys.next();
			Object objRow = jsonObj.get(key);

			if (objRow instanceof JSONObject) {
				JSONObject row = (JSONObject) objRow;
				Iterator<String> collumnKeys = row.keys();
				
				int y = -1;
				
				try {
					y = parseKeyToInteger(key);
				} catch (ConfigException e) {
					e.printStackTrace();
					continue;
				}

				// Collumns
				while (collumnKeys.hasNext()) {
					key = collumnKeys.next();

					Object objCollumn = row.get(key);
					
					int x = -1;

					try {
						x = parseKeyToInteger(key);
					} catch (ConfigException e1) {
						e1.printStackTrace();
						continue;
					}

					if (objCollumn instanceof JSONObject) {
						JSONObject collumn = (JSONObject) objCollumn;
						String ipAdress = collumn.getString("ip");
						
						Device device = new Device(ipAdress, Pad.at(x, y));
						deviceList.add(device);
					} else
						System.err.println("Invalid config collumn: " + key);
				}

			} else
				System.err.println("Invalid config row: " + key);
		}
		
		return deviceList;

	}

	/**
	 * Parses {@link String} key from {@link JSONObject}
	 * to {@link Integer}. The integer must be in bounds
	 * of the {@link Launchpad}'s {@link Pad} count.
	 * 
	 * @param key - key to parse
	 * @return {@link Integer} - parsed index.
	 * @throws ConfigException - Thrown if the provided string
	 * cannot be parsed to integer or it is out of bounds of
	 * Launchpad's pads count.
	 */
	private int parseKeyToInteger(String key) throws ConfigException {
		int num = -1;
		
		try {
			num = Integer.parseInt(key);
		}
		catch (NumberFormatException e) {
			throw new ConfigException("Invalid key: " + key, e);
		}

		// Indexes start from 0
		if (num < LAUNCHPAD_PAD_MIN_X || num > LAUNCHPAD_PAD_MAX_X) {
			throw new ConfigException("Key out of launchpad button bounds. Key: " + key);
		}

		return num;
	}

	/**
	 * Reads the configuration file to {@link String}.
	 * @throws IOException
	 */
	private void readConfigurationFile() throws IOException {
		Path path = Paths.get(strPath);
		json = Files.readString(path);
	}

}
