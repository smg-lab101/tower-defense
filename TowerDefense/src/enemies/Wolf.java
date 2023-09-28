package enemies;

import static helps.Constants.Enemies.*;

import managers.EnemyManager;


public class Wolf extends Enemy {

	public Wolf(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, WOLF, enemyManager);
	}

}