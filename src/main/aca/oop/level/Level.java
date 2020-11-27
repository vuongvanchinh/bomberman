package aca.oop.level;

import aca.oop.Board;
import aca.oop.exceptions.LoadLevelException;

public abstract class Level implements ILevel {

   protected int width;
   protected int height;
   protected int thLevel;
   protected String[] lineTiles;
   protected Board board;

   protected Level(String path, Board board) throws LoadLevelException {
      loadLevel(path);
      this.board = board;
   }
   
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

}
