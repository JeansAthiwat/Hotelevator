package logic.game;

import java.util.Random;

import utils.Randomizer;

public enum PatienceLevel {
	HIGH, MEDIUM, LOW;

	public static PatienceLevel getRandomPatienceLevel() {
		PatienceLevel[] levels = PatienceLevel.values();
		int index = Randomizer.getRandomInt(0, levels.length - 1);
		return levels[index];
	}
}
