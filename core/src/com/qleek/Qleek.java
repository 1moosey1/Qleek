package com.qleek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.qleek.player.Item;
import com.qleek.player.Paegant;
import com.qleek.player.Player;
import com.qleek.player.Service;
import com.qleek.screens.*;
import com.qleek.utils.Achievement;
import com.qleek.utils.Achievements;
import com.qleek.utils.Observable;
import com.qleek.utils.UtilityListener;

public class Qleek extends Game {
	
	public static Skin skin;
	public SpriteBatch batch;
	
	public Player player;
	public Achievements achievements;
	public UtilityListener achievementListener;
	
	public TextureAtlas itemAtlas;
	public BaseScreen gameScreen, paegantScreen, shopScreen,
		inventoryScreen, wwyScreen;
	
	@Override
	public void create () {
		
		Gdx.input.setCatchBackKey(true);
		System.out.println(Gdx.graphics.getWidth());
		System.out.println(Gdx.graphics.getHeight());
		
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		FileHandle fileHandle;
		
		// Load all game images
		// Item images
		fileHandle = Gdx.files.internal("Items/ItemPack.pack");
		itemAtlas = new TextureAtlas(fileHandle);
		Item.setAtlas(itemAtlas);
		
		// Initialize screens 
		gameScreen = new GameScreen(this);
		paegantScreen = new PaegantScreen(this);
		shopScreen = new ShopScreen(this);
		inventoryScreen = new InventoryScreen(this);
		wwyScreen = new WWYScreen(this);
		
		// Initialize player and achievement functionality
		player = new Player();
		achievements = Achievements.getInstance();
		achievementListener = new UtilityListener() {
			
			@Override
			public void achievementUnlocked(Achievement achievement) {				
				((BaseScreen) Qleek.this.getScreen()).displayAchievement(achievement);
			}
		};
		
		achievements.addListener(achievementListener);
		Observable.addObserver(achievements);
		
		// Load all the services in the game
		fileHandle = Gdx.files.internal("service.meow");
		Service.initServices(fileHandle.readString());
		
		// Load item text in the game
		fileHandle = Gdx.files.internal("item.meow");
		Item.initItems(fileHandle.readString());
		
		// Load all the paegants in the game
		fileHandle = Gdx.files.internal("paegant.meow");
		Paegant.initPaegants(fileHandle.readString());
		
		// Start game
		setScreen(gameScreen);
	}
	
	@Override
	public void dispose() {	
		
		skin.dispose();
		batch.dispose();
		
		itemAtlas.dispose();
		
		gameScreen.dispose();
		paegantScreen.dispose();
		shopScreen.dispose();
		inventoryScreen.dispose();
		wwyScreen.dispose();
	}
}
