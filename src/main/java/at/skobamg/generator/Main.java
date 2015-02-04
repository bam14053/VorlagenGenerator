/**
 * 
 */
package at.skobamg.generator;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sun.javafx.stage.ScreenHelper.ScreenAccessor;

import at.skobamg.generator.mediator.IEventMediator;
import at.skobamg.generator.view.HauptfensterController;
import at.skobamg.generator.view.ScreensAbstract;
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
		//Get all the bean components
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainAppFactory.class);
		HauptfensterController mainController = context.getBean(HauptfensterController.class);
		IEventMediator mediator = context.getBean(IEventMediator.class);				
		//Close and get required instances
		context.close();
		mediator.setStage(stage);
		//Start the window
		Scene scene = new Scene(mainController.getView());	
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Vorlagengenerator");
		stage.show();
	}

}
