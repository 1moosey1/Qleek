package com.qleek.player;

import com.badlogic.gdx.utils.Array;

public class Service {
	
	private static Array<Service> serviceList
		= new Array<Service>();
	
	private SERVICE service;
	private String name, description;
	private int cost, level, aps;

	public Service(String nm, String descrip, SERVICE srv, int lvl) {
		
		service = srv;
		
		name = nm;
		description = descrip;
		
		level = lvl;
		cost = calculateCost();
		aps = calculateAPS();
		
		serviceList.add(this);
	}
	
	public static void initServices(String serviceText) {
		
		String[] text = serviceText.split("\\n");
		
		int i = 0;
		for(SERVICE srv : SERVICE.values())
			new Service(text[i++], text[i++], srv, 0);
	}
	
	public static Array<Service> getServices() { return serviceList; }
	
	public String getName()          { return name;            }
	public String getDescription()   { return description;     }
	public int    getCost()          { return cost;            }
	public int    getLevel()         { return level;           }
	public int    getAPS()           { return aps;             }
	public int    getUpgrade()       { return service.upgrade; }
	
	public void upgrade() {
		
		level++;
		cost = calculateCost();
		aps = calculateAPS();
	}
	
	private int calculateCost() {
		return (int) (service.baseCost * (Math.pow(1.07, level))); }
	
	private int calculateAPS() {
		return service.baseAPS + (service.upgrade * level); }

	private enum SERVICE {
		
		//Next Door Rascals
		ONE(5, 1, 1), 
		
		//Big Suzy
		TWO(250, 4, 2);
		
		final int baseCost, baseAPS, upgrade;
		SERVICE(int cost, int aps, int u) {
			
			baseCost = cost;
			baseAPS = aps;
			upgrade = u;
		}
	}
}
