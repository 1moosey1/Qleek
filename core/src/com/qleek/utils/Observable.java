package com.qleek.utils;

import com.badlogic.gdx.utils.Array;

public class Observable {

	private static Array<Observer> observers
		= new Array<Observer>();
	
	public static void addObserver(Observer observer) {
		observers.add(observer);
	}
	
	public static void removeObserver(Observer observer) {
		observers.add(observer);
	}
	
	// Notify all observers of a particular event
	protected void notify(Observable observable, Event event) {
		
		for(Observer observer : observers)
			observer.onNotify(observable, event);
	}
}
