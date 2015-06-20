package com.qleek.utils;

import com.qleek.player.Service;

public class Achievements extends Observer {
	
	private static Achievements achievements;
	private UtilityListener achievementListener;
	
	private Achievements() {}
	
	// Singleton pattern
	public static Achievements getInstance() {
		
		if(achievements == null)
			achievements = new Achievements();
		
		return achievements;
	}
	
	public void addListener(UtilityListener listener) {
		achievementListener = listener;
	}
	
	@Override
	public void onNotify(Observable observable, Event event) {
		
		switch(event) {
			
			case SERVICE_UPGRADED:
				
				Service service = (Service) observable;
				serviceAchieveCheck(service);
				break;
		}
	}
	
	//Handles the checks for service related achievements
	private void serviceAchieveCheck(Service service) {
		
		switch(service.getLevel()) {
		
			case 1:
				unlock(Achievement.SERVICE_1);
				break;
				
			case 100:
				unlock(Achievement.SERVICE_100);
				break;
				
			case 500:
				unlock(Achievement.SERVICE_500);
				break;
				
			case 999:
				unlock(Achievement.SERVICE_999);
				break;
		}
	}
	
	//Unlocks the achievement
	private void unlock(Achievement achievement) {
		
		if(!achievement.isUnlocked()) {
			
			achievement.unlock();
			achievementListener.achievementUnlocked(achievement);
		}
	}
}
