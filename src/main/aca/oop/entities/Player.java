package aca.oop.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.bomb.Bomb;
import aca.oop.entities.bomb.DirectionalExplosion;
import aca.oop.entities.mob.Mob;
import aca.oop.entities.mob.enemy.Enemy;
import aca.oop.entities.tile.item.Item;
import aca.oop.graphics.Screen;
import aca.oop.graphics.Sprite;
import aca.oop.input.Keyboard;
import aca.oop.level.Audio;
import aca.oop.level.Coordinates;

public class Player extends Mob {

   private List<Bomb> bombs;
   private int timer = 5;

   public List<Bomb> getBombs() {
      return this.bombs;
   }

   public void setBombs(List<Bomb> bombs) {
      this.bombs = bombs;
   }

   public int getTimer() {
      return this.timer;
   }

   public void setTimer(int timer) {
      this.timer = timer;
   }

   public Keyboard getInput() {
      return this.input;
   }

   public void setInput(Keyboard input) {
      this.input = input;
   }

   public int getTimeBetweenPutBombs() {
      return this.timeBetweenPutBombs;
   }

   public void setTimeBetweenPutBombs(int timeBetweenPutBombs) {
      this.timeBetweenPutBombs = timeBetweenPutBombs;
   }
   protected Keyboard input;
   protected int timeBetweenPutBombs = 0;

   public static List<Item> items = new ArrayList<Item>();

   public Player(int x, int y, Board board) {
      super(x, y, board);
      bombs = board.getBombs();
      input = board.getInput();
      sprite = Sprite.player_right;
   }

   public void update() {
      clearBombs();
      if (!alive) {
         afterKill();
         return;
      }
      if (timeBetweenPutBombs < -7500) {
         timeBetweenPutBombs = 0;
      } else {
         timeBetweenPutBombs--;
      }
      animate();
      calculateMove();
      detectPlaceBomb();
   }

   public void render(Screen screen) {
      calculateXOffset();
      if (alive) {
         chooseSprite();
      } else {
         sprite = Sprite.player_dead1;
      }
      screen.renderEntity((int)x, (int)y - sprite.SIZE, this);
   }

   public void calculateXOffset() {
      int xScroll = Screen.calculateXOffset(board, this);
      //System.out.println("x scroll<player.calculateXOffset> : " + xScroll);
      Screen.setOffset(xScroll, 0);
   }

   /**
    * mod unique. 
    */
   
   private void detectPlaceBomb()  {
      if (input.space && Game.getBombRate() > 0 && timeBetweenPutBombs < 0) {
         int xt = Coordinates.pixelToTile(x + sprite.getSize() / 2);
         //subtract half player height and minus 1 y position
         int yt = Coordinates.pixelToTile((y + sprite.getSize() / 2) - sprite.getSize());
         placeBomb(xt, yt);
         Game.addBombRate(-1);
         timeBetweenPutBombs = 30;
      } 
   }

   protected void placeBomb(int x, int y) {
      Bomb b = new Bomb(x, y, board);
      board.addBomb(b);
      Audio.playPlaceBomb();
   }

   private void clearBombs() {
      Iterator<Bomb> bs = bombs.iterator();
      Bomb b;
      while (bs.hasNext()) {
         b = bs.next();
         if (b.isRemoved()) {
            bs.remove();
            Game.addBombRate(1);
         }
      }
   }

   @Override
   public void kill() {
     
      if (!alive) {
         return;
      }
      System.out.println("Toa do xy:" + this.x + " " + this.y + "player.java");
      alive = false;
      board.addLives(-1);
      Message msg = new Message("- 1 LIVE", getXMessage(), getXMessage(), 4, Color.WHITE, 18);
      board.addMessage(msg);
      Audio.bomberDie();
   }

   @Override
   protected void afterKill() {
      if (timeAfter > 0) --timeAfter;
      else {
         if (bombs.isEmpty()) {
            if (board.getLives() == 0) {
               board.endGame();
            } else {
               board.restartLevel();
            }
         }
      }

   }

   /**
    * c < 4: collision detection for each corner of the player;
    * divide with tiles size to pass to tile coordinate.
    * these values are the best from multiple tests.
    */
   @Override
   protected boolean canMove(double x, double y) {
      for (int c = 0; c < 4; c++) { // four corner of the player.
         double xt = ((this.x + x) + c % 2 * 11) / Game.TILES_SIZE;
         double yt = ((this.y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE;
         //System.out.println(String.format("xt:%.1f, yt: %.1f", xt, yt));
         Entity a = board.getEntity(xt, yt, this);
         
         if (a instanceof Enemy || a instanceof DirectionalExplosion) {
            this.shifter(x, y);
         }

         if (!a.collide(this)) {
            return false;
         }
      }
      return true;
   }
   
   @Override
   protected void calculateMove() {
      int xa = 0;
      int ya = 0;
      if(input.up) {ya--;}
		if(input.down) {ya++;}
		if(input.left) {xa--;}
		if(input.right) {xa++;}
      //System.out.println("calculate move in player.java" + (xa + ya));
      if (xa != 0 || ya != 0) {
         move(xa * Game.getPlayerSpeed(), ya * Game.getPlayerSpeed());
         moving = true;
      } else {
         moving = false;
      }
      timer--;
   }

   @Override
   public void move(double xa, double ya) {
      //System.out.println(String.format("xa: %.1f, ya:%.1f",xa, ya));
      if (xa > 0) {direction = 1;}
      if (xa < 0) {direction = 3;}
      if (ya > 0) {direction = 2;}
      if (ya < 0) {direction = 0;}

      boolean vertical = canMove(0, ya);
      boolean horizontal = canMove(xa, 0);
      if (vertical) {
         this.y += ya;
      }

      if (horizontal) {
         this.x += xa;
      }

      if (timer <= 0 && (horizontal || vertical)) {
         Audio.playMenuMove();
         timer = 35;
      }
   }

   @Override
   public boolean collide(Entity e) {
      if (e instanceof DirectionalExplosion) {
         kill();
         return false;
      }

      if (e instanceof Enemy) {
         kill();
         return true;
      }

      return true;
   }

   public void addItem(Item item) {
      if (item.isRemoved()) { return;}
      items.add(item);
      item.setValues();
   }

   public  void clearUsedItem() {
      Item item;
      int i = 0;
      while (i < items.size()) {
         item = items.get(i);
         if (!item.isActive()) {
            items.remove(i);
         } else {
            i++;
         }
      }
   }

   public void removeItems() {
      items.clear();
   }

   /**void 
    * sprite.
    */

   private void chooseSprite() {
      switch(direction) {
         case 0:
            sprite = Sprite.player_up;
            if (moving) {
               //System.out.println("up sprite");
               sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
            }
            break;
         case 2:
            sprite = Sprite.player_down;
            if (moving) {
               sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
            }
            break;
         case 3:
            sprite = Sprite.player_left;
            if (moving) {
               sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
            }
            break;
         case 1:
         default:
            sprite = Sprite.player_right;
            if (moving) {
               sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
            }
            break;
      }
   }
}
