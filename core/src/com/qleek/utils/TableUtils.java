package com.qleek.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TableUtils {

	//Can not instantiate 
	private TableUtils() {}
	
	//Adds x blank cells to your current row
	public static void addBlankCells(Table table, int cells) {
		
		for(int i = 0; i < cells; i++)
			table.add();
	}
}
