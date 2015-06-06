package com.qleek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.Qleek;

public class GameScreen extends BaseScreen {
	
	public GameScreen(Qleek game) {
		
		super(game);
		Gdx.input.setInputProcessor(HUD);
		
		create();
	}
	
	public void create() {
		
		Label testLabel = new Label("Label Test", uiSkin);
		TextButton testButton = new TextButton("Button Test", uiSkin);
		
		testButton.addListener(new InputListener() {
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        qleek.setScreen(qleek.shopScreen);
		        return true;
		    }
		});
		
		
		layout.add(testLabel).width(100).height(100);
		layout.row();
		layout.add(testButton).width(100).height(100);
		layout.add(headerWidget.getLayout());
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		super.render(delta);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}
