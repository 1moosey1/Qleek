package com.qleek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.qleek.screens.GameScreen;

public class Qleek extends Game {
	
	public SpriteBatch batch;
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose() {	
		batch.dispose();
	}
}
