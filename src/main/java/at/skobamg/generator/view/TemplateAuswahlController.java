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
 * @author abi
 *
 */
public class TemplateAuswahlController extends ScreensAbstract {
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
