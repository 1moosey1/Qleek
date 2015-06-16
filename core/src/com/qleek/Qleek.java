package com.qleek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.qleek.player.Player;
import com.qleek.player.Service;
import com.qleek.screens.*;

public class Qleek extends Game {
	
	public SpriteBatch batch;
	public static Skin skin;
	
	public Player player;
	public BaseScreen gameScreen, paegantScreen, shopScreen,
		inventoryScreen, wwyScreen;
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		player = new Player();	
		
		gameScreen = new GameScreen(this);
		paegantScreen = new PaegantScreen(this);
		shopScreen = new ShopScreen(this);
		inventoryScreen = new InventoryScreen(this);
		wwyScreen = new WWYScreen(this);
		
		FileHandle textHandle = Gdx.files.internal("service.meow");
		Service.initServices(textHandle.readString());
		
		setScreen(gameScreen);
	}
	
	@Override
	public void dispose() {	
		
		batch.dispose();
		gameScreen.dispose();
	}
}
