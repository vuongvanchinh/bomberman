package aca.oop;

import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import aca.oop.entities.Entity;
import aca.oop.entities.Message;
import aca.oop.entities.Player;
import aca.oop.entities.bomb.Bomb;
import aca.oop.entities.bomb.Explosion;
import aca.oop.entities.mob.Mob;
import aca.oop.entities.tile.item.Item;
import aca.oop.exceptions.LoadLevelException;
import aca.oop.graphics.IRender;
import aca.oop.graphics.Screen;
import aca.oop.input.Keyboard;
import aca.oop.level.Level;
import aca.oop.level.FileLevel;

public class Board implements IRender {
   public int width;
   public int height;
   protected Game game;
   protected Level level;
   protected Keyboard input;
   protected Screen screen;

   public List<Mob> mobs = new ArrayList<Mob>();
	protected List<Bomb> bombs = new ArrayList<Bomb>();
   public List<Message> messages = new ArrayList<Message>();
   public Entity[] entities;

   private int screenToShow = -1; //1:endgame, 2:changeLevel, 3:paused
   
   private int record = 0;

   private int time = Game.TIME;
	private int points = Game.POINTS;
   private int lives = Game.LIVES;
   
   public Board(Game game, Keyboard input, Screen screen) {
      this.game = game;
      this.input = input;
      this.screen = screen;
      this.record = getRecord(true);
      changeLevel(1); 
   }

  /**
   * *****************
   * Render and update
   * ****************
   */
   public void update() {
      if(game.isPaused()) {
         return;
      }
      updateEntities();
      updateMobs();
      updateBombs();
      updateMessages();
      detectEndGame();
      input.update();

      int i = 0;
      while (i < mobs.size()) {
         Entity temp = mobs.get(i);
         if (temp.isRemoved()) {
            mobs.remove(i);
         } else {
            i++;
         }
      }
   }

   public void render(Screen screen) {
      if (game.isPaused()) {
         return;
      }
      // only render the visible a part of screen.
      int x0 = Screen.xOffset >> 4;
      int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE; 
      int y0 = Screen.yOffset >> 4;
      //render one tile plus to fix black margins
      int y1 = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE;
      
      for (int y = y0; y < y1; y++) {
         for (int x = x0; x < x1; x++) {
            entities[x + y * level.getWidth()].render(screen);
         }
      }
      renderBombs(screen);
      renderMobs(screen);
   }
/*
 |******************
 | change level
 |******************
 */
   public void newGame() {
      resetProperties();
      changeLevel(1);
   }

   public void changeLevel(int level) {
      this.time = Game.TIME;
      screenToShow = 2;
      game.resetScreenDelay();
      game.pause();
      game.pause();
      mobs.clear();
      bombs.clear();
      messages.clear();

      try {//res\levels\Level1.txt
         this.level = new FileLevel("res/levels/Level" + level + ".txt", this);
         System.out.println("test 1");
         this.entities = new Entity[this.level.getHeight() * this.level.getWidth()];
         System.out.println("test2");
         this.level.createEntities();
         System.out.println("test3");
      } catch (LoadLevelException e) {
         endGame(); // no more level;
         
      }
   }

   public void changeLevelByCode(String str) {
   
   }

   public boolean isItemUsed(int x, int y, int level) {
      Item item;
      for (int i = 0; i < Player.items.size(); i++) {
         item = Player.items.get(i);
         if (item.getX() == x && item.getY() == y && level == item.getLevel()) {
            return true;
         }
      }
      return false;
   }

   @SuppressWarnings("static-access")
   private void resetProperties() {
      this.points = Game.POINTS;
      this.lives = Game.LIVES;

      game.resetPlayerSpeed();
      game.resetBombRadius();
      game.resetBombRate();
   }

   /*
   |**************
   | detection.
   |**************
   */
   protected void detectEndGame() {
      if (time <= 0) {
         restartLevel();
      }
   }

   public void endGame() {
      this.screenToShow = 1;
      this.game.resetScreenDelay();
      this.game.pause();
   }

   public boolean detectNoEnemies() {
      int total = 0;
      for (int i = 0; i < mobs.size(); i++) {
         if (!(mobs.get(i) instanceof Player)) {
            ++total;
         }
      }
      return (total == 0) && bombs.isEmpty();
   }

   public void gamePause() {
      game.resetScreenDelay();
      if (screenToShow <= 0) {
         screenToShow = 3;
      }
      game.pause();
   }

   public void gameResume() {
      game.resetScreenDelay();
      screenToShow = -1;
      game.run();
   }


   public void restartLevel() {
      changeLevel(level.getLevel());
   }

   public void nextLevel() {
      changeLevel(level.getLevel() + 1);
   }

   /*
      Screen.
   */

   public void drawScreen(Graphics g) {
      switch (screenToShow) {
         case 1:
            screen.drawEndGame(g, points/*, level.getActualCode()*/);
            break;
         case 2:
            screen.drawChangeLevel(g, level.getLevel());
            break;
         case 3:
            screen.drawPaused(g);
            break;
         default:
            break;
      }
   }
   
   /*
   |****************************
   | getter setter.
   |****************************
   */

  public Entity getEntity(double x, double y, Mob m) {
      Entity res = null;
   
      res = getExplosionAt((int)x, (int)y);
      if( res != null) return res;
      
      res = getBombAt(x, y);
      if( res != null) return res;
      
      res = getMobAtExcluding((int)x, (int)y, m);
      if( res != null) return res;
      
      res = getEntityAt((int)x, (int)y);
      
      return res;
  
   }

   /**
    * c.
    * @param initialize whether need to read from file.
    * @return
    */
   public int getRecord(boolean initialize) {
      if (initialize) {
         try {
            File recordFile = new File("res/record.txt");
            Scanner sc = new Scanner(recordFile);
            this.record = sc.nextInt();
            sc.close();
         } catch (Exception e) {
            System.out.println("Lỗi đọc file record.txt.");
            e.printStackTrace();
         }
      }
      return this.record;
   }

   /**
    * 
    * @param record record want to create;
    * @param toFile whether export to file?
    */
   public void createRecord(int record, boolean toFile) {
      this.record = record;
      if (toFile) {
         try {
            FileWriter myWriter = new FileWriter("res/record.txt");
            myWriter.write(record + "");
            myWriter.close();
            System.out.println("Successfully wrote to the record file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
      }
   }

   public int getWidth() {
      return this.level.getWidth();
   }

   public void setWidth(int width) {
      this.level.setWidth(width);
   }

   public int getHeight() {
      return this.level.getHeight();
   }

   public void setHeight(int height) {
      this.level.setHeight(height);
   }

   public Game getGame() {
      return this.game;
   }

   public void setGame(Game game) {
      this.game = game;
   }

   public Keyboard getInput() {
      return this.input;
   }

   public void setInput(Keyboard input) {
      this.input = input;
   }

   public Screen getScreen() {
      return this.screen;
   }

   public void setScreen(Screen screen) {
      this.screen = screen;
   }

   public List<Message> getMessages() {
      return this.messages;
   }

   public void setMessages(List<Message> messages) {
      this.messages = messages;
   }

   public Entity[] getEntities() {
      return this.entities;
   }

   public void setEntities(Entity[] entities) {
      this.entities = entities;
   }

   public int getShow() {
      return this.screenToShow;
   }

   public void setShow(int screenToShow) {
      this.screenToShow = screenToShow;
   }

   public int getTime() {
      return this.time;
   }

   public void setTime(int time) {
      this.time = time;
   }

   public int getPoints() {
      return this.points;
   }

   public void addPoints(int points) {
      this.points += points;
   }

   public int subtractTime() {
      if (game.isPaused()) {
         return this.time;
      } else {
         return this.time--;
      }
   }

   public int getLives() {
      return this.lives;
   }

   public void addLives(int lives) {
      this.lives += lives;
   }
	
	public List<Bomb> getBombs() {
		return bombs;
	}
	
	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs = bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		
		return null;
	}
	
	public Mob getMobAt(double x, double y) {
		Iterator<Mob> itr = mobs.iterator();
		
		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur.getXTile() == x && cur.getYTile() == y)
				return cur;
		}
		
		return null;
	}
	
	public Player getPlayer() {
		Iterator<Mob> itr = mobs.iterator();
		
		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur instanceof Player)
				return (Player) cur;
		}
		
		return null;
	}
	
	public Mob getMobAtExcluding(int x, int y, Mob a) {
		Iterator<Mob> itr = mobs.iterator();
		
		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
		}
		
		return null;
	}
	
	public Explosion getExplosionAt(int x, int y) {
		Iterator<Bomb> bs = bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			
			Explosion e = b.explosionAt(x, y);
			if(e != null) {
				return e;
			}
				
		}
		
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return entities[(int)x + (int)y * level.getWidth()];
	}
	
   /*
	|--------------------------------------------------------------------------
	| Adds and Removes
	|--------------------------------------------------------------------------
	 */
	public void addEntities(int pos, Entity e) {
      entities[pos] = e;
      //System.out.println("pos:" + pos);
	}
	
	public void addMob(Mob e) {
		mobs.add(e);
	}
	
	public void addBomb(Bomb e) {
		bombs.add(e);
	}
	
	public void addMessage(Message e) {
		messages.add(e);
	}
	
	/*
	|--------------------------------------------------------------------------
	| Renders
	|--------------------------------------------------------------------------
	 */
	protected void renderEntities(Screen screen) {
		for (int i = 0; i < entities.length; i++) {
			entities[i].render(screen);
		}
	}
	
	protected void renderMobs(Screen screen) {
		Iterator<Mob> itr = mobs.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	protected void renderBombs(Screen screen) {
		Iterator<Bomb> itr = bombs.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	public void renderMessages(Graphics g) {
		Message m;
		for (int i = 0; i < messages.size(); i++) {
			m = messages.get(i);
			g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
			g.setColor(m.getColor());
			g.drawString(m.getMessage(), (int)m.getX() - Screen.xOffset  * Game.SCALE, (int)m.getY());
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Updates
	|--------------------------------------------------------------------------
	 */
	protected void updateEntities() {
		if(game.isPaused() ) return;
		for (int i = 0; i < entities.length; i++) {
			entities[i].update();
		}
	}
	
	protected void updateMobs() {
		if(game.isPaused() ) return;
		Iterator<Mob> itr = mobs.iterator();
		
		while(itr.hasNext() && !game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() {
		if(game.isPaused() ) return;
		Iterator<Bomb> itr = bombs.iterator();
		
		while(itr.hasNext())
			itr.next().update();
	}
	
	protected void updateMessages() {
		if( game.isPaused() ) return;
		Message m;
		int left = 0;
      int i = 0;
      while (i < messages.size()) {
         m = messages.get(i);
         left = m.getDuration();
         if (left > 0) {
            m.setDuration(--left);
            m.setY(m.getY() - 0.2);
            i++;
         } else {
            messages.remove(i);
         }
      }
      
	}

   
}
