package pane;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import sharedObject.RenderableHolder;
import utils.Config;

public class GameScreen extends StackPane{
	private ImageView bgImage;
	private ContainerPane containerPane;
	private PausePane pausePane;
	
	public GameScreen() {
//		this.bgImage = new ImageView(new Image(ClassLoader.getSystemResource("gameBackgroundImage.png").toString(),
//				Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, true, false));
		this.bgImage = new ImageView(RenderableHolder.bgSprite);
		this.containerPane = new ContainerPane();
		this.pausePane = new PausePane();
		this.pausePane.setVisible(false);
		
		StackPane.setAlignment(bgImage, Pos.TOP_LEFT);
		
		this.getChildren().addAll(bgImage, containerPane, pausePane);
	}

	public ImageView getBgImage() {
		return bgImage;
	}

	public ContainerPane getContainerPane() {
		return containerPane;
	}

	public PausePane getPausePane() {
		return pausePane;
	}
	
	
}
