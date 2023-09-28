package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import userinterface.MyButton;
import static main.GameStates.*;

public class GameOver extends GameScene implements SceneMethods {

	private MyButton bReplay, bMenu;

	public GameOver(Game game) {
		super(game);

		initButtons();
	}

	private void initButtons() {

		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 150 + 130;
		int yOffset = 80;

		bReplay = new MyButton("Replay", x, y, w, h);
		bMenu = new MyButton("Menu", x, y + yOffset, w, h);
	}

	@Override
	public void render(Graphics g) {
		bMenu.draw(g);
		bReplay.draw(g);

		g.setColor(Color.orange);
		g.setFont(new Font("LucidaSans", Font.BOLD, 35));
		g.drawString("GAME OVER!", 210, 100 + 130);

	}

	private void totalReset() {
		game.getPlaying().totalReset();
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (bReplay.getBounds().contains(x, y)) {
			totalReset();
			SetGameState(PLAYING);

		}
		// replayGame();
		else if (bMenu.getBounds().contains(x, y)) {
			totalReset();
			SetGameState(MENU);
			
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		bReplay.setMouseOver(false);
		bMenu.setMouseOver(false);

		if (bReplay.getBounds().contains(x, y))
			bReplay.setMouseOver(true);
		else if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
	}

	@Override
	public void mousePressed(int x, int y) {

		if (bReplay.getBounds().contains(x, y))
			bReplay.setMousePressed(true);
		else if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
	}

	@Override
	public void mouseReleased(int x, int y) {
		bReplay.resetBooleans();
		bMenu.resetBooleans();
	}

	@Override
	public void mouseDragged(int x, int y) {

	}

}
