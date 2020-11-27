package aca.oop.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[120]; //120 is enough to this game
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean space;
	public boolean pause;
	public boolean enter;
	public boolean turnAudio;

	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];
		pause = keys[KeyEvent.VK_P];
		enter = keys[KeyEvent.VK_ENTER];
		turnAudio = keys[KeyEvent.VK_O];
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		//System.out.println("you pressed: " + e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		try {
			keys[e.getKeyCode()] = false;
		} catch (Exception ea) {
			System.out.println("");
		}
	}
}
