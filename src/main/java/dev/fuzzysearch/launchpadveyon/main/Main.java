package dev.fuzzysearch.launchpadveyon.main;

import java.io.FileInputStream;

import dev.fuzzysearch.launchpadveyon.config.exceptions.VeyonUnavailableException;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;

public class Main extends Application {

	public static void main(String[] args) {
		MainFacade app = new MainFacade();
		try {
			app.run();
		} catch (VeyonUnavailableException e) {
			e.printStackTrace();
		}
		
		launch(args);
	}

	@Override
	public void start(Stage primary) throws Exception {
		String fxmlDocPath = "./scenes/test.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		FXMLLoader loader = new FXMLLoader();
		VBox root = loader.load(fxmlStream);
		primary.setScene(new Scene(root));
        primary.show();

	}
	
	/**
	 * Finalizes the 
	 */
	@Override
	public void stop() {
		LaunchpadClient launchpadClient = ProgramManager.getInstance().getLaunchpadClient();
		if(launchpadClient != null)
			launchpadClient.reset();
	}
}
