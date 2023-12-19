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
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sharedObject.RenderableHolder;
import utils.Config;
import utils.InputUtility;

public class FloorZone extends GridPane {
	private Canvas hotelCanvas;

	public FloorZone() {
		initializeCanvas();
		initializeFloorZoneStyle();
		initializeClickableGridGetterPanes();
	}

	private void initializeClickableGridGetterPanes() {
		VBox floorPanes = new VBox();
		floorPanes.setPrefSize(Config.UNIT * 8.5, Config.UNIT * (7 * 1.125));

		for (int floor = Config.TOP_FLOOR; floor >= 0; floor--) {
			HBox floorPane = createFloorPane();
			for (int queue = 0; queue < Config.MAX_CUSTOMER_PER_FLOOR; queue++) {
				GridPane customerGridPane = createCustomerPane(queue, floor);
				floorPane.getChildren().add(customerGridPane);
			}
//			floorPane.getChildren().add(new Text(Integer.toString(floor)));
			floorPanes.getChildren().add(floorPane);
		}

		this.add(floorPanes, 0, 0);
	}

	private HBox createFloorPane() {
		HBox floorPane = new HBox();
		floorPane.setPrefSize(Config.UNIT * 8.5, Config.UNIT * 1.125);
		floorPane.setSpacing(Config.UNIT * 0.05);
		floorPane.setPadding(new Insets(0, 0, 0, Config.UNIT * 0.2));
		return floorPane;
	}

	private GridPane createCustomerPane(int queue, int floor) {
		GridPane customerGridPane = new GridPane();

		customerGridPane.setPrefSize(Config.UNIT * 0.75, Config.UNIT * 1.125);
//		customerGridPane.setBorder(new Border(
//				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2)), null, null));

		customerGridPane.setOnMouseClicked(e -> handleCustomerPaneClicked(queue, floor));
		customerGridPane.setOnMouseEntered(event -> setCursorToHand(customerGridPane));
		customerGridPane.setOnMouseExited(event -> setCursorToDefault(customerGridPane));

		return customerGridPane;
	}

	private void handleCustomerPaneClicked(int queue, int floor) {
		Integer[] pos = { queue, floor };
		InputUtility.setHotelGridPressed(pos, true);
	}

	private void setCursorToHand(GridPane customerGridPane) {
		customerGridPane.setCursor(Cursor.HAND);
	}

	private void setCursorToDefault(GridPane customerGridPane) {
		customerGridPane.setCursor(Cursor.DEFAULT);
	}

	private void initializeFloorZoneStyle() {
		this.setPrefSize(Config.UNIT * 8.5, Config.UNIT * (1.125 * 7));
		this.setBorder(
				new Border(new BorderStroke(Color.rgb(57, 57, 79), BorderStrokeStyle.SOLID, null, new BorderWidths(3)),
						null, null));
		this.setBackground(new Background(new BackgroundImage(RenderableHolder.wallpaper, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

	}

	public void initializeCanvas() {
		this.hotelCanvas = new Canvas(Config.UNIT * (8.5), Config.UNIT * (7 * 1.125));
		this.add(hotelCanvas, 0, 0);
	}

	public GraphicsContext getGc() {
		return this.hotelCanvas.getGraphicsContext2D();
	}

}
