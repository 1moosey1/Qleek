package com.qleek.widgets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.qleek.player.Item;
import com.qleek.player.Item.ITEMID;

public class CraftWidget extends BaseWidget {

	private ITEMID itemID;
	private TextureRegion baseTexture;
	private boolean ready;
	
	public CraftWidget() {
		
		baseTexture = Item.getBase();
		widgetLayout.setBackground(new SpriteDrawable(new Sprite(baseTexture)));
		widgetLayout.setTouchable(Touchable.enabled);
		widgetLayout.addListener(new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				if(CraftWidget.this.isReady())
					widgetListener.craftWidgetAction(CraftWidget.this);
			}	
		});
	}
	
	public void setItem(ITEMID id) {
	
		itemID = id;
		ready = true;
		widgetLayout.setBackground(new SpriteDrawable(new Sprite(Item.getRegion(id))));
	}
	
	public ITEMID getItemID() { return itemID; }
	public boolean isReady()  { return ready;  }
	public void reset() {
		
		ready = false;
		widgetLayout.setBackground(new SpriteDrawable(new Sprite(baseTexture)));
	}
}
