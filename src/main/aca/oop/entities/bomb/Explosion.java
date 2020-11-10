package aca.oop.entities.bomb;

import aca.oop.Board;
import aca.oop.entities.Entity;

import aca.oop.entities.mob.Mob;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;

public class Explosion extends Entity {
   protected boolean last = false;
   protected Board board;
   protected Sprite sprite1;
   protected Sprite sprite2;
   
   public Explosion(int x, int y, int direction, boolean last, Board board) {
      this.x = x;
      this.y = y;
      this.last = last;
      this.board = board;
      
      switch(direction) {
         case 0:
            if (!last) {
               sprite = Sprite.explosion_vertical2;
            } else {
               sprite = Sprite.explosion_vertical_top_last2;
            }
            break;
         case 1:
            if (!last) {
               sprite = Sprite.explosion_horizontal2;
            } else {
               sprite = Sprite.explosion_horizontal_right_last2;
            }
            break;

         case 2: 
            if (!last) {
               sprite = Sprite.explosion_vertical2;
            } else {
               sprite = Sprite.explosion_vertical_down_last2;
            }
            break;
         case 3:
            if (!last) {
               sprite = Sprite.explosion_horizontal2;
            } else {
               sprite = Sprite.explosion_horizontal_left_last2;
            }
            break;
         default:
            break;
      }
   }


 
   public void update() {
      

   }

 
   public void render(Screen screen) {
      int xt = (int)this.x << 4;
		int yt = (int)this.y << 4;
		
		screen.renderEntity(xt, yt , this);
   }

   @Override
   public boolean collide(Entity e) {
      if(e instanceof Mob) {
         System.out.println("kill");
			((Mob)e).kill();
		}
		return true;
   }

}
