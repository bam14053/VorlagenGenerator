/**
 * 
 */
package at.skobamg.generator.view;

import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import at.skobamg.generator.mediator.IEventMediator;

/**
 * @author abi
 *
 */
public class SwitchtypController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;

	public void zurückzumHauptFenster(ActionEvent event){
		mediator.zumHauptfenster();
	}
	
	public void neuenSwitchtyp(){
		
	}

}
