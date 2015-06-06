package com.qleek.screens;

import com.qleek.Qleek;

public class ShopScreen extends BaseScreen {

	public ShopScreen(Qleek game) {
		
		super(game);
		create();
	}

	@Override
	void create() {
		
		layout.add(headerWidget.getLayout());
	}
}
