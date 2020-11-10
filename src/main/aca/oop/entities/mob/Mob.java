package aca.oop.entities.mob;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.AnimatedEntity;

public abstract class Mob extends AnimatedEntity {
   protected Board board;
   protected int direction = -1;
   protected boolean alive = true;
   protected boolean moving = false;
   public int timeAfter = 80;

   protected Mob(int x, int y, Board board) {
      this.x = x;
      this.y = y;
      this.board = board;
   }
   
	protected abstract void calculateMove();
	
	protected abstract void move(double xa, double ya);
	
	public abstract void kill();
	
	protected abstract void afterKill();
	
	protected abstract boolean canMove(double x, double y);

   public Board getBoard() {
      return this.board;
   }

   public void setBoard(Board board) {
      this.board = board;
   }

   public int getDirection() {
      return this.direction;
   }

   public void setDirection(int direction) {
      this.direction = direction;
   }

   public boolean isAlive() {
      return this.alive;
   }

   public boolean getAlive() {
      return this.alive;
   }

   public void setAlive(boolean alive) {
      this.alive = alive;
   }

   public boolean isMoving() {
      return this.moving;
   }

   public boolean getMoving() {
      return this.moving;
   }

   public void setMoving(boolean moving) {
      this.moving = moving;
   }

   public int getTimeAfter() {
      return this.timeAfter;
   }

   public void setTimeAfter(int timeAfter) {
      this.timeAfter = timeAfter;
   }

   protected double getXMessage() {
      return (x * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
   }

   protected double getYMessage() {
      return (y * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
   }
}
