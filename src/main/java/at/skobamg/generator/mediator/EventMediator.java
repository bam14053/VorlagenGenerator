/**
 * 
 */
package at.skobamg.generator.mediator;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.MainAppFactory;
import at.skobamg.generator.logic.GenerateSnippetsCommand;
import at.skobamg.generator.logic.GenerateTemplateViewCommand;
import at.skobamg.generator.logic.GenerateXMLStringCommand;
import at.skobamg.generator.model.IGeneratorModel;
import at.skobamg.generator.model.IInterface;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.ITemplate;
import at.skobamg.generator.model.IViewElement;
import at.skobamg.generator.model.Interface;
import at.skobamg.generator.model.Type;
import at.skobamg.generator.model.ViewTyp;
import at.skobamg.generator.model.Interface.InvalidPortRangeException;
import at.skobamg.generator.model.Template;
import at.skobamg.generator.service.ISwitchtyp;
import at.skobamg.generator.view.BasisGenerierungsController;
import at.skobamg.generator.view.HauptfensterController;
import at.skobamg.generator.view.InterfacedefinitionsController;
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
	private BasisGenerierungsController basisGenerierungsController;
	@Autowired
	private ISwitchtyp switchtyp;
	@Autowired
	private IGeneratorModel generatorModel;
	private InterfacedefinitionsController interfacedefinitionsController;
	private ITemplate template;
	private Stage tempStage = new Stage();
	private Stage stage;

	public void zumHauptfenster() {
		stage.getScene().setRoot(hauptfensterController.getView());
		stage.setX(20);
		stage.setY(50);
		changeWindow("Vorlagen Generator");
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
		Scene scene = new Scene(vbox, vbox.getPrefWidth(), 80);
		stage.sizeToScene();
		stage.setTitle("Information!!!");
		stage.setScene(scene);		
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(this.stage);
		stage.setResizable(false);		
		stage.show();
	}

	@Override
	public void login(String username, String passwort) {
//		if(username.equals("root")&& passwort.equals("root"))
			zumHauptfenster();
//		else
//			nachrichtAnzeigen("Username/Passwort-Kombination falsch");
	}

	@Override
	public ArrayList<String> getSwitchNamen() {
		return switchtyp.getSwitchnamen();
	}

	@Override
	public void zumNeuenTemplateFenster() {
		if(tempStage.getScene() == null) {
			tempStage.setTitle("Neue Vorlage erstellen");
			tempStage.setScene(new Scene(neuesTemplateController.getView()));
			tempStage.initModality(Modality.WINDOW_MODAL);
			tempStage.initOwner(this.stage);
			tempStage.setResizable(false);
		}
		if(!tempStage.getScene().getRoot().equals(neuesTemplateController.getView())) {			
			tempStage.setTitle("Neue Vorlage erstellen");
			tempStage.getScene().setRoot(neuesTemplateController.getView());
			tempStage.setResizable(false);
		}
		tempStage.show();
	}

	@Override
	public void zumBenutzerhandbuch() throws IOException{   //Benutzerhandbuch öffnen 
		ClassLoader classLoader = getClass().getClassLoader();
		if(Desktop.isDesktopSupported())
			Desktop.getDesktop().open(new File(classLoader.getResource("Benutzerhandbuch.pdf").getFile()));
	}
	
	
	  
	@Override  /// Ausloggen des USERS
	public void exit() {
		stage.close();
		nachrichtAnzeigen("Sie wurden erfolgreich ausgeloggt");
	}
	@Override  /// Speichern unter
	public void SpeichernUnter() {
		 FileChooser fileChooser = new FileChooser();
		  
         //Set extension filter
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
         fileChooser.getExtensionFilters().add(extFilter);
         
         //Show save file dialog
         File file = fileChooser.showSaveDialog(tempStage);
         
         if(file != null);
             
	}
	
	@Override  /// Öffnen einer Datei
	public void Dateiöffnen() {
		 FileChooser fileChooser = new FileChooser();
		  
         //Set extension filter
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
         fileChooser.getExtensionFilters().add(extFilter);
         
         //Show save file dialog
         File file = fileChooser.showOpenDialog(tempStage);
         
         if(file != null);
             
	}


	@Override
	public void neuenTemplateErstellen(String switchname, String iosversion) {
		switchtyp.switchHinzufügen(switchname);
		template = new Template(switchname, iosversion, null, null);
		zeigeEinschränkungsfenster();
	}
	
	public void zumInterfacedefinitionsfenster(boolean portRange) {
		interfacedefinitionsController = new MainAppFactory().interfacedefinitionsController();
		interfacedefinitionsController.setMediator(this);
		interfacedefinitionsController.zeigeFenster(portRange);
		tempStage.setTitle("Definition der Interfaces");		
		tempStage.getScene().setRoot(interfacedefinitionsController.getView());
		tempStage.sizeToScene();
		tempStage.setResizable(true);
	}
	
	public void zeigeEinschränkungsfenster() {
		Stage stage = new Stage();
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		
		vBox.setPadding(new Insets(20));
		vBox.setAlignment(Pos.BASELINE_CENTER);
		hBox.setPadding(new Insets(10));
		hBox.setAlignment(Pos.CENTER);		
		
		Button ja = new Button("Ja");
		Button nein = new Button("Nein");
		
		//Adding actionhandlers
		ja.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();				
				zumInterfacedefinitionsfenster(true);
			}
		});
		
		nein.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
				zumInterfacedefinitionsfenster(false);
			}
		});
		
		HBox.setMargin(ja, new Insets(10));
		
		hBox.getChildren().add(ja);
		hBox.getChildren().add(nein);
		vBox.getChildren().add(new Label("Wollen Sie die Anzahl der konfigurierbaren Ports beschränken ?"));
		vBox.getChildren().add(hBox);	
	
		//Creating scene, setting stage properties
		Scene scene = new Scene(vBox, 500, 100);
		stage.setTitle("Wollen sie eine Einschränkung haben?");
		stage.setScene(scene);		
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(this.stage);
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public void zumBasisGenerierungsfenster() {
		tempStage.setTitle("Wählen sie Basissnippets aus");		
		tempStage.getScene().setRoot(basisGenerierungsController.getView());
		tempStage.sizeToScene();
		tempStage.setResizable(false);
	}

	@Override
	public HashMap<String, ISnippet> getSnippets() {
		return generatorModel.getAllSnippets();
	}

	@Override
	public ISnippet getSnippet(String name) {
		return generatorModel.getSnippet(name);
	}

	@Override
	public void xmlGenerieren(ArrayList<CheckBoxTreeItem<String>> checkedItems) {
		GenerateSnippetsCommand command = new GenerateSnippetsCommand(generatorModel, checkedItems);
		command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handle(WorkerStateEvent arg0) {
				if(arg0.getSource().getValue() instanceof ArrayList<?>)
					template.setSnippets((ArrayList<ISnippet>) arg0.getSource().getValue());
				
				//Start the command for the XMLString 
				GenerateXMLStringCommand command = new GenerateXMLStringCommand(template);				
				command.setOnSucceeded(hauptfensterController);
				
				//Start the command for the XML View
				GenerateTemplateViewCommand command2 = new GenerateTemplateViewCommand(template);
				command2.setOnSucceeded(hauptfensterController);
				
				//Starting both commands
				command.start();
				command2.start();
			}
		});
		command.start();
	}

	@Override
	public void setInterfaceDefinition(String[][] inputText, boolean portRange){
		ArrayList<IInterface> interfaces = new ArrayList<IInterface>();
		for(String[] line: inputText){
			if(portRange)
				try {
					if(line[2].equals("-"))
						interfaces.add(new Interface(line[0], line[1]));
					else
						interfaces.add(new Interface(line[0], line[1], line[2].split("-")[0], line[2].split("-")[1]));
				} catch (InvalidPortRangeException e) {
					nachrichtAnzeigen(e.getMessage());
				}
			else
				interfaces.add(new Interface(line[0], line[1]));
		}
		template.setInterfaces(interfaces);
		zumBasisGenerierungsfenster();
	}

	@Override
	public void applyChange(TreeView<IViewElement> xmlTree) {
		template = (ITemplate)xmlTree.getRoot().getValue();		
		updateHauptFenster();
	}
	
	private void updateHauptFenster(){
		GenerateXMLStringCommand xmlStringCommand = new GenerateXMLStringCommand(template);
		xmlStringCommand.setOnSucceeded(hauptfensterController);

		GenerateTemplateViewCommand xmlViewCommand = new GenerateTemplateViewCommand(template);
		xmlViewCommand.setOnSucceeded(hauptfensterController);

		xmlStringCommand.start();
		xmlViewCommand.start();
	}

	@Override
	public void deleteElement(TreeItem<IViewElement> selectedElement) {
		template.deleteElement(selectedElement);
		updateHauptFenster();
	}

	@Override
	public void addSnippet(String name) {
		template.addSnippet(name);
		updateHauptFenster();
	}

	@Override
	public void addSection(String name, TreeItem<IViewElement> snippet) {
		template.addSection(name, (ISnippet) snippet.getValue());
		updateHauptFenster();
	}

	@Override
	public void addCommand(String name, String execcommand, Type type,
			TreeItem<IViewElement> parent) {
	}

	@Override
	public void addInterface(String portbezeichnunglang,
			String portbezeichnungkurz, String portRange) {
		try {
			template.addInterface(portbezeichnunglang, portbezeichnungkurz, portRange);
			updateHauptFenster();
		} catch (InvalidPortRangeException e) {
			nachrichtAnzeigen(e.getMessage());
		}
	}

	@Override
	public void addParameter(String name, String execcommand, Type type,
			boolean required, TreeItem<IViewElement> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zurueckZumInterfacedefinitionsfenster() {
		tempStage.setTitle("Definition der Interfaces");		
		tempStage.getScene().setRoot(interfacedefinitionsController.getView());
		tempStage.sizeToScene();
	}

}
