package aca.oop.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
      addWindowListener(new java.awt.event.WindowAdapter() {
         @Override
         public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            game.pause();
            boolean quit = JOptionPane.showConfirmDialog(null, 
               "Are you sure you want to quit game?", "Quit?", 
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION; 
            if (quit){
               writeRecord();
               System.exit(0);
            } else {
               System.out.println("No quit");
               //System.exit(1);
               game.run();
            }
         }
     });

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

   public void setRecord(int record) {
      infoPane.setRecord(record);
   }
   public void writeRecord() {
      int record = game.getBoard().getRecord(false);
      game.getBoard().createRecord(record, true);

     // System.out.println("write to re");
   }

}
