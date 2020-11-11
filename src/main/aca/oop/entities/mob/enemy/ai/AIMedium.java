package aca.oop.entities.mob.enemy.ai;

import aca.oop.entities.Player;
import aca.oop.entities.mob.enemy.Enemy;

public class AIMedium extends AI {
   
   Player player;
   Enemy e;

   public AIMedium(Player player, Enemy e) {
      this.player = player;
      this.e = e;
   }

   /** 
    * direction.
    *  __0__
    * |     |
    * 3     1
    * |__2__|
    */
   @Override
   public int calculateDirection() {
      if (player == null) {
         return random.nextInt(4);
      }
      int vertical = random.nextInt(2);

      if (vertical == 1) {
         int v = calculateRowDirection();
         if (v != -1) { return v;}
         else return calculateColDirection();
      }  else {
         int h = calculateColDirection();
         if (h != -1) {return h;}
         else {
            return calculateRowDirection();
         }
      }
   }

   private int calculateColDirection() {
      if (player.getXTile() < e.getXTile()) {
         return 3;
      } else if (player.getXTile() > e.getXTile()) {
         return 1;
      }
      return -1;
   }

   private int calculateRowDirection() {
      if (player.getYTile() > e.getYTile()) {
         return 0;
      } else if (player.getYTile() < e.getYTile()) {
         return 2;
      }
      return -1;
   }
   
}
