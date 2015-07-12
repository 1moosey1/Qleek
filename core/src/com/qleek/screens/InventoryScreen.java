package com.qleek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.qleek.Qleek;
import com.qleek.player.Item;
import com.qleek.player.Item.ITEMID;
import com.qleek.utils.Achievement;
import com.qleek.utils.CraftManager;
import com.qleek.utils.Recipe;
import com.qleek.utils.UtilityListener;
import com.qleek.widgets.CraftWidget;
import com.qleek.widgets.QleekDialog;
import com.qleek.widgets.SquareWidget;
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
	
	// Three craft widgets
	private CraftWidget craftWidget1, craftWidget2, craftWidget3;
	
	// Four equip widgets
	// to do
	
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
		
		craftWidget1 = new CraftWidget();
		craftWidget2 = new CraftWidget();
		craftWidget3 = new CraftWidget();
		
		create();
	}

	private void create() {
		
		inventoryButton.setName("inventory");
		achievementButton.setName("achievements");
		craftWidget1.getLayout().setName("craftwidget1");
		craftWidget2.getLayout().setName("craftwidget2");
		craftWidget3.getLayout().setName("craftwidget3");
		
		inventoryGroup.fill();
		
		// ----- craftLayout 1 x 3 -----
		
		craftLayout.setDebug(true);
		
		// Row One
		craftLayout.add(craftWidget1.getLayout());
		craftLayout.add(craftWidget2.getLayout());
		craftLayout.add(craftWidget3.getLayout());

		// ----- End craftLayout -----
		
		// ----- inventoryLayout 2 x 2 -----
		
		inventoryLayout.setDebug(true);
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
			
			private ITEMID itemID;
			
			@Override
			public void squareWidgetAction(Item item) {
				
				itemID = item.getItemID();
				QleekDialog dialog = new QleekDialog(item);
				dialog.addListener(dialogListener);
				dialog.show(HUD);
			}
			
			@Override
			public void useItemAction() {
				
			}
			
			@Override
			public void equipItemAction() {
				
			}
			
			@Override
			public void craftItemAction() {
				
				qleek.player.subtractItem(itemID);
				reloadInventory();
				
				if(!craftWidget1.isReady())
					craftWidget1.setItem(itemID);
				
				else if(!craftWidget2.isReady())
					craftWidget2.setItem(itemID);
				
				else {
					
					craftWidget3.setItem(itemID);
					
					Recipe recipe = new Recipe(
							craftWidget1.getItemID(),
							craftWidget2.getItemID(),
							craftWidget3.getItemID());
					
					ITEMID creation =
							CraftManager.getInstance().craft(recipe);	
					qleek.player.addItem(creation);
					
					craftWidget1.reset();
					craftWidget2.reset();
					craftWidget3.reset();
					reloadInventory();
				}
			}
			
			@Override
			public void craftWidgetAction(CraftWidget widget) {
				
				qleek.player.addItem(widget.getItemID());
				widget.reset();
				
				reloadInventory();
			}
		};
		
		inventoryButton.addListener(inventoryListener);
		achievementButton.addListener(inventoryListener);
		craftWidget1.addListener(dialogListener);
		craftWidget2.addListener(dialogListener);
		craftWidget3.addListener(dialogListener);
	}
	
	private void populateInventory() {
		
		if(!inventoryShowing) {
			
			// Construct/Reconstruct table
			// ----- innerLayout 2 x 2 -----
		
			innerLayout.setDebug(true);
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
		
			vanityGroup.clear();
			vanityGroup.addActor(new Image(qleek.itemAtlas.findRegion("base")));
			vanityGroup.addActor(new Image(qleek.itemAtlas.findRegion("base")));
			vanityGroup.addActor(new Image(qleek.itemAtlas.findRegion("base")));
			
			inventoryShowing = true;
		}
	}
	
	private void populateAchievements() {
		
		if(inventoryShowing) {
			
			// Construct/Reconstruct Table
			// ----- innerLayout 1 x 1 -----
			
			innerLayout.setDebug(true);
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
		
		inventoryShowing = false;
		populateInventory();
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
