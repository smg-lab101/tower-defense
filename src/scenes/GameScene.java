package scenes;

import java.awt.image.BufferedImage;

import main.Game;

public class GameScene {
	
	public Game game;
	protected int animationIndex;
	protected int ANIMATION_SPEED = 60;
	protected int tick;
	
	public GameScene(Game game) {
		this.game=game;
	}

	public Game getGame() {
		return game;
		
	}
	
	protected boolean isAnimation(int spriteId) {
		return game.getTileManager().isSpriteAnimation(spriteId);
	}
	
	protected void updateTick() {
		tick++;
		if(tick >= ANIMATION_SPEED) {
			tick = 0;
			animationIndex++;
			if(animationIndex >= 4)
				animationIndex = 0;
		}
		
	}
	
	protected BufferedImage getSprite(int spriteID) {
		return game.getTileManager().getSprite(spriteID);
	}
	
	protected BufferedImage getSprite(int spriteID, int animationIndex) {
		return game.getTileManager().getAniSprite(spriteID, animationIndex);
	}
}
