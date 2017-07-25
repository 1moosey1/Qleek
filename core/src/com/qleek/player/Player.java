package com.qleek.player;

import com.badlogic.gdx.utils.Array;
import com.qleek.player.Item.ITEMID;
import com.qleek.utils.Observable;

public class Player extends Observable {
	
	private static final int PENALTY = 86400;

	private Cat playerCat;
	private int affection, aps, apsDelta, money;
	private float cumulatedTime, pTime;
	private Array<Item> inventory, equips;
	private boolean penalized;
	
	public Player() {	

		playerCat = new Cat(Cat.CAT.QLEEK);
		inventory = new Array<Item>();
		equips = new Array<Item>();
		equips.add(null);
		equips.add(null);
		equips.add(null);
		equips.add(null);
	}
	
	public void update(float delta) {
		
		if(!hasCat())
			return;
		
		cumulatedTime += delta;
		
		if(cumulatedTime >= 1) {
			
			if(apsDelta < aps)
				affection += aps - apsDelta;
			
			cumulatedTime -= 1;
			apsDelta = 0;
			
			// Called once a second
			updatePlayerLogic();
			playerCat.update();
			
		} else {
		
			int value = (int) (delta * aps);
			apsDelta += value;
			affection += value;
		}
	}
	
	public void updatePlayerLogic() {
		updatePlayerLogic(1);
	}
	
	public void updatePlayerLogic(double time) {
		
		if(isPenalized()) {
			
			pTime += time;
			if(pTime >= PENALTY) {
				
				penalized = false;
				pTime = 0;
			}
		}
	}
	
	/*******************************************************************
	 *						Getter Functions
	 *******************************************************************/
	public Cat getCat() { return playerCat; }
	public Array<Item> getInventory() { return inventory; }
	public Array<Item> getEquips()    { return equips;    }
	public boolean isPenalized()      { return penalized; }
	public int getAffection() { return affection; }
	public int getAPS()       { return aps;       }
	public int getMoney()     { return money;     }

	
	/*******************************************************************
	 *						Setter Functions
	 *******************************************************************/
	public void penalize() {
		penalized = true;
	}
	
	public void setAffection(int affection) { 
		this.affection = affection; 
	}
	
	public void setAPS(int aps) {
		this.aps = aps; 
	}
	
	public void setMoney(int money) {
		this.money = money; 
	}
	
	/*******************************************************************
	 *						Money Related Functions
	 *******************************************************************/
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
	
	/*******************************************************************
	 *						Item Related Fucntions
	 *******************************************************************/
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
	
	public void equip(int index, Item equip) {
		
		equips.set(index, equip);
		addAPS(equip.getAPS());
	}
	
	public void unequip(int index) {
		
		addAPS(-equips.get(index).getAPS());
		equips.set(index, null);
	}
	
	public int howMany(ITEMID itemID) {
		
		for(Item item : inventory) {
			
			if(item.getItemID() == itemID)
				return item.getQuantity();
		}
		
		return 0;
	}
	
	/*******************************************************************
	 *						Cat Related Functions
	 *******************************************************************/
	public boolean hasCat() {
		
		//if(playerCat == null || playerCat.isDead())
		//	return false;
		
		return true;
	}
	
	public void abandonCat() {
		playerCat = null;
	}
}
