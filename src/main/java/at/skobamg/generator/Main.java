/**
 * 
 */
package at.skobamg.generator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author abigale
 *
 */
public class Main extends Application{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(Main.class, (java.lang.String[])null);				
	}

	@Override
	public void start(Stage stage) throws Exception {
		Pane stackpane = (Pane) FXMLLoader.load(Main.class.getResource("view/xml/Hauptfenster.fxml"));
		Scene scene = new Scene(stackpane);
		stage.setScene(scene);
		stage.setTitle("Vorlagen Generator");		
		stage.show();
	}

}
