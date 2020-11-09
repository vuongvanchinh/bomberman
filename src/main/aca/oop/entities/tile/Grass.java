package aca.oop.entities.tile;

import aca.oop.entities.Entity;
import aca.oop.graphics.Sprite;

public class Grass extends Tile {

   public Grass(int x, int y, Sprite sprite) {
      super(x, y, sprite);
   }

   @Override
   public boolean collide(Entity e) {
      return true;
   }
}
