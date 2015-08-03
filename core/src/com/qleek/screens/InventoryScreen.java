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
import com.qleek.utils.Achievement;
import com.qleek.utils.CraftManager;
import com.qleek.utils.Recipe;
import com.qleek.utils.UtilityListener;
import com.qleek.widgets.QleekDialog;
import com.qleek.widgets.SquareWidget;
import com.qleek.widgets.StatusWidget;
import com.qleek.widgets.TableGroup;

public class InventoryScreen extends BaseScreen {
	
	private Table inventoryLayout, innerLayout, craftLayout;
	private ScrollPane inventoryPane, achievementPane;
	private TableGroup inventoryGroup, achievementGroup;
	private VerticalGroup vanityGroup;
	
	private boolean inventoryShowing;
	private TextButton inventoryButton, achievementButton;
	private InputListener inventoryListener;
	private UtilityListener dialogListener;
	private StatusWidget[] statusWidgets;
	
	public InventoryScreen(Qleek game) {
		
		super(game);
		
		inventoryLayout = new Table();
		innerLayout = new Table();
		craftLayout = new Table();

		inventoryGroup = new TableGroup(4);
		vanityGroup = new VerticalGroup();
		achievementGroup = new TableGroup(4);
		
		inventoryPane = new ScrollPane(inventoryGroup);
		achievementPane = new ScrollPane(achievementGroup);
		
		inventoryButton = new TextButton("Inventory", uiSkin);
		achievementButton = new TextButton("Achievements", uiSkin);
		
		statusWidgets = new StatusWidget[7];
		for(int i = 0; i < statusWidgets.length; i++)
			statusWidgets[i] = new StatusWidget();
		
		create();
	}

	private void create() {
		
		inventoryButton.setName("inventory");
		achievementButton.setName("achievements");
		statusWidgets[3].setName("equip");
		statusWidgets[4].setName("equip");
		statusWidgets[5].setName("equip");
		statusWidgets[6].setName("equip");
				
		// ----- craftLayout 1 x 3 -----
		
		// Row One
		craftLayout.add(statusWidgets[0].getLayout());
		craftLayout.add(statusWidgets[1].getLayout());
		craftLayout.add(statusWidgets[2].getLayout());

		// ----- End craftLayout -----
		
		// ----- vanityGroup -----
		for(int i = 3; i < statusWidgets.length; i++) {
			
			statusWidgets[i].setIndex(i-3);
			vanityGroup.addActor(statusWidgets[i].getLayout());
		}
		
		// ----- End vanityGroup -----
		
		// ----- inventoryLayout 2 x 2 -----
		inventoryLayout.defaults().expand().fill();
		
		// Row One
		inventoryLayout.add(inventoryButton).uniformX();
		inventoryLayout.add(achievementButton).uniformX();	
		inventoryLayout.row();
			
		// Row Two
		inventoryLayout.add(innerLayout).colspan(2)
			.height(Gdx.graphics.getHeight() * 0.725F);
		
		// ----- End inventoryLayout -----
		
		// ----- screenLayout 2 x 1 -----
		
		// Row Two
		screenLayout.add(inventoryLayout).fill()
			.height(Gdx.graphics.getHeight() * 0.8F);
		
		// ----- End screenLayout -----
		
		inventoryListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				String name = event.getListenerActor().getName();
				
				if(name == inventoryButton.getName())
					populateInventory();
				
				else if(name == achievementButton.getName())
					populateAchievements();
			}
		};
		
		dialogListener = new UtilityListener() {
			
			private Item item;
			
			@Override
			public void squareWidgetAction(Item item) {
				
				this.item = item;
				QleekDialog dialog = new QleekDialog(item);
				dialog.addListener(dialogListener);
				dialog.show(HUD);
			}
			
			@Override
			public void useItemAction() {
				
			}
			
			@Override
			public void equipItemAction() {

				boolean equipped = false;
				
				if(!statusWidgets[3].isReady()) {
					
					equipped = true;
					statusWidgets[3].setItem(item);
					qleek.player.getEquips().set(0, item);	
				} 
				else if(!statusWidgets[4].isReady()) {
					
					equipped = true;
					statusWidgets[4].setItem(item);
					qleek.player.getEquips().set(1, item);	
				} 
				else if(!statusWidgets[5].isReady()) {
					
					equipped = true;
					statusWidgets[5].setItem(item);
					qleek.player.getEquips().set(2, item);
				} 
				else if(!statusWidgets[6].isReady()) {
					
					equipped = true;
					statusWidgets[6].setItem(item);
					qleek.player.getEquips().set(3, item);
				}
				
				if(equipped) {
					
					qleek.player.removeItem(item.getItemID());
					reloadInventory();
				}
			}
			
			@Override
			public void craftItemAction() {
				
				qleek.player.removeItem(item.getItemID());
				
				if(!statusWidgets[0].isReady())
					statusWidgets[0].setItem(item);
				
				else if(!statusWidgets[1].isReady())
					statusWidgets[1].setItem(item);
				
				else {
					
					statusWidgets[2].setItem(item);
					craft();
				}
				
				reloadInventory();
			}
			
			@Override
			public void statusWidgetAction(StatusWidget widget) {
				
				if(widget.getItem().isSpecial())
					qleek.player.addItem(widget.getItem());
				else
					qleek.player.addItem(widget.getItemID());
				
				if(widget.getName() == "equip")
					qleek.player.unequip(widget.getIndex());
				
				widget.reset();
				reloadInventory();
			}
		};
		
		inventoryButton.addListener(inventoryListener);
		achievementButton.addListener(inventoryListener);
		for(int i = 0; i < statusWidgets.length; i++)
			statusWidgets[i].addListener(dialogListener);
	}
	
	private void craft() {
		
		Item item1, item2, item3;
		item1 = statusWidgets[0].getItem();
		item2 = statusWidgets[1].getItem();
		item3 = statusWidgets[2].getItem();
		
		if(item1.isSpecial() || item2.isSpecial() || item3.isSpecial()) {
			//TO DO
		}
		else {
			
			Recipe recipe = new Recipe(
					statusWidgets[0].getItemID(),
					statusWidgets[1].getItemID(),
					statusWidgets[2].getItemID());
			
			qleek.player.addItem(CraftManager.getInstance().craft(recipe));
		}
		
		statusWidgets[0].reset();
		statusWidgets[1].reset();
		statusWidgets[2].reset();
	}
	
	private void populateInventory() {
		
		if(!inventoryShowing) {
			
			// Construct/Reconstruct table
			// ----- innerLayout 2 x 2 -----	
			innerLayout.clear();
		
			// Row One
			innerLayout.add(inventoryPane).expand();
			innerLayout.add(vanityGroup);
			innerLayout.row();
		
			// Row Two
			innerLayout.add(craftLayout).colspan(2)
				.height(Gdx.graphics.getHeight() * 0.15F);
		
			// ----- End innerLayout -----
		
			inventoryGroup.clearGroup();
			for(Item item : qleek.player.getInventory()) {
				
				SquareWidget widget = new SquareWidget(item);
				widget.addListener(dialogListener);
				inventoryGroup.addToGroup(widget.getLayout());
			}
			
			Array<Item> equips = qleek.player.getEquips();
			for(int i = 3; i < statusWidgets.length; i++)
				statusWidgets[i].setItem(equips.get(i-3));
			
			inventoryShowing = true;
		}
	}
	
	private void populateAchievements() {
		
		if(inventoryShowing) {
			
			// Construct/Reconstruct Table
			// ----- innerLayout 1 x 1 -----	
			innerLayout.clear();
		
			// Row One
			innerLayout.add(achievementPane).expand();
		
			// ----- End innerLayout -----
			
			achievementGroup.clearGroup();
			for(Achievement achievement : Achievement.values())
				achievementGroup.addToGroup(new Image(qleek.itemAtlas.findRegion("base")));
			
			inventoryShowing = false;
		}
	}
	
	private void reloadInventory() {
		
		inventoryGroup.clearGroup();
		for(Item item : qleek.player.getInventory()) {
			
			SquareWidget widget = new SquareWidget(item);
			widget.addListener(dialogListener);
			inventoryGroup.addToGroup(widget.getLayout());
		}
	}
	
	@Override
	public void show() {
		
		super.show();
		populateInventory();
	}
	
	@Override
	public void hide() {
		inventoryShowing = false;
	}
}
