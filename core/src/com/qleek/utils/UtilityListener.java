package com.qleek.utils;

import com.qleek.player.Item;
import com.qleek.player.Paegant;
import com.qleek.widgets.StatusWidget;
import com.qleek.widgets.ItemWidget;
import com.qleek.widgets.ServiceWidget;

public abstract class UtilityListener {
	
	public void achievementUnlocked(Achievement achievement) {}
	public void serviceWidgetAction(ServiceWidget widget) {}
	public void paegantWidgetAction(Paegant paegant) {}
	public void itemWidgetAction(ItemWidget widget) {}
	public void statusWidgetAction(StatusWidget widget) {}
	public void squareWidgetAction(Item item) {}
	public void useItemAction() {}
	public void equipItemAction() {}
	public void craftItemAction() {}
}
