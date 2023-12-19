package utils;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import sharedObject.RenderableHolder;

public class SoundUtils {

	public static void playTrack(MediaPlayer mediaPlayer) {
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.play();
	}

	public static void playTrack(MediaPlayer mediaPlayer, double volume) {
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.setVolume(volume);
		mediaPlayer.play();
	}

}
