package aca.oop.entities.bomb;

import aca.oop.Board;
import aca.oop.entities.Entity;
import aca.oop.entities.mob.Mob;
import aca.oop.graphics.Screen;

public class DirectionalExplosion extends Entity {

   protected Board board;
   protected int direction;
   protected int xOrigin;
   protected int yOrigin;
   private int radius;
   protected Explosion[] explosions;


   public DirectionalExplosion(int x, int y, int direction, int radius, Board board) {
      this.board = board;
      this.direction = direction;
      this.xOrigin = x;
      this.yOrigin = y;
      this.radius = radius;
      this.x = x;
      this.y = y;
      explosions = new Explosion[calculatePermitDistance()]; // 
      createExplosions();
   }

   private void createExplosions() {
      boolean last = false;
      int x = (int)this.x;
      int y = (int)this.y;

      for (int i = 0; i <explosions.length; i ++) {
          last = (i == explosions.length - 1);//? true : false;
          switch(direction) {
            case 0: y--; break;
            case 1: x++; break;
            case 2: y++; break;
            case 3: x--; break;
            default: 
               break;
          }
          explosions[i] = new Explosion(x, y, direction, last, board);
         
      }
   }

   private int calculatePermitDistance() {
      int lRadius = 0;
      int x = (int)this.x;
      int y = (int)this.y;
      
      while(lRadius < this.radius) {
         // switch(direction) {
         //    case 0: y--; break;
         //    case 1: x++; break;
         //    case 2: y++; break;
         //    case 3: x--; break;
         //    default:
         //       break;
         // }
         if(direction == 0) {y--;}
			if(direction == 1) {x++;}
			if(direction == 2) {y++;}
         if(direction == 3) {x--;}
         
         Entity a = board.getEntity(x, y, null);

         if (a instanceof Mob) ++lRadius; //explosion has to be below the mob

         if (!a.collide(this)) { // cannot pass thru
            break;
         }
         ++lRadius;
      }
      return lRadius;
   }

   public Explosion explosionAt(int x, int y) {
      for (int i = 0; i < explosions.length; i++) {
         if (explosions[i].getX() == x && explosions[i].getY() == y) {
            return explosions[i];
         }
      }
      return null;
   }

   public void update() {
   }


   public void render(Screen screen) {
      for (int i = 0; i < explosions.length; i++) {
         explosions[i].render(screen);
      }

   }

   @Override
   public boolean collide(Entity e) {
      
      return true;
   }
   

   public int getRadius() {
      return this.radius;
   }

   public void setRadius(int radius) {
      this.radius = radius;
   }

}
