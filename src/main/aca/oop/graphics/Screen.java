package aca.oop.graphics;

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
   

}
