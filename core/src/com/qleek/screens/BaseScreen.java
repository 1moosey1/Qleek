package com.qleek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.qleek.Qleek;
import com.qleek.widgets.HeaderWidget;

public abstract class BaseScreen implements Screen {
	
	protected final Qleek qleek;
	protected final Stage HUD;
	protected Table screenLayout;
	
	protected Skin uiSkin = Qleek.skin;
	protected HeaderWidget headerWidget;
	
	private InputListener headerListener;
	
	public BaseScreen(Qleek game) {
		
		qleek = game;
		
		HUD = new Stage(new ScreenViewport(), qleek.batch);
		screenLayout = new Table();
		headerWidget = new HeaderWidget();

		create();
	}
	
	private void create() {
		
		screenLayout.setDebug(true);
		screenLayout.setFillParent(true);
		screenLayout.defaults().expand();
		HUD.addActor(screenLayout);	
		
		// Row One
		screenLayout.add(headerWidget.getLayout()).fill().top()
			.height(Gdx.graphics.getHeight() * 0.20F);
		screenLayout.row();
		
		// Anon class for handling header buttons
		headerListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				String name = event.getListenerActor().getName();
				
				if(name == headerWidget.backButtonName()) 
					qleek.setScreen(qleek.gameScreen);
				
				else if(name == headerWidget.optionsButtonName())
					qleek.setScreen(qleek.gameScreen);
			}
		};
		
		headerWidget.addListener(headerListener);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(HUD);
	}

	@Override
	public void render(float delta) {

		qleek.player.update(delta);
		headerWidget.updateDisplay(qleek.player);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		
		HUD.act(delta);
		HUD.draw();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}
	
	@Override
	public void hide() {}

	@Override
	public void dispose() {
		
		HUD.dispose();
		uiSkin.dispose();
	}
}
