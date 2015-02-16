/**
 * 
 */
package at.skobamg.generator.mediator;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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
	private Stage tempStage = new Stage();
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
		vbox.setAlignment(Pos.BASELINE_CENTER);
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
		stage.setTitle("Information!!!");
		stage.setScene(scene);		
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(this.stage);
		stage.setResizable(false);
		stage.show();
	}

	@Override /// LOGIN
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
		{
		}
		if(tempStage.getScene() == null) {
			tempStage.setScene(new Scene(neuesTemplateController.getView()));
			tempStage.initModality(Modality.WINDOW_MODAL);
			tempStage.initOwner(this.stage);
			tempStage.setResizable(false);
		}
		neuesTemplateController.getView();
		tempStage.show();
	}
	
	
	@Override
	public void zumBenutzerhandbuch() throws IOException {   //Benutzerhandbuch öffnen 
		Runtime.getRuntime().exec("cmd.exe /c start c:/Users/GWD/git/VorlagenGenerator/VorlagenGenerator/PDF-Benutzerhandbuch/Benutzerhandbuch.pdf "); // Verweis auf das PDF
	}
	  
	@Override  /// Ausloggen des USERS
	public void exit() {
		stage.close();
		ausloggen("Sie wurden erfolgreich ausgeloggt!!"); // Meldung die am Bildschirm dargestellt wird.
		
	}
	 /////////////////////////////////////////////////////////////User erhaltet Meldung über erfolgreiches Ausloggen.
	public void ausloggen(String nachricht) {
		final Stage stage = new Stage();
		VBox vbox = new VBox();
		//Setting vbox properties
		vbox.setPadding(new Insets(10));
		//Creating extra controls
		Button b = new Button("OK");
		vbox.setAlignment(Pos.BASELINE_CENTER);
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
		stage.setTitle("Logout");
		stage.setScene(scene);		
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(this.stage);
		stage.setResizable(false);
		stage.show();
	}

  //Templatefenster mit Eingabeüberpüfung
	@Override
	public void neuenTemplateErstellen(String switchname, String iosversion) {
		{
			if(switchname.equals("Cisco")&&iosversion.equals("12.0"))
			//switchtyp.switchHinzufügen(switchname);	
			System.out.println("Alle Felder sind ausgefüllt gratuliere!"); //Hilfestellung zu Fensterwechsel [Entfernbar]
			else
				nachrichtAnzeigen("Bitte Switchname und IOS-Version auswählen/eingeben");
		}	
			
	}
	
}



