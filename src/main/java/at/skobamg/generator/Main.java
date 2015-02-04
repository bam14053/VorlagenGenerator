/**
 * 
 */
package at.skobamg.generator;

import org.hamcrest.core.IsSame;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import at.skobamg.generator.mediator.IEventMediator;
import at.skobamg.generator.service.ISwitchtyp;
import at.skobamg.generator.service.Verzeichnisse;
import at.skobamg.generator.view.HauptfensterController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
		ISwitchtyp iSwitchtyp = context.getBean(ISwitchtyp.class);		
		//Load the switchtypes into the program
		new Thread(new Runnable() {			
			@Override
			public void run() {
				Verzeichnisse.verzeichnisseErstellen();
				iSwitchtyp.laden();
			}
		}).start();	
		//Save all the data in memory before closing
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				iSwitchtyp.speichern();
			}
		});
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
