package com.qleek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Array;
import com.qleek.Qleek;
import com.qleek.player.Item;
import com.qleek.player.Service;
import com.qleek.utils.UtilityListener;
import com.qleek.widgets.CostChangable;
import com.qleek.widgets.ItemWidget;
import com.qleek.widgets.ServiceWidget;

public class ShopScreen extends BaseScreen {
	
	private Table shopLayout;
	private ScrollPane scrollPane;
	private VerticalGroup verticalGroup;
	
	private boolean serviceShowing;
	private TextButton serviceButton, shopButton;
	private InputListener shopListener;
	private UtilityListener widgetListener;
	
	private Array<CostChangable> changableWidgets;

	public ShopScreen(Qleek game) {
		
		super(game);
		
		shopLayout = new Table();
		verticalGroup = new VerticalGroup();
		scrollPane = new ScrollPane(verticalGroup);
		
		serviceButton = new TextButton("Services", uiSkin);
		shopButton = new TextButton("Shop", uiSkin);
		
		changableWidgets = new Array<CostChangable>();
		
		create();
	}

	private void create() {
		
		serviceButton.setName("service");
		shopButton.setName("shop");
		
		verticalGroup.fill();
		
		// ----- shopLayout 2 x 2 -----
		shopLayout.setDebug(true);
		shopLayout.defaults().expand().fill();
		
		// Row One
		shopLayout.add(serviceButton).uniformX();
		shopLayout.add(shopButton).uniformX();	
		shopLayout.row();
			
		// Row Two
		shopLayout.add(scrollPane).colspan(2)
			.width(Gdx.graphics.getWidth() * 0.9F)
			.height(Gdx.graphics.getHeight() * 0.725F);
		
		// ----- End shopLayout -----
		
		// ----- screenLayout 2 x 1 -----
		
		// Row Two
		screenLayout.add(shopLayout).fill()
			.height(Gdx.graphics.getHeight() * 0.8F);
		
		// ----- End screenLayout -----
		
		shopListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				String name = event.getListenerActor().getName();
				
				if(name == serviceButton.getName())
					populateService();
				
				else if(name == shopButton.getName())
					populateShop();
			}
		};
		
		widgetListener = new UtilityListener() {
			
			@Override
			public void serviceWidgetAction(ServiceWidget widget) {
				
				Service service = widget.getService();
				if(qleek.player.canPurchase(service.getCost()) &&
						service.isUpgradable()) {
				
					qleek.player.purchase(service.getCost());
					qleek.player.addAPS(service.upgrade());
					widget.updateDisplay();
				}
			}
			
			@Override
			public void itemWidgetAction(ItemWidget widget) {
				
				Item item = widget.getItem();
				if(qleek.player.canPurchase(item.getCost())) {
					
					qleek.player.purchase(item.getCost());
					qleek.player.addItem(item.getItemID());
					widget.updateDisplay();
				}
			}
		};
		
		serviceButton.addListener(shopListener);
		shopButton.addListener(shopListener);
	}
	
	private void populateService() {
		
		if(!verticalGroup.hasChildren() || !serviceShowing) {
			
			changableWidgets.clear();
			verticalGroup.clearChildren();
			
			for(Service service : Service.getServices()) {
				
				ServiceWidget widget = new ServiceWidget(service);
				widget.addListener(widgetListener);
				
				changableWidgets.add(widget);
				verticalGroup.addActor(widget.getLayout());
			}
			
			serviceShowing = true;
		}
	}
	
	private void populateShop() {
				
		if(serviceShowing) {
			
			changableWidgets.clear();
			verticalGroup.clearChildren();

			for(Item.ITEMID itemID : Item.shopItems) {
		
				Item item = new Item(
						itemID, qleek.player.getAffection(), qleek.player.getMoney());
				item.setQuantity(qleek.player.howMany(itemID));
				
				ItemWidget widget = new ItemWidget(item);
				widget.addListener(widgetListener);

				changableWidgets.add(widget);
				verticalGroup.addActor(widget.getLayout());
			}
			
			serviceShowing = false;
		}
	}
	
	@Override
	public void show() {
		
		super.show();
		populateService();
	}
	
	@Override
	public void render(float delta) {
		
		super.render(delta);
			
		for(CostChangable widget : changableWidgets)
			widget.updateCostProperty(qleek.player.getMoney());
	}
}
