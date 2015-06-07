package com.qleek.screens;

import com.qleek.Qleek;

public class ShopScreen extends BaseScreen {

	public ShopScreen(Qleek game) {
		
		super(game);
		create();
	}

	@Override
	void create() {
		
		screenLayout.add(headerWidget.getLayout());
	}

	@Override
	public void show() {}

	@Override
	public void hide() {}
}
