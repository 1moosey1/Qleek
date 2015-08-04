package com.qleek.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.qleek.Qleek;

public abstract class ExitDialog extends Dialog {
	
	private ImageButton exitButton;
	private InputListener exitListener;

	
	public ExitDialog() {
		
		super("", Qleek.skin, "exitdialog");
		setModal(true);
		
		exitButton = new ImageButton(Qleek.skin.getDrawable("checkbox_cross"));
		exitListener = new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		 		return true;
		 	}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				hide();
			}
		};
		exitButton.addListener(exitListener);	
		getTitleTable().add(exitButton).size(60, 40).padRight(-30).padTop(-10);
		
		create();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
	    setClip(true);
	    super.draw(batch, parentAlpha);
	    setClip(false);
	}
	
	public abstract void create();
}
