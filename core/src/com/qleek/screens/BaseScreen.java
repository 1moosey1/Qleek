package com.qleek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.qleek.Qleek;
import com.qleek.utils.Achievement;
import com.qleek.widgets.HeaderWidget;
import com.qleek.widgets.QleekDialog;

public abstract class BaseScreen implements Screen {
	
	protected final Qleek qleek;
	protected final Stage HUD;
	protected Table screenLayout;
	
	protected Skin uiSkin = Qleek.skin;
	protected HeaderWidget headerWidget;
	protected InputMultiplexer inputMultiplexer;
	protected ScreenProcessor screenProcessor;
	private InputListener headerListener;
	
	public BaseScreen(Qleek game) {
		
		qleek = game;
		
		screenProcessor = new ScreenProcessor();
		inputMultiplexer = new InputMultiplexer();
		
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
		
		inputMultiplexer.addProcessor(HUD);
		inputMultiplexer.addProcessor(screenProcessor);
	}
	
	public void displayOptions() {}
	
	public void displayAchievement(Achievement achievement) {
		new QleekDialog(achievement).show(HUD);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(inputMultiplexer);
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
	public void pause() {
		qleek.timeStamp = TimeUtils.millis();
	}

	@Override
	public void resume() {
		qleek.handleIdleTime();
	}
	
	@Override
	public void hide() {}

	@Override
	public void dispose() {
		HUD.dispose();
	}
	
	protected class ScreenProcessor implements InputProcessor {
	
		@Override
		public boolean keyDown(int keycode) {
		
			if(keycode == Keys.BACK) {
				
				if(qleek.getScreen().equals(qleek.gameScreen))
					Gdx.app.exit();
			
				qleek.setScreen(qleek.gameScreen);
			}
			
			return true; 
		}
		
		public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
		public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
		public boolean touchDragged(int screenX, int screenY, int pointer) { return true; }
		public boolean keyUp(int keycode) { return false; }
		public boolean keyTyped(char character) { return false; }
		public boolean mouseMoved(int screenX, int screenY) { return false; }
		public boolean scrolled(int amount) { return false; }
	}
}
