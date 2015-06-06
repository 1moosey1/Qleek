package com.qleek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.qleek.screens.*;

public class Qleek extends Game {
	
	public SpriteBatch batch;
	public BaseScreen gameScreen, shopScreen;
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		
		gameScreen = new GameScreen(this);
		shopScreen = new ShopScreen(this);
		
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose() {	
		batch.dispose();
	}
}
