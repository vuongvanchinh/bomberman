package aca.oop.entities;

import aca.oop.graphics.Sprite;
import aca.oop.entities.tile.Grass;
import aca.oop.entities.tile.Wall;
import aca.oop.graphics.IRender;
import aca.oop.level.Coordinates;

public abstract class Entity implements IRender {
	protected double x;
	protected double y;
	protected boolean removed = false;
	protected Sprite sprite;
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	
	public abstract boolean collide(Entity e);

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public int getXTile() {
		return Coordinates.pixelToTile(x + 2);
	}
	
	public int getXRelateTile() {
		return Coordinates.pixelToTile(x + sprite.SIZE / 2);
	}

	public int getYRelateTile() {
		return Coordinates.pixelToTile(y + sprite.SIZE / 2);
	}

	public int getYTile() {
		return Coordinates.pixelToTile(y + 2);
	}

	public void shifter(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public boolean checkCollision(Entity e) {
		boolean horizontal;
		boolean vertical;
		if (e.x <= this.x) {
			horizontal = (this.x - e.x) < e.sprite.getRealWidth();
		} else {
			horizontal = (e.x - this.x) < this.sprite.getRealWidth();
		}

		if (e.y <= this.y) {
			vertical = (this.y - e.y) < e.sprite.getRealHeight();
		} else {
			vertical = (e.y - this.y) < this.sprite.getRealHeight();
		}
		return horizontal && vertical;
	}

	public boolean checkCollision(Entity e, int precision) {
		boolean horizontal;
		boolean vertical;
		if (e.x <= this.x) {
			horizontal = (this.x - e.x) < e.sprite.getRealWidth() - precision;
		} else {
			horizontal = (e.x - this.x) < this.sprite.getRealWidth() - precision;
		}

		if (e.y <= this.y) {
			vertical = (this.y - e.y) < e.sprite.getRealHeight() - precision;
		} else {
			vertical = (e.y - this.y) < this.sprite.getRealHeight() - precision;
		}
		return horizontal && vertical;
	}

	

	/**
	 * test.
	 * @param args
	 */
	public static void main(String[] args) {
		Entity t1 = new Grass(10, 10, Sprite.grass);
		Entity t2 = new Wall(0, 15, Sprite.wall);
		System.out.println(t1.checkCollision(t2)); // true
		System.out.println(t2.checkCollision(t1)); // true
	}
}
