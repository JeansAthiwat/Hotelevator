package entity.building;

import entity.Entity;
import entity.customer.BaseCustomer;
import entity.sidebar.TimeGauge;
import javafx.scene.canvas.GraphicsContext;
import logic.game.GameLogic;
import sharedObject.RenderableHolder;
import utils.Config;
import utils.CustomerUtils;
import utils.InputUtility;
import utils.SoundUtils;

public class CustomerGrid extends Entity {
	private final BaseCustomer[][] customerGrid;

	public CustomerGrid() {
		super(100);
		customerGrid = new BaseCustomer[Config.MAX_CUSTOMER_PER_FLOOR][Config.TOTAL_FLOOR];
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

		for (BaseCustomer[] queue : customerGrid) {
			for (BaseCustomer customer : queue) {
				if (customer != null) {
					customer.draw(gc);
				}
			}
		}
	}

	@Override
	public void update() {
		handleCustomerUpdates();
		handleAddPassengerToCabin(); // check for in put and perform
	}

	private void handleCustomerUpdates() {
		for (int queue = 0; queue < customerGrid.length; queue++) {
			for (int floor = 0; floor < customerGrid[0].length; floor++) {
				BaseCustomer customer = customerGrid[queue][floor];
				if (customer != null) {
					customer.update();
					if (customer.getPatienceLeft() <= 0) {
						removeCustomerFromFloor(queue, floor);
						deductTimeFromTimeGauge();
					}
				}
			}
		}
	}

	private void handleAddPassengerToCabin() {
		Integer[] grid = InputUtility.getHotelGridPressed();
		if (grid[0] == null) {
			return;
		}

		int selectedQueue = grid[0];
		int selectedFloor = grid[1];
		BaseCustomer customer = getCustomer(selectedQueue, selectedFloor);

		if (customer == null) {
			return;
		}

		Elevator selectedElevator = GameLogic.getInstance().selectedElev;

		if (selectedElevator.getCurrentFloor() == selectedFloor) {
			boolean isAdded = addCustomerToCabin(customer, selectedElevator.getInsideCabin());
			if (isAdded) {
				removeCustomerFromFloor(selectedQueue, selectedFloor);
			}
		} else {
			SoundUtils.playTrack(RenderableHolder.addPassengerFailedTrack, 0.5);
		}
	}

	public BaseCustomer[][] getFloors() {
		return customerGrid;
	}

	public BaseCustomer getCustomer(int queue, int floor) {
		if (queue < 0 || queue >= customerGrid.length || floor < 0 || floor >= customerGrid[0].length) {
			return null;
		}
		return customerGrid[queue][floor];
	}

	private boolean addCustomerToCabin(BaseCustomer customer, InsideCabin insideCabin) {
		if (!customer.canEnter(insideCabin)) {
			SoundUtils.playTrack(RenderableHolder.addPassengerFailedTrack, 0.5);
			return false;
		}
		SoundUtils.playTrack(RenderableHolder.addPassengerSucceedTrack, 0.2);
		CustomerUtils.addPassengerToFirstToFirstEmptyQueueOfCabin(customer, insideCabin);
		return true;
	}

	private void removeCustomerFromFloor(int queue, int floor) {
		CustomerUtils.removeCustomerFromFloor(customerGrid[queue][floor], customerGrid, queue, floor);
	}

	private void deductTimeFromTimeGauge() {
		TimeGauge timeGauge = GameLogic.getInstance().getTimeGauge();
		int timeDeducted = (int) Math.round(Config.MAX_TIME_GAUGE * Config.TIME_GAUGE_DECREASE_PERCENT);
		timeGauge.setTimeLeft(timeGauge.getTimeLeft() - timeDeducted);
	}

	public BaseCustomer[][] getCustomersGrid() {
		return customerGrid;
	}
}
