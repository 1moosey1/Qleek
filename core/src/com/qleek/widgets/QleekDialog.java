package com.qleek.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.Qleek;
import com.qleek.player.Item;
import com.qleek.utils.Achievement;
import com.qleek.utils.Prize;
import com.qleek.utils.UtilityListener;

public class QleekDialog extends Dialog {
	
	private UtilityListener dialogListener;
	
	public QleekDialog(Achievement achievement) {
		
		super("Achievement Unlocked", Qleek.skin);
		
		Table dialogLayout = getContentTable();
		dialogLayout.defaults().padBottom(10F);
		dialogLayout.add(achievement.getName());
		dialogLayout.row();
		
		dialogLayout.add(achievement.getDescription());
		dialogLayout.row();
		
		getButtonTable().defaults().fill().expand()
			.height(Gdx.graphics.getHeight() * 0.04F);	
		button("Pawesome");
	}
	
	public QleekDialog(Prize prize) {
		
		super("Congradulations", Qleek.skin);
		
		Table dialogLayout = getContentTable();
		dialogLayout.defaults().pad(10F);
		
		if(prize.getItem() != null)
			dialogLayout.add(new Image(Item.getRegion(prize.getItem())));
		
		int place = prize.getPlace();
		if(place == 1)
			dialogLayout.add("1st Place");
		else if(place == 2)
			dialogLayout.add("2nd Place");
		else if(place == 3)
			dialogLayout.add("3rd Place");
		else
			dialogLayout.add("You did not place");
		
		dialogLayout.row();
		
		dialogLayout.add("Earnings: " + prize.getMoney()).colspan(2);
		dialogLayout.row();
		
		getButtonTable().defaults().fill().expand()
			.height(Gdx.graphics.getHeight() * 0.04F);	
		button("Pawesome");
	}
	
	public QleekDialog(final Item item) {
		
		super(item.getName(), Qleek.skin);
		
		Table dialogLayout = getContentTable();
		dialogLayout.defaults().pad(10F);
		
		TextButton exitButton = new TextButton("X", Qleek.skin);
		TextButton useButton = new TextButton("Use", Qleek.skin);
		TextButton equipButton = new TextButton("Equip", Qleek.skin);
		TextButton craftButton = new TextButton("Craft", Qleek.skin);
		
		useButton.setName("use");
		equipButton.setName("equip");
		craftButton.setName("craft");
		
		InputListener inputListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				String name = event.getListenerActor().getName();
				if(name == "use")
					dialogListener.useItemAction();
				else if(name == "equip")
					dialogListener.equipItemAction();
				else if(name == "craft")
					dialogListener.craftItemAction();
					
				QleekDialog.this.hide();
			}
		};
		
		exitButton.addListener(inputListener);
		useButton.addListener(inputListener);
		equipButton.addListener(inputListener);
		craftButton.addListener(inputListener);
		
		// Row One
		dialogLayout.add();
		dialogLayout.add();
		dialogLayout.add(exitButton).colspan(3).right().fill();
		dialogLayout.row();
		
		// Row Two
		dialogLayout.add(new Image(item.getRegion())).colspan(2);
		dialogLayout.add(item.getName());
		dialogLayout.row();
		
		// Row Three
		dialogLayout.add(item.getDescription()).colspan(3);
		dialogLayout.row();
		
		// Row Four
		
		if(item.isUsable())
			dialogLayout.add(useButton).uniform().fill();
		else
			dialogLayout.add().uniform().fill();
		
		dialogLayout.add(equipButton).uniform().fill();
		dialogLayout.add(craftButton).uniform().fill()
			.height(Gdx.graphics.getHeight() * 0.04F);
	}
	
	public void addListener(UtilityListener listener) {
		dialogListener = listener;
	}
}
