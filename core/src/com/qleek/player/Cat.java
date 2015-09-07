package com.qleek.player;

public class Cat {
	
	private CAT catType;
	private boolean dead;
	private double age, decay;
	
	public Cat(CAT cat) {
		
		catType = cat;
		decay = 1;
	}
	
	public void update() {
		update(1);
	}
	
	public void update(double time) {
		
		age = age + (time * decay);
		if(age > catType.lifespan)
			dead = true;
	}
	
	public boolean isDead() { return dead; }

	public enum CAT {
		
		QLEEK(4.5);
		
		final double lifespan;
		CAT(double span) {
			
			lifespan = span * 24 * 60 * 60;
		}
	}
}
