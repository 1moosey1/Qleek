package com.qleek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.Qleek;
import com.qleek.utils.TableUtils;

public class GameScreen extends BaseScreen {
	
	Table buttonLayout;
	TextButton paegantButton, shopButton, inventoryButton, wwyButton;
	Label meownyLabel;
	
	public GameScreen(Qleek game) {
		
		super(game);
		Gdx.input.setInputProcessor(HUD);
		
		buttonLayout = new Table();
		paegantButton = new TextButton("Pag", uiSkin);
		shopButton = new TextButton("Shp", uiSkin);
		inventoryButton = new TextButton("Inv", uiSkin);
		wwyButton = new TextButton("wwy", uiSkin);
		
		meownyLabel = new Label("Meowny: $$$$$", uiSkin);
		
		create();
	}
	
	public void create() {
		
		buttonLayout.setDebug(true);
		buttonLayout.defaults().expand().fill().uniform();
		
		// ----- buttonLayout 2 x 5 -----
		
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
		
			
		// ----- screenLayout 5 x 5 -----
		
		// Row One
		screenLayout.add(headerWidget.getLayout()).colspan(5).fill();
		screenLayout.row();
		
		// Row Two
		screenLayout.add(meownyLabel).colspan(5).top().left();
		screenLayout.row();
		
		// Row Three
		screenLayout.add();
		screenLayout.row();
		
		// Row Four
		screenLayout.add();
		screenLayout.row();
		
		// Row Five
		screenLayout.add(buttonLayout).colspan(5).fill();
		
		// ----- End screenlayout -----
	}

	@Override
	public void show() {	
		headerWidget.disableBack();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
	}

	@Override
	public void hide() {
		headerWidget.enableBack();
	}
}
