/**
 * 
 */
package at.skobamg.generator.view;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author abigale
 *
 */
public class HauptfensterController extends ScreensAbstract {
	@Autowired
	private SwitchtypController switchMenuView;
	@Autowired
	private TemplateAuswahlController templateAuswahlController;
	
	public void neueVorlage(ActionEvent event){
		Stage stage = new Stage();
		stage.setScene(new Scene(templateAuswahlController.getView())); 
		stage.setTitle("Neuen Template erstellen");
		stage.show();
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
	
	public void neuenSwitchtyp(ActionEvent event){
		Stage stage = new Stage();
		stage.setScene(new Scene(switchMenuView.getView())); 
		stage.setTitle("Neuen Switchtyp definieren");
		stage.show();
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
}
