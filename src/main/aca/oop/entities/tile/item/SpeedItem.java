package aca.oop.entities.tile.item;

import aca.oop.Game;
import aca.oop.entities.Entity;
import aca.oop.entities.Player;
import aca.oop.graphics.Sprite;

public class SpeedItem extends Item {
   
   public SpeedItem(int x, int y, int level, Sprite sprite) {
      super(x, y, level, sprite);
   }

   @Override
  
   public boolean collide(Entity e) {
      if (e instanceof Player) {
       //((Player) e).addPowerup(this);
         remove();
         return true;
      }
      return false;
      
   }
   @Override
   public void setValues() {
      this.active = true;
      Game.addPlayerSpeed(0.1);
   }
}
