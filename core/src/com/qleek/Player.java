package com.qleek;

public class Player {

	private int affection, aps, apsDelta;
	private float cumulatedTime;
	
	public Player() {
		
		//aps = 100;
	}
	
	public void update(float delta) {
		
		cumulatedTime += delta;
		
		if(cumulatedTime >= 1) {
			
			if(apsDelta < aps)
				affection += aps - apsDelta;
			
			cumulatedTime -= 1;
			apsDelta = 0;
			
		} else {
		
			int value = (int) (delta * aps);
			apsDelta += value;
			affection += value;
		}
	}
	
	public int getAffection() {
		return affection;
	}
	
	public void addAffection(int value) {
		affection += value;
	}
}
