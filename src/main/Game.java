package main;

import javax.swing.JFrame;

import helps.LoadSave;
import managers.TileManager;
import scenes.Editing;
import scenes.GameOver;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;

public class Game extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;

	private TileManager tileManager;
	private GameScreen gameScreen;
	private Thread gameThread;

	// Classes
	private Menu menu;
	private Render render;
	private Playing playing;
	private Settings settings;
	private Editing editing;
	private GameOver gameOver;

	public Game() {
		createDefaultLevel();
		initClasses();

		setVisible(true); // supposed to be only at the end
		setDefaultCloseOperation(EXIT_ON_CLOSE); // default is DO_NOTHING_ON_CLOSE
		setLocationRelativeTo(null); // starts in center
		setResizable(false);

		add(gameScreen);
		pack(); // needs to be added after all elements are added
		setVisible(true);
	}

	private void createDefaultLevel() {
		int[] arr = new int[400];
		for (int i = 0; i < arr.length; i++)
			arr[i] = 0;

		LoadSave.CreateLevel("new_level", arr);
	}

	private void initClasses() {
		tileManager = new TileManager();
		render = new Render(this);
		gameScreen = new GameScreen(this);
		menu = new Menu(this);
		settings = new Settings(this);
		playing = new Playing(this);
		editing = new Editing(this);
		gameOver = new GameOver(this);
	}

	private void start() {
		gameThread = new Thread(this); // "this" will automatically look for the "run" method
		gameThread.start();
	}

	private void updateGame() {
		switch (GameStates.gameState) {
		case EDIT:
			editing.update();
			break;
		case MENU:
			break;
		case PLAYING:
			playing.update();
			break;
		case SETTINGS:
			break;
		case GAME_OVER:
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.gameScreen.initInputs();
		game.start();
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();

		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {
			now = System.nanoTime();

			// Render at 120fps
			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				frames++;
			}
			// Update 60x per second
			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}
			// Checking FPS and UPS
			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}
		}
	}

	// Getters and Setters
	public Render getRender() {
		return render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Settings getSettings() {
		return settings;
	}

	public Editing getEditing() {
		return editing;
	}

	public TileManager getTileManager() {
		return tileManager;
	}

	public GameOver getGameOver() {
		return gameOver;
	}

}