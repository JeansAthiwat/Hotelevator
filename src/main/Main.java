package main;

import utils.Config;
import utils.SoundUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import pane.MainMenu;
import sharedObject.RenderableHolder;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {

		MainMenu mainMenu = MainMenu.getInstance();
		Scene scene = new Scene(mainMenu, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

		stage.setScene(scene);
		stage.setTitle("Hotelevator");
		stage.setResizable(false);
		stage.show();

		SoundUtils.playTrack(RenderableHolder.gameSoundTrack, 0.03);

	}

	public static void main(String[] args) {
		launch();
	}
}
