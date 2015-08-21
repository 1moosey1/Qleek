package com.qleek.widgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.qleek.player.Paegant;

public class PaegantWidget extends BaseWidget {
	
	private Paegant paegant;
	
	public PaegantWidget(Paegant paegant) {
		
		this.paegant = paegant;
		create();
	}

	private void create() {
		
		ImageButtonStyle style = new ImageButtonStyle();
		style.imageUp = new SpriteDrawable(paegant.getBRegion());
		ImageButton paegantButton = new ImageButton(style);
		
		// ----- widgetLayout - 2 x 3 -----
		
		//widgetLayout.defaults().expand().uniform();
		widgetLayout.setSkin(uiSkin);
		widgetLayout.setBackground(new SpriteDrawable(paegant.getSRegion()));
		
		// Row One
		widgetLayout.add(paegant.getName());
		widgetLayout.row();
		
		// Row Two
		widgetLayout.add(paegantButton);
		
		// ----- End widgetLayout -----
		
		InputListener paegantListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				widgetListener.paegantWidgetAction(paegant);
			}
		};
		
		paegantButton.addListener(paegantListener);
	}
}
