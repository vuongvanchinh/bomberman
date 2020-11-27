package aca.oop.level;

import aca.oop.Game;

public class Coordinates {
   private Coordinates() {

   }
   
   public static int pixelToTile(double i) {
      return (int)(i / Game.TILES_SIZE);
   } 

   public static int tileToPixel(int i) {
      return (i * Game.TILES_SIZE);
   }

   public static int tileToPixel(double i) {
      return (int)(i * Game.TILES_SIZE);
   }
   
   public static void main(String[] args) {
      System.out.println(pixelToTile(17 + 0));
      System.out.println(pixelToTile(17 + 0 + 12));
   }
}
