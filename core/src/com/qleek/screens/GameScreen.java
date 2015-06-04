package com.qleek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.*;
import com.qleek.Qleek;

public class GameScreen implements Screen {
	
	private final Qleek qleek;
	private Stage hud;
	private Table hudLayout;
	
	public GameScreen(Qleek game) {
		
		qleek = game;
		
		hud = new Stage(new ScreenViewport(), qleek.batch);
		
		//InputMultiplexer ip = new InputMultiplexer();
		//ip.addProcessor(hud);
		Gdx.input.setInputProcessor(hud);
		
		hudLayout = new Table();
		hudLayout.setDebug(true);
		hudLayout.setFillParent(true);
		hud.addActor(hudLayout);
		
		Skin uiskin = new Skin(Gdx.files.internal("uiskin.json"));		
		Label testLabel = new Label("Label Test", uiskin);
		TextButton testButton = new TextButton("Button Test", uiskin);
		
		
		testButton.addListener(new InputListener() {
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("down");
		        return true;
		    }
		});
		
		
		hudLayout.add(testLabel).width(100).height(100);
		hudLayout.row();
		hudLayout.add(testButton).width(100).height(100);
	}
	
	public void create() {}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		hud.act(delta);
		hud.draw();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
		hud.dispose();
	}
}
