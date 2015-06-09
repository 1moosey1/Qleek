package widgets;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.Player;
import com.qleek.utils.TableUtils;

public class HeaderWidget {
	
	private Skin uiSkin;

	private Table widgetLayout;
	private TextButton backButton, optionsButton;
	private Label affectionLabel, apsLabel, test;
	
	public HeaderWidget(Skin skin) {
		
		uiSkin = skin;
		
		widgetLayout = new Table();	
		backButton = new TextButton("< Back", uiSkin);
		optionsButton = new TextButton(".\n.\n.", uiSkin);
		affectionLabel = new Label("0", uiSkin);
		apsLabel = new Label("0 / second", uiSkin);
		test = new Label("$ 5,0000,000", uiSkin);
		
		create();
	}
	
	private void create() {
		
		backButton.setName("back");
		optionsButton.setName("options");
		
		// ----- widgetLayout - 3 x 10 -----
		
		widgetLayout.setDebug(true);
		widgetLayout.defaults().expand();
		
		// Row One
		widgetLayout.add(backButton).fill().colspan(2);
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
		widgetLayout.add(test).colspan(10).top().left();
				
		// ----- End widgetLayout -----
	}
	
	public void updateDisplay(Player player) {	
		affectionLabel.setText(Integer.toString(player.getAffection()));
	}
	
	public void addListener(InputListener listener) {
		
		backButton.addListener(listener);
		optionsButton.addListener(listener);
	}
	
	public Table getLayout() {
		return widgetLayout;
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
