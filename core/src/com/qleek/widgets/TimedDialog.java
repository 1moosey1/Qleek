package com.qleek.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.qleek.Qleek;

public abstract class TimedDialog extends Dialog {
	
	private Timer timer;
	private Task removeTask;

	public TimedDialog() {
		
		super("", Qleek.skin, "timedialog");
		setModal(false);
		
		timer = new Timer();
		removeTask = new Task() {

			@Override
			public void run() {
				hide();
			}
		};
		
		timer.scheduleTask(removeTask, 2);
		create();
	}
	
	public abstract void create();
}
