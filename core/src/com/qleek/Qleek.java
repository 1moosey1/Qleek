package com.qleek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.qleek.player.Player;
import com.qleek.player.Service;
import com.qleek.screens.*;
import com.qleek.utils.Achievements;
import com.qleek.utils.Observable;

public class Qleek extends Game {
	
	public static Skin skin;
	public SpriteBatch batch;
	
	public Player player;
	public Achievements achievements;
	
	public BaseScreen gameScreen, paegantScreen, shopScreen,
		inventoryScreen, wwyScreen;
	
	@Override
	public void create () {
		
		Gdx.input.setCatchBackKey(true);
		
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		player = new Player();
		achievements = Achievements.getInstance();
		Observable.addObserver(achievements);
		
		gameScreen = new GameScreen(this);
		paegantScreen = new PaegantScreen(this);
		shopScreen = new ShopScreen(this);
		inventoryScreen = new InventoryScreen(this);
		wwyScreen = new WWYScreen(this);
		
		//Load all the services in the game
		FileHandle textHandle = Gdx.files.internal("service.meow");
		Service.initServices(textHandle.readString());
		
		setScreen(gameScreen);
	}
	
	@Override
	public void dispose() {	
		
		skin.dispose();
		batch.dispose();
		
		gameScreen.dispose();
		paegantScreen.dispose();
		shopScreen.dispose();
		inventoryScreen.dispose();
		wwyScreen.dispose();
		
		Service.getServices().clear();
	}
}
