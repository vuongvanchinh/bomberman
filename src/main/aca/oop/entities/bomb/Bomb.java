package aca.oop.entities.bomb;

import aca.oop.Board;
import aca.oop.entities.AnimatedEntity;
import aca.oop.entities.Entity;
import aca.oop.entities.Player;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;
import aca.oop.level.Coordinates;

public class Bomb extends AnimatedEntity {
   protected double timeToExplode = 120;
   public int timeAfter = 20;
   protected Board board;
   protected boolean allowedToPassThru = true;

   protected boolean exploded = false;

   public Bomb(int x, int y, Board board) {
      this.x = x;
      this.y = y;
      this.board = board;
      sprite = Sprite.bomb;
   }

   @Override
   public void update() {

   }

   @Override
   public void render(Screen screen) {

   }

   public void renderExplosions() {

   }

   public void updateExplosions() {

   }

   public void explode() {
      timeToExplode = 0;
   }

   protected void explosions() {

   }

   @Override
   public boolean collide(Entity e) {
      if (e instanceof Player) {
         double diffX = e.getX() - Coordinates.tileToPixel(this.getX());
         double diffY = e.getY() - Coordinates.tileToPixel(this.getY());

         if (!(diffX >= -0 && diffX < 16 && diffY >= 1 && diffX <= 28)) {
            allowedToPassThru = false;
         }
         return allowedToPassThru;
      }

      if (e instanceof DirectionalExplosion) {
         explode();
         return true;
      }
      
      return false;
   }
}
