package aca.oop.entities.mob.enemy;

import java.awt.Color;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.Entity;
import aca.oop.entities.Message;
import aca.oop.entities.mob.Player;
import aca.oop.entities.bomb.DirectionalExplosion;
import aca.oop.entities.mob.Mob;
import aca.oop.entities.mob.enemy.ai.AI;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;
import aca.oop.level.Audio;
import aca.oop.level.Coordinates;

public abstract class Enemy extends Mob {
   protected int points;
   protected double speed;
   protected AI ai;

   protected double MAX_STEPS;
   protected double rest;
   protected double steps;

   protected int finalAnimation = 30;
   protected Sprite deadSprite;
   
   protected Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
      super(x, y, board);
      this.points = points;
      this.speed = speed;
      calculateMoveValues();
      timeAfter = 20;
      deadSprite = dead;
   }

   public void calculateMoveValues() {
      this.MAX_STEPS = Game.TILES_SIZE / this.speed;
      rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
      steps = MAX_STEPS;
   }

   public void update() {
      animate();

      if (!this.alive) {
         afterKill();
      } else {
         calculateMove();
      }
   }

   public void render(Screen screen) {
      if (alive) {
         chooseSprite();
      } else {
         if (timeAfter > 0) {
            this.sprite = deadSprite;
            animate = 0;
         } else {
            this.sprite = Sprite.movingSprite(
                  Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
         }
      }
      screen.renderEntity((int)this.x, (int)this.y, this);
   }

   protected abstract void chooseSprite();

   @Override
   protected void calculateMove() {
      int xa = 0;
      int ya = 0;
      if (steps <= 0) {
         direction = ai.calculateDirection();
         steps = MAX_STEPS;
      }
      if(direction == 0) {ya--;}
      if(direction == 1) {xa++;}
		if(direction == 2) {ya++;}
		if(direction == 3) {xa--;}
		
      if (canMove(xa , ya)) {
         steps -= 1 + rest;
         move(xa * speed, ya * speed);
         moving = true;
      } else {
         steps = 0;
         moving = false;
      }

   }

   @Override
   protected void move(double xa, double ya) {
      if (!alive) return;
      this.x += xa;
      this.y += ya;
   }

   @Override
   public void kill() {
      if (!alive) return;

      alive = false;
      board.addPoints(points);
      if (board.getPoints() > board.getRecord(false)) {
         board.createRecord(board.getPoints(), false);
      }
      Message msg = new Message("+" + points, getXMessage(), getYMessage(), 4, Color.WHITE, 14);
      board.addMessage(msg);
      Audio.playEntityDie();
   }

   @Override
   protected void afterKill() {
     if (timeAfter > 0) {
        timeAfter--;
     } else {
         if (finalAnimation > 0) --finalAnimation;
         else {
            remove();
         }
     }

   }

   @Override
   protected boolean canMove(double x, double y) {
      // int xt = Coordinates.pixelToTile(this.x + x);
      // int yt = Coordinates.pixelToTile(this.y + y);
      // int aroundX = Coordinates.pixelToTile(this.x + x + this.sprite.SIZE - 0.1) - xt;
      // int aroundY = Coordinates.pixelToTile(this.y + y + this.sprite.SIZE - 0.1) - yt;
      // for (int i = 0; i <= aroundX; i++) {
      //    for (int j = 0; j <= aroundY; j++) {
      //       Entity a = board.getEntity(xt + i, yt + j, this);
      //       if (!a.collide(this)) {
      //          return false;
      //       }
      //    }
      // }
      // return true;
      
      double xr = this.x;
      double yr =  this.y; //subtract y to get more accurate results
		
		//the thing is, subract 15 to 16 (sprite size), so if we add 1 tile we get the next pixel tile with this
		//we avoid the shaking inside tiles with the help of steps
		if( this.direction == 0) {
			yr +=  this.sprite.getSize() -1;
			xr +=  this.sprite.getSize()/2; 
		} 
		if( this.direction == 1) {
         yr +=  this.sprite.getSize() / 2;
         xr += 1;
      }
		if( this.direction == 2) {
         xr +=  this.sprite.getSize() / 2;
         yr += 1;
      }
		if( this.direction == 3) {
         xr +=  this.sprite.getSize() -1;
         yr +=  this.sprite.getSize()/2;
      }		
		int xx = Coordinates.pixelToTile(xr) +(int)x;
		int yy = Coordinates.pixelToTile(yr) +(int)y;
		Entity a =  this.board.getEntity(xx, yy, this); //entity of the position we want to go
		return a.collide(this);
   }

   
   public boolean collide(Entity e) {
      if (e instanceof Enemy) {
         //backForward();
         return false;
         
      }

      if (e instanceof DirectionalExplosion) {
         kill();
         return false;
      }

      if (e instanceof Player) {
         if (this.checkCollision(e)) {
            ((Player) e).kill();
         }
         return true;
      }

      return true;
   }

   public void addSpeed(double offset) {
      this.speed += offset;
   }

   // public void backForward() {
   //    this.x = Coordinates.pixelToTile(this.x + 2) * 16;
   //    this.y = Coordinates.pixelToTile(this.y + 2) * 16;
   //    steps = MAX_STEPS;
   // }
}
