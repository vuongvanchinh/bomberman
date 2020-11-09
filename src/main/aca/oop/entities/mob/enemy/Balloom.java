package aca.oop.entities.mob.enemy;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.Entity;
import aca.oop.graphics.Sprite;

public class Balloom extends Enemy {
   public Balloom(int x, int y, Board board) {
      super(x, y, board, Sprite.balloom_dead, Game.getPlayerSpeed() / 2, 100);
      this.sprite = Sprite.balloom_left1;
      // direction =
   }

   @Override
   protected void chooseSprite() {
      switch (direction) {
         case 0:
         case 1:
            this.sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3,
                  animate, 60);
            break;
         case 3:
            this.sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate,
                  60);
            break;
         default:
            break;
      }

   }

   @Override
   protected boolean canMove(double x, double y) {
     
      return false;
   }

   @Override
   public boolean collide(Entity e) {
     
      return false;
   }
   
}
