package setup;

import static setup.ProgramConfig.DEFAULT_FULL_FILE_PATH;
import static setup.ProgramConfig.LAUNCHPAD_PAD_ROWS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import exceptions.config.InvalidLaunchpadConfigIndex;
import models.Device;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.Pad;

public class LaunchpadConfigReader {

	private LaunchpadClient launchpadClient;
	private ArrayList<Device> deviceList = new ArrayList<Device>();
	private String strPath;
	private String json;

	public LaunchpadConfigReader(LaunchpadClient launchpadClient) {
		this.launchpadClient = launchpadClient;
		this.strPath = DEFAULT_FULL_FILE_PATH;

		configure();
	}

	private void configure() {
		try {
			readConfigurationFile();
		} catch (IOException e) {
			System.err.println("Could not find config file or could not read it.");
			System.err.println("Canceling configuration...");

			return;
		}

		readJSON();
		lightUpByConfiguration();
	}

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
				} catch (Exception e1) {
					e1.printStackTrace();
					continue;
				}

				// Collumns
				while (collumnKeys.hasNext()) {
					key = collumnKeys.next();

					Object objCollumn = row.get(key);
					
					int collumnIndex = -1;

					try {
						collumnIndex = parseKeyToInteger(key);
					} catch (Exception e) {
						e.printStackTrace();
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

	private int parseKeyToInteger(String key) throws Exception {
		int num = Integer.parseInt(key);

		// Indexes start from 0
		if (num < 0 || num > LAUNCHPAD_PAD_ROWS - 1) {
			throw new InvalidLaunchpadConfigIndex("Key out of launchpad button bounds. Key: " + key);
		}

		return num;
	}

	private void readConfigurationFile() throws IOException {
		Path path = Paths.get(strPath);
		json = Files.readString(path);
	}
	
	private void lightUpByConfiguration() {
		for(Device d: deviceList) {
			launchpadClient.setPadLight(Pad.at(d.getCollumn(), d.getRow()), Color.ORANGE, BackBufferOperation.NONE);
		}
	}

}
