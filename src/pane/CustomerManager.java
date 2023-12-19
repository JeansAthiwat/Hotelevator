package pane;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sharedObject.RenderableHolder;
import utils.Config;
import utils.InputUtility;

public class CustomerManager extends GridPane {
	private Canvas customerManagerCanvas;

	public CustomerManager() {
		initializeCustomerManager();
		initializeCanvas();
		initializeCabinPane();
	}

	private void initializeCustomerManager() {
		setPrefSize(Config.UNIT * 14, Config.UNIT * 1.125);
		setBackground(new Background(new BackgroundImage(RenderableHolder.insideCabin, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));

	}

	private void initializeCanvas() {
		customerManagerCanvas = new Canvas(Config.UNIT * 14, Config.UNIT * 1.125);
		this.add(customerManagerCanvas, 0, 0);
	}

	private void initializeCabinPane() {
		HBox cabinPaneHolder = new HBox();
		cabinPaneHolder.setPrefSize(Config.UNIT * 14, Config.UNIT * 1.125);
		cabinPaneHolder.setPadding(new Insets(0, 0, 0, Config.UNIT * 3));
		cabinPaneHolder.setSpacing(Config.UNIT);

		for (int i = 0; i < Config.MAX_CUSTOMER_PER_CABIN; i++) {
			GridPane passengerGridPane = createPassengerPane(i);
			cabinPaneHolder.getChildren().add(passengerGridPane);
		}

		this.add(cabinPaneHolder, 0, 0);
	}

	private GridPane createPassengerPane(int queue) {
		GridPane passengerGridPane = new GridPane();
		passengerGridPane.setPrefSize(Config.UNIT * 0.75, Config.UNIT * 1.125);
//		passengerGridPane.setBorder(new Border(
//				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2)), null, null));

		passengerGridPane.setOnMouseClicked(e -> handlePassengerPaneClicked(queue));
		passengerGridPane.setOnMouseEntered(event -> setCursorToHand(passengerGridPane));
		passengerGridPane.setOnMouseExited(event -> setCursorToDefault(passengerGridPane));

		return passengerGridPane;
	}

	private void handlePassengerPaneClicked(int queue) {
		InputUtility.setPassengerIndexPressed(queue, true);
	}

	private void setCursorToHand(GridPane passengerGridPane) {
		passengerGridPane.setCursor(Cursor.HAND);
	}

	private void setCursorToDefault(GridPane passengerGridPane) {
		passengerGridPane.setCursor(Cursor.DEFAULT);
	}

	public GraphicsContext getGc() {
		return customerManagerCanvas.getGraphicsContext2D();
	}
}
