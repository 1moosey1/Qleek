package widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.qleek.utils.TableUtils;

public class HeaderWidget {
	
	private Skin uiSkin;

	private Table widgetLayout;
	private TextButton backButton, optionsButton;
	private Label affectionLabel, apsLabel;
	
	public HeaderWidget(Skin skin) {
		
		uiSkin = skin;
		
		widgetLayout = new Table();	
		backButton = new TextButton("<", uiSkin);
		optionsButton = new TextButton("...", uiSkin);
		affectionLabel = new Label("Affection Label", uiSkin);
		apsLabel = new Label("Affection Per Second", uiSkin);
		
		create();
	}
	
	private void create() {
		
		// ----- widgetLayout - 3 x 10 -----
		
		widgetLayout.setDebug(true);
		widgetLayout.defaults().expand();
		
		// Row One
		widgetLayout.add(backButton).fill();
		TableUtils.addBlankCells(widgetLayout, 8);
		widgetLayout.add(optionsButton).fill();
		widgetLayout.row();
		
		// Row Two
		widgetLayout.add(affectionLabel).colspan(10).top();
		widgetLayout.row().top();
		
		// Row Three
		widgetLayout.add(apsLabel).colspan(10).top();
				
		// ----- End widgetLayout -----
	}
	
	public Table getLayout() {
		return widgetLayout;
	}
	
	public void enableBack() {
		backButton.setVisible(true);
	}
	
	public void disableBack() {
		backButton.setVisible(false);
	}
}
