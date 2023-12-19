package entity.building;

import entity.Entity;
import entity.customer.BaseCustomer;
import entity.sidebar.TimeGauge;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import logic.game.GameLogic;
import sharedObject.RenderableHolder;
import utils.Config;
import utils.CustomerUtils;
import utils.InputUtility;
import utils.SoundUtils;

public class Elevator extends Entity {

	private boolean isSelected;
	private InsideCabin insideCabin;
	private int currentFloor;
	private int id;
	private KeyCode upKey, downKey, selectKey;
	private double moveY;
	private double x, y;

	private String lastCustomerType;
	private boolean hasMoved;

	private int comboCount;

	public Elevator(int id, double x, double y, KeyCode upKey, KeyCode downKey, KeyCode selectKey) {
		super(100);
		this.id = id;
		this.isSelected = false;
		this.currentFloor = 0;
		this.insideCabin = new InsideCabin(this);
		this.x = x;
		this.y = y;
		this.upKey = upKey;
		this.downKey = downKey;
		this.selectKey = selectKey;
		this.moveY = 0;
		this.lastCustomerType = null;
		this.comboCount = 1;
		this.hasMoved = false;
	}

	@Override
	public void draw(GraphicsContext gc) {
		Color outlineColor = Color.rgb(57, 57, 79);
		for (int i = 0; i < 32 * 20; i += 32) {
			gc.drawImage(RenderableHolder.shaftBg, 0, i);
		}
		if (this.isSelected()) {
			outlineColor = Color.rgb(255, 255, 0);
		}
		gc.drawImage(RenderableHolder.cabinSprite, x, y);
		gc.setStroke(outlineColor);
		gc.setLineWidth(5);
		gc.strokeRoundRect(x, y, Config.UNIT * 1.5, Config.FLOOR_HEIGHT, Config.UNIT * 0.1, Config.UNIT * 0.1);
	}

	@Override
	public void update() {
		moveElevatorSprite();
		insideCabin.update();
		handleElevatorMovementUpdate();
		if (isSelected) {
			handleSelectedCabinInteraction();
		}

	}

	public void moveElevatorSprite() {
		if (Math.abs(this.y - (((Config.TOTAL_FLOOR - 1) - this.getCurrentFloor()) * Config.FLOOR_HEIGHT)) < 10e-8) {
			this.y = (6 - this.getCurrentFloor()) * Config.FLOOR_HEIGHT;
			this.moveY = 0;
		} else {
			if (this.moveY > 0) {
//				this.moveY -= 0.0225 * Config.UNIT;
				this.y += Config.ELEV_SPEED;
			}
			if (this.moveY < 0) {
//				this.moveY += 0.0225 * Config.UNIT;
				this.y -= Config.ELEV_SPEED;
			}
		}
	}

	private void handleElevatorMovementUpdate() {

		if (InputUtility.getKeyPressed(selectKey)) {
			GameLogic.getInstance().getSelectedElev().setSelected(false);
			this.setSelected(true);
		}

		if (y == (((Config.TOTAL_FLOOR - 1) - currentFloor) * Config.FLOOR_HEIGHT)) {
			if (InputUtility.getKeyPressed(upKey) && currentFloor != Config.TOP_FLOOR) {
				moveUp();
				moveY = -Config.FLOOR_HEIGHT;
			}
			if (InputUtility.getKeyPressed(downKey) && currentFloor != Config.FIRST_FLOOR) {
				moveDown();
				moveY = Config.FLOOR_HEIGHT;
			}
		}

	}

	private int moveUp() {
		SoundUtils.playTrack(RenderableHolder.moveElevatorTrack, 0.5);
		this.setCurrentFloor(this.getCurrentFloor() + 1);
		this.hasMoved = true;
		return this.currentFloor;
	}

	private int moveDown() {
		SoundUtils.playTrack(RenderableHolder.moveElevatorTrack, 0.4);
		this.setCurrentFloor(this.getCurrentFloor() - 1);
		this.hasMoved = true;
		return this.currentFloor;
	}

	private void handleSelectedCabinInteraction() {

		Integer queue = InputUtility.getPassengerIndexPressed();
		BaseCustomer customer = getCustomerFromQueuePressed(queue);

		if (customer != null) {
			TimeGauge timeGauge = GameLogic.getInstance().getTimeGauge();
			customer.setCurrentFloor(currentFloor);

			performDestinationCheck(customer, timeGauge);
			setLastCustomerType(customer.toString());
			CustomerUtils.removePassengerFromCabin(customer, insideCabin, queue);
		}
	}

	private BaseCustomer getCustomerFromQueuePressed(Integer queue) {
		return (queue == null) ? null : insideCabin.getPassengers()[queue]; // return Customer if present otherwise
																			// return null
	}

	private void performDestinationCheck(BaseCustomer customer, TimeGauge timeGauge) {
		boolean isOnSameFloor = customer.getCurrentFloor() == customer.getDestinationFloor();
		if (isOnSameFloor) {
			int scoreGain = Config.BASE_SCORE_GAIN;
			if (!hasMoved) {
				setComboCount(getComboCount() + customer.getSpaceNeeded());
				SoundUtils.playTrack(RenderableHolder.sendPassengerSucceedTrack[(Math.min((getComboCount() - 1), 4))]);

				if (customer.toString().equals(lastCustomerType)) {
					scoreGain = (int) (scoreGain * (customer.getRewardMultiplier() * getComboCount()));
				} else {
					scoreGain = (int) (scoreGain * getComboCount());
				}

			} else {
				setComboCount(customer.getSpaceNeeded());
				SoundUtils.playTrack(RenderableHolder.sendPassengerSucceedTrack[getComboCount() - 1]);
			}
			int bonusTimeFromCombo = (int) (scoreGain / Config.BASE_SCORE_GAIN);
			timeGauge.setTimeLeft(timeGauge.getTimeLeft() + (int) Math.round(Config.TIME_GAIN * bonusTimeFromCombo));
			System.out.println("On the same floor: " + !hasMoved + " lastCustomerType:" + lastCustomerType
					+ " CurrentCustomerType:" + customer.toString() + " comboCount:" + getComboCount() + " SCORE"
					+ scoreGain + " timeGain"
					+ Integer.toString((int) Math.round(Config.TIME_GAIN * bonusTimeFromCombo)));

			setLastCustomerType(customer.toString());
			timeGauge.setScore(timeGauge.getScore() + scoreGain);
		} else {
			SoundUtils.playTrack(RenderableHolder.sendPassengerFailedTrack, 0.8);
			SoundUtils.playTrack(RenderableHolder.sendPassengerFailedHumanTrack, 0.7);
			timeGauge.setTimeLeft(timeGauge.getTimeLeft() - (int) Math.round(Config.TIME_LOSS));
		}
		hasMoved = false;
	}

	public int getId() {
		return id;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = Math.max(Config.FIRST_FLOOR, Math.min(Config.TOP_FLOOR, currentFloor));
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void setSelected(boolean select) {
		SoundUtils.playTrack(RenderableHolder.selectCabinTrack, 0.2);
		this.isSelected = select;
	}

	public InsideCabin getInsideCabin() {
		return insideCabin;
	}

	public String getLastCustomerType() {
		return lastCustomerType;
	}

	public void setLastCustomerType(String recentPassengerType) {
		this.lastCustomerType = recentPassengerType;
	}

	public int getComboCount() {
		return comboCount;
	}

	public void setComboCount(int comboCount) {
		if (comboCount > 5)
			comboCount = 5;
		if (comboCount < 1)
			comboCount = 1;
		this.comboCount = comboCount;
	}

}