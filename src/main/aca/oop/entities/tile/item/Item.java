package aca.oop.entities.tile.item;

import aca.oop.entities.tile.Tile;
import aca.oop.graphics.Sprite;

public abstract class Item extends Tile {

   protected int duration = -1; // is infinite, duration in life.
   protected boolean active = false;
   protected int level;

   protected Item(int x, int y, int level, Sprite sprite) {
      super(x, y, sprite);
      this.level = level;
   }
   
   public abstract void setValues();

   public void removeLive() {
      if (duration > 0) {
         duration --;
      }
      if (duration == 0) {
         active = false;
      }
   }

   public int getDuration() {
      return this.duration;
   }

   public int getLevel() {
      return level;
   }

   public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
