package com.qleek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.qleek.player.Item;
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

    // Game Settings
    public static float density;
	private static final int IDLECAP = 3600;
    private static final int defaultFontSize = 12;
    private static final Color defaultFontColor = Color.CYAN;

    // Game Assets
    public static Skin skin;
    public static SpriteBatch batch;
    private BitmapFont gameFont;
    private TextureAtlas uuiAtlas, duiAtlas;

    // Global Game Variables
	public Player player = new Player();

    // ???
	private Achievements achievements;
	private SoundManager soundManager;
	private UtilityListener achievementListener;

    // Game Screens
	public BaseScreen gameScreen, paegantScreen, shopScreen,
		inventoryScreen, wwyScreen;

    public long timeStamp;

	@Override
	public void create () {
		
		Gdx.input.setCatchBackKey(true);

        skin = new Skin();
        density = getDensity();
        batch = new SpriteBatch();

        // Load game assets
        loadGameFont();
        loadUserInterface();
        loadAtlases();
        loadGameText();
		
		// Initialize screens 
		initalizeScreens();

		// Sound and Achievements
		loadAchievementManager();
		loadSoundManager();
		
		// Start game
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
		
		gameFont.dispose();
		uuiAtlas.dispose();
		duiAtlas.dispose();
		
		//SaveManager.getInstance().writeSave(this);
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

    // Return screen density
    private static float getDensity() {
        return Gdx.graphics.getDensity();
    }

    // Load game font and adjust size according to density
    private void loadGameFont() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/catamaran-regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = (int) Math.ceil(defaultFontSize * density);
        parameter.color = defaultFontColor;

        gameFont = generator.generateFont(parameter);
        skin.add("default-font", this.gameFont);
        generator.dispose();
    }

    // Load user interface and density specific images
    private void loadUserInterface() {

        duiAtlas = new TextureAtlas(Gdx.files.internal("ui/dui.atlas"));
        skin.addRegions(duiAtlas);

        uuiAtlas = new TextureAtlas(Gdx.files.internal("ui/uui.atlas"));
        skin.addRegions(uuiAtlas);

        skin.load(Gdx.files.internal("ui/uui.json"));
    }

    private void loadAtlases() {

        TextureAtlas atlas;

        // Load Items
        atlas = new TextureAtlas(Gdx.files.internal("item/ItemPack.pack"));
        Item.setAtlas(atlas);

        // Load Paegant Items
        atlas = new TextureAtlas(Gdx.files.internal("paegant/paegant.pack"));
        Paegant.setAtlas(atlas);
    }

    private void loadGameText() {

        FileHandle fileHandle;

        // Load service text
        fileHandle = Gdx.files.internal("data/service.meow");
        Service.initServices(fileHandle.readString());

        // Load item text
        fileHandle = Gdx.files.internal("data/item.meow");
        Item.initItems(fileHandle.readString());

        // Load paegant text
        fileHandle = Gdx.files.internal("data/paegant.meow");
        Paegant.initPaegants(fileHandle.readString());
    }

    // Create screen instances
    private void initalizeScreens() {

        gameScreen = new GameScreen(this);
        paegantScreen = new PaegantScreen(this);
        shopScreen = new ShopScreen(this);
        inventoryScreen = new InventoryScreen(this);
        wwyScreen = new WWYScreen(this);
    }

    // Initialize sound functionality
    private void loadSoundManager() {

        soundManager = SoundManager.getInstance();
        //Observable.addObserver(SoundManager.getInstance());
        //Observable.addObserver(soundManager);
    }

    // Initialize achievement functionality
    private void loadAchievementManager() {

        achievements = Achievements.getInstance();
        achievementListener = new UtilityListener() {

            @Override
            public void achievementUnlocked(Achievement achievement) {
                ((BaseScreen) Qleek.this.getScreen()).displayAchievement(achievement);
            }
        };
        achievements.addListener(achievementListener);
        //Observable.addObserver(achievements);
    }
}