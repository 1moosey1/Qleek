package com.qleek.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.player.Service;

public class ServiceWidget extends BaseWidget {
	
	private Service service;
	private TextButton upgradeButton;
	private Label nameLabel, descriptionLabel, levelLabel, apsLabel, costLabel;
	private InputListener serviceListener;
	
	public ServiceWidget(Service srv) {
		
		service = srv;
		
		widgetLayout = new Table();
		upgradeButton = new TextButton("Upgrade", uiSkin);
		nameLabel = new Label(service.getName(), uiSkin);
		levelLabel = new Label ("Lv. " + service.getLevel(), uiSkin);
		descriptionLabel = new Label(service.getDescription(), uiSkin);
		costLabel = new Label("Cost\n$"  + service.getCost(), uiSkin);
		
		apsLabel = new Label(
				"Current: " + service.getCAPS() + " / s \n" +
				"Next: " + service.getNAPS() + " / s", uiSkin);
		
		create();
	}

	private void create() {
		
		nameLabel.setWrap(true);
		descriptionLabel.setWrap(true);
		
		// ----- widgetLayout - 3 x 2 -----
		
		widgetLayout.setDebug(true);
		widgetLayout.defaults().expand().uniform();
		
		// Row One
		widgetLayout.add(nameLabel).fill().colspan(2).center();
		//widgetLayout.add();
		widgetLayout.add(levelLabel);
		widgetLayout.row();
		
		// Row Two
		widgetLayout.add(descriptionLabel).fill().colspan(3).left();
		widgetLayout.row();
		
		// Row Three
		widgetLayout.add(costLabel);
		widgetLayout.add(apsLabel);
		widgetLayout.add(upgradeButton).fill()
			.height(Gdx.graphics.getHeight() * 0.07F);
		
		// ----- End widgetLayout -----
		
		serviceListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				widgetListener.serviceWidgetAction(ServiceWidget.this);
			}
		};
		
		upgradeButton.addListener(serviceListener);
	}

	public void updateDisplay() {
		
		levelLabel.setText("Lv. " + service.getLevel());
		costLabel.setText("Cost\n$" + service.getCost());
		apsLabel.setText(
				"Current: " + service.getCAPS() + " / s \n" +
				"Next: " + service.getNAPS() + " / s");
	}
	
	public Service getService() {
		return service;
	}
}
