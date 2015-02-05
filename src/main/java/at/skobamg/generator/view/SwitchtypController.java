/**
 * 
 */
package at.skobamg.generator.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.mediator.IEventMediator;

/**
 * @author abi
 *
 */
public class SwitchtypController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;
	@FXML
	private ComboBox<String> switchname;
	@FXML
	private TextField iosversion;

	public void zurückzumHauptFenster(ActionEvent event){
		mediator.zumHauptfenster();
	}
	
	@Override
	public Pane getView() {
		switchname.getItems().clear();
		switchname.setVisibleRowCount(4);
		for(String s : mediator.getSwitchNamen())
			switchname.getItems().add(s);
		return super.getView();
	}

	public void neuenSwitchtyp(){
		if(switchname.getSelectionModel().getSelectedItem().isEmpty() || iosversion.getText().isEmpty())
			mediator.nachrichtAnzeigen("Bitte geben Sie alle erforderlichen Werte ein");
		else{
			if(mediator.neuenSwitchtyp(switchname.getSelectionModel().getSelectedItem(), iosversion.getText())){
				mediator.nachrichtAnzeigen("Neuer Switchtyp wurde erfolgreich hinzugefügt");
				mediator.zumHauptfenster();
			}
			else
				mediator.nachrichtAnzeigen("Fehler, neuer Switchtyp konnte nicht hinzugefügt werden");			
		}
	}

}
