/**
 * 
 */
package at.skobamg.generator.mediator;

import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.view.HauptfensterController;
import at.skobamg.generator.view.SwitchtypController;
import at.skobamg.generator.view.TemplateAuswahlController;

/**
 * @author abi
 *
 */
public class EventMediator implements IEventMediator {
	@Autowired
	private SwitchtypController switchtypController;
	@Autowired
	private TemplateAuswahlController templateAuswahlController;
	@Autowired
	private HauptfensterController hauptfensterController;
	private Stage stage;

	public void zumHauptfenster() {
		stage.getScene().setRoot(hauptfensterController.getView());
		changeWindow("Vorlagengenerator");
	}

	public void zumNeueVorlageFenster() {
		stage.getScene().setRoot(templateAuswahlController.getView());
		changeWindow("Neue Vorlage erstellen");
	}

	public void zumNeuenSwitchtypFenster() {
		stage.getScene().setRoot(switchtypController.getView());
		changeWindow("Neuen Switchtyp definieren");
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	private void changeWindow(String title){
		stage.setTitle(title);
		stage.sizeToScene();
	}

	public void neuenSwitchtyp(String switchV, String IOSv) {
		
	}

}
