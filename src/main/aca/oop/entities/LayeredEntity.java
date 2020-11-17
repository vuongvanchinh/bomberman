package aca.oop.entities;

import java.util.LinkedList;

import aca.oop.entities.tile.destroyable.DestroyableTile;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;

public class LayeredEntity extends Entity {

   protected LinkedList<Entity> entities = new LinkedList<Entity>();

   public LayeredEntity(int x, int y, Entity... entities) {
      this.x = x;
      this.y = y;
      this.sprite = Sprite.grass;
      for (int i = 0; i < entities.length; i++) {
         this.entities.add(entities[i]);

         if (i > 1 && entities[i] instanceof DestroyableTile) {
               ((DestroyableTile)entities[i]).addBelowSprite(entities[i-1].getSprite());
         }
      }
   }

   public void update() {
      clearRemoved();
      getTopEntity().update();

   }

   public void render(Screen screen) {
      getTopEntity().render(screen);
   }

   @Override
   public boolean collide(Entity e) {
      return getTopEntity().collide(e);
   }

   public Entity getTopEntity() {
      return this.entities.getLast();
   }

   private void clearRemoved() {
      Entity top = getTopEntity();
      if (top.isRemoved()) {
         this.entities.removeLast();
      }
   }

   public void addBeforeTop(Entity e) {
      int size = entities.size();
      if (size > 0) {
         this.entities.add(size -1, e);
      }
   }
}
