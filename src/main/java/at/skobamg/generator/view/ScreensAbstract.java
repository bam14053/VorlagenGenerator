package at.skobamg.generator.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class ScreensAbstract implements IScreens{
	@FXML
	protected Pane view;
	
	public Pane getView(){
		return view;		
	}
	
}
