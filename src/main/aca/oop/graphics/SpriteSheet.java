package aca.oop.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public int[] pixels;
	//C:/Users/Dell/Desktop/bomberman/res/textures/classic.png res\textures\classic.png
	public static SpriteSheet tiles = new SpriteSheet("res/textures/classic.png", 256);
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
		//System.out.println("Test sprite sheet");
	}
	
	// public static void main(String[] args) {
	// 	new SpriteSheet("res/textures/classic.png", 256);
	// }
	
	private void load() {
		try {
			File a = new File(this.path);
			BufferedImage image = ImageIO.read(a);
			//System.out.println("test Sprite sheet.");
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
