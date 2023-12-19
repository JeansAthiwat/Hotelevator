package sharedObject;

import utils.Config;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import entity.building.CustomerGrid;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class RenderableHolder {
	private static RenderableHolder instance = new RenderableHolder();

	private List<IRenderable> entities;
	private Comparator<IRenderable> comparator;
	public static Image hotelSprite;
	public static Image shaftBg;
	public static Image scifiTile;
	public static Image sideBarSprite;
	public static Image bottomBarSprite;
	public static Image insideCabin;
	public static Image hotelFloorSprite;
	public static Image bgSprite;
	public static Image gameName;
	public static Image startBg;
	public static Image instBg;
	public static Image exitBg;
	public static Image healthBar;
	public static Image pausePaneBackground;
	public static Image instruction;
	public static WritableImage wallpaper;
	public static WritableImage health;
	public static WritableImage healthContainer;
	public static WritableImage cabinSprite;
	public static WritableImage insBg;
	public static Font pixelStyleFont;

	public static Image standardCustomerHigh, standardCustomerMedium, standardCustomerLow;
	public static Image fatCustomerHigh, fatCustomerMedium, fatCustomerLow;
	public static Image vipCustomerHigh, vipCustomerMedium, vipCustomerLow;

	public static MediaPlayer gameSoundTrack, gameStartTrack, gameOverTrack, tickTockTrack, buttonClickTrack,
			buttonHoverTrack, selectCabinTrack, moveElevatorTrack, addPassengerSucceedTrack, addPassengerFailedTrack,
			sendPassengerFailedTrack, sendPassengerFailedHumanTrack, standardCustomerSpawn, fatCustomerSpawn,
			vipCustomerSpawn;

	public static MediaPlayer[] sendPassengerSucceedTrack;

	static {
		loadResource();
	}

	public RenderableHolder() {
		entities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			return -1;
		};
	}

	public static RenderableHolder getInstance() {
		return instance;
	}

	public static void setInstance() {
		instance = new RenderableHolder();
	}

	public static void loadResource() {
		gameName = new Image(ClassLoader.getSystemResource("game-name.png").toString(), (Config.UNIT * 10),
				(Config.UNIT * 1.125 * 7), true, false);
		healthBar = new Image(ClassLoader.getSystemResource("healthbar.png").toString(), 23 * 6, 80 * 6, false, false);
		health = new WritableImage(healthBar.getPixelReader(), 84, 48, 54, 384);
		healthContainer = new WritableImage(healthBar.getPixelReader(), 0, 0, 54, 80 * 6);
		shaftBg = new Image(ClassLoader.getSystemResource("shaftbg.png").toString(), (Config.UNIT * 1.5),
				(Config.UNIT * 1.125 * 7), true, false);
		scifiTile = new Image(ClassLoader.getSystemResource("scifi_sheet.png").toString(), (491), (717), false, false);
		wallpaper = new WritableImage(scifiTile.getPixelReader(), 370, 132, 68, 90);
		cabinSprite = new WritableImage(scifiTile.getPixelReader(), 370, 0, 120, 90);
		insideCabin = new Image(ClassLoader.getSystemResource("insideCabin.png").toString(), (Config.UNIT * 14),
				(Config.UNIT * 1.125), true, false);
		pausePaneBackground = new Image(ClassLoader.getSystemResource("pausePaneBackground.png").toString(),
				(Config.UNIT * 2), (Config.UNIT * 1.125), true, false);

		instruction = new Image(ClassLoader.getSystemResource("instruction.png").toString(),
				(Config.SCREEN_WIDTH - (Config.UNIT * 2)), (Config.SCREEN_HEIGHT - (Config.UNIT * 2)), true, false);

		loadCustomer();

		loadSound();

		bgSprite = new Image(ClassLoader.getSystemResource("bg-superreduced.gif").toString(), (Config.UNIT * 16),
				(Config.UNIT * 8 * 1.125), true, false);
		pixelStyleFont = Font.loadFont(ClassLoader.getSystemResource("pixelFont.ttf").toString(), 20);
	}

	public void add(IRenderable entity) {
		entities.add(entity);
		Collections.sort(entities, comparator);
	}

	public List<IRenderable> getEntities() {
		return entities;
	}

	private static void loadCustomer() {
		standardCustomerHigh = new Image(ClassLoader.getSystemResource("StandardHc.gif").toString(),
				(Config.UNIT * 0.75), (Config.UNIT), true, false);
		standardCustomerMedium = new Image(ClassLoader.getSystemResource("StandardMc.gif").toString(),
				(Config.UNIT * 0.75), (Config.UNIT), true, false);
		standardCustomerLow = new Image(ClassLoader.getSystemResource("StandardLc.gif").toString(),
				(Config.UNIT * 0.75), (Config.UNIT), true, false);

		fatCustomerHigh = new Image(ClassLoader.getSystemResource("FatHc.gif").toString(), (Config.UNIT * 0.75),
				(Config.UNIT), true, false);
		fatCustomerMedium = new Image(ClassLoader.getSystemResource("FatMc.gif").toString(), (Config.UNIT * 0.75),
				(Config.UNIT), true, false);
		fatCustomerLow = new Image(ClassLoader.getSystemResource("FatLc.gif").toString(), (Config.UNIT * 0.75),
				(Config.UNIT), true, false);

		vipCustomerHigh = new Image(ClassLoader.getSystemResource("VIPHc.gif").toString(), (Config.UNIT * 0.75),
				(Config.UNIT), true, false);
		vipCustomerMedium = new Image(ClassLoader.getSystemResource("VIPMc.gif").toString(), (Config.UNIT * 0.75),
				(Config.UNIT), true, false);
		vipCustomerLow = new Image(ClassLoader.getSystemResource("VIPLc.gif").toString(), (Config.UNIT * 0.75),
				(Config.UNIT), true, false);

	}

	private static void loadSound() {
		gameSoundTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/gameSoundTrack.wav").toString()));
		gameSoundTrack.setCycleCount(MediaPlayer.INDEFINITE);

		gameStartTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/gameStartTrack.wav").toString()));
		gameOverTrack = new MediaPlayer(new Media(ClassLoader.getSystemResource("music/gameOverTrack.wav").toString()));

		tickTockTrack = new MediaPlayer(new Media(ClassLoader.getSystemResource("music/tickTockTrack.wav").toString()));
		tickTockTrack.setOnEndOfMedia(() -> {
			tickTockTrack.stop();
			tickTockTrack.seek(Duration.ZERO);
		});

		buttonClickTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/buttonClickTrack.wav").toString()));

		buttonHoverTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/buttonHoverTrack.wav").toString()));

		selectCabinTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/selectCabinTrack.wav").toString()));

		moveElevatorTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/moveElevatorTrack.wav").toString()));

		addPassengerSucceedTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/addPassengerSucceedTrack.wav").toString()));

		addPassengerFailedTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/addPassengerFailedTrack.wav").toString()));
		sendPassengerFailedTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/sendPassengerFailedTrack.wav").toString()));
		sendPassengerFailedHumanTrack = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/sendPassengerFailedHumanTrack.wav").toString()));

		standardCustomerSpawn = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/standardCustomerSpawn.wav").toString()));
		fatCustomerSpawn = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/fatCustomerSpawn.wav").toString()));
		vipCustomerSpawn = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/vipCustomerSpawn.wav").toString()));

		MediaPlayer sendPassengerSucceedTrack1 = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/sendPassengerSucceedTrack1.wav").toString()));
		MediaPlayer sendPassengerSucceedTrack2 = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/sendPassengerSucceedTrack2.wav").toString()));
		MediaPlayer sendPassengerSucceedTrack3 = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/sendPassengerSucceedTrack3.wav").toString()));
		MediaPlayer sendPassengerSucceedTrack4 = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/sendPassengerSucceedTrack4.wav").toString()));
		MediaPlayer sendPassengerSucceedTrack5 = new MediaPlayer(
				new Media(ClassLoader.getSystemResource("music/sendPassengerSucceedTrack5.wav").toString()));
		MediaPlayer[] mediaPlayerArray = { sendPassengerSucceedTrack1, sendPassengerSucceedTrack2,
				sendPassengerSucceedTrack3, sendPassengerSucceedTrack4, sendPassengerSucceedTrack5 };
		sendPassengerSucceedTrack = mediaPlayerArray;

	}
}
