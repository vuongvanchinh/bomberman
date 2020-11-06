package aca.oop.entities.mob.enemy;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.graphics.Sprite;

public class Doll extends Enemy {

   public Doll(int x, int y, Board board) {
      super(x, y, board, Sprite.doll_dead,Game.getPlayerSpeed() / 4, 1000);
      this.sprite = Sprite.doll_right1;
   
   }
   @Override
   protected void chooseSprite() {
      switch(direction) {
         case 0:
         case 1:
            if (moving) {
               this.sprite = Sprite.movingSprite(
                  Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 60);
            } else {
               this.sprite = Sprite.doll_left1;
            }
            break;
         case 2:
         case 3:
            if (moving) {
               this.sprite = Sprite.movingSprite(
                  Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 60);
            } else {
               this.sprite = Sprite.doll_left1;
            }
            break;
         default:
            break;
      }

   }
}
