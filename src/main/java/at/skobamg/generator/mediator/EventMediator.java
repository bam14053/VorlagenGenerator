/**
 * 
 */
package at.skobamg.generator.mediator;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import at.skobamg.generator.service.ISwitchtyp;
import at.skobamg.generator.view.HauptfensterController;
import at.skobamg.generator.view.NeuesTemplateController;

/**
 * 
 *
 */
public class EventMediator implements IEventMediator {
	@Autowired
	private NeuesTemplateController neuesTemplateController;
	@Autowired
	private HauptfensterController hauptfensterController;
	@Autowired
	private ISwitchtyp switchtyp;
	private Stage stage;

	public void zumHauptfenster() {
		stage.getScene().setRoot(hauptfensterController.getView());
		changeWindow("Vorlagen Generator");
	}

	public void zumNeueVorlageFenster() {
		stage.getScene().setRoot(neuesTemplateController.getView());
		changeWindow("Neue Vorlage erstellen");
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	private void changeWindow(String title){
		stage.setTitle(title);
		stage.sizeToScene();
	}

	public void nachrichtAnzeigen(String nachricht) {
		final Stage stage = new Stage();
		VBox vbox = new VBox();
		//Setting vbox properties
		vbox.setPadding(new Insets(10));
		//Creating extra controls
		Button b = new Button("OK");
		vbox.setAlignment(Pos.BASELINE_RIGHT);
		b.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});
		//Adding to pane
		vbox.getChildren().add(new Label(nachricht));
		vbox.getChildren().add(b);
		
		//Creating scene, setting stage properties
		Scene scene = new Scene(vbox, 320, 80);
		stage.setTitle("Eine Nachricht für Sie");
		stage.setScene(scene);		
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(this.stage);
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public void login(String username, String passwort) {
		if(username.equals("root")&& passwort.equals("root"))
			zumHauptfenster();
		else
			nachrichtAnzeigen("Username/Passwort-Kombination falsch");
	}

	@Override
	public ArrayList<String> getSwitchNamen() {
		return switchtyp.getSwitchnamen();
	}

	@Override
	public void zumNeuenTemplateFenster() {
		Stage stage = new Stage();
		stage.setScene(new Scene(neuesTemplateController.getView()));
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(this.stage);
		stage.setResizable(false);
		stage.show();
	}

}
