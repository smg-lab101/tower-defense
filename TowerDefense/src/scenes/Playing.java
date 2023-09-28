package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enemies.Enemy;
import helps.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import objects.Tower;
import userinterface.ActionBar;
import static helps.Constants.Tiles.GRASS_TILE;

public class Playing extends GameScene implements SceneMethods {

	private int[][] lvl;
	private ActionBar actionBar;
	private int mouseX, mouseY;
	private EnemyManager enemyManager;
	private TowerManager towerManager;
	private ProjectileManager projectileManager;
	private WaveManager waveManager;
	private PathPoint start, end;
	private Tower selectedTower;
	private int goldTick;
	private boolean gamePaused;

	public Playing(Game game) {
		super(game);

		LoadDefaultLevel();

		actionBar = new ActionBar(0, 640, 640, 100, this);

		enemyManager = new EnemyManager(this, start, end);
 		towerManager = new TowerManager(this);
		projectileManager = new ProjectileManager(this);
		waveManager = new WaveManager(this);

	}

	private void LoadDefaultLevel() {
		lvl = LoadSave.getLevelDate("new_level");
		ArrayList<PathPoint> points = LoadSave.getLevelPathPoints("new_level");
		start = points.get(0);
		end = points.get(1);
	}

	public void setLevel(int[][] lvl) {
		this.lvl = lvl;
	}

	public void update() {

		if (!gamePaused ) {
			updateTick();
			waveManager.update();

			// GoldTick
			goldTick++;
			if (goldTick % (60 * 3) == 0) {
				actionBar.addGold(1);
			}

			if (areAllEnemiesDead()) {
				if (areThereMoreWaves()) {
					waveManager.startWaveTimer();
					if (waveTimerOver()) {
						waveManager.increaseWaveIndex();
						enemyManager.getEnemies().clear();
						waveManager.resetEnemyIndex();
					}
				}
//				else {
//					
//				}
			}

			if (isTimeForNewEnemy()) {
				spawnEnemy();
			}

			enemyManager.update();
			towerManager.update();
			projectileManager.update();
		}
	}

	private boolean waveTimerOver() {
		return waveManager.isWaveTimerOver();
	}

	private boolean areThereMoreWaves() {

		return waveManager.areThereMoreWaves();

	}

	private boolean areAllEnemiesDead() {

		if (waveManager.areThereMoreEnemiesInWave()) {
			return false;
		}
		for (Enemy e : enemyManager.getEnemies()) {
			if (e.isAlive())
				return false;
		}
		return true;

	}

	private void spawnEnemy() {
		enemyManager.spawnEnemy(waveManager.getNextEnemy());
	}

	private boolean isTimeForNewEnemy() {
		if (waveManager.isTimeForNewEnemy()) {
			if (waveManager.areThereMoreEnemiesInWave()) {
				return true;
			}
		}
		return false;
	}

	public void setSelectedTower(Tower selectedTower) {
		this.selectedTower = selectedTower;
	}

	@Override
	public void render(Graphics g) {
		drawLevel(g);
		actionBar.draw(g);
		enemyManager.draw(g);
		towerManager.draw(g);
		projectileManager.draw(g);

		drawSelectedTower(g);
		drawHighlight(g);

		drawWaveInfos(g);
	}

	private void drawWaveInfos(Graphics g) {

	}

	private void drawHighlight(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(mouseX, mouseY, 32, 32);

	}

	private void drawSelectedTower(Graphics g) {
		if (selectedTower != null) {
			g.drawImage(towerManager.getTowerImages()[selectedTower.getTowerType()], mouseX, mouseY, null);
		}
	}

	private void drawLevel(Graphics g) {
		for (int y = 0; y < lvl.length; y++) {
			for (int x = 0; x < lvl[y].length; x++) {
				int id = lvl[y][x];
				if (isAnimation(id)) {
					g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
				} else {
					g.drawImage(getSprite(id), x * 32, y * 32, null);
				}
			}
		}
	}

	public int getTileType(int x, int y) {
		int xCord = x / 32;
		int yCord = y / 32;

		if (xCord < 0 || xCord > 19) {
			return 0;
			// 0 == WATER_TILE
		}
		if (yCord < 0 || yCord > 19) {
			return 0;
			// 0 == WATER_TILE
		}
		int id = lvl[y / 32][x / 32];
		return game.getTileManager().getTile(id).getTileType();
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (y >= 640)
			actionBar.mouseClicked(x, y);
		else {
			if (selectedTower != null) {
				if (isTileGrass(mouseX, mouseY)) {
					if (getTowerAt(mouseX, mouseY) == null) {
						towerManager.addTower(selectedTower, mouseX, mouseY);

						deductCost(selectedTower.getTowerType());
						selectedTower = null;
					}
				}
			} else {
				// get tower if exists
				Tower t = getTowerAt(mouseX, mouseY);

				// if there is no tower, nothing will be shown == a selected tower becomes
				// unselected
				actionBar.displayTower(t);
			}
		}
	}

	private void deductCost(int towerType) {
		actionBar.payForTower(towerType);

	}

	public void removeTower(Tower displayedTower) {
		towerManager.removeTower(displayedTower);

	}

	public void upgradeTower(Tower displayedTower) {
		towerManager.upgradeTower(displayedTower);
	}

	private Tower getTowerAt(int x, int y) {
		return towerManager.getTowerAt(x, y);
	}

	private boolean isTileGrass(int x, int y) {
		int id = lvl[y / 32][x / 32];
		int tileType = game.getTileManager().getTile(id).getTileType();
		return tileType == GRASS_TILE;
	}

	public void shootEnemy(Tower t, Enemy e) {
		projectileManager.newProjectile(t, e);

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			selectedTower = null;
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y >= 640)
			actionBar.mouseMoved(x, y);
		else {
			// clean coordinates for snap
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y >= 640) {
			actionBar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		actionBar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {

	}

	public void rewardPlayer(int enemyType) {
		actionBar.addGold(helps.Constants.Enemies.GetReward(enemyType));
	}

	public TowerManager getTowerManager() {
		return towerManager;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public WaveManager getWaveManager() {
		return waveManager;
	}
	
	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}
	
	public boolean isGamePaused() {
		return gamePaused;
	}

	public void removeOneLife() {
		actionBar.removeOneLife();
		
	}

	public void totalReset() {
		actionBar.totalReset();
		
		//manager
		enemyManager.totalReset();
		towerManager.totalReset();
		projectileManager.totalReset();
		waveManager.totalReset();
		
		mouseX = 0;
		mouseY = 0;
		selectedTower = null;
		goldTick = 0;
		gamePaused = false;
		
	}

}
