package aca.oop.entities.mob.enemy;

import java.awt.Color;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.Entity;
import aca.oop.entities.Message;
import aca.oop.entities.Player;
import aca.oop.entities.bomb.DirectionalExplosion;
import aca.oop.entities.mob.Mob;
import aca.oop.entities.mob.enemy.ai.AI;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;
import aca.oop.level.Coordinates;

public abstract class Enemy extends Mob {
   protected int points;
   protected double speed;
   protected AI ai;
   protected final double  MAX_STEPS;
   protected final double rest;
   protected double steps;

   protected int finalAnimation = 30;
   protected Sprite deadSprite;
   
   protected Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
      super(x, y, board);
      this.points = points;
      this.speed = speed;
      this.MAX_STEPS = Game.TILES_SIZE / speed;
      rest = (this.MAX_STEPS - (int) this.MAX_STEPS) / this.MAX_STEPS;
      steps = MAX_STEPS;
      timeAfter = 20;
      deadSprite = dead;
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
      screen.renderEntity((int)this.x, (int)this.y - sprite.SIZE, this);
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
		
      if (canMove(xa, ya)) {
         steps -= (1 + rest);
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
      double xr = this.x;
      double yr = this.y - 16;

      if(direction == 0) { yr += sprite.getSize() - 1 ; xr += sprite.getSize()/2; } 
		if(direction == 1) {yr += sprite.getSize() / 2; xr += 1;}
		if(direction == 2) { xr += sprite.getSize() / 2; yr += 1;}
		if(direction == 3) { xr += sprite.getSize() -1; yr += sprite.getSize()/2;}
		
		int xx = Coordinates.pixelToTile(xr) +(int)x;
		int yy = Coordinates.pixelToTile(yr) +(int)y;
		
      Entity a = board.getEntity(xx, yy, this); //entity of the position we want to go
      return a.collide(this);
   }

   
   public boolean collide(Entity e) {
      if (e instanceof DirectionalExplosion) {
         kill();
         return false;
      }

      if (e instanceof Player) {
         //System.out.println("toa do enemy: " + this.x + " " + this.y + "enemy.java");
         ((Player) e).kill();
         return true;
      }
      return true;
   }
}
