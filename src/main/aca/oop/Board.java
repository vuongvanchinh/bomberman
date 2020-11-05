package aca.oop;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aca.oop.entities.Entity;
import aca.oop.entities.Message;
import aca.oop.graphics.IRender;
import aca.oop.graphics.Screen;
import aca.oop.input.Keyboard;
//import aca.oop.level.Level;

public class Board implements IRender {
   public int width;
   public int height;
   protected Game game;
   protected Keyboard input;
   protected Screen screen;

   public List<Message> messages = new ArrayList<Message>();

   public Entity[] entities;
   private int screenToShow = -1; //1:endgame, 2:changelevel, 3:paused
   
   private int time = Game.TIME;
	private int points = Game.POINTS;
   private int lives = Game.LIVES;
   
   public Board(Game game, Keyboard input, Screen screen) {
      this.game = game;
      this.input = input;
      this.screen = screen;
      changeLevel(1);
   }

   //protected Level level;


   public void update() {
      // TO DO Auto-generated method stub

   }

   public void render(Screen screen) {
      // TO DO Auto-generated method stub

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

   private void changeLevel(int level) {
      this.time = Game.TIME;
      screenToShow = 2;
      game.resetScreenDelay();
      game.pause();

      try {
         
      } catch (Exception e) {
         //TO DO: handle exception
      }
   }

   public void changeLevelByCode(String str) {
      
   }


   @SuppressWarnings("static-access")
   private void resetProperties() {
      this.points = Game.POINTS;
      this.lives = Game.LIVES;

      game.playerSpeed = 1.0;
      game.bombRadius = 1;
      game.bombRate = 1;

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
      return false;
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
      //changeLevel(level.getLevel());
   }

   public void nextLevel(int level) {
      //changeLevel(level.getLevel() + 1);
   }
   
   
}
