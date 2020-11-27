package aca.oop.entities.mob.enemy;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.mob.enemy.ai.AIHighest;
import aca.oop.graphics.Sprite;

public class Minvo extends Enemy {

   public Minvo(int x, int y, Board board) {
      super(x, y, board, Sprite.minvo_dead, Game.getPlayerSpeed(), 800);
      this.sprite = Sprite.minvo_right1;
      ai = new AIHighest(board.getPlayer(), this);
      direction = ai.calculateDirection();
   }

   @Override
   protected void chooseSprite() {
      switch (direction) {
         case 0:
         case 1:
            if (moving) {
               this.sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animate,
                     60);
            }
            break;
         case 2:
         case 3:
            if (moving) {
               this.sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animate,
                     60);
            } else {
               this.sprite = Sprite.minvo_left1;
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
