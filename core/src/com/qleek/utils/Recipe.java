package com.qleek.utils;

import com.qleek.player.Item.ITEMID;

public class Recipe implements Comparable<Recipe> {
	
	private ITEMID component1, component2, component3, creation;
	
	public Recipe(ITEMID id1, ITEMID id2, ITEMID id3) {
		this(id1, id2, id3, null);
	}

	public Recipe(ITEMID id1, ITEMID id2, ITEMID id3, ITEMID id4) {
		
		component1 = id1;
		component2 = id2;
		component3 = id3;
		creation = id4;
		
		if(component2.ordinal() < component1.ordinal()) {
			
			ITEMID temp = component1;
			component1 = component2;
			component2 = temp;
		}
		
		if(component3.ordinal() < component2.ordinal()) {
			
			ITEMID temp = component2;
			component2 = component3;
			component3 = temp;		
		}
		
		if(component2.ordinal() < component1.ordinal()) {
			
			ITEMID temp = component1;
			component1 = component2;
			component2 = temp;
		}
	}
	
	public ITEMID getFirst()  { return component1; }
	public ITEMID getSecond() { return component2; }
	public ITEMID getThird()  { return component3; }
	public ITEMID getItem()   { return creation;   }

	@Override
	public int compareTo(Recipe recipe) {
		
		if((component1 == recipe.getFirst()) 
				&& (component2 == recipe.getSecond()) 
				&& (component3 == recipe.getThird()))
			return 0;
			
		return -1;
	}
}
