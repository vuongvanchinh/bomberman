package aca.oop.entities;

import java.awt.Color;
import aca.oop.graphics.Screen;

public class Message extends Entity {
   protected String content;
   protected int duration;
   protected Color color;
   protected int size;

   public Message(String content, double x, double y, int duration, Color color, int size) {
      this.x = x;
      this.y = y;
      this.content = content;
      this.color = color;
      this.duration = duration * 60;
      this.size = size;
   }


   public String getMessage() {
      return this.content;
   }

   public void setMessage(String content) {
      this.content = content;
   }

   public int getDuration() {
      return this.duration;
   }

   public void setDuration(int duration) {
      this.duration = duration;
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public int getSize() {
      return this.size;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void setSize(int size) {
      this.size = size;
   }

   
   public void update() {
   }


   public void render(Screen screen) {
   }


   public boolean collide(Entity e) {
      return true;
   }

}
