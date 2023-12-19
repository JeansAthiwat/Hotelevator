package logic.game;

import java.util.ArrayList;

import entity.Entity;
import entity.building.CustomerGrid;
import entity.building.Elevator;
import entity.customer.FatCustomer;
import entity.customer.StandardCustomer;
import entity.customer.VIPCustomer;
import entity.sidebar.TimeGauge;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.Main;
import sharedObject.RenderableHolder;
import utils.Config;
import utils.CustomerUtils;
import utils.Randomizer;
import utils.SoundUtils;

public class GameLogic {

	private boolean isGameOver = false;
	public Elevator selectedElev;
	private ArrayList<Entity> gameObjectContainer;
	private TimeGauge timeGauge;
	private CustomerGrid hotel;
	private Elevator elevator1;
	private Elevator elevator2;
	private Elevator elevator3;
	private Timeline customerGenerator;

	private static GameLogic instance = null;

	private GameLogic() {
		System.out.println("new instance");
		this.gameObjectContainer = new ArrayList<>();
		this.timeGauge = new TimeGauge();
		this.hotel = new CustomerGrid();

		this.elevator1 = new Elevator(1, 0, 6 * Config.FLOOR_HEIGHT, KeyCode.Q, KeyCode.A, KeyCode.DIGIT1);
		this.elevator1.setSelected(true);
		this.elevator2 = new Elevator(2, 0, 6 * Config.FLOOR_HEIGHT, KeyCode.W, KeyCode.S, KeyCode.DIGIT2);
		this.elevator3 = new Elevator(3, 0, 6 * Config.FLOOR_HEIGHT, KeyCode.E, KeyCode.D, KeyCode.DIGIT3);

		addNewObject(timeGauge);
		addNewObject(hotel);
		addNewObject(elevator1);
		addNewObject(elevator2);
		addNewObject(elevator3);

		this.customerGenerator = new Timeline(new KeyFrame(Duration.seconds(Config.SPAWN_DELAY), event -> generateCustomer()));
		customerGenerator.setCycleCount(Timeline.INDEFINITE);
		customerGenerator.play();
	}

	public static GameLogic getInstance() {
		if (instance == null) {
			instance = new GameLogic();
		}
		return instance;
	}

	public void resetInstance() {
		instance = null;
		RenderableHolder.setInstance();
	}

	protected void addNewObject(Entity entity) {
		gameObjectContainer.add(entity);
		RenderableHolder.getInstance().add(entity);
	}

	public void logicUpdate() {
//		System.out.println(Main.getElapsedTimeMilliSeconds());
		try {
			for (Entity entity : this.gameObjectContainer) {
				entity.update();
				if (entity instanceof Elevator) {
					Elevator elev = (Elevator) entity;
					if (elev.isSelected())
						instance.selectedElev = elev;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

//		CustomerUtils.addCustomerToFloorFromGenerator(new StandardCustomer(), this.getHotel().getCustomersGrid());
	}

	private void generateCustomer() {
		int cType = Randomizer.getRandomInt(0, 2);
		PatienceLevel pType = PatienceLevel.getRandomPatienceLevel();
		boolean isAdded;
		switch (cType) {
		case 0: {
			isAdded = CustomerUtils.addCustomerToFloorFromGenerator(new StandardCustomer(pType),
					this.hotel.getCustomersGrid());
			if (isAdded)
				SoundUtils.playTrack(RenderableHolder.standardCustomerSpawn, 0.05);
			break;
		}

		case 1: {
			isAdded = CustomerUtils.addCustomerToFloorFromGenerator(new FatCustomer(pType),
					this.hotel.getCustomersGrid());
			if (isAdded)
				SoundUtils.playTrack(RenderableHolder.fatCustomerSpawn, 0.15);
			break;
		}

		case 2: {
			isAdded = CustomerUtils.addCustomerToFloorFromGenerator(new VIPCustomer(pType),
					this.hotel.getCustomersGrid());
			if (isAdded)
				SoundUtils.playTrack(RenderableHolder.vipCustomerSpawn, 0.2);
			break;
		}

		default:
			isAdded = CustomerUtils.addCustomerToFloorFromGenerator(new StandardCustomer(pType),
					this.hotel.getCustomersGrid());
			if (isAdded)
				SoundUtils.playTrack(RenderableHolder.standardCustomerSpawn, 0.05);
		}
	}

	public CustomerGrid getHotel() {
		return hotel;
	}

	public TimeGauge getTimeGauge() {
		return timeGauge;
	}

	public ArrayList<Entity> getGameObjectContainer() {
		return this.gameObjectContainer;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public Timeline getCustomerGenerator() {
		return customerGenerator;
	}

	public Elevator getSelectedElev() {
		return selectedElev;
	}

}
