package aca.oop.entities.bomb;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.AnimatedEntity;
import aca.oop.entities.Entity;
import aca.oop.entities.Player;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;
import aca.oop.level.Coordinates;
//import aca.oop.entities.bomb.DirectionalExplosion;
import aca.oop.entities.mob.Mob;

public class Bomb extends AnimatedEntity {
   protected double timeToExplode = 120;
   public int timeAfter = 20;
   protected Board board;
   protected boolean allowedToPassThru = true;
   protected DirectionalExplosion[] explosions = null;
   protected boolean exploded = false;

   public Bomb(int x, int y, Board board) {
      this.x = x;
      this.y = y;
      this.board = board;
      sprite = Sprite.bomb;
   }

   public void update() {
      if(timeToExplode > 0) {
         timeToExplode--;
      } else {
			if(!exploded) 
				explosion();
			else
				updateExplosions();
			if(timeAfter > 0) 
				timeAfter--;
			else
				remove();
		}		
		animate();
   }

   public void render(Screen screen) {
      if(exploded) {
			sprite =  Sprite.bomb_exploded2;
			renderExplosions(screen);
		} else
			 sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60);
		
		int xt = (int)x << 4;
		int yt = (int)y << 4;
		
		screen.renderEntity(xt, yt , this);
   }

   public void renderExplosions(Screen screen) {
      for (int i = 0; i < explosions.length; i++) {
         explosions[i].render(screen);
      }
   }

   public void updateExplosions() {
      for (int i = 0; i < explosions.length; i++) {
			explosions[i].update();
		}
   }

   public void explode() {
      timeToExplode = 0;
   }

   protected void explosion() {
      allowedToPassThru = true;
      exploded = true;

      Mob a = board.getMobAt(this.x, this.y);
      if (a != null) {
         a.kill();
      }

      explosions = new DirectionalExplosion[4];

      for (int i = 0; i < explosions.length; i++) {
         explosions[i] = new DirectionalExplosion((int)this.x, (int)this.y, i, Game.getBombRadius(), board);
      }
   }

   public Explosion explosionAt(int x, int y) {
		if(!exploded) return null;
		
		for (int i = 0; i < explosions.length; i++) {
			if(explosions[i] == null) return null;
			Explosion e = explosions[i].explosionAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}

   @Override
   public boolean collide(Entity e) {
      if (e instanceof Player) {
         double diffX = e.getX() - Coordinates.tileToPixel(this.getX());
         double diffY = e.getY() - Coordinates.tileToPixel(this.getY());

         if (!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffX <= 28)) {
            allowedToPassThru = false;
         }
         return allowedToPassThru;
      }

      if (e instanceof DirectionalExplosion) {
         explode();
         return true;
      }
      
      return false;
   }
}
