/**
 * 
 */
package at.skobamg.generator.view;

import org.springframework.beans.factory.annotation.Autowired;
import at.skobamg.generator.mediator.IEventMediator;

/**
 * @author abi
 *
 */
public class TemplateAuswahlController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;

	public void zurückzumHauptFenster(){
		mediator.zumHauptfenster();
	}
}
