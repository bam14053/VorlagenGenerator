/**
 * 
 */
package at.skobamg.generator.mediator;
import java.util.ArrayList;

import javafx.stage.Stage;

/**
 * Mediatorinterface gibt an welche Methoden Klassen implementieren müssen, die diesen Interface benützen
 *
 */
public interface IEventMediator {
	/**
	 * Navigieren zum Hauptfenster
	 */
	void zumHauptfenster();
	void zumNeuenTemplateFenster();
	void setStage(Stage stage);
	/**
	 * Display a new window displaying an error message
	 * @param error
	 */
	void nachrichtAnzeigen(String nachricht);
	void login(String username, String passwort);
	ArrayList<String> getSwitchNamen();
}
