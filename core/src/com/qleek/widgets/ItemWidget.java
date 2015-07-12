package com.qleek.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.player.Item;

public class ItemWidget extends BaseWidget implements CostChangable {
	
	private Item item;
	private TextButton purchaseButton;
	private Label nameLabel, descriptionLabel, quantityLabel, costLabel;
	
	public ItemWidget(Item item) {
		
		this.item = item;
		
		purchaseButton = new TextButton("Purchase", uiSkin);
		nameLabel = new Label(item.getName(), uiSkin);
		descriptionLabel = new Label(item.getDescription(), uiSkin);
		quantityLabel = new Label("x" + item.getQuantity(), uiSkin);
		costLabel = new Label("$" + item.getCost(), uiSkin);
		
		create();
	}

	private void create() {
		
		// ----- widgetLayout - 2 x 3 -----
		
		widgetLayout.setDebug(true);
		widgetLayout.defaults().expand().uniform();
		
		// Row One
		widgetLayout.add(nameLabel).left();
		widgetLayout.add(quantityLabel);
		widgetLayout.add(costLabel);
		widgetLayout.row();
		
		// Row Two
		widgetLayout.add(descriptionLabel).colspan(2).left();
		widgetLayout.add(purchaseButton).fill()
			.height(Gdx.graphics.getHeight() * 0.07F);
		widgetLayout.row();
		
		// ----- End widgetLayout -----
		
		InputListener itemListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				widgetListener.itemWidgetAction(ItemWidget.this);
			}
		};
		
		purchaseButton.addListener(itemListener);
	}
	
	public Item getItem() {
		return item;
	}
	
	@Override
	public void updateDisplay() {
		
		item.incQuantity();
		quantityLabel.setText("x" + item.getQuantity());
	}
	
	@Override
	public void updateCostProperty(int playerMoney) {
		
		if(item.getCost() > playerMoney)
			purchaseButton.setDisabled(true);
		else
			purchaseButton.setDisabled(false);
	}
}
