package com.qleek.utils;

public class SoundManager extends Observer {
	
	private static SoundManager soundManager;
	private boolean sound;

	private SoundManager() {}
	
	// Singleton Pattern
	public static SoundManager getInstance() {
		
		if(soundManager == null)
			soundManager = new SoundManager();
		
		return soundManager;
	}
	
	public boolean sound() {
		return sound;
	}
	
	public void toggleSound() {
		sound = !sound;
	}

	@Override
	public void onNotify(Observable observable, Event event) {
		// TODO Auto-generated method stub	
	}
}
