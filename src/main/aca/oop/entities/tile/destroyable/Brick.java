package aca.oop.entities.tile.destroyable;

import aca.oop.entities.Entity;
import aca.oop.entities.bomb.DirectionalExplosion;
import aca.oop.entities.mob.enemy.Kondoria;
import aca.oop.graphics.Screen;
//import aca.oop.level.Coordinates;
import aca.oop.graphics.Sprite;

public class Brick extends DestroyableTile {
   
   public Brick(int x, int y, Sprite sprite) {
      super(x, y, sprite);
   }

   // @Override
   // public void update() {
   //    super.update();
   // }

   @Override
   public void render(Screen screen) {
      // int x = Coordinates.tileToPixel(this.x);
      // int y = Coordinates.tileToPixel(this.y);

      if (destroyed) {
         this.sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
         screen.renderEntityWithBelowSprite((int)this.x, (int)this.y, this, this.belowSprite);
      } else {
         screen.renderEntity((int)this.x, (int)this.y, this);
      }
   }

   @Override
   public boolean collide(Entity e) {
      if (e   instanceof DirectionalExplosion) {
         destroy();
      } else if (e instanceof Kondoria) {
         return true;
      }

      return false;
   }
}
