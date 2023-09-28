package objects;

import static helps.Constants.Towers.*;

public class Tower {
	
	private int x, y, id, towerType, cooldownTick, damage;
	private float range, cooldown;
	private int tier;
	
	public Tower(int x, int y, int id, int towerType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.towerType = towerType;
		tier = 1;
		
		setDefaultDamage();
		setDefaultRange();
		setDefaultCooldown();
	
	}
	
	public void update() {
		cooldownTick++;
	}
	
	public void upgradeTower() {
		this.tier++;
		
		switch(towerType) {
		case ARCHER:
			damage += 2;
			range += 20;
			cooldown -= 1;
		case CANNON:
			damage += 5;
			range += 20;
			cooldown -= 15;
		case WIZARD:
			range += 20;
			cooldown -= 10;
		
		}
	}

	public boolean isCooldownOver() {
		return cooldownTick >= cooldown;
	}
	
	public void resetCooldown() {
		cooldownTick = 0;
		
	}

	private void setDefaultDamage() {
		damage = helps.Constants.Towers.GetDefaultDamage(towerType);		
	}

	private void setDefaultRange() {
		range = helps.Constants.Towers.GetDefaultRange(towerType);		
	}

	private void setDefaultCooldown() {
		cooldown = helps.Constants.Towers.GetDefaultCooldown(towerType);	
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTowerType() {
		return towerType;
	}

	public void setTowerType(int towerType) {
		this.towerType = towerType;
	}

	public int getDamage() {
		return damage;
	}

	public float getRange() {
		return range;
	}

	public float getCooldown() {
		return cooldown;
	}
	
	public int getTier() {
		return tier;
	}
		
}
