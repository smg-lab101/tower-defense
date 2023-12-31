package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import userinterface.MyButton;
import static main.GameStates.*;

public class Settings extends GameScene implements SceneMethods {
	
	private MyButton bMenu;

	public Settings(Game game) {
		super(game);
		
		initButtons();
	}
	
	private void initButtons() {
		bMenu = new MyButton("Menu", 20, 20, 100, 30);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(0, 0, 640, 640);
		
		drawButtons(g);
		
		g.setColor(Color.white);
		g.setFont(new Font("LucidaSans", Font.BOLD, 20));
		g.drawString("nothing to see here...", 30, 580 );
		
 	}
	
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
	}
	
	

	@Override
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x,y)) {
 			SetGameState(MENU);
			System.out.println("In Menu mode");
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
	}

	@Override
	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
	}
	
	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}
	
	private void resetButtons() {
		bMenu.resetBooleans();
	}

	@Override
	public void mouseDragged(int x, int y) {

	}

}
