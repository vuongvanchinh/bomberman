package aca.oop.entities.tile.item;

import aca.oop.Game;
import aca.oop.entities.Entity;
import aca.oop.entities.Player;
import aca.oop.graphics.Sprite;
import aca.oop.level.Audio;

public class SpeedItem extends Item {
   
   public SpeedItem(int x, int y, int level, Sprite sprite) {
      super(x, y, level, sprite);
   }

   @Override
  
   public boolean collide(Entity e) {
      if (e instanceof Player) {
       ((Player) e).addItem(this);
         remove();
         Audio.eat();
         return true;
      }
      return false;
      
   }
   @Override
   public void setValues() {
      this.active = true;
      Game.addPlayerSpeed(0.3);
   }
}
