package pane;


import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.game.GameLogic;
import sharedObject.RenderableHolder;
import utils.Config;
import utils.SoundUtils;

public class MainMenu extends StackPane {

	private VBox btnWrapper;
	private Button startBtn, instructionBtn, exitBtn;
	private ImageView gameTitle;
	private StackPane instructionPane;
	private static MainMenu instance = new MainMenu();
	

	public MainMenu() {
//		this.getChildren().add(image);
		this.gameTitle = new ImageView(RenderableHolder.gameName);
		this.setBackground(new Background(new BackgroundImage(RenderableHolder.bgSprite, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		initializeBtnWrapper();
		this.getChildren().add(gameTitle);
		initializeInstructionPane();
		setAlignment(gameTitle, Pos.TOP_CENTER);
		setMargin(gameTitle, new Insets(Config.UNIT * 2, 0, 0, 0));
		setAlignment(btnWrapper, Pos.CENTER);
		setMargin(btnWrapper, new Insets(Config.UNIT * 1.8, 0, 0, 0));

	}

	public static MainMenu getInstance() {
		return instance;
	}

	private void initializeBtnWrapper() {
		this.btnWrapper = new VBox(Config.UNIT / 2);
		this.btnWrapper.setAlignment(Pos.CENTER);
		initializeStart();
		initializeInstruction();
		initializeExit();
		this.btnWrapper.getChildren().addAll(startBtn, instructionBtn, exitBtn);
		this.getChildren().add(btnWrapper);
	}

	private void initializeStart() {
		this.startBtn = new Button("Start");
		this.startBtn.setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 38));
//		this.startBtn
//				.setBackground(new Background(new BackgroundImage(RenderableHolder.startBg, BackgroundRepeat.NO_REPEAT,
//						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		this.startBtn
				.setBackground(new Background(new BackgroundFill(Color.rgb(59, 70, 99), new CornerRadii(15), null)));
		this.startBtn.setBorder(new Border(new BorderStroke(Color.rgb(23, 22, 36), BorderStrokeStyle.SOLID,
				new CornerRadii(10), new BorderWidths(7)), null, null));
		this.startBtn.setTextFill(Color.rgb(94, 106, 111));
		this.startBtn.setOnMouseEntered((e) -> {
			SoundUtils.playTrack(RenderableHolder.buttonHoverTrack, 0.3);
			this.startBtn.setCursor(Cursor.HAND);
			this.startBtn.setTextFill(Color.rgb(231, 140, 156));
		});
		this.startBtn.setOnMouseExited((e) -> {
			this.startBtn.setCursor(Cursor.DEFAULT);
			this.startBtn.setTextFill(Color.rgb(94, 106, 111));
			this.startBtn.setPadding(new Insets(0, 0, 0, 0));
			this.startBtn.setPadding(new Insets(12.7, 25.3, 12.7, 25.3));
		});

		this.startBtn.setOnAction((e) -> {
			SoundUtils.playTrack(RenderableHolder.gameStartTrack, 0.8);
			GameScreen gameScreen = new GameScreen();
			this.getScene().setRoot(gameScreen);
			gameScreen.getContainerPane().requestFocus();
			GameLogic logic = GameLogic.getInstance();
			AnimationTimer animation = new AnimationTimer() {

				@Override
				public void handle(long arg0) {
					// TODO Auto-generated method stub
					// timer from GPT
					if (!gameScreen.getPausePane().isVisible()) {
						gameScreen.getContainerPane().paintComponent();
						logic.logicUpdate();
						if (logic.isGameOver()) {
							logic.getCustomerGenerator().stop();
							gameScreen.getContainerPane().paintComponent();
							logic.logicUpdate();
							logic.resetInstance();
							this.stop();
						}
					}
				}

			};
			animation.start();
		});
	}

	private void initializeExit() {
		this.exitBtn = new Button("Exit");
		this.exitBtn.setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 20));
//		this.exitBtn
//				.setBackground(new Background(new BackgroundImage(RenderableHolder.exitBg, BackgroundRepeat.NO_REPEAT,
//						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		this.exitBtn
				.setBackground(new Background(new BackgroundFill(Color.rgb(59, 70, 99), new CornerRadii(15), null)));
		this.exitBtn.setBorder(new Border(new BorderStroke(Color.rgb(23, 22, 36), BorderStrokeStyle.SOLID,
				new CornerRadii(10), new BorderWidths(6)), null, null));
		this.exitBtn.setTextFill(Color.rgb(94, 106, 111));
		this.exitBtn.setOnMouseEntered((e) -> {
			SoundUtils.playTrack(RenderableHolder.buttonHoverTrack, 0.3);
			this.exitBtn.setCursor(Cursor.HAND);
			this.exitBtn.setTextFill(Color.rgb(231, 140, 156));
		});
		this.exitBtn.setOnMouseExited((e) -> {
			this.exitBtn.setCursor(Cursor.DEFAULT);
			this.exitBtn.setTextFill(Color.rgb(94, 106, 111));
		});
		this.exitBtn.setOnAction((e) -> {
			Platform.exit();
		});
	}

	private void initializeInstruction() {
		this.instructionBtn = new Button("Instruction");
		this.instructionBtn.setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 20));
//		this.instructionBtn
//				.setBackground(new Background(new BackgroundImage(RenderableHolder.instBg, BackgroundRepeat.NO_REPEAT,
//						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		this.instructionBtn
				.setBackground(new Background(new BackgroundFill(Color.rgb(59, 70, 99), new CornerRadii(15), null)));
		this.instructionBtn.setBorder(new Border(new BorderStroke(Color.rgb(23, 22, 36), BorderStrokeStyle.SOLID,
				new CornerRadii(10), new BorderWidths(5)), null, null));
		this.instructionBtn.setTextFill(Color.rgb(94, 106, 111));
		this.instructionBtn.setOnMouseEntered((e) -> {
			SoundUtils.playTrack(RenderableHolder.buttonHoverTrack, 0.3);
			this.instructionBtn.setCursor(Cursor.HAND);
			this.instructionBtn.setTextFill(Color.rgb(231, 140, 156));
		});
		this.instructionBtn.setOnMouseExited((e) -> {
			this.instructionBtn.setCursor(Cursor.DEFAULT);
			this.instructionBtn.setTextFill(Color.rgb(94, 106, 111));
		});
		this.instructionBtn.setOnAction((e) -> {
			SoundUtils.playTrack(RenderableHolder.buttonClickTrack);
			this.instructionPane.setVisible(true);
		});
	}

	private void initializeInstructionPane() {
		this.instructionPane = new StackPane();
		this.instructionPane.setPrefSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		this.instructionPane
				.setBackground(new Background(new BackgroundFill(Color.rgb(59, 70, 99), new CornerRadii(15), null)));
		this.instructionPane.setBorder(new Border(new BorderStroke(Color.rgb(23, 22, 36), BorderStrokeStyle.SOLID,
				new CornerRadii(10), new BorderWidths(5)), null, null));
		ImageView instruction = new ImageView(RenderableHolder.instruction);
		setAlignment(instruction, Pos.TOP_CENTER);
		this.instructionPane.getChildren().add(instruction);
		setAlignment(instructionPane, Pos.CENTER);
		setMargin(instructionPane, new Insets(Config.UNIT, Config.UNIT, Config.UNIT, Config.UNIT));
		this.getChildren().add(instructionPane);
		this.instructionPane.setVisible(false);
		Button closeBtn = new Button("Close");
		closeBtn.setFont(Font.font(RenderableHolder.pixelStyleFont.getFamily(), 22));
		closeBtn.setBackground(new Background(new BackgroundFill(Color.rgb(59, 70, 99), new CornerRadii(15), null)));
		closeBtn.setBorder(new Border(new BorderStroke(Color.rgb(23, 22, 36), BorderStrokeStyle.SOLID,
		new CornerRadii(10), new BorderWidths(6)), null, null));
		closeBtn.setTextFill(Color.rgb(94, 106, 111));
		closeBtn.setOnMouseEntered((e) -> {
			SoundUtils.playTrack(RenderableHolder.buttonHoverTrack, 0.3);
			closeBtn.setCursor(Cursor.HAND);
			closeBtn.setTextFill(Color.rgb(231, 140, 156));
		});
		closeBtn.setOnMouseExited((e) -> {
			closeBtn.setCursor(Cursor.DEFAULT);
			closeBtn.setTextFill(Color.rgb(94, 106, 111));
		});
		this.instructionPane.getChildren().add(closeBtn);
		setAlignment(closeBtn, Pos.BOTTOM_CENTER);
		setMargin(closeBtn, new Insets(0, 0, Config.UNIT / 4, 0));
		closeBtn.setOnAction((e) -> {
			SoundUtils.playTrack(RenderableHolder.buttonClickTrack);
			this.instructionPane.setVisible(false);
		});
	}

}


