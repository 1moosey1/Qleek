package com.qleek.player;

import com.badlogic.gdx.utils.Array;
import com.qleek.utils.Event;
import com.qleek.utils.Observable;
import com.qleek.utils.Prize;

@SuppressWarnings("unused")
public class Player extends Observable {

	private int affection, aps, apsDelta, money;
	private float cumulatedTime;
	private Array<Item> inventory;
	
	public Player() {	
		
		inventory = new Array<Item>();
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
	
	public Array<Item> getInventory() { return inventory; }
	public int getAffection() { return affection; }
	public int getAPS()       { return aps;       }
	public int getMoney()     { return money;     }
	
	public void addAffection(int value) {
		affection += value;
	}
	
	public void resetAffection() {
		affection = 0;
	}
	
	public void addAPS(int value) {
		aps += value;
	}
	
	public void addMoney(int value) {
		money += value;
	}
	
	public boolean canPurchase(int cost) {
		
		if(money >= cost)
			return true;
		
		return false;
	}
	
	public void purchase(int cost) {
		money = money - cost;
	}
	
	public void addItem(Item.ITEMID itemID) {
		
		if(itemID == null)
			return;
		
		for(Item invItem : inventory) {
			
			if(invItem.getItemID() == itemID) {
				
				invItem.incQuantity();
				return;
			}
		}
		
		Item item = new Item(itemID);
		item.incQuantity();
		inventory.add(item);
	}
	
	public void subtractItem(Item.ITEMID itemID) {	
		
		for(int i = 0; i < inventory.size; i++) {
			
			Item item = inventory.get(i);
			if(item.getItemID() == itemID) {
				
				item.decQuantity();
				if(item.getQuantity() == 0)
					inventory.removeIndex(i);
				
				break;
			}
		}
	}
	
	public int howMany(Item.ITEMID itemID) {
		
		for(Item item : inventory) {
			
			if(item.getItemID() == itemID)
				return item.getQuantity();
		}
		
		return 0;
	}
}
