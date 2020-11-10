package aca.oop.entities.mob.enemy;

import java.awt.Color;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.Entity;
import aca.oop.entities.Message;
import aca.oop.entities.Player;
import aca.oop.entities.bomb.DirectionalExplosion;
import aca.oop.entities.mob.Mob;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;

public abstract class Enemy extends Mob {
   protected int points;
   protected double speed;

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

   protected boolean canMove(double x, double y, int i) {
     
      return false;
   }
   
   public boolean collide(Entity e) {
      if (e instanceof DirectionalExplosion) {
         kill();
         return false;
      }

      if (e instanceof Player) {
         ((Player) e).kill();
         return false;
      }
      return true;
   }
}
