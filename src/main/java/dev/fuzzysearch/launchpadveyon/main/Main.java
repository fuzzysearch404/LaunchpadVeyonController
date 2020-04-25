package dev.fuzzysearch.launchpadveyon.main;

import java.io.FileInputStream;

import dev.fuzzysearch.launchpadveyon.app.dialogs.ErrorAlert;
import dev.fuzzysearch.launchpadveyon.config.exceptions.VeyonUnavailableException;
import dev.fuzzysearch.launchpadveyon.lights.LaunchpadLightManager;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.GUI_MAIN_FXML_PATH;
import static dev.fuzzysearch.launchpadveyon.config.Configuration.GUI_MAIN_TITLE;

public class Main extends Application {
	
	private static boolean veyonUnavailable = false;
	private static MainFacade app = new MainFacade();

	public static void main(String[] args) {
		try {
			Main.app.run();
		} catch (VeyonUnavailableException e) {
			Main.veyonUnavailable = true;
			e.printStackTrace();
			return;
		}
		
		System.out.println("[Init]: Starting JavaFX");
		launch(args);
	}

	@Override
	public void start(Stage primary) throws Exception {
		if(veyonUnavailable) {
			new ErrorAlert("Veyon unavailable", "This program requires"
					+ "Veyon CLI on this system environment");
			return;
		}
		
		FileInputStream fxmlStream = new FileInputStream(GUI_MAIN_FXML_PATH);
		FXMLLoader loader = new FXMLLoader();
		VBox root = loader.load(fxmlStream);
		ProgramManager.getInstance().setVirtualLaunchpadController(loader.getController());
		primary.setScene(new Scene(root));
		primary.setTitle(GUI_MAIN_TITLE);
        primary.show();
        
        if(app.noConfigurationFile) {
        	new ErrorAlert("Unable to configure", "Could not find existing configuration"
        			+ "file or create a new one. Make sure program has permissions to read/"
        			+ "write files.");
        }
        if(app.invalidConfiguration) {
        	new ErrorAlert("Unable to configure", "The current JSON configuration file"
					+ " has invalid syntax or is corrupted.");
        }
        if(app.noPhysicalLaunchpad) {
        	new ErrorAlert("No physical Launchpad", "Could not detect MIDI Launchpad."
        			+ " Connect the Launchpad device and restart to use physical Launchpad."
        			+ " This time program will run on virtual Launchpad mode only.");
        }

        /** We could not do this before, because the VirtualLaunchpadController did not exist before
         *  and it is required for the light manager.
         */
        System.out.println("[Init]: Lighting up Launchpads");
        LaunchpadLightManager lightManager = ProgramManager.getInstance().getLightManager();
        lightManager.lightUpContextSwitchButtons();
		lightManager.lightUpPadsByDevices();
		lightManager.initBrightness();
	}
	
	@Override
	public void stop() {
		ProgramManager.getInstance().shutdownProgram();
	}
	
}
