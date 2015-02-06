/**
 * 
 */
package at.skobamg.generator.view;

import org.springframework.beans.factory.annotation.Autowired;
import at.skobamg.generator.mediator.IEventMediator;

/**
 *
 */
public class HauptfensterController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;
	
}
