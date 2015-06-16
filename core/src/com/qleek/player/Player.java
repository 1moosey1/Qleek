package com.qleek.player;

public class Player {

	private int affection, aps, apsDelta, money;
	private float cumulatedTime;
	
	public Player() {
		
		money = 5000;
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
	
	public int getAffection() { return affection; }
	public int getAPS()       { return aps;       }
	public int getMoney()     { return money;     }
	
	public void addAffection(int value) {
		affection += value;
	}
	
	public void addAPS(int value) {
		aps += value;
	}
	
	public boolean purchaseService(Service service) {
		
		int cost = service.getCost();
		if(money >= cost) {
			
			money -= cost;
			service.upgrade();
			addAPS(service.getUpgrade());
			return true;
		}
		
		return false;
	}
}
