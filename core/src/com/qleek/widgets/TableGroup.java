package com.qleek.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class TableGroup extends VerticalGroup {

	private int maxPerRow, count;
	private HorizontalGroup currentHGroup;
	
	public TableGroup(int max) {
		
		maxPerRow = max;
		currentHGroup = new HorizontalGroup();
		addActor(currentHGroup);
	}
	
	public void addToGroup(Actor actor) {
		
		currentHGroup.addActor(actor);
		count++;
		
		if(count == maxPerRow) {
			
			currentHGroup = new HorizontalGroup();
			addActor(currentHGroup);
			count = 0;
		}
	}
	
	public void clearGroup() {
		
		count = 0;
		
		clear();
		currentHGroup.clear();
		addActor(currentHGroup);
	}
}
