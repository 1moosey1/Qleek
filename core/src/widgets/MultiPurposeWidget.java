package widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;


public class MultiPurposeWidget {
	
	private Skin uiSkin;
	
	private Table widgetLayout;
	private TextButton Button;
	private Label nameLabel, flavorLabel, levelLabel;
	
	public MultiPurposeWidget (Skin skin, String Name,String flav,String butTxt, int level ) {
		uiSkin = skin;
		
		widgetLayout = new Table();
		Button = new TextButton(butTxt,uiSkin);
		nameLabel = new Label(Name, uiSkin);
		flavorLabel = new Label(flav, uiSkin);
		levelLabel = new Label ("Lv. "+ Integer.toString(level), uiSkin);
		
		create();
	}

	private void create() {
		
		//First Row
		widgetLayout.add(nameLabel).align(Align.left).colspan(2);
		widgetLayout.add(levelLabel).align(Align.right);
		widgetLayout.row();
		
		//Second Row
		widgetLayout.add(flavorLabel).colspan(3);
		widgetLayout.row();
		
		//third row
		widgetLayout.add(Button).align(Align.right).colspan(2);
	}
	
	public Table getLayout(){
		return widgetLayout;
	}
	
}
