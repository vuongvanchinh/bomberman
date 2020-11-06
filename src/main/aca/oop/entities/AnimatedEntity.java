package aca.oop.entities;

import aca.oop.graphics.Screen;

public class AnimatedEntity extends Entity {
	protected int animate = 0;
	protected final int MAX_ANIMATE = 7500; // save the animation status and dont let this get too big

	protected void animate() {
		if (animate < MAX_ANIMATE)
			animate++;
		else
			animate = 0; // reset animation
	}

	@Override
	public void update() {
		

	}

	@Override
	public void render(Screen screen) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean collide(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}
}
