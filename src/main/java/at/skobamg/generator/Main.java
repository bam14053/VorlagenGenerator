/**
 * 
 */
package at.skobamg.generator;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import at.skobamg.generator.view.HauptfensterController;
import at.skobamg.generator.view.SwitchtypController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 *
 */
public class Main extends Application{

	/**
	 * 
	 */
	public static void main(String[] args) {
		Application.launch(Main.class, (java.lang.String[])null);				
	}

	@Override
	public void start(Stage stage) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainAppFactory.class);
		HauptfensterController mainController = context.getBean(HauptfensterController.class);
		Scene scene = new Scene(mainController.getView());	
		stage.setScene(scene);
		stage.setTitle("Vorlagengenerator");
		stage.show();
	}

}
