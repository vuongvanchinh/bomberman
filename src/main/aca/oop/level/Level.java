package aca.oop.level;

import aca.oop.Board;
import aca.oop.exceptions.LoadLevelException;

public abstract class Level implements ILevel {

   protected int width;
   protected int height;
   protected int thLevel;
   protected String[] lineTiles;
   protected Board board;

   protected static String[] codes = { //TO DO: change this code system to actualy load the code from each level.txt
		"level1",
      "level2",
      "level3",
      "level4",
      "level5",
	};

   protected Level(String path, Board board) throws LoadLevelException {
      loadLevel(path);
      this.board = board;
   }

   //public abstract void loadLevel(String path) throws LoadLevelException;
   
   public abstract void createEntities();
   

   public int getWidth() {
      return this.width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public int getLevel() {
      return this.thLevel;
   }

   public void setLevel(int level) {
      this.thLevel = level;
   }

   public String getActualCode() {
      return codes[this.thLevel -1];
   } 
}
