/**
 * 
 */
package at.skobamg.generator.view;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.mediator.IEventMediator;

/**
 *
 *
 */
public class LoginfensterController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;
	@FXML
	private TextField username;
	@FXML
	private PasswordField passwort;
	
	public void login() {
		mediator.login(username.getText(),passwort.getText());
	}
}
