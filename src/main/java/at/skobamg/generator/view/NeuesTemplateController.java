/**
 * 
 */
package at.skobamg.generator.view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.mediator.IEventMediator;


public class NeuesTemplateController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;
	@FXML
	private ComboBox<String> switchname;
	@FXML
	private TextField iosversion;

	public void templateErstellen(){
		try {
			if(switchname.getSelectionModel().getSelectedItem().isEmpty() || iosversion.getText().isEmpty())
				mediator.nachrichtAnzeigen("Bitte geben sie alle erforderlichen Daten ein");
			else
				mediator.neuenTemplateErstellen(switchname.getSelectionModel().getSelectedItem(), iosversion.getText());
		}catch(NullPointerException | IndexOutOfBoundsException e){
			mediator.nachrichtAnzeigen("Bitte geben sie alle erforderlichen Daten ein");
		}
		
	}	
	
	public void zumHauptFenster(ActionEvent actionEvent) {
		((Stage)(((Node)actionEvent.getSource()).getScene().getWindow())).hide();
	}

	@Override
	public Pane getView() {
		switchname.getItems().clear();
		iosversion.setText("");
		switchname.setVisibleRowCount(4);
		for(String s : mediator.getSwitchNamen())
			switchname.getItems().add(s);
		return super.getView();
	}		
	
}
