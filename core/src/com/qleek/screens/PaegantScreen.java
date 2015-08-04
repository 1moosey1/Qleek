package com.qleek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Array;
import com.qleek.Qleek;
import com.qleek.player.Item;
import com.qleek.player.Paegant;
import com.qleek.utils.Prize;
import com.qleek.utils.UtilityListener;
import com.qleek.widgets.PaegantWidget;
import com.qleek.widgets.TimedDialog;

public class PaegantScreen extends BaseScreen {
	
	private Table paegantLayout;
	private ScrollPane scrollPane;
	private VerticalGroup verticalGroup;
	
	private boolean localShowing;
	private TextButton localButton, nationalButton;
	private InputListener paegantListener;
	private UtilityListener widgetListener;

	public PaegantScreen(Qleek game) {
		
		super(game);
		
		paegantLayout = new Table();
		verticalGroup = new VerticalGroup();
		scrollPane = new ScrollPane(verticalGroup);
		
		localButton = new TextButton("Local", uiSkin);
		nationalButton = new TextButton("National", uiSkin);
		
		create();
	}

	private void create() {
		
		localButton.setName("local");
		nationalButton.setName("national");
		
		verticalGroup.fill();
		
		// ----- paegantLayout 2 x 2 -----
		
		paegantLayout.setDebug(true);
		paegantLayout.defaults().expand().fill();
		
		// Row One
		paegantLayout.add(localButton).uniformX();
		paegantLayout.add(nationalButton).uniformX();	
		paegantLayout.row();
			
		// Row Two
		paegantLayout.add(scrollPane).colspan(2)
			.width(Gdx.graphics.getWidth() * 0.9F)
			.height(Gdx.graphics.getHeight() * 0.725F);
		
		// ----- End paegantLayout -----
		
		// ----- screenLayout 2 x 1 -----
		
		// Row Two
		screenLayout.add(paegantLayout).fill()
			.height(Gdx.graphics.getHeight() * 0.8F);
		
		// ----- End screenLayout -----
		
		paegantListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				String name = event.getListenerActor().getName();
				
				if(name == localButton.getName())
					populateLocal();
				
				else if(name == nationalButton.getName())
					populateNational();
			}
		};
		
		widgetListener = new UtilityListener() {
			
			@Override
			public void paegantWidgetAction(Paegant paegant) {
				
				final Prize prize = paegant.enter(qleek.player.getAffection());
				
				qleek.player.addMoney(prize.getMoney());
				qleek.player.addItem(prize.getItem());
				qleek.player.setAffection(0);
				
				// Display prize information via dialog
				new TimedDialog() {
					
					@Override
					public void create() {
						
						Table dialogLayout = getContentTable();
					
						int place = prize.getPlace();
						if(place == 1)
							dialogLayout.add("1st Place").colspan(2);
						else if(place == 2)
							dialogLayout.add("2nd Place").colspan(2);
						else if(place == 3)
							dialogLayout.add("3rd Place").colspan(2);
						else
							dialogLayout.add("You did not place");
						dialogLayout.row();
					
						if(prize.getItem() != null) {
							
							dialogLayout.add(new Image(Item.getRegion(prize.getItem())));
							dialogLayout.add(prize.getItem().name());
						}
						dialogLayout.row();
					
						dialogLayout.add("Earnings: " + prize.getMoney()).colspan(2);
					}	
				}.show(HUD);
			}
		};
		
		localButton.addListener(paegantListener);
		nationalButton.addListener(paegantListener);
	}
	
	private void populateLocal() {
		
		Array<Paegant> paegantList = Paegant.getServices();
		if(!verticalGroup.hasChildren() || !localShowing) {
			
			verticalGroup.clearChildren();
			for(Paegant paegant : paegantList) {
				
				PaegantWidget widget = new PaegantWidget(paegant);
				widget.addListener(widgetListener);
				
				verticalGroup.addActor(widget.getLayout());
			}
			
			localShowing = true;
		}
	}
	
	private void populateNational() {
		// To do
	}
	
	@Override
	public void show() {
		
		super.show();
		populateLocal();
	}
}
