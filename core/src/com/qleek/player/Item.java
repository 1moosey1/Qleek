package com.qleek.player;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class Item {
	
	public  static Array<ITEMID> shopItems = new Array<ITEMID>();
	private static TextureAtlas itemAtlas;
	private static ObjectMap<ITEMID, String[]> info = new ObjectMap<ITEMID, String[]>();
	
	private ITEMID itemID;
	private String name, description;
	private int quantity, cost, aps;
	private boolean useable, special;

	public Item(ITEMID id) {
		
		itemID = id;
		String[] text = info.get(id);
		
		name = text[0];
		description = text[1];	
		aps = id.aps;
	}
	
	public Item(ITEMID id, int affection, int money) {
		
		this(id);
		cost = (int) (id.baseCost + (affection + money) * 0.01);
	}
	
	public static void setAtlas(TextureAtlas atlas) {
		itemAtlas = atlas;
	}
	
	public static void initItems(String itemText) {
		
		String[] text = itemText.split("\\n");
		shopItems.clear();
		
		int textIndex = 0;
		for(ITEMID id : ITEMID.values())
			info.put(id, new String[] {text[textIndex++], text[textIndex++]});
		
		// Default items avaiable in the shop
		shopItems.addAll(ITEMID.ITEM1, ITEMID.ITEM2);
	}
	
	public static TextureRegion getBase() { return itemAtlas.findRegion("base"); }
	public static TextureRegion getRegion(ITEMID id) {
		return itemAtlas.findRegion(id.name());
	}
	
	public ITEMID  getItemID()       { return itemID;           }
	public String  getName()         { return name;             }
	public String  getDescription()  { return description;      }
	public boolean isUsable()        { return useable;          }
	public boolean isSpecial()       { return special;          }
	public int     getQuantity()     { return quantity;         }
	public int     getCost()         { return cost;             }
	public int     getAPS()          { return aps;              }
	public TextureRegion getRegion() { return itemAtlas.findRegion(itemID.name()); }
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setAPS(int aps) {
		this.aps = aps;
	}
	
	public void incQuantity() {
		quantity++;
	}
	
	public void decQuantity() {
		quantity--;
	}

	public enum ITEMID {
		
		// First place ribbon
		ITEM1(0, 10),
		
		// Second place ribbon
		ITEM2(0, 5),
		
		// Third place ribbon
		ITEM3(0, 1);
		
		private final int baseCost, aps;
		ITEMID(int baseCost, int aps) {
			
			this.baseCost = baseCost;
			this.aps = aps;
		}
	}
}
