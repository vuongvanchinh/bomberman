package aca.oop.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import aca.oop.Game;

import java.awt.BorderLayout;

public class Frame extends JFrame {

   /**
    *
    */
   private static final long serialVersionUID = 1L;
   
   public GamePanel gamePane;
   private JPanel containerPane;
   private InfoPanel infoPane;
   
   private Game game;
   
   public Frame() {
      containerPane = new JPanel(new BorderLayout());
      gamePane = new GamePanel(this);
      infoPane = new InfoPanel(gamePane.getGame());
      
      containerPane.add(infoPane, BorderLayout.PAGE_START);
      containerPane.add(gamePane, BorderLayout.PAGE_END);

      game = gamePane.getGame();
      
      add(containerPane);

      setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

      game.start();
   }

   public void changeLevel(int level) {
      game.getBoard().changeLevel(level);
   }
   
   public void pauseGame() {
		game.getBoard().gamePause();
	}
	
	public void resumeGame() {
		game.getBoard().gameResume();
	}
	
	public boolean isRunning() {
		return game.isRunning();
	}
	
	public void setTime(int time) {
		infoPane.setTime(time);
	}
	
	public void setLives(int lives) {
		infoPane.setLives(lives);
	}
	
	public void setPoints(int points) {
		infoPane.setPoints(points);
	}


}
