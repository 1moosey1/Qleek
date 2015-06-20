package com.qleek.player;

import com.qleek.utils.Event;
import com.qleek.utils.Observable;

@SuppressWarnings("unused")
public class Player extends Observable {

	private int affection, aps, apsDelta, money;
	private float cumulatedTime;
	
	public Player() {
		
		money = 5000000;
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
		if(money >= cost && service.isUpgradable()) {
			
			money -= cost;
			addAPS(service.upgrade());		
			return true;
		}
		
		return false;
	}
}
