package aca.oop.entities;

import aca.oop.Board;
import aca.oop.entities.mob.Mob;
import aca.oop.graphics.Screen;

public class Player extends Mob {

   protected Player(int x, int y, Board board) {
      super(x, y, board);
      
   }

   @Override
   public void update() {
      

   }

   @Override
   public void render(Screen screen) {
      

   }

   @Override
   protected void calculateMove() {
      

   }

   @Override
   protected void move(double xa, double ya) {
      

   }

   @Override
   public void kill() {
      // TO DO Auto-generated method stub

   }

   @Override
   protected void afterKill() {
      // TO DO Auto-generated method stub

   }

   @Override
   protected boolean canMove(double x, double y) {
      // TO DO Auto-generated method stub
      return false;
   }
   
}
