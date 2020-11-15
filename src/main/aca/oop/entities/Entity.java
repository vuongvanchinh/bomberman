package aca.oop.entities;

import aca.oop.graphics.Sprite;
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
	
	public void shifter(double x, double y) {
		this.x += x;
		this.y += y;
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
