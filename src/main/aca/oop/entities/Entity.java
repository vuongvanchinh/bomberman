package aca.oop.entities;

import aca.oop.graphics.Sprite;
import aca.oop.graphics.Screen;
import aca.oop.level.Coordinates;

public abstract class Entity {
   protected double x, y;
	protected boolean removed = false;
	protected Sprite sprite;
	
	
	public abstract void update();
	
	
	public abstract void render(Screen screen);
	
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
		return Coordinates.pixelToTile(x + sprite.SIZE / 2); //plus half block for precision
	}
	
	public int getYTile() {
		return Coordinates.pixelToTile(y - sprite.SIZE / 2); //plus half block
	}
}
