package com.qleek.player;

import com.badlogic.gdx.utils.Array;
import com.qleek.utils.Prize;

public class Paegant {
	
	private static Array<Paegant> paegantList
		= new Array<Paegant>();
	
	private PAEGANT paegant;
	private String name, description;
	
	public Paegant(String name, String description, PAEGANT paegant) {
		
		this.paegant = paegant;
		this.name = name;
		this.description = description;
		
		paegantList.add(this);
	}
	
	public static void initPaegants(String paegantText) {
		
		String[] text = paegantText.split("\\n");
		paegantList.clear();
		
		int textIndex = 0;
		for(PAEGANT  pgnt : PAEGANT.values())
			new Paegant(text[textIndex++], text[textIndex++], pgnt);
	}
	
	public static Array<Paegant> getServices() { return paegantList; }
	public String  getName()         { return name;             }
	public String  getDescription()  { return description;      }
	
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
		LOCALONE(5, 10, 15,
				1.25, 1.15, 1.05,
				Item.ITEMID.ITEM1,
				Item.ITEMID.ITEM2,
				Item.ITEMID.ITEM2);
		
		// National Paegants
		// To do
		
		final int percent1, percent2, percent3;
		final double multp1, multp2, multp3;
		final Item.ITEMID prize1, prize2, prize3;
		
		PAEGANT(int p1, int p2, int p3, 
				double m1, double m2, double m3,
				Item.ITEMID... prizes) {
			
			percent1 = p1;
			percent2 = p2;
			percent3 = p3;
			
			multp1 = m1;
			multp2 = m2;
			multp3 = m3;
			
			prize1 = prizes[0];
			prize2 = prizes[1];
			prize3 = prizes[2];
		}
	}
}
