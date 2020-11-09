package aca.oop.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import aca.oop.Game;

public class GamePanel extends JPanel {
   
   /**
    *
    */
   private static final long serialVersionUID = 1L;
   
   private Game game;

   public GamePanel (Frame frame) {
      setLayout(new BorderLayout());
      setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

      try {
         game = new Game(frame);
         add(game);
         game.setVisible(true);

      } catch (Exception e) {
         e.printStackTrace();
         System.exit(0);
      }
      setVisible(true);
      setFocusable(true);
   }

   public Game getGame() {
      return game;
   }
}
