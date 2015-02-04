/**
 * 
 */
package at.skobamg.generator.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
	private TextField switchname;
	@FXML
	private TextField iosversion;

	public void zurückzumHauptFenster(ActionEvent event){
		mediator.zumHauptfenster();
	}
	
	public void neuenSwitchtyp(){
		if(switchname.getText().isEmpty() || iosversion.getText().isEmpty())
			mediator.nachrichtAnzeigen("Bitte geben Sie alle erforderlichen Werte ein");
		else{
			if(mediator.neuenSwitchtyp(switchname.getText(), iosversion.getText())){
				mediator.nachrichtAnzeigen("Neuer Switchtyp wurde erfolgreich hinzugefügt");
				mediator.zumHauptfenster();
			}
			else
				mediator.nachrichtAnzeigen("Fehler, neuer Switchtyp konnte nicht hinzugefügt werden");			
		}
	}

}
