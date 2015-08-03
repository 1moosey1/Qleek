package com.qleek.utils;

public class SoundManager extends Observer {
	
	private static SoundManager soundManager;

	private SoundManager() {}
	
	// Singleton Pattern
	public static SoundManager getInstance() {
		
		if(soundManager == null)
			soundManager = new SoundManager();
		
		return soundManager;
	}

	@Override
	public void onNotify(Observable observable, Event event) {
		// TODO Auto-generated method stub	
	}
}
