package aca.oop.entities.tile;

import aca.oop.entities.Entity;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;

public abstract class Tile extends Entity {

   protected Tile(int x, int y, Sprite sprite) {
      this.x = x;
      this.y = y;
      this.sprite = sprite;
   }

   public boolean collide(Entity e) {
      return false;
   }
   
   public void render(Screen screen) {
      screen.renderEntity((int)this.x, (int)this.y, this);
   }

   
   public void update(){}

}
