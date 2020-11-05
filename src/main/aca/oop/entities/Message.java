package aca.oop.entities;

import java.awt.Color;
import aca.oop.graphics.Screen;

public class Message extends Entity {
   protected String message;
   protected int duration;
   protected Color color;
   protected int size;

   public Message(String message, double x, double y, int duration, Color color, int size) {
      this.x = x;
      this.y = y;
      this.message = message;
      this.color = color;
      this.duration = duration;
      this.size = size;
   }


   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
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

   public void setSize(int size) {
      this.size = size;
   }

   @Override
   public void update() {
      // TO DO Auto-generated method stub

   }

   @Override
   public void render(Screen screen) {
      // TO DO Auto-generated method stub

   }

   @Override
   public boolean collide(Entity e) {
      // TO DO Auto-generated method stub
      return true;
   }

}
