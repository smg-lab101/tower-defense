package managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helps.LoadSave;
import objects.Projectile;
import objects.Tower;
import enemies.Enemy;
import scenes.Playing;
import static helps.Constants.Towers.*;
import static helps.Constants.Projectiles.*;

public class ProjectileManager {

	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private BufferedImage[] projectile_images, explosion_images;
	private int projectileId = 0;

	public ProjectileManager(Playing playing) {
		this.playing = playing;
		importImages();
	}

	private void importImages() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		projectile_images = new BufferedImage[3];

		for (int i = 0; i < 3; i++) {
			projectile_images[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
		}
		importExplosion(atlas);
	}

	private void importExplosion(BufferedImage atlas) {
		explosion_images = new BufferedImage[7];
		for (int i = 0; i < 7; i++) {
			explosion_images[i] = atlas.getSubimage(i * 32, 2 * 32, 32, 32);
		}
	}

	public void newProjectile(Tower t, Enemy e) {
 		int type = getProjectileType(t);

		int xDist = (int) (t.getX() - e.getX());
		int yDist = (int) (t.getY() - e.getY());
		int totalDist = Math.abs(xDist) + Math.abs(yDist);

		float xPercent = (float) Math.abs(xDist) / totalDist;

		float xSpeed = xPercent * helps.Constants.Projectiles.GetSpeed(type);
		float ySpeed = helps.Constants.Projectiles.GetSpeed(type) - xSpeed;

		if (t.getX() > e.getX()) {
			xSpeed *= -1;
		}

		if (t.getY() > e.getY()) {
			ySpeed *= -1;
		}

		float rotate = 0;

		if (type == ARROW) {
			float arcValue = (float) Math.atan(yDist / (float) xDist);
			rotate = (float) Math.toDegrees(arcValue);

			if (xDist < 0) {
				rotate += 180;
			}
		}
		for (Projectile p : projectiles) {
			if(!p.isActive()) {
				if (p.getProjectileType() == type) {
					p.reuse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDamage(), rotate);
					return;
				}
			}
		}
		
		projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDamage(), rotate,
				projectileId++, type));
	}

	public void draw(Graphics g) {

		Graphics2D graphics2d = (Graphics2D) g;

		for (Projectile p : projectiles) {
			if (p.isActive()) {
				if (p.getProjectileType() == ARROW) {
					graphics2d.translate(p.getPosition().x, p.getPosition().y);
					graphics2d.rotate(Math.toRadians(p.getRotation()));
					graphics2d.drawImage(projectile_images[p.getProjectileType()], -16, -16, null);
					graphics2d.rotate(-Math.toRadians(p.getRotation()));
					graphics2d.translate(-p.getPosition().x, -p.getPosition().y);
				} else {
					graphics2d.drawImage(projectile_images[p.getProjectileType()], (int) p.getPosition().x - 16,
							(int) p.getPosition().y - 16, null);
				}
			}
		}
		drawExplosions(graphics2d);
	}

	private void drawExplosions(Graphics2D graphics2d) {

		for (Explosion e : explosions) {
			if (e.getIndex() < 7) {
				graphics2d.drawImage(explosion_images[e.getIndex()], (int) e.getPosition().x - 16,
						(int) e.getPosition().y - 16, null);
			}
		}
	}

	public void update() {
		for (Projectile p : projectiles) {
			if (p.isActive()) {
				p.move();
				if (isProjectileHittingEnemy(p)) {
					p.setActive(false);
					if (p.getProjectileType() == BOMB) {
						explosions.add(new Explosion(p.getPosition()));
						explodeOnEnemies(p);
					}
				} else if (isProjectileOutOfBounds(p)) {
					p.setActive(false);
				}
			}
		}

		for (Explosion e : explosions) {
			if (e.getIndex() < 7) {
				e.update();
			}
		}
	}

	private void explodeOnEnemies(Projectile p) {
		for (Enemy e : playing.getEnemyManager().getEnemies()) {

			if (e.isAlive()) {

				float radius = 40.0f;

				float xDist = Math.abs(p.getPosition().x - e.getX());
				float yDist = Math.abs(p.getPosition().y - e.getY());

				float realDistance = (float) Math.hypot(xDist, yDist);

				if (realDistance <= radius) {
					e.hurt(p.getDamage());
				}
			}
		}
	}
	
	private boolean isProjectileOutOfBounds(Projectile p) {
		if (p.getPosition().x >= 0 && p.getPosition().x <= 640 && 
			p.getPosition().y >= 0 && p.getPosition().y <= 800) {
			return false;
		}
		return true;
	}

	private boolean isProjectileHittingEnemy(Projectile p) {
		for (Enemy e : playing.getEnemyManager().getEnemies()) {
			if (e.isAlive()) {
				if (e.getBounds().contains(p.getPosition())) {
					e.hurt(p.getDamage());
					
					if (p.getProjectileType() == CHAINS) {
						e.slow();
					}
					return true;
				}
			}
		}
		return false;
	}

	private int getProjectileType(Tower t) {
		switch (t.getTowerType()) {
		case ARCHER:
			return ARROW;
		case CANNON:
			return BOMB;
		case WIZARD:
			return CHAINS;
		}
		return 0;
	}
	
	public void totalReset() {
		projectiles.clear();
		explosions.clear();
		projectileId = 0;
	}

	public class Explosion {

		private Point2D.Float position;
		private int explosionIndex = 0, explosionTick = 0;

		public Explosion(Point2D.Float position) {
			this.position = position;
		}

		public void update() {
			explosionTick++;
			if (explosionTick >= 12) {
				explosionTick = 0;
				explosionIndex++;
			}
		}

		public int getIndex() {
			return explosionIndex;
		}

		public Point2D.Float getPosition() {
			return position;
		}
	}

}
