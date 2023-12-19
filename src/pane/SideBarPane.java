package pane;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import utils.Config;

public class SideBarPane extends Canvas {

	private GraphicsContext gc;

	public SideBarPane() {
		super(Config.UNIT * 1, Config.UNIT * (7 * 1.125));
		this.gc = this.getGraphicsContext2D();
	}

	public GraphicsContext getGc() {
		return gc;
	}

}
