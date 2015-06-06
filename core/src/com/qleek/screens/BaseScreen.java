package com.qleek.screens;

import widgets.HeaderWidget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.qleek.Qleek;

public abstract class BaseScreen implements Screen {
	
	protected final Qleek qleek;
	protected final Stage HUD;
	protected VerticalGroup HUDLayout;
	protected Table layout;
	
	protected final Skin uiSkin;
	
	protected static HeaderWidget headerWidget;
	
	public BaseScreen(Qleek game) {
		
		qleek = game;
		HUD = new Stage(new ScreenViewport(), qleek.batch);
		uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
		
		layout = new Table();
		layout.setDebug(true);
		layout.setFillParent(true);
		HUD.addActor(layout);
		
		headerWidget = new HeaderWidget(uiSkin);
	}
	
	abstract void create();

	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		
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
