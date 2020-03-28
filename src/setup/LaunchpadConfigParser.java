package setup;

import static config.Configuration.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import exceptions.config.ConfigException;
import exceptions.config.ProgramUncofiguredException;
import models.Device;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.Pad;

/**
 * Class for user's configuration file parsing
 * at the initialization phase of the startup.
 * 
 * @author Roberts Ziediņš
 *
 */
public class LaunchpadConfigParser {

	private LaunchpadClient launchpadClient;
	private ArrayList<Device> deviceList = new ArrayList<Device>();
	private String strPath;
	private String json;

	public LaunchpadConfigParser(LaunchpadClient launchpadClient) throws ProgramUncofiguredException {
		this.launchpadClient = launchpadClient;
		this.strPath = CONFIG_FILE_DEFAULT_FULL_FILE_PATH;

		configure();
	}
	
	public LaunchpadConfigParser(LaunchpadClient launchpadClient, String pathToConfig)
			throws ProgramUncofiguredException {
		this.launchpadClient = launchpadClient;
		this.strPath = pathToConfig;

		configure();
	}

	/**
	 * The main method of this class that executes
	 * all the configuration phases.
	 * 
	 * @throws ProgramUncofiguredException if the
	 * configuration failed.
	 */
	private void configure() throws ProgramUncofiguredException {
		try {
			readConfigurationFile();
		} catch (IOException e) {
			throw new ProgramUncofiguredException("Could not find config file or could not read it.", e);
		}

		readJSON();
		lightUpByConfiguration();
	}

	/**
	 * Reads the loaded JSON {@link String} to
	 * a {@link JSONObject} and extracts the
	 * configuration data from it and converts
	 * it to a {@link ArrayList} of {@link Device}.
	 * 
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
				
				int rowIndex = -1;
				
				try {
					rowIndex = parseKeyToInteger(key);
				} catch (ConfigException e) {
					e.printStackTrace();
					continue;
				}

				// Collumns
				while (collumnKeys.hasNext()) {
					key = collumnKeys.next();

					Object objCollumn = row.get(key);
					
					int collumnIndex = -1;

					try {
						collumnIndex = parseKeyToInteger(key);
					} catch (ConfigException e1) {
						e1.printStackTrace();
						continue;
					}

					if (objCollumn instanceof JSONObject) {
						JSONObject collumn = (JSONObject) objCollumn;
						String ipAdress = collumn.getString("ip");
						
						Device device = new Device(ipAdress, rowIndex, collumnIndex);
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
		if (num < 0 || num > LAUNCHPAD_PAD_ROWS - 1) {
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
	
	/**
	 * Lights up lights on {@link Launchpad} according to
	 * the provided configuration.
	 */
	private void lightUpByConfiguration() {
		for(Device d: deviceList) {
			launchpadClient.setPadLight(Pad.at(d.getCollumn(), d.getRow()), COLOR_DEVICE_LOADED, BackBufferOperation.NONE);
		}
	}

}
