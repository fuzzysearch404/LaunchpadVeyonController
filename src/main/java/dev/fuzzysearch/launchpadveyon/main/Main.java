package dev.fuzzysearch.launchpadveyon.main;

import java.io.FileInputStream;

import dev.fuzzysearch.launchpadveyon.config.exceptions.VeyonUnavailableException;
import dev.fuzzysearch.launchpadveyon.lights.LaunchpadLightManager;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static dev.fuzzysearch.launchpadveyon.config.Configuration.GUI_MAIN_FXML_PATH;

public class Main extends Application {

	public static void main(String[] args) {
		MainFacade app = new MainFacade();
		try {
			app.run();
		} catch (VeyonUnavailableException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("[Init]: Starting JavaFX");
		launch(args);
	}

	@Override
	public void start(Stage primary) throws Exception {
		FileInputStream fxmlStream = new FileInputStream(GUI_MAIN_FXML_PATH);
		FXMLLoader loader = new FXMLLoader();
		VBox root = loader.load(fxmlStream);
		ProgramManager.getInstance().setVirtualLaunchpadController(loader.getController());
		primary.setScene(new Scene(root));
        primary.show();

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
