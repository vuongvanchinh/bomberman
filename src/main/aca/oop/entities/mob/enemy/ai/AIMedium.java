package aca.oop.entities.mob.enemy.ai;

import aca.oop.Game;
import aca.oop.entities.mob.Player;
import aca.oop.entities.mob.enemy.Enemy;

public class AIMedium extends AI {
   
   Player player;
   Enemy e;

	int changeSpeed = 150;
	boolean increase = false;
	
   public AIMedium(Player player, Enemy e) {
      this.player = player;
      this.e = e;
	}
	
	public void changeRandomSpeed() {
		if (changeSpeed == 0) {
			changeSpeed = 150;
		} else {
			int r = random.nextInt(2);
			int t = changeSpeed % 10;
			if (t == 0 && !increase && r == 1) {
				e.addSpeed(Game.getPlayerSpeed() / 2);
				increase = true;
			}
			else if (t == 5 && increase && r == 0) {
				e.addSpeed(-Game.getPlayerSpeed() / 2);
				increase = false;
			}
			changeSpeed--;
			e.calculateMoveValues();
		}
	}

   /** 
    * direction.
    *  __0__
    * |     |
    * 3     1
    * |__2__|
    */
   @Override
   public int calculateDirection() {
		changeRandomSpeed();

		if(player == null) {
			return random.nextInt(4);
      }
      int vertical = random.nextInt(2);
      
		if(vertical == 1) {
			int v = calculateRowDirection();
			if(v != -1)
				return v;
			else
				return calculateColDirection();
		} else {
			int h = calculateColDirection();
			if(h != -1)
				return h;
			else
				return calculateRowDirection();
		}
	}
	

   protected int calculateColDirection() {
		if(player.getXTile() < e.getXTile())
			return 3;
		else if(player.getXTile() > e.getXTile())
			return 1;
			
		return -1;
	}
	
	protected int calculateRowDirection() {
		if(player.getYTile() < e.getYTile())
			return 0;
		else if(player.getYTile() > e.getYTile())
			return 2;
		return -1;
	}
}
