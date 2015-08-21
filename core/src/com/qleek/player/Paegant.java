package com.qleek.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.qleek.player.Item.ITEMID;
import com.qleek.utils.Prize;

public class Paegant {
	
	public static Array<Paegant> paegantList;
	public static TextureAtlas paegantAtlas;
	
	private PAEGANT paegant;
	private String name, description;
	
	public Paegant(String name, String description, PAEGANT paegant) {
		
		this.paegant = paegant;
		this.name = name;
		this.description = description;
		
		paegantList.add(this);
	}
	
	public static void setAtlas(TextureAtlas atlas) {
		paegantAtlas = atlas;
	}
	
	public static void initPaegants(String paegantText) {
		
		paegantList = new Array<Paegant>();
		String[] text = paegantText.split("\\n");
		
		int textIndex = 0;
		for(PAEGANT  pgnt : PAEGANT.values())
			new Paegant(text[textIndex++], text[textIndex++], pgnt);
	}
	
	public Sprite getSRegion() { return paegantAtlas.createSprite(paegant.name()); }
	public Sprite getBRegion() { return paegantAtlas.createSprite(paegant.name() + "B"); } 
	public String  getName()         { return name;             }
	public String  getDescription()  { return description;      }
	
	public ITEMID[] getRewards() { 
		return new ITEMID[] {paegant.prize1, paegant.prize2, paegant.prize3}; 
	}
	
	public double[] getOdds() {
		return new double[] {paegant.percent1, paegant.percent2, paegant.percent3};
	}
	
	public Prize enter(int affection) {
		
		int place, money, 
			range1, range2, range3;
		Item.ITEMID prizeItem;

		money = affection;		
		double random = Math.random() * 100;

		range1 = paegant.percent1 - 1;	
		range2 = paegant.percent1 + paegant.percent2 - 1;
		range3 = paegant.percent1 + paegant.percent2 + paegant.percent3 - 1;	
		
		if(random <= range1) {
			
			place = 1;
			money *= paegant.multp1;
			prizeItem = paegant.prize1;
		} 
		else if(random <= range2) {
			
			place = 2;
			money *= paegant.multp2;
			prizeItem = paegant.prize2;
		}
		else if(random <= range3) {
			
			place = 3;
			money *= paegant.multp3;
			prizeItem = paegant.prize3;
		}
		else {
			
			place = 0;
			prizeItem = null;
		}
		
		return new Prize(place, money, prizeItem);
	}

	private enum PAEGANT {
		
		// Local Paegant
		LOCALONE(5, 10, 15, 1.25, 1.15, 1.05,
				ITEMID.ITEM1, ITEMID.ITEM2, ITEMID.ITEM2);
		
		final int percent1, percent2, percent3;
		final double multp1, multp2, multp3;
		final ITEMID prize1, prize2, prize3;
		
		PAEGANT(int p1, int p2, int p3, 
				double m1, double m2, double m3,
				ITEMID r1, ITEMID r2, ITEMID r3) {
			
			percent1 = p1;
			percent2 = p2;
			percent3 = p3;
			
			multp1 = m1;
			multp2 = m2;
			multp3 = m3;
			
			prize1 = r1;
			prize2 = r2;
			prize3 = r3;
		}
	}
}
