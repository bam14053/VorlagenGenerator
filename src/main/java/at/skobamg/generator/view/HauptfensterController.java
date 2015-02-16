/**
 * 
 */
package at.skobamg.generator.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

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
	
	public void neuesTemplate(){
		mediator.zumNeuenTemplateFenster();
	}
	public void Benutzerhandbuch() throws IOException { // Benutzerhandbuch öffnen
		mediator.zumBenutzerhandbuch();
	}

	public void programschließen() {
		mediator.exit();
	}
}
