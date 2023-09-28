package userinterface;

import static main.GameStates.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import helps.Constants.Towers;
import objects.Tower;
import scenes.Playing;

public class ActionBar extends Bar {

	private int x, y, width, height;
	private Playing playing;
	private MyButton bMenu, bPause;

	private MyButton[] towerButtons;
	private Tower selectedTower;
	private Tower displayedTower;
	private MyButton sellTowerButton, upgradeTowerButton;

	private DecimalFormat formatter;

	private int gold = 100;
	private boolean showTowerCost;
	private int towerCostType;
	
	private int lives = 25;

	public ActionBar(int x, int y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		formatter = new DecimalFormat("0.0");

		initButtons();
	}

	public void draw(Graphics g) {

		// Background
		g.setColor(Color.orange);
		g.fillRect(x, y, width, height);
 
		drawButtons(g);
		drawDisplayedTower(g);
		drawWaveInfo(g);
		drawGoldInfo(g);

		if (showTowerCost) {
			drawTowerCost(g);
		}
		
		if (playing.isGamePaused()) {
			g.setColor(Color.orange);
			g.setFont(new Font("LucidaSans", Font.BOLD , 23));
			g.drawString("GAME PAUSED", 110, 750);
		}
		
		//Lives
		g.setColor(Color.black);
		g.setFont(new Font("LucidaSans", Font.PLAIN, 18));
		g.drawString("Lives: " + lives, 270, 790);
		
	}

	private void drawTowerCost(Graphics g) {
		g.setColor(Color.gray);
		g.clearRect(290, 644, 110, 50);
		g.setColor(Color.black);
		g.drawRect(290, 644, 110, 50);

		g.setFont(new Font("LucidaSans", Font.BOLD , 15));
		g.drawString("" + getTowerCostName(), 304, 664);
		g.drawString("Cost: " + getTowerCostCost() + "g", 304, 660 + 25);

		// show if not enough gold for tower
		if (isTowerTooExpensive()) {
			g.setFont(new Font("LucidaSans", Font.PLAIN, 10));

			g.drawString("Not enought gold!", 304, 685 + 25);
		}
	}

	private boolean isTowerTooExpensive() {
		return getTowerCostCost() > gold;
	}

	private int getTowerCostCost() {
		return helps.Constants.Towers.GetCost(towerCostType);
	}

	private String getTowerCostName() {
		return helps.Constants.Towers.GetName(towerCostType);
	}

	private void drawGoldInfo(Graphics g) {
		g.drawString("Gold: " + gold + "g", 110, 790);

	}

	private void drawWaveInfo(Graphics g) {
		g.setFont(new Font("LucidaSans", Font.PLAIN, 18));
		g.setColor(Color.black);
		drawWaveTimerInfo(g);
		drawEnemiesLeftInfo(g);
		drawWavesLeftInfo(g);
	}

	private void drawWavesLeftInfo(Graphics g) {
		int current = playing.getWaveManager().getWaveIndex() + 1;
		int size = playing.getWaveManager().getWaves().size();

		g.drawString("Wave " + current + " / " + size, 450, 770);
	}

	private void drawEnemiesLeftInfo(Graphics g) {
		int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
		g.drawString("Enemies Left: " + remaining, 450, 790);

	}

	private void drawWaveTimerInfo(Graphics g) {
		if (playing.getWaveManager().isWaveTimerStarted()) {

			float timeLeft = playing.getWaveManager().getSecondsLeft();
			String formatedText = formatter.format(timeLeft);
			g.drawString("Next Wave in " + formatedText, 450, 750);

		}

	}

	private void drawDisplayedTower(Graphics g) {
		if (displayedTower != null) {
			g.setColor(Color.gray);
			g.fillRect(410, 644, 220, 85);
			g.setColor(Color.black);
			g.drawRect(410, 644, 220, 85);
			g.drawImage(playing.getTowerManager().getTowerImages()[displayedTower.getTowerType()], 420, 650, 50, 50,
					null);

			g.setColor(Color.black);
			g.setFont(new Font("LucidaSans", Font.BOLD, 15));
			g.drawString("" + Towers.GetName(displayedTower.getTowerType()), 490, 660);
			g.setFont(new Font("LucidaSans", Font.PLAIN, 15));
			g.drawString("ID: " + displayedTower.getId() + "   Tier: " + displayedTower.getTier(), 490, 675);

			drawDisplayedTowerBorder(g);
			drawDisplayedTowerRange(g);

			sellTowerButton.draw(g);
			drawButtonFeedback(g, sellTowerButton);

			if (displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
				upgradeTowerButton.draw(g);
				drawButtonFeedback(g, upgradeTowerButton);
			}

			g.setColor(Color.white);
			if (sellTowerButton.isMouseOver()) {
				g.drawString("Sell for " + getSellAmount(displayedTower) + "g", 490, 696);
			} else if (upgradeTowerButton.isMouseOver() && gold >= getUpgradeAmount(displayedTower)) {
				g.drawString("Upgrade for " + getUpgradeAmount(displayedTower) + "g", 490, 698);
			}
		}
	}

	private int getSellAmount(Tower displayedTower) {
		int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
		upgradeCost *= 0.5f;
		return helps.Constants.Towers.GetCost(displayedTower.getTowerType()) / 2 + upgradeCost;
	}

	private int getUpgradeAmount(Tower displayedTower) {
		return (int) (helps.Constants.Towers.GetCost((displayedTower.getTowerType())) * 0.3f);
	}

	private void drawDisplayedTowerRange(Graphics g) {
		g.setColor(Color.white);
		g.drawOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2,
				displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2,
				(int) displayedTower.getRange() * 2, (int) displayedTower.getRange() * 2);
	}

	private void drawDisplayedTowerBorder(Graphics g) {
		g.setColor(Color.ORANGE);
		g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);

	}

	public void displayTower(Tower t) {
		displayedTower = t;
	}

	private void initButtons() {
		bMenu = new MyButton("Menu", 4, 644, 100, 30);
		bPause = new MyButton("Pause", 4, 676, 100, 30);
		towerButtons = new MyButton[3];
		int w = 50;
		int h = 50;
		int xStart = 112;
		int yStart = 644;
		int xOffset = 60;

		for (int i = 0; i < towerButtons.length; i++) {
			towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);
		}

		sellTowerButton = new MyButton("Sell", 420, 702, 80, 22);
		upgradeTowerButton = new MyButton("Upgrade", 545, 702, 80, 22);
	}

	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bPause.draw(g);

		for (MyButton b : towerButtons) {
			g.setColor(Color.gray);
			g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
			g.drawImage(playing.getTowerManager().getTowerImages()[b.getId()], b.getX(), b.getY(), b.getWidth(),
					b.getHeight(), null);

			drawButtonFeedback(g, b);
		}
	}

	private void sellTowerButtonClicked() {
		playing.removeTower(displayedTower);
		gold += helps.Constants.Towers.GetCost(displayedTower.getTowerType()) / 2;
		int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
		upgradeCost *= 0.5f;
		gold += upgradeCost;
		displayedTower = null;

	}

	private void upgradeTowerButtonClicked() {
		playing.upgradeTower(displayedTower);
		gold -= getUpgradeAmount(displayedTower);

	}
	
	private void togglePause() {

		playing.setGamePaused(!playing.isGamePaused());
		
		if (playing.isGamePaused()) {
			bPause.setText("Unpause");
		} else {
			bPause.setText("Pause");
		}
		
	}

	public void mouseClicked(int x, int y) {

		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
		}else if (bPause.getBounds().contains(x, y)) {
			togglePause();
		} else {

			if (displayedTower != null) {
				if (sellTowerButton.getBounds().contains(x, y)) {
					sellTowerButtonClicked();
					return;

				} else if (upgradeTowerButton.getBounds().contains(x, y) && displayedTower.getTier() < 3
						&& gold >= getUpgradeAmount(displayedTower)) {
					upgradeTowerButtonClicked();
					return;
				}
			}

			for (MyButton b : towerButtons) {
				if (b.getBounds().contains(x, y)) {
					if (!isEnoughGoldForTower(b.getId())) {
						return;
					}
					selectedTower = new Tower(0, 0, -1, b.getId());
					playing.setSelectedTower(selectedTower);
					return;
				}
			}
		}
	}

	private boolean isEnoughGoldForTower(int towerType) {
		return gold >= helps.Constants.Towers.GetCost(towerType);
	}

	public void mouseMoved(int x, int y) {

		// set false
		bMenu.setMouseOver(false);
		bPause.setMouseOver(false);
		showTowerCost = false;

		sellTowerButton.setMouseOver(false);
		upgradeTowerButton.setMouseOver(false);

		for (MyButton b : towerButtons) {
			b.setMouseOver(false);
		}

		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMouseOver(true);
		} else if (bPause.getBounds().contains(x, y)) {
			bPause.setMouseOver(true);
		} else {

			if (displayedTower != null) {
				if (sellTowerButton.getBounds().contains(x, y)) {
					sellTowerButton.setMouseOver(true);
					return;
				} else if (upgradeTowerButton.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
					upgradeTowerButton.setMouseOver(true);
					return;
				}
			}

			for (MyButton b : towerButtons) {
				if (b.getBounds().contains(x, y)) {
					b.setMouseOver(true);
					showTowerCost = true;
					towerCostType = b.getId();
					return;
				}
			}
		}
	}

	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y)) {
			bMenu.setMousePressed(true);
		} else if (bPause.getBounds().contains(x, y)) {
			bPause.setMousePressed(true);
		} else {

			if (displayedTower != null) {
				if (sellTowerButton.getBounds().contains(x, y)) {
					sellTowerButton.setMousePressed(true);
				}
				if (upgradeTowerButton.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
					upgradeTowerButton.setMousePressed(true);
				}
			}

			for (MyButton b : towerButtons) {
				if (b.getBounds().contains(x, y)) {
					b.setMousePressed(true);
				}
			}
		}
	}

	public void mouseReleased(int x, int y) {
		resetButtons();
		bMenu.resetBooleans();
		bPause.resetBooleans();
		sellTowerButton.resetBooleans();
		upgradeTowerButton.resetBooleans();

		for (MyButton b : towerButtons) {
			b.resetBooleans();
		}
	}

	private void resetButtons() {

	}

	public void payForTower(int towerType) {
		this.gold -= helps.Constants.Towers.GetCost(towerType);

	}

	public void addGold(int getReward) {
		this.gold += getReward;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void removeOneLife() {
		lives--;
		if (lives <= 0) {
			SetGameState(GAME_OVER);
		}
	}

	public void totalReset() {
		lives = 25;
		towerCostType = 0;
		showTowerCost = false;
		gold = 100;
		selectedTower = null;
		displayedTower = null;
		
	}

}
