/**
 * 
 */
package at.skobamg.generator.view;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.mediator.IEventMediator;

/**
 *
 */
public class HauptfensterController implements IScreens{
	@Autowired
	private IEventMediator mediator;
	@FXML
	private SplitPane view;

	@Override
	public SplitPane getView() {
		return view;
	}
	
}
