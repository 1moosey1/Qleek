package com.qleek.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.qleek.player.Item.ITEMID;

public class CraftManager {
	
	private static CraftManager craftManager;
	private ObjectMap<ITEMID, Array<Recipe>> recipeMap;
	
	private CraftManager() {
		
		recipeMap = new ObjectMap<ITEMID, Array<Recipe>>();
		add(new Recipe(ITEMID.ITEM1, ITEMID.ITEM1, ITEMID.ITEM1, ITEMID.ITEM2));
	}

	public static CraftManager getInstance() {
		
		if(craftManager == null)
			craftManager = new CraftManager();
		
		return craftManager;
	}
	
	private void add(Recipe recipe) {
		
		ITEMID key = recipe.getFirst();
		Array<Recipe> recipes;
		
		if(!recipeMap.containsKey(key)) {
		
			recipes = new Array<Recipe>();
			recipeMap.put(key, recipes);
		}
		
		recipes = recipeMap.get(key);
		recipes.add(recipe);
	}
	
	public ITEMID craft(Recipe recipe) {
		
		Array<Recipe> recipes = recipeMap.get(recipe.getFirst());
		for(Recipe r : recipes) {
			
			if(r.compareTo(recipe) == 0)
				return r.getItem();
		}
		
		return null;
	}
}
