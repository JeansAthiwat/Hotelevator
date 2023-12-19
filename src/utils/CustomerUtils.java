package utils;

import entity.building.InsideCabin;
import entity.customer.BaseCustomer;
import entity.customer.VIPCustomer;
import entity.sidebar.TimeGauge;
import logic.game.GameLogic;

public class CustomerUtils {

	public static boolean addCustomerToFloorFromGenerator(BaseCustomer customer, BaseCustomer[][] customerGrid) { // BaseCustomer[queue][floor]
		int floorIndex = customer.getCurrentFloor();
		if (customerQueueIsFull(customerGrid, floorIndex)) {
//			System.out.println("customer NOT addded");
			return false;
		}
		addCustomerToFirstEmptyQueueOfFloor(customer, customerGrid, floorIndex);
//		System.out.println("customer addded");
		return true;
	}

	private static boolean customerQueueIsFull(BaseCustomer[][] customerGrid, int floorIndex) {

		for (int row = customerGrid.length - 1; row >= 0; row--) {
			BaseCustomer customer = customerGrid[row][floorIndex];
			// Perform operations with the element of the desired row
			if (customer == null)
				return false; // grid is empty
		}
		return true;

	}

	private static boolean addCustomerToFirstEmptyQueueOfFloor(BaseCustomer customer, BaseCustomer[][] customerGrid,
			int floorIndex) {

		for (int queue = 0; queue < customerGrid.length; queue++) {
			if (customerGrid[queue][floorIndex] == null) {
				customer.setCurrentQueue(queue);
				customerGrid[queue][floorIndex] = customer;
				return true; // added
			}
		}
		return false; // floor is full

	}

	public static void removeCustomerFromFloor(BaseCustomer customer, BaseCustomer[][] customerGrid, int queueIndex,
			int floorIndex) { // tries to fill the empty space in the front first
		customerGrid[queueIndex][floorIndex] = null;
		// Perform the rearrangement
		int nonNullRow = 0;

		for (int row = 0; row < customerGrid.length; row++) {
			if (customerGrid[row][floorIndex] != null) {
				BaseCustomer tempCustomer = customerGrid[row][floorIndex];
				tempCustomer.setCurrentQueue(nonNullRow);
				customerGrid[row][floorIndex] = customerGrid[nonNullRow][floorIndex];
				customerGrid[nonNullRow][floorIndex] = tempCustomer;
				nonNullRow++;
			}
		}

	}

	public static boolean addPassengerToFirstToFirstEmptyQueueOfCabin(BaseCustomer customer, InsideCabin insideCabin) {
		BaseCustomer[] passengers = insideCabin.getPassengers();

		for (int queue = 0; queue < passengers.length; queue++) {
			if (passengers[queue] == null) {
				customer.setCurrentQueue(queue);
				passengers[queue] = customer;
				insideCabin.setNumberOfPassenger(insideCabin.getNumberOfPassenger() + customer.getSpaceNeeded());
				;
				return true;
			}
		}
		return false; // somethings is wrong

	}

	public static void removePassengerFromCabin(BaseCustomer customer, InsideCabin insideCabin, int queueIndex) {
		BaseCustomer[] passengers = insideCabin.getPassengers();
		passengers[queueIndex] = null;
		// Perform the rearrangement
		int nullIndex = 0;
		for (int queue = 0; queue < passengers.length; queue++) {
			if (passengers[queue] != null) {
				BaseCustomer tempCustomer = passengers[queue];
				tempCustomer.setCurrentQueue(nullIndex);
				passengers[queue] = passengers[nullIndex];
				passengers[nullIndex] = tempCustomer;
				nullIndex++;
			}
		}
		insideCabin.setNumberOfPassenger((insideCabin.getNumberOfPassenger() - customer.getSpaceNeeded()));
	}

	public static boolean containVIP(BaseCustomer[] passengers) {
		for (int i = 0; i < passengers.length; i++) {
			if (passengers[i] instanceof VIPCustomer) {
				return true;
			}
		}
		return false;
	}

}
