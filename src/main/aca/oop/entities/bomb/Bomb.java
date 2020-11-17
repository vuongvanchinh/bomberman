package aca.oop.entities.bomb;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.AnimatedEntity;
import aca.oop.entities.Entity;
import aca.oop.entities.mob.Player;
import aca.oop.entities.tile.Grass;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;
import aca.oop.level.Audio;
import aca.oop.level.Coordinates;
//import aca.oop.entities.bomb.DirectionalExplosion;
import aca.oop.entities.mob.Mob;

public class Bomb extends AnimatedEntity {
   protected double timeToExplode = 120;
   public int timeAfter = 20;
   protected Board board;
   protected boolean allowedToPassThru = true;
   protected DirectionalExplosion[] dExplosions = null;
   protected boolean exploded = false;

   public Bomb(int x, int y, Board board) {
      this.x = x;
      this.y = y;
      this.board = board;
      sprite = Sprite.bomb;
   }

   public void update() {
      if (allowedToPassThru && !board.getPlayer().checkCollision(new Grass((int)x, (int)y, Sprite.grass))) {
         allowedToPassThru = false;
      }
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
		
		// int xt = (int)x << 4;
		// int yt = (int)y << 4;
		
		screen.renderEntity((int)x, (int)y, this);
   }

   public void renderExplosions(Screen screen) {
      for (int i = 0; i < dExplosions.length; i++) {
         dExplosions[i].render(screen);
      }
   }

   public void updateExplosions() {
      for (int i = 0; i < dExplosions.length; i++) {
			dExplosions[i].update();
		}
   }

   public void explode() {
      timeToExplode = 0;
   }

   protected void explosion() {
      allowedToPassThru = true;
      exploded = true;
      // Mob a = board.getMobAt(Coordinates.pixelToTile(this.x), Coordinates.pixelToTile(this.y));
      // if (a != null) {
      //    a.kill();
      // }
      board.killMobAround(this);

      dExplosions = new DirectionalExplosion[4];

      for (int i = 0; i < dExplosions.length; i++) {
         dExplosions[i] = new DirectionalExplosion((int)this.x, (int)this.y, i, Game.getBombRadius(), board);
      }
      Audio.playBombExplode();
   }

   public Explosion explosionAt(int x, int y) {
		if(!exploded) return null;
		
		for (int i = 0; i < dExplosions.length; i++) {
			if(dExplosions[i] == null) return null;
			Explosion e = dExplosions[i].explosionAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}

   @Override
   public boolean collide(Entity e) {
      if (e instanceof Player) {
         return allowedToPassThru;
      }

      if (e instanceof DirectionalExplosion) {
         explode();
         return true;
      }
      
      return false;
   }
}
