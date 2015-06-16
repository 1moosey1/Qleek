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
import com.qleek.player.Service;
import com.qleek.utils.WidgetListener;
import com.qleek.widgets.ServiceWidget;

public class ShopScreen extends BaseScreen {
	
	private Table shopLayout;
	private ScrollPane scrollPane;
	private VerticalGroup verticalGroup;
	
	private boolean serviceShowing;
	private TextButton serviceButton, shopButton;
	private InputListener shopListener;
	private WidgetListener widgetListener;

	public ShopScreen(Qleek game) {
		
		super(game);
		
		shopLayout = new Table();
		verticalGroup = new VerticalGroup();
		scrollPane = new ScrollPane(verticalGroup);
		
		serviceButton = new TextButton("Services", uiSkin);
		shopButton = new TextButton("Shop", uiSkin);
		
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
		shopLayout.add(serviceButton);
		shopLayout.add(shopButton);	
		shopLayout.row();
			
		//Row Two
		shopLayout.add(scrollPane).colspan(2)
			.width(Gdx.graphics.getWidth() * 0.9F)
			.height(Gdx.graphics.getHeight() * 0.725F);
		
		// ----- End shopLayout -----
		
		// ----- screenLayout 2 x 1 -----
		
		//Row Two
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
		
		widgetListener = new WidgetListener() {
			
			@Override
			public void serviceWidgetAction(ServiceWidget widget) {
				
				Service service = widget.getService();
				boolean purchased = qleek.player.purchaseService(service);
				
				if(purchased)
					widget.updateDisplay();
			}
		};
		
		serviceButton.addListener(shopListener);
		shopButton.addListener(shopListener);
	}
	
	private void populateService() {
		
		Array<Service> serviceList = Service.getServices();
		if(!verticalGroup.hasChildren() || !serviceShowing) {
			
			verticalGroup.clearChildren();
			for(Service service : serviceList) {
				
				ServiceWidget widget = new ServiceWidget(service);
				widget.addListener(widgetListener);
				
				verticalGroup.addActor(widget.getLayout());
			}
			
			serviceShowing = true;
		}
	}
	
	private void populateShop() {
		// To do
	}
	
	@Override
	public void show() {
		
		super.show();
		populateService();
	}
}
