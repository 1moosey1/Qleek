package com.qleek.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.Qleek;
import com.qleek.player.Item;
import com.qleek.player.Item.ITEMID;
import com.qleek.player.Paegant;
import com.qleek.utils.Prize;
import com.qleek.utils.UtilityListener;
import com.qleek.widgets.ExitDialog;
import com.qleek.widgets.PaegantWidget;
import com.qleek.widgets.TimedDialog;

public class PaegantScreen extends BaseScreen {
	
	private ScrollPane scrollPane;
	private HorizontalGroup horizontalGroup;
	private InputListener paegantListener;
	private UtilityListener widgetListener;
	private Paegant selectedPaegant;
	
	private ExitDialog paegantDialog;

	public PaegantScreen(Qleek game) {
		
		super(game);
		
		horizontalGroup = new HorizontalGroup();
		scrollPane = new ScrollPane(horizontalGroup);
		
		create();
	}

	private void create() {
		
		horizontalGroup.fill();
		screenLayout.add(scrollPane);
		
		paegantListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				paegantDialog.setVisible(false);
				final Prize prize = selectedPaegant.enter(qleek.player.getAffection());
				
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
							
							dialogLayout.add(new Image(Item.itemAtlas.findRegion(prize.getItem().name())));
							dialogLayout.add(prize.getItem().name());
						}
						dialogLayout.row();
					
						dialogLayout.add("Earnings: " + prize.getMoney()).colspan(2);
					}	
					
					@Override
					public void hide() {
					
						super.hide();
						paegantDialog.setVisible(true);
					}
				}.show(HUD);
			}
		};
		
		widgetListener = new UtilityListener() {
			
			@Override
			public void paegantWidgetAction(Paegant paegant) {
				
				selectedPaegant = paegant;
				
				paegantDialog = new ExitDialog() {

					@Override
					public void create() {
						
						Table dialogLayout = getContentTable();
						dialogLayout.setSkin(uiSkin);
						
						dialogLayout.add(selectedPaegant.getName()).colspan(3).padBottom(20);
						dialogLayout.row();
						
						dialogLayout.add(selectedPaegant.getDescription()).colspan(3);
						dialogLayout.row();
						
						dialogLayout.add("1st Place");
						dialogLayout.add("2nd Place");
						dialogLayout.add("3rd Place");
						dialogLayout.row();
						
						ITEMID[] rewards = selectedPaegant.getRewards();
						dialogLayout.add(new Image(Item.itemAtlas.findRegion(rewards[0].name()))).padLeft(20);
						dialogLayout.add(new Image(Item.itemAtlas.findRegion(rewards[1].name())));
						dialogLayout.add(new Image(Item.itemAtlas.findRegion(rewards[2].name()))).padRight(20);
						dialogLayout.row();
						
						double[] odds = selectedPaegant.getOdds();
						dialogLayout.add(odds[0] + "%");
						dialogLayout.add(odds[1] + "%");
						dialogLayout.add(odds[2] + "%");
						dialogLayout.row();
						
						TextButton enterButton = new TextButton("Enter Paegant", uiSkin);
						dialogLayout.add(enterButton).colspan(3);
						enterButton.addListener(paegantListener);
					}
				};
				paegantDialog.show(HUD);
			}
		};
	}
	
	private void populateLocal() {
			
		horizontalGroup.clearChildren();
		for(Paegant paegant : Paegant.paegantList) {
				
			PaegantWidget widget = new PaegantWidget(paegant);
			widget.addListener(widgetListener);
			
			System.out.println("Added");
			horizontalGroup.addActor(widget.getLayout());
		}
	}

	@Override
	public void show() {
		
		super.show();
		populateLocal();
	}
	
	@Override
	public void dispose() {
		
		super.dispose();
		Paegant.paegantAtlas.dispose();
	}
}
