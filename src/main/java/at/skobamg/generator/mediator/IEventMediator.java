/**
 * 
 */
package at.skobamg.generator.mediator;
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
	/**
	 * Navigieren zum Vorlagenfenster
	 */
	void zumNeueVorlageFenster();
	/**
	 * Navigieren zum Switchtypfenster
	 */
	void zumNeuenSwitchtypFenster();
	/**
	 * Stage vom Fenster speichern
	 * @param stage
	 */
	void setStage(Stage stage);
	boolean neuenSwitchtyp(String switchV, String IOSv);
	/**
	 * Display a new window displaying an error message
	 * @param error
	 */
	void nachrichtAnzeigen(String nachricht);
}
