package entity.customer;

import entity.Entity;
import entity.building.InsideCabin;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import logic.game.PatienceLevel;
import sharedObject.RenderableHolder;
import utils.Config;
import utils.Randomizer;

public abstract class BaseCustomer extends Entity {

	private int currentFloor;
	private int currentQueue;
	private int destinationFloor;
	protected int spaceNeeded;
	protected double maxPatience;
	protected double patienceLeft;
	protected double rewardMultiplier;
	protected Image image;
	protected Color gaugeColor;

	public BaseCustomer() {
		super(100);
		setDestinationFloor();
		this.spaceNeeded = 1;
		this.rewardMultiplier = 1;
	}

	public BaseCustomer(int spaceNeeded, double rewardMultiplier) {
		super(100);
		setDestinationFloor();
		this.spaceNeeded = spaceNeeded;
		this.rewardMultiplier = rewardMultiplier;
	}

	private void setDestinationFloor() {
		currentFloor = Randomizer.getRandomInt(0, 6);
		do {
			destinationFloor = Randomizer.getRandomInt(0, 6);
		} while (destinationFloor == currentFloor);
	}

	public abstract boolean canEnter(InsideCabin insideCabin);

	protected abstract void setCustomerPatienceType(PatienceLevel patienceLevel);

	public String toString() {
		return "BaseCustomer";
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public double getRewardMultiplier() {
		return rewardMultiplier;
	}

	public void setCurrentFloor(int currentFloor) {
		if (Config.FIRST_FLOOR <= currentFloor && currentFloor <= Config.TOP_FLOOR)
			;
		this.currentFloor = currentFloor;
	}

	public int getDestinationFloor() {
		return destinationFloor;
	}

	public double getPatienceLeft() {
		return patienceLeft;
	}

	public void setPatienceLeft(double patienceLeft) {
		if (patienceLeft <= 0)
			patienceLeft = 0;
		this.patienceLeft = patienceLeft;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getCurrentQueue() {
		return currentQueue;
	}

	public void setCurrentQueue(int currentQueue) {
		this.currentQueue = currentQueue;
	}

	public double getMaxPatience() {
		return maxPatience;
	}

	public int getSpaceNeeded() {
		return spaceNeeded;
	}

	@Override
	public void update() {
		this.setPatienceLeft(getPatienceLeft() - Config.DECREASE_RATE);
	}

	public void draw(GraphicsContext gc) {
		int currentQueue = getCurrentQueue();
		int currentFloor = getCurrentFloor();
		drawCleaner(gc, currentQueue, currentFloor, Config.UNIT * (0.2), Config.UNIT * (0.8), 7);
	}

	public void drawInCabin(GraphicsContext gc) {
		int currentQueue = getCurrentQueue();
		drawCleaner(gc, currentQueue, 0, Config.UNIT * (3), Config.UNIT * (0.75 + 1), 1);
	}

	private void drawCleaner(GraphicsContext gc, int currentQueue, int currentFloor,
			double leftPaddingOfTheClickingPane, double customerWidthIncludePaneSpacing, int totalFloor) {

		double oneTwelvthUnit = (Config.UNIT * 1 / 12);
		double allowedImageWidth = Config.UNIT * (0.75);

		double currentPatienceLeft = allowedImageWidth * (getPatienceLeft() / getMaxPatience());
		double yFirstFloor = Config.FLOOR_HEIGHT * totalFloor;
		double XPos = currentQueue * customerWidthIncludePaneSpacing;
		double YPos = yFirstFloor - (Config.UNIT / 20) - ((currentFloor) * Config.FLOOR_HEIGHT); // +5 = space for
																									// patienceGauge

		gc.drawImage(image, leftPaddingOfTheClickingPane + XPos, YPos - (Config.FLOOR_HEIGHT));

		gc.setFill(this.gaugeColor);
		gc.setStroke(Config.PATIENCE_GAUGE_BORDER);
		gc.strokeRect(leftPaddingOfTheClickingPane + XPos, YPos - oneTwelvthUnit, allowedImageWidth, oneTwelvthUnit); // (startx,starty,width,height)
		gc.fillRect(leftPaddingOfTheClickingPane + XPos, YPos - oneTwelvthUnit, currentPatienceLeft, oneTwelvthUnit);

		gc.setFont(RenderableHolder.pixelStyleFont);
		gc.setFill(Config.TEXT_FILL);
		gc.setStroke(Config.TEXT_STROKE);
		gc.fillText(Integer.toString(getDestinationFloor() + 1),
				leftPaddingOfTheClickingPane + XPos + (Config.UNIT * 0.025), YPos - (Config.FLOOR_HEIGHT * 0.70));
		gc.strokeText(Integer.toString(getDestinationFloor() + 1),
				leftPaddingOfTheClickingPane + XPos + (Config.UNIT * 0.025), YPos - (Config.FLOOR_HEIGHT * 0.70));

	}

}
