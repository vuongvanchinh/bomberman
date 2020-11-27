package aca.oop.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import aca.oop.Board;
import aca.oop.Game;
import aca.oop.entities.Entity;
import aca.oop.entities.mob.Player;

public class Screen {
   protected int width;
   protected int height;
   public int[] pixels;
   private int transparentColor = 0xffff00ff;

   public static int xOffset = 0;
   public static int yOffset = 0;

   /**
    * chinh.
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

   public void renderEntity(int xp, int yp, Entity entity) { //save entity pixels
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp; //add offset
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp; //add offset
				if(xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) break; //fix black margins
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
		if(player == null){
			System.out.println("player is null");
			return 0;
		}
		int temp = xOffset;
		
		double playerX = player.getX() / 16;
		double complement = 0.5;
		int firstBreakpoint = board.getWidth() / 4;
		int lastBreakpoint = board.getWidth() - firstBreakpoint;
		
		if(playerX > firstBreakpoint + complement && playerX < lastBreakpoint - complement) {
			temp = (int)player.getX()  - (Game.WIDTH / 2);
		}
		
		return temp;
	}

	/*Game Screen*/
	public void drawEndGame(Graphics g, int points/*, String code*/) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getRealWidth(), getRealHeight());
		
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("GAME OVER", getRealWidth(), getRealHeight(), g);

		font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.yellow);
		drawCenteredString("Score: " + points, getRealWidth(), getRealHeight() + (Game.TILES_SIZE * 2) * Game.SCALE, g);
		
		font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.GRAY);
		drawCenteredString("Press enter to start new game", getRealWidth(), getRealHeight() * 2  - (Game.TILES_SIZE * 2) * Game.SCALE, g);
	}

	public void drawChangeLevel(Graphics g, int level) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getRealWidth(), getRealHeight());
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("LEVEL " + level, getRealWidth() , getRealHeight(), g);
	}

	public void drawPaused(Graphics g) {
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.green);
		drawCenteredString("TẠM DỪNG", getRealWidth(), getRealHeight(), g);
	}

	private void drawCenteredString(String str, int w, int h, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = (w - fm.stringWidth(str)) / 2;
		int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(str, x, y);
	}

	public int getWidth() {
		return this.width;
	}

	public int getRealWidth() {
		return this.width * Game.SCALE;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getRealHeight() {
		return this.height * Game.SCALE;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
