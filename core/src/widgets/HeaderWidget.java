package widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class HeaderWidget {
	
	private Skin uiSkin;

	private Table widgetLayout;
	private TextButton backButton, optionsButton;
	private Label affectionLabel, apsLabel;
	
	public HeaderWidget(Skin skin) {
		
		uiSkin = skin;
		
		widgetLayout = new Table();
		backButton = new TextButton("< Back", uiSkin);
		optionsButton = new TextButton("Options", uiSkin);
		affectionLabel = new Label("0", uiSkin);
		apsLabel = new Label("0", uiSkin);
		
		create();
	}
	
	private void create() {
		
		//First Row
		widgetLayout.add(backButton).align(Align.left);
		widgetLayout.add();
		widgetLayout.add(optionsButton).align(Align.right);
		widgetLayout.row();
		
		//Second Row
		widgetLayout.add(affectionLabel).colspan(3);
		widgetLayout.row();
		
		//Third Row
		widgetLayout.add(apsLabel).colspan(3);
	}
	
	public Table getLayout() {
		return widgetLayout;
	}
}
