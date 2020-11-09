package aca.oop.entities.tile.item;

import aca.oop.Game;
import aca.oop.entities.Entity;
import aca.oop.entities.Player;
import aca.oop.graphics.Sprite;

public class BombItem extends Item {

   public BombItem(int x, int y, int level, Sprite sprite) {
      super(x, y, level, sprite);
      
   }

   public boolean collide(Entity e) {
      if(e instanceof Player) {
			//((Player) e).addPowerup(this);
			remove();
			return true;
		}
      return false;
   }

   @Override
   public void setValues() {
      active = true;
      Game.addBombRate(1);
   }

}
