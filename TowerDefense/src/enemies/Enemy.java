package enemies;

import java.awt.Rectangle;

import managers.EnemyManager;

import static helps.Constants.Direction.*;

public abstract class Enemy {
	
	protected EnemyManager enemyManager;
	protected float x, y;
	protected Rectangle bounds;
	protected int health;
	protected int maxHealth;
	protected int ID;
	protected int enemyType;
	protected int lastDir;
	protected boolean alive = true;
	protected int slowTickLimit = 120;
	protected int slowTick = slowTickLimit;

	public Enemy(float x, float y, int id, int enemyType, EnemyManager enemyManager) {
		this.enemyManager = enemyManager;
		this.x = x;
		this.y = y;
		this.ID = id;
		this.enemyType = enemyType;

		bounds = new Rectangle((int) x, (int) y, 32, 32);

		lastDir = -1;
		setStartHealth();
	}
	
	private void setStartHealth() {
		health = helps.Constants.Enemies.GetStartHealth(enemyType);
		maxHealth = health;
	}
	
	public void hurt(int damage) {
		this.health -= damage;
		if(health <= 0) {
			alive = false;
			enemyManager.rewardPlayer(enemyType);
		}
	}
	
	public void slow() {
		slowTick = 0;
	}
	
	//to kill enemy when endPoint reached
	public void kill() {
		alive = false;
		health = 0;
	}

	public void move(float speed, int dir) {
		lastDir = dir;
		
		if (slowTick < slowTickLimit) {
			slowTick++;
			speed *= 0.25f;
		}
		
		switch (dir) {
		case LEFT:
			this.x -= speed;
			break;
		case UP:
			this.y -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case DOWN:
			this.y += speed;
			break;
		}
		
		updateHitbox();
	}

	private void updateHitbox() {
		bounds.x = (int) x;
		bounds.y = (int) y;
	}

	// For position fix, not general move
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public float getHealthbarFloat() {
		return health / (float) maxHealth;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getHealth() {
		return health;
	}

	public int getID() {
		return ID;
	}

	public int getEnemyType() {
		return enemyType;
	}

	public int getLastDir() {
		return lastDir;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isSlowed() {
		return slowTick < slowTickLimit;
	}

	


}
