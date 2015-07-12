package com.qleek.utils;

import com.qleek.player.Item;

public class Prize {

	private int place, money;
	private Item.ITEMID itemID;
	
	public Prize(int place, int money, Item.ITEMID itemID) {
		
		this.place = place;
		this.money = money;
		this.itemID = itemID;
	}
	
	public int getPlace()        { return place;  }
	public int getMoney()        { return money;  }
	public Item.ITEMID getItem() { return itemID; }
}
