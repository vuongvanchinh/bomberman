package aca.oop.graphics;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.Entity;
import aca.oop.entities.Player;

public class Screen {
   protected int width;
   protected int height;
   public int[] pixels;
   private int transparentColor = 0xffff00ff;

   public static int xOffset = 0;
   public static int yOffset = 0;

   /**
    * 
    * @param width
    * @param height
    */
   public Screen(int width, int height) {
      this.width = width;
      this.height = height;
      pixels = new int[width * height];
   }

   public void clear() {
      for (int i = 0; i < pixels.length; i++) {
         pixels[i] = 0;
      }
   }

   public void renderEntity(int xp, int yp, Entity entity) { // save entity pixels
		xp = xOffset;
		yp = yOffset;
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp; //add offset
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp; //add offset
				if(xa < entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) break; //fix black margins
				if(xa < 0) xa = 0; //start at 0 from left
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if(color != transparentColor) pixels[xa + ya * width] = color;
			}
		}
   }
   
   public void renderEntityWithBelowSprite(int xp, int yp, Entity entity, Sprite below) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp;
				if(xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) break; //fix black margins
				if(xa < 0) xa = 0;
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if(color != transparentColor) 
					pixels[xa + ya * width] = color;
				else
					pixels[xa + ya * width] = below.getPixel(x + y * below.getSize());
			}
		}
   }
   
   public static void setOffset(int xO, int yO) {
		xOffset = xO;
		yOffset = yO;
   }
   
   public static int calculateXOffset(Board board, Player player) {
		if(player == null) return 0;
		int temp = xOffset;
		
		double playerX = player.getX() / 16;
		double complement = 0.5;
		int firstBreakpoint = board.getWidth() / 4;
		int lastBreakpoint = board.getWidth() - firstBreakpoint;
		
		if( playerX > firstBreakpoint + complement && playerX < lastBreakpoint - complement) {
			temp = (int)player.getX()  - (Game.WIDTH / 2);
		}
		
		return temp;
	}
   
}
