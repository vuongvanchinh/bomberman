package aca.oop.entities.mob.enemy;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.mob.enemy.ai.AIMedium;
import aca.oop.graphics.Sprite;

public class Oneal extends Enemy {

   public Oneal(int x, int y, Board board) {
      super(x, y, board, Sprite.oneal_dead, Game.getBombRadius(), 200);
      this.sprite = Sprite.oneal_left1;
      ai = new AIMedium(board.getPlayer(), this);
      direction = ai.calculateDirection();
   }

   @Override
   protected void chooseSprite() {
      switch (direction) {
         case 0:
         case 1:
            if (moving) {
               this.sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate,
                     60);
            }
            break;
         case 2:
         case 3:
            if (moving) {
               this.sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate,
                     60);
            } else {
               this.sprite = Sprite.oneal_left1;
            }
            break;
         default:
            break;
      }

   }

   @Override
   protected boolean canMove(double x, double y) {

      return false;
   }

}
