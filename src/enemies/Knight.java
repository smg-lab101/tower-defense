package enemies;

import static helps.Constants.Enemies.*;

import managers.EnemyManager;


public class Knight extends Enemy {

	public Knight(float x, float y, int id, EnemyManager enemyManager) {
		super(x, y, id, KNIGHT, enemyManager);
	}

}