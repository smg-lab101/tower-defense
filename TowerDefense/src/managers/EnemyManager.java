package managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.*;
import helps.LoadSave;
import objects.PathPoint;
import scenes.Playing;
import static helps.Constants.Direction.*;
import static helps.Constants.Tiles.*;
import static helps.Constants.Enemies.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[] enemyImages;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private final int SPRITE_SIZE = 32;
	private PathPoint start, end;
	private int healthbarWidth = 20;
	private BufferedImage slowEffectImage;

	public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
		this.playing = playing;
		enemyImages = new BufferedImage[4];
		this.start = start;
		this.end = end;

		loadEnemyImages();
		loadEffectImage();
	}

	private void loadEffectImage() {
		slowEffectImage = LoadSave.getSpriteAtlas().getSubimage(32 * 9, 32 * 2, 32, 32);
	}

	private void loadEnemyImages() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();

		for (int i = 0; i < 4; i++) {
			enemyImages[i] = atlas.getSubimage(i * 32, 32, 32, 32);
		}
	}

	public void update() {

		for (Enemy e : enemies) {
			if (e.isAlive()) {
				updateEnemyMove(e);
			}
		}
	}

	private void updateEnemyMove(Enemy e) {

		if (e.getLastDir() == -1)
			setNewDirectionAndMove(e);

		int newX = (int) (e.getX() + getSpeedXAndWidth(e.getLastDir(), e.getEnemyType()));
		int newY = (int) (e.getY() + getSpeedYAndHeight(e.getLastDir(), e.getEnemyType()));

		if (getTileType(newX, newY) == ROAD_TILE) {
			e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
		} else if (isAtEnd(e)) {
			e.kill();
			playing.removeOneLife();
		} else {
			setNewDirectionAndMove(e);
		}
	}

	private void setNewDirectionAndMove(Enemy e) {
		int dir = e.getLastDir();

		// move into current tile 100%
		int xCord = (int) (e.getX() / 32);
		int yCord = (int) (e.getY() / 32);

		fixEnemyOffsetTile(e, dir, xCord, yCord);

		if (isAtEnd(e)) {
			return;
		}

		if (dir == LEFT || dir == RIGHT) {
			int newY = (int) (e.getY() + getSpeedYAndHeight(UP, e.getEnemyType()));
			if (getTileType((int) e.getX(), newY) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType()), UP);
			else
				e.move(GetSpeed(e.getEnemyType()), DOWN);
		} else {
			int newX = (int) (e.getX() + getSpeedXAndWidth(RIGHT, e.getEnemyType()));
			if (getTileType(newX, (int) e.getY()) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType()), RIGHT);
			else
				e.move(GetSpeed(e.getEnemyType()), LEFT);
		}
	}

	private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
		switch (dir) {
		case RIGHT:
			if (xCord < 19)
				xCord++;
			break;
		case DOWN:
			if (yCord < 19)
				yCord++;
			break;
		}
		e.setPosition(xCord * 32, yCord * 32);
	}

	private boolean isAtEnd(Enemy e) {
		if (e.getX() == end.getxCord() * 32) {
			if (e.getY() == end.getyCord() * 32) {
				return true;
			}
		}
		return false;
	}

	private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}

	private float getSpeedXAndWidth(int dir, int enemyType) {
		if (dir == LEFT)
			return -GetSpeed(enemyType);
		else if (dir == RIGHT)
			return GetSpeed(enemyType) + SPRITE_SIZE;

		return 0;
	}

	private float getSpeedYAndHeight(int dir, int enemyType) {
		if (dir == UP)
			return -GetSpeed(enemyType);
		else if (dir == DOWN)
			return GetSpeed(enemyType) + SPRITE_SIZE;

		return 0;
	}

	public void spawnEnemy(int nextEnemy) {
		addEnemy(nextEnemy);
	}

	public void addEnemy(int enemyType) {

		int x = start.getxCord() * 32;
		int y = start.getyCord() * 32;

		switch (enemyType) {
		case ORC:
			enemies.add(new Orc(x, y, 0, this));
			break;
		case BAT:
			enemies.add(new Bat(x, y, 0, this));
			break;
		case KNIGHT:
			enemies.add(new Knight(x, y, 0, this));
			break;
		case WOLF:
			enemies.add(new Wolf(x, y, 0, this));
			break;
		}
	}

	public void draw(Graphics g) {

		ArrayList<Enemy> enemyListCopy = new ArrayList<>();
		enemyListCopy = enemies;
		for (Enemy e : enemyListCopy) {

//		for (Enemy e : enemies) {
			if (e.isAlive()) {
				drawEnemy(e, g);
				drawHealthbar(e, g);
				drawEffects(e, g);
			}
		}
	}

	private void drawEffects(Enemy e, Graphics g) {
		if (e.isSlowed()) {
			g.drawImage(slowEffectImage, (int) e.getX(), (int) e.getY(), null);
		}
	}

	private void drawHealthbar(Enemy e, Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) e.getX() + 16 - (getNewBarWidth(e) / 2), (int) e.getY() - 10, getNewBarWidth(e), 3);
	}

	private int getNewBarWidth(Enemy e) {
		return (int) (healthbarWidth * e.getHealthbarFloat());
	}

	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemyImages[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public int getAmountOfAliveEnemies() {
		int size = 0;
		for (Enemy e : enemies) {
			if (e.isAlive()) {
				size++;
			}
		}
		return size;
	}

	public void rewardPlayer(int enemyType) {
		playing.rewardPlayer(enemyType);
	}

	public void totalReset() {
		enemies.clear();
	}

}
