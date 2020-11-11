package aca.oop.entities;

public abstract class AnimatedEntity extends Entity {
	protected int animate = 0;
	protected static final int MAX_ANIMATE = 7500; // save the animation status and dont let this get too big

	protected void animate() {
		if (animate < MAX_ANIMATE)
			animate++;
		else
			animate = 0; // reset animation
		//System.out.println("animate: " + animate);
	}
}
	
