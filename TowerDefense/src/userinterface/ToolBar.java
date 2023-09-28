package userinterface;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import helps.LoadSave;

import java.util.HashMap;

import objects.Tile;
import scenes.Editing;

public class ToolBar extends Bar {

	private Editing editing;
	private MyButton bMenu, bSave;
	private MyButton bPathStart, bPathEnd;
	private BufferedImage pathStart, pathEnd;
	private Tile selectedTile;
	private Map <MyButton, ArrayList<Tile>> map = new HashMap<MyButton, ArrayList<Tile>>();

	private MyButton bGrass, bWater, bRoadS, bRoadC, bWaterC, bWaterB, bWaterI;
	private MyButton currentButton;
	private int currentIndex = 0;

	public ToolBar(int x, int y, int width, int height, Editing editing) {
		super(x, y, width, height);
		this.editing = editing;
		initPathImages();
		initButtons();
	}

	private void initPathImages() {
		pathStart = LoadSave.getSpriteAtlas().getSubimage(7 * 32, 2 * 32, 32, 32);
		pathEnd = LoadSave.getSpriteAtlas().getSubimage(8 * 32, 2 * 32, 32, 32);
	}

	private void initButtons() {
		bMenu = new MyButton("Menu", 4, 644, 100, 30);
		bSave = new MyButton("Save", 4, 674, 100, 30);

 		int w = 50;
		int h = 50;
		int xStart = 112;
		int yStart = 644;
		int xOffset = 60;

		int i = 0;

		bGrass = new MyButton("Grass", xStart, yStart, w, h, i++);
		bWater = new MyButton("Water", xStart + xOffset, yStart, w, h, i++);

		initMapButton(bRoadS, editing.getGame().getTileManager().getRoadsS(), xStart, yStart, xOffset, w, h, i++);
		initMapButton(bRoadC, editing.getGame().getTileManager().getRoadsC(), xStart, yStart, xOffset, w, h, i++);
		initMapButton(bWaterC, editing.getGame().getTileManager().getCorners(), xStart, yStart, xOffset, w, h, i++);
		initMapButton(bWaterB, editing.getGame().getTileManager().getBeaches(), xStart, yStart, xOffset, w, h, i++);
		initMapButton(bWaterI, editing.getGame().getTileManager().getIslands(), xStart, yStart, xOffset, w, h, i++);

		bPathStart = new MyButton("PathStart", xStart, yStart + xOffset, w, h, i++);
		bPathEnd = new MyButton("PathEnd", xStart + xOffset, yStart + xOffset, w, h, i++);
	}

	private void initMapButton(MyButton b, ArrayList<Tile> list, int x, int y, int xOff, int width, int height,
			int id) {
		b = new MyButton("", x + xOff * id, y, width, height, id);
		map.put(b, list);
	}

	private void saveLevel() {
		editing.saveLevel();
	}

	public void rotateSprite() {
		currentIndex++;

		if (currentIndex >= map.get(currentButton).size()) {
			currentIndex = 0;
		}

		selectedTile = map.get(currentButton).get(currentIndex);
		editing.setSelectedTile(selectedTile);
	}

	public void draw(Graphics g) {

		// Background
		g.setColor(Color.orange);
		g.fillRect(x, y, width, height);

		// Buttons
		drawButtons(g);
	}

	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bSave.draw(g);

		drawPathButton(g, bPathStart, pathStart);
		drawPathButton(g, bPathEnd, pathEnd);

		drawNormalButton(g, bGrass);
		drawNormalButton(g, bWater);

		drawSelectedTile(g);
		drawMapButtons(g);
	}

	private void drawPathButton(Graphics g, MyButton b, BufferedImage image) {
		g.drawImage(image, b.getX(), b.getY(), b.getWidth(), b.getHeight(), null);
		drawButtonFeedback(g, b);
	}

	private void drawNormalButton(Graphics g, MyButton b) {
		g.drawImage(getButtImage(b.getId()), b.getX(), b.getY(), b.getWidth(), b.getHeight(), null);

		drawButtonFeedback(g, b);

	}

	private void drawMapButtons(Graphics g) {

		for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {

			MyButton b = entry.getKey();
			BufferedImage img = entry.getValue().get(0).getSprite();

			g.drawImage(img, b.getX(), b.getY(), b.getWidth(), b.getHeight(), null);

			drawButtonFeedback(g, b);

		}

	}

	private void drawSelectedTile(Graphics g) {
		if (selectedTile != null) {
			g.drawImage(selectedTile.getSprite(), 550, 644, 50, 50, null);
			g.setColor(Color.WHITE);
			g.drawRect(550, 644, 50, 50);
		}

	}

	public BufferedImage getButtImage(int id) {
		return editing.getGame().getTileManager().getSprite(id);
	}

	public void mouseClicked(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
			System.out.println("In Menu mode");

		} else if (bSave.getBounds().contains(x, y)) {
			saveLevel();
		} else if (bWater.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(bWater.getId());
			editing.setSelectedTile(selectedTile);
			return;
		} else if (bGrass.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(bGrass.getId());
			editing.setSelectedTile(selectedTile);
			return;
		} else if (bPathStart.getBounds().contains(x, y)) {
			selectedTile = new Tile(pathStart, -1, -1);
			editing.setSelectedTile(selectedTile);
			return;
		} else if (bPathEnd.getBounds().contains(x, y)) {
			selectedTile = new Tile(pathEnd, -2, -2);
			editing.setSelectedTile(selectedTile);
			return;
		} else {

			for (MyButton b : map.keySet()) {
				if (b.getBounds().contains(x, y)) {
					selectedTile = map.get(b).get(0);
					editing.setSelectedTile(selectedTile);
					currentButton = b;
					currentIndex = 0;
					return;
				}
			}
		}
	}

	public void mouseMoved(int x, int y) {

		// set all false
		bMenu.setMouseOver(false);
		bSave.setMouseOver(false);
		bGrass.setMouseOver(false);
		bWater.setMouseOver(false);
		bPathStart.setMouseOver(false);
		bPathEnd.setMouseOver(false);

		for (MyButton b : map.keySet()) {
			b.setMouseOver(false);
		}

		// check all
		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMouseOver(true);

		} else if (bSave.getBounds().contains(x, y)) {
			bSave.setMouseOver(true);
		} else if (bWater.getBounds().contains(x, y)) {
			bWater.setMouseOver(true);
		} else if (bGrass.getBounds().contains(x, y)) {
			bGrass.setMouseOver(true);
		} else if (bPathStart.getBounds().contains(x, y)) {
			bPathStart.setMouseOver(true);
		} else if (bPathEnd.getBounds().contains(x, y)) {
			bPathEnd.setMouseOver(true);
		} else {
			for (MyButton b : map.keySet()) {
				if (b.getBounds().contains(x, y)) {
					b.setMouseOver(true);
					return;
				}
			}
		}
	}

	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMousePressed(true);
		} else if (bSave.getBounds().contains(x, y)) {
			bSave.setMousePressed(true);
		} else if (bWater.getBounds().contains(x, y)) {
			bWater.setMousePressed(true);
		} else if (bGrass.getBounds().contains(x, y)) {
			bGrass.setMousePressed(true);
		} else if (bPathStart.getBounds().contains(x, y)) {
			bPathStart.setMousePressed(true);
		} else if (bPathEnd.getBounds().contains(x, y)) {
			bPathEnd.setMousePressed(true);
		} else {
			for (MyButton b : map.keySet()) {
				if (b.getBounds().contains(x, y)) {
					b.setMousePressed(true);
					return;
				}
			}
		}
	}

	public void mouseReleased(int x, int y) {
		bSave.resetBooleans();
		bMenu.resetBooleans();
		bGrass.resetBooleans();
		bWater.resetBooleans();
		bPathStart.resetBooleans();
		bPathEnd.resetBooleans();
		for (MyButton b : map.keySet()) {
			b.resetBooleans();
		}
	}

	public BufferedImage getStartPathImage() {
		return pathStart;
	}

	public BufferedImage getEndPathImage() {
		return pathEnd;
	}

}
