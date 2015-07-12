package com.qleek.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.player.Paegant;

public class PaegantWidget extends BaseWidget {
	
	private Paegant paegant;
	private TextButton enterButton;
	private Label nameLabel, descriptionLabel;
	
	public PaegantWidget(Paegant paegant) {
		
		this.paegant = paegant;
		
		enterButton = new TextButton("Enter", uiSkin);
		nameLabel = new Label(paegant.getName(), uiSkin);
		descriptionLabel = new Label(paegant.getDescription(), uiSkin);
		
		create();
	}

	private void create() {
		
		nameLabel.setWrap(true);
		descriptionLabel.setWrap(true);
		
		// ----- widgetLayout - 2 x 3 -----
		
		widgetLayout.setDebug(true);
		widgetLayout.defaults().expand().uniform();
		
		// Row One
		widgetLayout.add(nameLabel).fill().colspan(2).center();
		widgetLayout.add(enterButton).fill()
			.height(Gdx.graphics.getHeight() * 0.07F);
		widgetLayout.row();
		
		// Row Two
		widgetLayout.add(descriptionLabel).fill().colspan(3).left();
		widgetLayout.row();
		
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
		
		enterButton.addListener(paegantListener);
	}
}
