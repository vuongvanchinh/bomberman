package aca.oop;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import aca.oop.graphics.Screen;
import aca.oop.gui.Frame;
import aca.oop.input.Keyboard;
import aca.oop.level.Audio;

public class Game extends Canvas {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * |-------------------------------------------------------------------------- |
	 * Options & Configs
	 * |--------------------------------------------------------------------------
	 */
	public static final double VERSION = 1.9;
	
	public static final int TILES_SIZE = 16;
	public static final int	WIDTH = TILES_SIZE * (31 / 2); //minus one to ajust the window,
	public static final int HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;
	
	public static final String TITLE = "Bomberman " + VERSION;
	
	//initial configs
	private static final int BOMB_RATE = 1;
	private static final int BOMB_RADIUS = 1;
	private static final double PLAYER_SPEED = 1.0;
	
	public static final int TIME = 200;
	public static final int POINTS = 0;
	public static final int LIVES = 100;
	
	protected static final int SCREEN_DELAY = 3;
	
	
	//can be modified with bonus
	protected static int bombRate = BOMB_RATE;
	protected static int bombRadius = BOMB_RADIUS;
	protected static double playerSpeed = PLAYER_SPEED;
	
	
	//Time in the level screen in seconds
	protected int screenDelay = SCREEN_DELAY;
	
	private Keyboard input;
	private boolean running = false;
	private boolean paused = true;
	
	private Board board;
	private Screen screen;
	private Frame frame;
	
	//this will be used to render the game, each render is a calculated image saved here
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(Frame frame) {
		this.frame = frame;
		this.frame.setTitle(TITLE);
		
		screen = new Screen(WIDTH, HEIGHT);
		input = new Keyboard();
		Audio.playVictory();
		addKeyListener(input);
		board = new Board(this, input, screen);
	}
	
	
	private void renderGame() { //render will run the maximum times it can per second
		BufferStrategy bs = getBufferStrategy(); //create a buffer to store images using canvas
		if(bs == null) { //if canvas dont have a bufferstrategy, create it
			createBufferStrategy(3); //triple buffer
			return;
		}
		
		screen.clear();
		
		board.render(screen);

		for (int i = 0; i < pixels.length; i++) { //create the image to be rendered
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		board.renderMessages(g);
		
		g.dispose(); //release resources
		bs.show(); //make next buffer visible
	}
	
	private void renderScreen() { // merge these render methods
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		Graphics g = bs.getDrawGraphics();
		
		board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		input.update();
		board.update();
	}
	
	public void start() {
		running = true;
		
		long  lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			if(paused) {
				if(screenDelay <= 0) { //time passed? lets reset status to show the game
					board.setShow(-1);
					paused = false;
				}
					
				renderScreen();
			} else {
				renderGame();
			}
				
			
			frames++;
			if(System.currentTimeMillis() - timer > 1000) { //once per second
				frame.setTime(board.subtractTime());
				frame.setPoints(board.getPoints());
				frame.setLives(board.getLives());
				frame.setRecord(board.getRecord(false));
				timer += 1000;
				frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
				
				if(board.getShow() == 2)
					--screenDelay;
			}
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Getters & Setters
	|--------------------------------------------------------------------------
	 */
	public static double getPlayerSpeed() {
		return playerSpeed;
	}
	
	public static int getBombRate() {
		return bombRate;
	}
	
	public static int getBombRadius() {
		return bombRadius;
	}
	
	public static void addPlayerSpeed(double i) {
		playerSpeed += i;
	}
	
	public static void addBombRadius(int i) {
		bombRadius += i;
	}
	
	public static void addBombRate(int i) {
		bombRate += i;
	}
	
	//screen delay
	public int getScreenDelay() {
		return screenDelay;
	}
	
	public void decreaseScreenDelay() {
		screenDelay--;
	}
	
	public void resetScreenDelay() {
		screenDelay = SCREEN_DELAY;
	}

	public Keyboard getInput() {
		return input;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void run() {
		running = true;
		paused = false;
	}
	
	public void stop() {
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void pause() {
		paused = true;
	}

	public static void resetPlayerSpeed() {
		playerSpeed = PLAYER_SPEED;
	}

	public static void resetBombRadius() {
		bombRadius = BOMB_RADIUS;
	}

	public static void resetBombRate() {
		bombRate = BOMB_RATE;
	}
}//orient object programming