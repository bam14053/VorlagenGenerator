/**
 * 
 */
package at.skobamg.generator.view;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.mediator.IEventMediator;

/**
 * @author abi
 *
 */
public class NeuesTemplateController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;
	@FXML
	private ComboBox<String> switchname;
	@FXML
	private ComboBox<String> iosversion;
	@FXML
	private ToggleGroup berechtigung;

	public void zurückzumHauptFenster(){
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
	
}
