package com.qleek.player;

import com.badlogic.gdx.utils.Array;
import com.qleek.utils.Event;
import com.qleek.utils.Observable;

public class Service extends Observable {
	
	public static final int MAXLEVEL = 999;
	
	private static Array<Service> serviceList
		= new Array<Service>();
	
	private SERVICE service;
	private String name, description;
	private int cost, level, aps;

	public Service(String name, String description, SERVICE service, int level) {
		
		this.service = service;	
		this.name = name;
		this.description = description;
		this.level = level;
		
		cost = calculateCost();
		aps = calculateAPS();
		
		serviceList.add(this);
	}
	
	public static void initServices(String serviceText) {
		
		String[] text = serviceText.split("\\n");
		serviceList.clear();
		
		int textIndex = 0;
		for(SERVICE srv : SERVICE.values())
			new Service(text[textIndex++], text[textIndex++], srv, 0);
	}
	
	public static Array<Service> getServices() { return serviceList; }	
	public String  getName()         { return name;             }
	public String  getDescription()  { return description;      }
	public int     getCost()         { return cost;             }
	public int     getLevel()        { return level;            }
	public int     getNAPS()         { return aps;              }
	public boolean isUpgradable()    { return level < MAXLEVEL; }
	
	public int getCAPS() { 
		
		if(level == 0)
			return 0;
		
		return aps - service.upgrade;
	}	
	
	public int upgrade() {
		
		level++;
		cost = calculateCost();
		aps = calculateAPS();
		notify(this, Event.SERVICE_UPGRADED);
		
		if(level == 1)
			return service.baseAPS;
		
		return service.upgrade;
	}

	private int calculateCost() {
		return (int) (service.baseCost * (Math.pow(1.075, level))); }
	
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
