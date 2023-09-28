package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import main.Game;
import userinterface.MyButton;
import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods{
		
	private MyButton bPlaying, bSettings, bQuit, bEdit;

	public Menu(Game game) {
		super(game);

		
		initButtons();
	}

	private void initButtons() {
		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 150 + 80;
		int yOffset = 100;
		
		bPlaying = new MyButton("Play", x, y, w, h);
		bEdit = new MyButton("Edit", x, y + yOffset , w, h);
		bSettings = new MyButton("Settings", x, y + yOffset * 2, w, h);
		bQuit = new MyButton("Quit", x, y + yOffset * 3, w, h);
		
	}

	@Override
	public void render(Graphics g) {
		
		drawButtons(g);
		
		
	}
	
	private void drawButtons(Graphics g) {
		bPlaying.draw(g);
	 	bEdit.draw(g);
		bSettings.draw(g);
		bQuit.draw(g);
		
		g.setColor(Color.orange);
		g.setFont(new Font("LucidaSans", Font.BOLD, 35));
		g.drawString("TOWER DEFENSE", 170, 100 + 50);
		
		g.setFont(new Font("LucidaSans", Font.PLAIN, 15));
		g.drawString("by SMG – based on Kaarin Gaming", 200, 700);
		
		
		
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (bPlaying.getBounds().contains(x,y)) {
 			SetGameState(PLAYING);
		}else if(bEdit.getBounds().contains(x, y)) {
			SetGameState(EDIT);
			System.out.println("in editing mode");
		}else if(bSettings.getBounds().contains(x, y)) {
			SetGameState(SETTINGS);
			System.out.println("in setting mode");
		}else if (bQuit.getBounds().contains(x, y)) {
			System.exit(0);
		}
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		bPlaying.setMouseOver(false);
		bEdit.setMouseOver(false);
		bSettings.setMouseOver(false);
		bQuit.setMouseOver(false);
		
		if (bPlaying.getBounds().contains(x,y))
				bPlaying.setMouseOver(true);
		else if(bEdit.getBounds().contains(x, y))
			bEdit.setMouseOver(true);
		else if(bSettings.getBounds().contains(x, y))
				bSettings.setMouseOver(true);
		else if (bQuit.getBounds().contains(x, y))
				bQuit.setMouseOver(true);
			
	}

	@Override
	public void mousePressed(int x, int y) {
		
		if (bPlaying.getBounds().contains(x,y)) 
			bPlaying.setMousePressed(true);
		else if (bEdit.getBounds().contains(x, y))
			bEdit.setMousePressed(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMousePressed(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMousePressed(true);
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bPlaying.resetBooleans();
		bEdit.resetBooleans();
		bSettings.resetBooleans();
		bQuit.resetBooleans();
	}
	
	@Override
	public void mouseDragged(int x, int y) {
		
	}

}
