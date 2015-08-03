package com.qleek.utils;

public enum Achievement {
	
	SERVICE_1
		("Welcome to the service industry",
		"Upgrade a service to level 1."),
	
	SERVICE_100
		("Achievement 2",
		"Upgrade a service to level 100."),
		
	SERVICE_500
		("Achievement 3",
		"Upgrade a service to level 500."),
		
	SERVICE_999
		("Achievement 4",
		"Upgrade a service to level 999.");
	
	private final String name, description;
	private boolean unlocked;
	
	Achievement(String name, String description) {
		
		this.name = name;
		this.description = description;
	}
	
	public String getName()        { return name;        }
	public String getDescription() { return description; }
	public boolean isUnlocked()    { return unlocked;    }
	public void unlock()           { unlocked = true;    }
}
