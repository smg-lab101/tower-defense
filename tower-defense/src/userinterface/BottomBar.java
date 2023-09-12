package userinterface;

import static main.GameStates.*;
import static main.GameStates.SetGameState;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import managers.TileManager;
import objects.Tile;
import scenes.Playing;

public class BottomBar {

	private int x, y, width, height;
	private Playing playing;
	private MyButton bMenu;
	
	private Tile selectedTile;

	private ArrayList<MyButton> tileButtons = new ArrayList<>();

	public BottomBar(int x, int y, int width, int height, Playing playing) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.playing = playing;

		initButtons();
	}

	public void draw(Graphics g) {

		// Background
		g.setColor(new Color(220, 123, 15));
		g.fillRect(x, y, width, height);

		// Buttons
		drawButtons(g);
	}

	private void initButtons() {
		bMenu = new MyButton("Menu", 4, 644, 100, 30);

		int w = 50;
		int h = 50;
		int xStart = 112;
		int yStart = 644;
		int xOffset = 60;

		int i = 0;

		for (Tile tile : playing.getTileManager().tiles) {

			tileButtons.add(new MyButton(tile.getName(), xStart, yStart, w, h, i));
			xStart += xOffset;
			i++;

		}
	}

	private void drawButtons(Graphics g) {
		bMenu.draw(g);

		drawTileButtons(g);
		drawSelectedTile(g);

	}

	private void drawSelectedTile(Graphics g) {
		if(selectedTile != null) {
			g.drawImage(selectedTile.getSprite(), 550, 644, 50, 50, null);
			g.setColor(Color.WHITE);
			g.drawRect(550, 644, 50, 50);
		}
		
	}

	private void drawTileButtons(Graphics g) {

		for (MyButton b : tileButtons) {
			
			//Sprite
			g.drawImage(getButtImage(b.getId()), b.getX(), b.getY(), b.getWidth(), b.getHeight(), null);

			// MouseOver and Border 
			if (b.isMouseOver()) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.BLACK);
			}

			g.drawRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());

			// MousePressed 
			if (b.isMousePressed() && b.isMouseOver()) {
				g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
				g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
			}

		}

	}

	public BufferedImage getButtImage(int id) {
		return playing.getTileManager().getSprite(id);
	}

	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
			System.out.println("In Menu mode");
		}else {
			for (MyButton b : tileButtons) {
				if (b.getBounds().contains(x, y)) {
					selectedTile = playing.getTileManager().getTile(b.getId());
					playing.setSelectedTile(selectedTile);
					//playing.setSelectedTile(selectedTile);
					return;
				}
			}
		}
	}

	public void mouseMoved(int x, int y) {
		
		//set all false
		bMenu.setMouseOver(false);

		for (MyButton b : tileButtons) {
			b.setMouseOver(false);
		}
		
		//check all 
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else {
			for (MyButton b : tileButtons) {
				if (b.getBounds().contains(x, y)) {

					b.setMouseOver(true);
					return;
				}
			}
		}
	}

	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else {
			for (MyButton b : tileButtons) {
				b.setMousePressed(true);
			//REMOVED RETURN STATEMENT
			}
		}
	}

	public void mouseReleased(int x, int y) {
		resetButtons();bMenu.resetBooleans();
		for (MyButton b : tileButtons)
			b.resetBooleans();
	}

	private void resetButtons() {

	}
}
