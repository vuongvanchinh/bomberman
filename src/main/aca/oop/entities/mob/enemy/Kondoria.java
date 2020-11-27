package aca.oop.entities.mob.enemy;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.mob.enemy.ai.AIHigher;
import aca.oop.graphics.Sprite;

public class Kondoria extends Enemy {

   public Kondoria(int x, int y, Board board) {
      super(x, y, board, Sprite.kondoria_dead, Game.getPlayerSpeed() / 2, 1000);
      this.sprite = Sprite.kondoria_right1;
      ai = new AIHigher(board.getPlayer(), this);
      direction = ai.calculateDirection();
   }

   @Override
   protected void chooseSprite() {
      switch (direction) {
         case 0:
         case 1:
            if (moving) {
               this.sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3,
                     animate, 60);
            } else {
               this.sprite = Sprite.kondoria_left1;
            }
            break;
         case 2:
         case 3:
            if (moving) {
               this.sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3,
                     animate, 60);
            } else {
               this.sprite = Sprite.kondoria_left1;
            }
            break;
         default:
            break;
      }

   }

   // @Override
   // protected boolean canMove(double x, double y) {
      
   //    return false;
   // }

}
