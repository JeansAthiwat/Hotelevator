package utils;

import java.util.Random;

public class Randomizer {

    private static final Random random = new Random();
    
    public static int getRandomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
