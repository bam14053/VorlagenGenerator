package at.skobamg.generator.view;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public abstract class ScreensAbstract implements IScreens{
	@FXML
	protected Pane view;
	
	@Override
	public Pane getView(){
		return view;		
	}
	
}
