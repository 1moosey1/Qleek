package com.qleek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.Qleek;
import com.qleek.utils.TableUtils;
import com.qleek.widgets.ExitDialog;

public class GameScreen extends BaseScreen {
	
	private Table buttonLayout;
	private TextButton paegantButton, shopButton, inventoryButton, wwyButton;
	
	// Test cat image
	private Texture texture;
	
	public GameScreen(Qleek game) {
		
		super(game);
		
		buttonLayout = new Table();
		paegantButton = new TextButton("Pag", uiSkin);
		shopButton = new TextButton("Shp", uiSkin);
		inventoryButton = new TextButton("Inv", uiSkin);
		wwyButton = new TextButton("Wwy", uiSkin);
		
		texture = new Texture(Gdx.files.internal("cat.png"));
		
		create();
	}
	
	private void create() {
		
		headerWidget.disableBack();
		paegantButton.setName("paegant");
		shopButton.setName("shop");
		inventoryButton.setName("inventory");
		wwyButton.setName("wwy");
		
		// ----- buttonLayout 2 x 5 -----
		
		buttonLayout.setDebug(true);
		buttonLayout.defaults().expand().fill().uniform();
		
		// Row One 
		TableUtils.addBlankCells(buttonLayout, 4);
		buttonLayout.add(wwyButton);
		buttonLayout.row();
		
		// Row Two
		TableUtils.addBlankCells(buttonLayout, 2);
		buttonLayout.add(paegantButton);
		buttonLayout.add(shopButton);
		buttonLayout.add(inventoryButton);
		
		// ----- End buttonlayout -----	
			
		// ----- screenLayout 2 x 1 -----
		
		// Row Two
		screenLayout.add(buttonLayout).fill().bottom()
			.height(Gdx.graphics.getHeight() * 0.20F);
		
		// ----- End screenlayout -----
		
		// Inner class for handling hud buttons (no reference saved)
		InputListener menuListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {	
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				String name = event.getListenerActor().getName();
				
				if(name == paegantButton.getName())
					qleek.setScreen(qleek.paegantScreen);
				
				else if(name == shopButton.getName())
					qleek.setScreen(qleek.shopScreen);
				
				else if(name == inventoryButton.getName())
					qleek.setScreen(qleek.inventoryScreen);
				
				else if(name == wwyButton.getName())
					qleek.setScreen(qleek.wwyScreen);
			}
		};
		
		paegantButton.addListener(menuListener);
		shopButton.addListener(menuListener);
		inventoryButton.addListener(menuListener);
		wwyButton.addListener(menuListener);
		
		// Seperate instance that enables touch responce for this screen
		inputMultiplexer.removeProcessor(screenProcessor);
		screenProcessor = new GameProcessor();
		inputMultiplexer.addProcessor(screenProcessor);
	}
	
	private void displayCatNotif() {
		
		new ExitDialog() {

			@Override
			public void create() {
				
				Table dialogLayout = getContentTable();
				
				dialogLayout.add("Where is your cat?");
				dialogLayout.row();
				
				dialogLayout.add("You seem to be lacking a cat").padTop(20);
				dialogLayout.row();
				
				dialogLayout.add("Head over to the options menu to adopt a new one");
			}	
		}.show(HUD);
	}
	
	@Override 
	public void draw() {
		
		super.draw();
		qleek.batch.begin();
		qleek.batch.draw(texture, 142, 416);
		qleek.batch.end();
	}
	
	@Override
	public void show() {
		
		super.show();
		if(qleek.player.getCat() == null)
			displayCatNotif();
	}
	
	@Override
	public void dispose() {
		
		super.dispose();
		texture.dispose();
	}
	
	private class GameProcessor extends ScreenProcessor {
		
		private int tdx, tdy, cumulatedDistance;
		private final int distance = 128;
		
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			
			if(touchingCat(screenX, screenY)) {
				
				tdx = screenX;
				tdy = screenY;
			}
			
			return true;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			
			cumulatedDistance = 0;		
			return true;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			
			if(touchingCat(screenX, screenY)) {
				
				cumulatedDistance += Math.sqrt(Math.pow(screenX - tdx, 2) 
						+ Math.pow(screenY - tdy, 2));
				
				System.out.println(cumulatedDistance);
					
				if(cumulatedDistance >= distance) {
					
						qleek.player.addAffection(1);
						cumulatedDistance = 0;
				}
			}
			
			tdx = screenX;
			tdy = screenY;
			
			return true;
		}
		
		public boolean touchingCat(int x, int y) {
			
			if((x >= 142 && x <= 398) && 
					(y >= 416 && y <= 544))
				
				return true;
			
			return false;
		}
	}
}
