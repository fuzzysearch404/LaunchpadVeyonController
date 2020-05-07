package dev.fuzzysearch.launchpadveyon.main;

import dev.fuzzysearch.launchpadveyon.config.exceptions.VeyonUnavailableException;
import dev.fuzzysearch.launchpadveyon.main.manager.ProgramManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static MainFacade app = new MainFacade();

	public static void main(String[] args) {
		try {
			Main.app.run();
		} catch (VeyonUnavailableException e) {
			e.printStackTrace();
		}
		
		System.out.println("[Init]: Starting JavaFX");
		launch(args);
	}

	@Override
	public void start(Stage primary) throws Exception {
		app.startJavaFXAndLightUpLaunchpads(primary);
	}
	
	@Override
	public void stop() {
		ProgramManager.getInstance().shutdownProgram();
	}
	
}
