/**
 * 
 */
package at.skobamg.generator;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import at.skobamg.generator.mediator.IEventMediator;
import at.skobamg.generator.model.IGeneratorModel;
import at.skobamg.generator.model.InvalidTypeException;
import at.skobamg.generator.model.Verzeichnisse;
import at.skobamg.generator.service.ISwitchtyp;
import at.skobamg.generator.service.ISnippetTemplateService;
import at.skobamg.generator.service.SnippetTemplateService;
import at.skobamg.generator.view.LoginfensterController;
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

	
	public void start(Stage stage) throws Exception {
		//Get all the bean components
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainAppFactory.class);
		LoginfensterController mainController = context.getBean(LoginfensterController.class);
		IEventMediator mediator = context.getBean(IEventMediator.class);	
		final ISwitchtyp iSwitchtyp = context.getBean(ISwitchtyp.class);		
		final IGeneratorModel generatorModel = context.getBean(IGeneratorModel.class);
		//Load the switchtypes into the program
		new Thread(new Runnable() {			
			
			public void run() {
				Verzeichnisse.verzeichnisseErstellen();
				iSwitchtyp.laden();
				try {
					generatorModel.addSnippets(new SnippetTemplateService().snippetsLaden());
				} catch (InvalidTypeException e) {
					e.printStackTrace();
				}
			}
		}).start();	
		//Save all the data in memory before closing
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {			
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
		stage.setTitle("Vorlagen Generator"); // Title of program
		stage.show(); // Show the Mainwindow
	}

}
