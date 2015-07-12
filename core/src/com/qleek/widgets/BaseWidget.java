package com.qleek.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.qleek.Qleek;
import com.qleek.utils.UtilityListener;

public abstract class BaseWidget {
	
	protected Skin uiSkin = Qleek.skin;
	protected Table widgetLayout;
	protected UtilityListener widgetListener;
	
	public BaseWidget() {
		widgetLayout = new Table();
	}
	
	public Table getLayout() {
		return widgetLayout;
	}
	
	public void addListener(UtilityListener listener) {
		widgetListener = listener;
	}
	
	public void updateDisplay() {}
}
