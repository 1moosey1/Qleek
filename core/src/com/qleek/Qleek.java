package com.qleek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.qleek.player.Item;
import com.qleek.player.Item.ITEMID;
import com.qleek.player.Paegant;
import com.qleek.player.Player;
import com.qleek.player.Service;
import com.qleek.screens.*;
import com.qleek.utils.Achievement;
import com.qleek.utils.Achievements;
import com.qleek.utils.Observable;
import com.qleek.utils.SaveManager;
import com.qleek.utils.SoundManager;
import com.qleek.utils.UtilityListener;

public class Qleek extends Game {
	
	public static Skin skin;
	private static final int IDLECAP = 3600;
	
	public SpriteBatch batch;
	public Player player;
	public Achievements achievements;
	public SoundManager soundManager;
	public UtilityListener achievementListener;
	
	public BaseScreen gameScreen, paegantScreen, shopScreen,
		inventoryScreen, wwyScreen;
	public long timeStamp;
	
	private BitmapFont font;
	private TextureAtlas uuiAtlas, duiAtlas;
	
	@Override
	public void create () {
		
		Gdx.input.setCatchBackKey(true);
		System.out.println("Width: " + Gdx.graphics.getWidth());
		System.out.println("Height: " + Gdx.graphics.getHeight());
		System.out.println("Density: " + Gdx.graphics.getDensity());
		
		// Reusable Variables
		FileHandle fileHandle;
		TextureAtlas atlas;
		
		/*******************************************************************
		 *					Check phone density (DPI)
		 *******************************************************************/
		float density = Gdx.graphics.getDensity();
		String densityString = "";
		
		if(density <= 1)
			densityString = "mdpi";
		else if(density <= 1.5)
			densityString = "hdpi";
		else if(density <= 2)
			densityString = "xhdpi";
		else if(density <= 3)
			densityString = "xxhdpi";
		
		/*******************************************************************
		 *				Load skin, related regions and font
		 *******************************************************************/
		skin = new Skin();
		
		fileHandle = Gdx.files.internal("ui/" + densityString + "/font.fnt");
		font = new BitmapFont(fileHandle);
	    skin.add("default-font", font);
		
	    fileHandle = Gdx.files.internal("ui/" + densityString + "/dui.atlas");
	    duiAtlas = new TextureAtlas(fileHandle);
	    skin.addRegions(duiAtlas);
	    
	    fileHandle = Gdx.files.internal("ui/uui.atlas");
	    uuiAtlas = new TextureAtlas(fileHandle);
	    skin.addRegions(uuiAtlas);
	    
		fileHandle = Gdx.files.internal("ui/uui.json");
		skin.load(fileHandle);
		
		/*******************************************************************
		 *						Load game images
		 *******************************************************************/
		// Item images
		fileHandle = Gdx.files.internal("item/ItemPack.pack");
		atlas = new TextureAtlas(fileHandle);
		Item.setAtlas(atlas);
		
		// Paegant Images
		fileHandle = Gdx.files.internal("paegant/paegant.pack");
		atlas = new TextureAtlas(fileHandle);
		Paegant.setAtlas(atlas);
		
		/*******************************************************************
		 *							Initialization
		 *******************************************************************/
		batch = new SpriteBatch();
		
		// Initialize player
		player = new Player();
		
		// Initialize screens 
		gameScreen = new GameScreen(this);
		paegantScreen = new PaegantScreen(this);
		shopScreen = new ShopScreen(this);
		inventoryScreen = new InventoryScreen(this);
		wwyScreen = new WWYScreen(this);
		
		// Initialize sound functionality
		Observable.addObserver(SoundManager.getInstance());
		
		// Initialize achievement functionality
		achievements = Achievements.getInstance();
		achievementListener = new UtilityListener() {
			
			@Override
			public void achievementUnlocked(Achievement achievement) {				
				((BaseScreen) Qleek.this.getScreen()).displayAchievement(achievement);
			}
		};
		achievements.addListener(achievementListener);
		
		soundManager = SoundManager.getInstance();
		
		Observable.addObserver(achievements);
		Observable.addObserver(soundManager);
		
		/*******************************************************************
		 *						Load in game text
		 *******************************************************************/
		// Load all the services in the game
		fileHandle = Gdx.files.internal("data/service.meow");
		Service.initServices(fileHandle.readString());
		
		// Load item text in the game
		fileHandle = Gdx.files.internal("data/item.meow");
		Item.initItems(fileHandle.readString());
		
		// Load all the paegants in the game
		fileHandle = Gdx.files.internal("data/paegant.meow");
		Paegant.initPaegants(fileHandle.readString());
		
		/*******************************************************************
		 *						Load save data
		 *******************************************************************/
		SaveManager saveManager = SaveManager.getInstance();
		saveManager.readSave();
		
		if(saveManager.hasSaveData()) {
			
			String[] saveData;
			
			// Reward idle time
			timeStamp = Long.parseLong(saveManager.getTimeData());
			handleIdleTime();
			
			// Load saved player data
			saveData = saveManager.getPlayerData();
			player.setAffection(Integer.parseInt(saveData[0]));
			player.setAPS(Integer.parseInt(saveData[1]));
			player.setMoney(Integer.parseInt(saveData[2]));
			
			// Load saved inventory data
			String[] itemData;
			Array<Item> itemList = player.getInventory();
			
			saveData = saveManager.getInventoryData();
			for(int i = 1; i < saveData.length; i++) {
				
				itemData = saveData[i].split(":");
				Item item = new Item(ITEMID.valueOf(itemData[0]));
				item.setQuantity(Integer.parseInt(itemData[1]));
				item.setAPS(Integer.parseInt(itemData[2]));
				itemList.add(item);
			}
			
			// Load saved equip data
			itemList = player.getEquips();
			saveData = saveManager.getEquipData();
			for(int i = 0; i < saveData.length; i++) {
				
				itemData = saveData[i].split(":");
				if(!itemData[0].equals("0")) {
					
					Item item = new Item(ITEMID.valueOf(itemData[0]));
					item.setAPS(Integer.parseInt(itemData[1]));
					itemList.set(i, item);
				}
			}
			
			// Load saved achievement data
			Achievement[] achieveList = Achievement.values();
			saveData = saveManager.getAchievementData();
			for(int i = 0; i < saveData.length; i++) {
			
				boolean bValue = Boolean.parseBoolean(saveData[i]);
				if(bValue)
					achieveList[i].unlock();
			}
			
			// Load saved service data
			Array<Service> serviceList = Service.serviceList;
			saveData = saveManager.getServiceData();
			for(int i = 0; i < saveData.length; i++)
				serviceList.get(i).setLevel(Integer.parseInt(saveData[i]));
		}	
		
		// Start game
		setScreen(gameScreen);
	}
	
	public void handleIdleTime() {
		
		long idleTime = TimeUtils.timeSinceMillis(timeStamp);
		idleTime /= 1000;
		
		int affection;
		if(idleTime > IDLECAP)
			affection = player.getAPS() * IDLECAP;
		else
			affection = (int) (player.getAPS() * idleTime);
		
		player.addAffection(affection);
		
		if(player.hasCat())
			player.getCat().update(idleTime);
		
		if(player.isPenalized())
			player.updatePlayerLogic(idleTime);
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
		
		font.dispose();
		uuiAtlas.dispose();
		duiAtlas.dispose();
		
		SaveManager.getInstance().writeSave(this);
	}
}
