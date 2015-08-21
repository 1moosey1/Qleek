package com.qleek.widgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.qleek.player.Item;
import com.qleek.player.Item.ITEMID;

public class StatusWidget extends BaseWidget {

	private Item item;
	private int index;
	private boolean ready;
	
	public StatusWidget() {
		
		widgetLayout.setBackground(new SpriteDrawable(Item.getBase()));
		widgetLayout.setTouchable(Touchable.enabled);
		widgetLayout.addListener(new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				if(StatusWidget.this.isReady())
					widgetListener.statusWidgetAction(StatusWidget.this);
			}	
		});
	}
	
	public void setItem(Item item) {
		
		if(item == null)
			return;
	
		this.item = item;
		ready = true;
		widgetLayout.setBackground(new SpriteDrawable(item.getSRegion()));
	}
	
	public void setName(String name) {
		widgetLayout.setName(name);
	}
	
	public void setIndex(int value) {
		index = value;
	}
	
	public String getName()   { return widgetLayout.getName(); }
	public Item getItem()     { return item;                   }
	public ITEMID getItemID() { return item.getItemID();       }
	public int getIndex()     { return index;                  }
	public boolean isReady()  { return ready;                  }
	public void reset() {
		
		ready = false;
		widgetLayout.setBackground(new SpriteDrawable(Item.getBase()));
	}
}
