package utils;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class InputUtility {

	public static double mouseX, mouseY;
	public static boolean mouseOnScreen = true;
	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();
	private static Integer[] hotelGridPressed = new Integer[2];
	private static Integer passengerIndexPressed;

	public static boolean getKeyPressed(KeyCode keycode) {
		return keyPressed.contains(keycode);
	}

	public static void setKeyPressed(KeyCode keycode, boolean pressed) {
		if (pressed) {
			if (!keyPressed.contains(keycode)) {
				keyPressed.add(keycode);
			}
		} else {
			keyPressed.remove(keycode);
		}
		System.out.println(keyPressed);
	}

	public static Integer[] getHotelGridPressed() {
		Integer[] grid = (Integer[]) hotelGridPressed;
		hotelGridPressed = new Integer[2];
		return grid;
	}

	public static void setHotelGridPressed(Integer[] hotelGrid, boolean pressed) {
		if (pressed) {
			if (!hotelGridPressed.equals(hotelGrid)) {
				hotelGridPressed = hotelGrid;
			}
		} else {
			hotelGridPressed = new Integer[2];
		}
		System.out.println("Clicked queue:" + hotelGridPressed[0] + "-floor:" + hotelGridPressed[1]);
	}

	public static Integer getPassengerIndexPressed() {
		Integer index = (Integer) passengerIndexPressed;
		passengerIndexPressed = null;
		return index;
	}

	public static void setPassengerIndexPressed(Integer index, boolean pressed) {
		if (pressed) {
			if (!(passengerIndexPressed == index)) {
				passengerIndexPressed = index;
			}
		} else {
			passengerIndexPressed = null;
		}
		System.out.println("InputUtil Index P:" + passengerIndexPressed);
	}
}
