package com.qleek.player;

import com.badlogic.gdx.utils.Array;
import com.qleek.player.Item.ITEMID;
import com.qleek.utils.Observable;

public class Player extends Observable {

	private int affection, aps, apsDelta, money;
	private float cumulatedTime;
	private Array<Item> inventory, equips;
	
	public Player() {	
		
		inventory = new Array<Item>();
		equips = new Array<Item>();
		equips.add(null);
		equips.add(null);
		equips.add(null);
		equips.add(null);
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
	public Array<Item> getEquips()    { return equips;    }
	public int getAffection() { return affection; }
	public int getAPS()       { return aps;       }
	public int getMoney()     { return money;     }
	
	public void setAffection(int affection) { 
		this.affection = affection; 
	}
	
	public void setAPS(int aps) {
		this.aps = aps; 
	}
	
	public void setMoney(int money) {
		this.money = money; 
	}
	
	public void addAffection(int value) {
		affection += value;
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
	
	public void addItem(Item item) {
		
		addAPS(item.getAPS());
		inventory.add(item);
	}
	
	public void addItem(ITEMID itemID) {
		
		if(itemID == null)
			return;
		
		for(Item invItem : inventory) {
			
			if(invItem.getItemID() == itemID) {
				
				invItem.incQuantity();
				addAPS(invItem.getAPS());
				return;
			}
		}
		
		Item item = new Item(itemID);
		item.incQuantity();
		addAPS(item.getAPS());
		inventory.add(item);
	}
	
	public void removeItem(ITEMID itemID) {	
		
		for(int i = 0; i < inventory.size; i++) {
			
			Item item = inventory.get(i);
			if(item.getItemID() == itemID) {
				
				item.decQuantity();
				addAPS(-item.getAPS());
				if(item.getQuantity() == 0)
					inventory.removeIndex(i);
				
				break;
			}
		}
	}
	
	public void unequip(int index) {
		equips.set(index, null);
	}
	
	public int howMany(ITEMID itemID) {
		
		for(Item item : inventory) {
			
			if(item.getItemID() == itemID)
				return item.getQuantity();
		}
		
		return 0;
	}
}
