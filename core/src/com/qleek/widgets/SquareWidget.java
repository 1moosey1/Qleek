package com.qleek.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.qleek.Qleek;
import com.qleek.player.Item;

public class SquareWidget extends BaseWidget {
	
	private Item item;
	
	public SquareWidget(Item item) {
		
		widgetLayout.defaults().expand();
		
		this.item = item;
		widgetLayout.setSkin(Qleek.skin);
		widgetLayout.setBackground(new SpriteDrawable(item.getSRegion()));
		widgetLayout.setTouchable(Touchable.enabled);
		widgetLayout.add("x" + item.getQuantity()).bottom().right();
		
		widgetLayout.addListener(new ActorGestureListener() {
			
			@Override 
			public boolean longPress(Actor actor, float x, float y) {
				
				widgetListener.squareWidgetAction(SquareWidget.this.item);
				return true;
			}
		});
	}
}
