/**
 * 
 */
package at.skobamg.generator.view;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author abi
 *
 */
public class SwitchtypController extends ScreensAbstract {
	@Autowired
	private HauptfensterController hauptfensterController;

	public void zurückzumHauptFenster(ActionEvent event){
		Stage stage = new Stage();
		stage.setScene(new Scene(hauptfensterController.getView())); 
		stage.setTitle("Neuen Switchtyp definieren");
		stage.show();
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
}
