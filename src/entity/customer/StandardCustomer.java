package entity.customer;

import entity.building.InsideCabin;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.game.PatienceLevel;
import sharedObject.RenderableHolder;
import utils.Config;
import utils.CustomerUtils;

public class StandardCustomer extends BaseCustomer {

	public StandardCustomer(PatienceLevel patienceLevel) {
		super();
		setCustomerPatienceType(patienceLevel);
	}

	@Override
	public String toString() {
		return "StandardCustomer";
	}

	@Override
	public boolean canEnter(InsideCabin insideCabin) {
		int cabinCapacity = insideCabin.getCapacity();
		int currentSpace = insideCabin.getNumberOfPassenger();
		int spaceNeeded = this.getSpaceNeeded();
		if (CustomerUtils.containVIP(insideCabin.getPassengers()))
			return false;
		if (cabinCapacity < currentSpace + spaceNeeded) {
			return false;
		}
		return true;
	}

	@Override
	protected void setCustomerPatienceType(PatienceLevel patienceLevel) {
		switch (patienceLevel) {
		case LOW: {
			this.maxPatience = Config.MAX_LOW_PATIENCE;
			this.gaugeColor = Config.PATIENCE_GAUGE_LOW_P;
			this.setImage(RenderableHolder.standardCustomerLow);
			break;
		}

		case MEDIUM: {
			this.maxPatience = Config.MAX_MEDIUM_PATIENCE;
			this.gaugeColor = Config.PATIENCE_GAUGE_MEDIUM_P;
			this.setImage(RenderableHolder.standardCustomerMedium);
			break;
		}

		case HIGH: {
			this.maxPatience = Config.MAX_HIGH_PATIENCE;
			this.gaugeColor = Config.PATIENCE_GAUGE_HIGH_P;
			this.setImage(RenderableHolder.standardCustomerHigh);
			break;
		}

		}
		this.setPatienceLeft(maxPatience);

	}

}
