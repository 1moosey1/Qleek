package com.qleek.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.qleek.Qleek;
import com.qleek.utils.WidgetListener;

public abstract class BaseWidget {
	
	protected Skin uiSkin = Qleek.skin;
	protected Table widgetLayout;
	protected WidgetListener widgetListener;
	
	public Table getLayout() {
		return widgetLayout;
	}
	
	public void addListener(WidgetListener listener) {
		widgetListener = listener;
	}
}
