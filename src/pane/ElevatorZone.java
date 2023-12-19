package pane;

import entity.Entity;
import entity.building.Elevator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.game.GameLogic;
import sharedObject.RenderableHolder;
import utils.Config;

public class ElevatorZone extends Canvas {

	private int id;
	private GraphicsContext gc;

	public ElevatorZone(int id) {
		super((Config.UNIT * 1.5), (Config.UNIT * (1.125 * 7)));
		this.id = id;
		this.gc = this.getGraphicsContext2D();
		addListener();
	}

	private void addListener() {
		GameLogic logic = GameLogic.getInstance();
		this.setOnMouseClicked((e) -> {
			System.out.println("unselect elev " + GameLogic.getInstance().getSelectedElev().getId());
			GameLogic.getInstance().getSelectedElev().setSelected(false);
			for (Entity entity : logic.getGameObjectContainer()) {
				if (entity instanceof Elevator) {
					Elevator elev = (Elevator) entity;
					if (elev.getId() == this.id) {
						elev.setSelected(true);
						System.out.println("select elev " + elev.getId());
					}
				}
			}
		});
	}
	
	public GraphicsContext getGc() {
		return this.gc;
	}
}
