package enemies;

import static helps.Constants.Enemies.*;

import managers.EnemyManager;


public class Orc extends Enemy {

	public Orc(float x, float y, int id, EnemyManager enemyManager) {
	 	 super(x, y, id, ORC, enemyManager);
	}
}
