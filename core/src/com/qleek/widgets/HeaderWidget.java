package com.qleek.widgets;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.player.Player;
import com.qleek.utils.TableUtils;

public class HeaderWidget extends BaseWidget {
	
	private ImageButton backButton;
	private TextButton optionsButton;
	private Label affectionLabel, apsLabel, meownyLabel;
	
	public HeaderWidget() {
		
		backButton = new ImageButton(uiSkin, "back");
		optionsButton = new TextButton(".\n.\n.", uiSkin);
		affectionLabel = new Label("", uiSkin);
		apsLabel = new Label("", uiSkin);
		meownyLabel = new Label("", uiSkin);
		
		create();
	}
	
	private void create() {
		
		backButton.setName("back");
		optionsButton.setName("options");
		
		// ----- widgetLayout - 3 x 10 -----
		
		widgetLayout.setDebug(true);
		widgetLayout.defaults().expand();
		
		// Row One
		widgetLayout.add(backButton).colspan(2).fill();
		TableUtils.addBlankCells(widgetLayout, 7);
		widgetLayout.add(optionsButton).fill();
		widgetLayout.row();
		
		// Row Two
		widgetLayout.add(affectionLabel).colspan(10).bottom();
		widgetLayout.row().top();
		
		// Row Three
		widgetLayout.add(apsLabel).colspan(10).top();
		widgetLayout.row();
		
		// Row Four
		widgetLayout.add(meownyLabel).colspan(10).top().left();
				
		// ----- End widgetLayout -----
	}
	
	public void updateDisplay(Player player) {
		
		affectionLabel.setText("<3 " + player.getAffection() + " <3");
		apsLabel.setText(player.getAPS() + " / second");
		meownyLabel.setText("$" + player.getMoney());
	}
	
	public void addListener(InputListener listener) {
		
		backButton.addListener(listener);
		optionsButton.addListener(listener);
	}
	
	public String backButtonName() {
		return backButton.getName();
	}
	
	public String optionsButtonName() {
		return optionsButton.getName();
	}
	
	public void enableBack() {
		backButton.setVisible(true);
	}
	
	public void disableBack() {
		backButton.setVisible(false);
	}
}
