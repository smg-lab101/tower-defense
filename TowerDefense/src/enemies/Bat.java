package enemies;

import static helps.Constants.Enemies.*;

import managers.EnemyManager;


public class Bat extends Enemy {

	public Bat(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, BAT, enemyManager);
		
	}

}