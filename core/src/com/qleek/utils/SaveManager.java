package com.qleek.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.qleek.Qleek;
import com.qleek.player.Item;
import com.qleek.player.Player;
import com.qleek.player.Service;

public class SaveManager {
	
	private static SaveManager saveManager;
	private static final String DIV = "~";
	
	private boolean hasData;
	private String timeData;
	private String[] playerData, invData, equipData,
		achieveData, serviceData;
	
	private SaveManager() {}

	public static SaveManager getInstance() {
		
		if(saveManager == null)
			saveManager = new SaveManager();
		
		return saveManager;
	}
	
	public void readSave() {
		
		FileHandle fileHandle = Gdx.files.local("save.dat");
		String unparsedData;
		
		try {
			unparsedData = fileHandle.readString();
		} catch(GdxRuntimeException ex) {
			
			hasData = false;
			return;
		}
		
		System.out.println(unparsedData);
		System.out.println(Base64Coder.decodeString(unparsedData));
		
		hasData = true;
		String[] saveData = Base64Coder.decodeString(unparsedData).split(DIV);
		
		timeData    = saveData[0];	
		playerData  = saveData[1].split(":");
		invData     = saveData[2].split("\\*");		
		equipData   = saveData[3].split("\\*");		
		achieveData = saveData[4].split("\\*");		
		serviceData = saveData[5].split("\\*");
	}
	
	public void writeSave(Qleek qleek) {
		
		StringBuilder saveData = new StringBuilder();
		FileHandle fileHandle = Gdx.files.local("save.dat");
		
		// Time Stamp
		saveData.append(qleek.timeStamp + DIV);
		
		// Player Data
		Player player = qleek.player;
		saveData.append(player.getAffection() + ":" +
				player.getAPS() + ":" + player.getMoney() + DIV);
		
		// Inventory Data
		saveData.append("0");
		for(Item item : player.getInventory())
			saveData.append("*" + item.getItemID().toString() + ":" + 
					item.getQuantity() + ":" + item.getAPS());
		saveData.append(DIV);
		
		// Equip Data
		for(Item item : player.getEquips())
			if(item != null)
				saveData.append(item.getItemID().toString() + ":" + item.getAPS() + "*");
			else
				saveData.append("0*");
		saveData.append(DIV);
		
		// Achievement Data
		for(Achievement achievement: Achievement.values())
			saveData.append(achievement.isUnlocked() + "*");
		saveData.append(DIV);
		
		// Service Data
		for(Service service : Service.getServices())
			saveData.append(service.getLevel() + "*");
		
		// Write data
		String cData = Base64Coder.encodeString(saveData.toString());
		fileHandle.writeString(cData, false);
	}
	
	public boolean  hasSaveData()        { return hasData;     }
	public String   getTimeData()        { return timeData;    }
	public String[] getPlayerData()      { return playerData;  }
	public String[] getInventoryData()   { return invData;     }
	public String[] getEquipData()       { return equipData;   }
	public String[] getAchievementData() { return achieveData; }
	public String[] getServiceData()     { return serviceData; }
}
