package pane;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.game.GameLogic;
import sharedObject.RenderableHolder;
import utils.Config;
import utils.SoundUtils;

public class PausePane extends VBox {

	private Button resumeBtn, mainMenuBtn;

	public PausePane() {
		this.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.8), null, null)));
		this.setAlignment(Pos.CENTER);
		this.setSpacing(Config.UNIT * 0.5);
		initializeResumeBtn();
		initializeMainMenuBtn();
	}

	private void initializeResumeBtn() {
		this.resumeBtn = new Button("Resume");
		this.resumeBtn.setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 30));
		this.resumeBtn
				.setBackground(new Background(new BackgroundFill(Color.rgb(59, 70, 99), new CornerRadii(15), null)));
		this.resumeBtn.setBorder(new Border(new BorderStroke(Color.rgb(23, 22, 36), BorderStrokeStyle.SOLID,
				new CornerRadii(10), new BorderWidths(7)), null, null));
		this.resumeBtn.setTextFill(Color.rgb(94, 106, 111));
		this.getChildren().add(this.resumeBtn);
		this.resumeBtn.setOnMouseClicked((e) -> {
			SoundUtils.playTrack(RenderableHolder.buttonClickTrack);
			GameLogic.getInstance().getCustomerGenerator().play();
			this.getParent().getChildrenUnmodifiable().get(1).requestFocus();
			this.setVisible(false);
		});

		this.resumeBtn.setOnMouseEntered((e) -> {
			SoundUtils.playTrack(RenderableHolder.buttonHoverTrack, 0.3);
			this.resumeBtn.setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 36));
			this.resumeBtn.setTextFill(Color.rgb(231, 140, 156));
			this.resumeBtn.setCursor(Cursor.HAND);
		});

		this.resumeBtn.setOnMouseExited((e) -> {
			this.resumeBtn.setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 30));
			this.resumeBtn.setTextFill(Color.rgb(94, 106, 111));
			this.resumeBtn.setCursor(Cursor.DEFAULT);

		});
	}

	private void initializeMainMenuBtn() {
		this.mainMenuBtn = new Button("Main Menu");
		this.getMainMenuBtn().setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 30));
		this.mainMenuBtn
				.setBackground(new Background(new BackgroundFill(Color.rgb(59, 70, 99), new CornerRadii(15), null)));
		this.mainMenuBtn.setBorder(new Border(new BorderStroke(Color.rgb(23, 22, 36), BorderStrokeStyle.SOLID,
				new CornerRadii(10), new BorderWidths(7)), null, null));
		this.mainMenuBtn.setTextFill(Color.rgb(94, 106, 111));
		this.getMainMenuBtn().setOnAction((e) -> {
			SoundUtils.playTrack(RenderableHolder.buttonClickTrack);
			GameLogic.getInstance().setGameOver(true);
			this.setVisible(false);
			this.getParent().getScene().setRoot(MainMenu.getInstance());
		});

		this.getMainMenuBtn().setOnMouseEntered((e) -> {
			this.getMainMenuBtn().setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 36));
			this.mainMenuBtn.setTextFill(Color.rgb(231, 140, 156));
			this.getMainMenuBtn().setCursor(Cursor.HAND);
		});

		this.getMainMenuBtn().setOnMouseExited((e) -> {
			this.getMainMenuBtn().setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 30));
			this.mainMenuBtn.setTextFill(Color.rgb(94, 106, 111));
			this.getMainMenuBtn().setCursor(Cursor.DEFAULT);
		});

		this.getChildren().add(mainMenuBtn);
	}

	public Button getResumeBtn() {
		return resumeBtn;
	}

	public Button getMainMenuBtn() {
		return mainMenuBtn;
	}
}
