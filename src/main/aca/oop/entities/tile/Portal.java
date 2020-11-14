package aca.oop.entities.tile;

import aca.oop.Board;
import aca.oop.entities.Entity;
import aca.oop.entities.Player;
import aca.oop.graphics.Sprite;
import aca.oop.level.Audio;

public class Portal extends Tile {

   protected Board board;

   public Portal(int x, int y, Board board, Sprite sprite) {
      super(x, y, sprite);
      this.board = board;
   }
   
   @Override
	public boolean collide(Entity e) {
		
		if(e instanceof Player ) {
			
			if (!board.detectNoEnemies()) {
				return false;
			}

			if(e.getXTile() == getX() && e.getYTile() == getY() && board.detectNoEnemies()) {
				Audio.playVictory();
				board.nextLevel();
			}
			
			return true;
		}
		
		return false;
	}
}
