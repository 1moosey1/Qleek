package com.qleek.utils;

public abstract class Observer {

	// Abstract definition of an observer
	// This method is called whenever an event takes place 
	public abstract void onNotify(Observable observable, Event event);
}
