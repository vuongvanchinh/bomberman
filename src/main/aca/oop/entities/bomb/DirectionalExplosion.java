package aca.oop.entities.bomb;

import aca.oop.Board;
import aca.oop.entities.Entity;
import aca.oop.entities.mob.Mob;
import aca.oop.graphics.Screen;
import aca.oop.level.Coordinates;

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
      int xt = Coordinates.pixelToTile(this.x);
      int yt= Coordinates.pixelToTile(this.y);
      for (int i = 0; i <explosions.length; i ++) {
          last = (i == explosions.length - 1);//? true : false;
          switch(direction) {
            case 0: yt--; break;
            case 1: xt++; break;
            case 2: yt++; break;
            case 3: xt--; break;
            default: 
               break;
          }
          explosions[i] = new Explosion(Coordinates.tileToPixel(xt), Coordinates.tileToPixel(yt), direction, last, board);
      }
   }

   private int calculatePermitDistance() {
      int lRadius = 0;
      int xt = Coordinates.pixelToTile(this.x);
      int yt = Coordinates.pixelToTile(this.y);
      while(lRadius < this.radius) {
         if(direction == 0) {yt--;}
			if(direction == 1) {xt++;}
			if(direction == 2) {yt++;}
         if(direction == 3) {xt--;}
         
         
         Entity a = board.getEntity(xt, yt, null);
         //System.out.println(a.getClass() + " " + xt + " " + yt);
         if (a instanceof Mob) lRadius++; //explosion has to be below the mob

         if (!a.collide(this)) { // cannot pass thru
            break;
         }
         lRadius++;
      }
      return lRadius;
   }

   public Explosion explosionAt(int x, int y) {
      for (int i = 0; i < explosions.length; i++) {
         if (explosions[i].getXTile() == x && explosions[i].getYTile() == y) {
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

   // @Override
   // public boolean checkCollision(Entity e) {
   //    for (int i = 0; i < this.explosions.length; i++) {
   //       if (explosions[i].checkCollision(e)) {return true;}
   //    }
   //    return false;   
   // }
}
