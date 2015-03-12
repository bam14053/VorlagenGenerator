/**
 * 
 */
package at.skobamg.generator.view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.logic.GenerateTemplateViewCommand;
import at.skobamg.generator.logic.GenerateXMLStringCommand;
import at.skobamg.generator.mediator.IEventMediator;
import at.skobamg.generator.model.ICommand;
import at.skobamg.generator.model.IInterface;
import at.skobamg.generator.model.IParameter;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.IViewElement;
import at.skobamg.generator.model.Interface.InvalidPortRangeException;
import at.skobamg.generator.model.Type;
import at.skobamg.generator.model.ViewTyp;

/**
 *
 */
public class HauptfensterController implements IScreens, EventHandler<WorkerStateEvent>{
	@Autowired
	private IEventMediator mediator;
	@FXML
	private SplitPane view;
	@FXML
	private AnchorPane xmlCode;
	@FXML
	private AnchorPane xmlView;
	@FXML
	private TextField interfaceNameLang;
	@FXML
	private TextField interfaceNameKurz;
	@FXML
	private TextField interfacePortRange;
	@FXML
	private TextField snippetName;	
	@FXML 
	private TextField sectionName;
	@FXML
	private TextField commandName;
	@FXML
	private TextField execcommand;
	@FXML
	private ComboBox<Type> commandType;
	@FXML
	private Label commandLabel;
	@FXML
	private Label commandNameLabel;
	@FXML
	private Label commandTypLabel;	
	@FXML
	private GridPane parameterGridPane;
	@FXML
	private Label requiredLabel;
	@FXML
	private CheckBox required;
	@FXML
	private Menu snippetMenu;
	@FXML
	private Menu sectionMenu;
	private TreeItem<IViewElement> selectedElement;
	private TreeItem<IViewElement> selectedElementForCopy;
	private TextArea text = new TextArea();
	private TreeView<IViewElement> xmlTree = new TreeView<IViewElement>();
	
	@Override
	public SplitPane getView() {
		//Configuring the XMlCode
		xmlCode.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				text.setPrefWidth(xmlCode.getWidth());
			}
		});
		xmlCode.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				text.setPrefHeight(xmlCode.getHeight());
			}
		});
		xmlCode.getChildren().add(text);		
		text.setWrapText(true);			
		//Configuring the XML View
		xmlView.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				xmlTree.setPrefWidth(xmlView.getWidth());
			}
		});
		xmlView.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				xmlTree.setPrefHeight(xmlView.getHeight());
			}
		});		
		xmlView.getChildren().add(xmlTree);
		xmlTree.setShowRoot(false);
		xmlTree.getSelectionModel().selectedItemProperty().addListener(new TreeItemChangeListener());		
		//Filling commandtype
		for(String snippet : mediator.getGeneratorSnippets()) {
			MenuItem menuItem = new MenuItem(snippet);
			menuItem.setOnAction(new MenuItemHandler(snippet));
			snippetMenu.getItems().add(menuItem);
		}
		for(String section : mediator.getGeneratorSections()) {
			MenuItem menuItem = new MenuItem(section);
			menuItem.setOnAction(new MenuItemHandler(section));
			sectionMenu.getItems().add(menuItem);
		}
		commandType.getItems().addAll(Type.values());				
		disableInputFields();
		return view;
	}
	
	public void neuesTemplate(){
		mediator.zumNeuenTemplateFenster();
	}
	
	public void Benutzerhandbuch(){ // Benutzerhandbuch öffnen
		try{
			mediator.zumBenutzerhandbuch();
		}catch(IOException e) {
			mediator.nachrichtAnzeigen("Fehler! Benutzerhandbuch konnte nicht geöffnet werden");
		}		
	}
	
	public void hinzufuegen(ActionEvent actionEvent){
		if(text.getText().isEmpty()) return;
		Stage stage = new Stage();
		BorderPane bp = new BorderPane();
		VBox vBox = new VBox(5);		
		AnchorPane anchorPane = new AnchorPane();
		
		//Label time!!!
		Label title = new Label("Wählen Sie aus welchen Element sie hinzufügen wollen?");
		title.setFont(new Font("Arial", 20));
		title.setWrapText(true);
		
		//Content in the middle
		ToggleGroup toggleGroup = new ToggleGroup();
		ArrayList<RadioButton> buttons = new ArrayList<RadioButton>();
		if(selectedElement == null ||  selectedElement.getValue().getViewTyp().equals(ViewTyp.IInterface)){
			buttons.add(new RadioButton("Interface"));
			buttons.add(new RadioButton("Snippet"));
		}else{
			if(selectedElement.getValue().getViewTyp().equals(ViewTyp.ISnippet)){
				buttons.add(new RadioButton("Interface"));
				buttons.add(new RadioButton("Snippet"));
				buttons.add(new RadioButton("Section"));
			}
			else if(selectedElement.getValue().getViewTyp().equals(ViewTyp.ISection)){
				buttons.add(new RadioButton("Interface"));
				buttons.add(new RadioButton("Snippet"));
				buttons.add(new RadioButton("Section"));
				buttons.add(new RadioButton("Command"));
			}
			else{
				buttons.add(new RadioButton("Interface"));
				buttons.add(new RadioButton("Snippet"));
				buttons.add(new RadioButton("Command"));
				buttons.add(new RadioButton("Parameter"));
			}
		}
		
		for(RadioButton button : buttons){
			button.setToggleGroup(toggleGroup);
			button.setUserData(button.getText());
			vBox.getChildren().add(button);
		}
		
		//Content at the buttom and listseners
		Button buttonWeiter = new Button("Weiter");
		Button buttonAbbrechen = new Button("Abbrechen");
		buttonAbbrechen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});
		buttonWeiter.setOnAction(new NewElementButtonHandler(toggleGroup));
		
		anchorPane.setPrefWidth(30);
		anchorPane.getChildren().addAll(buttonWeiter, buttonAbbrechen);
		AnchorPane.setRightAnchor(buttonWeiter, 1.0);
		AnchorPane.setLeftAnchor(buttonAbbrechen, 1.0);
		
		//Adding the contents to their places 
		BorderPane.setMargin(vBox, new Insets(10));
		bp.setTop(title);
		bp.setCenter(vBox);
		bp.setBottom(anchorPane);
		stage.setScene(new Scene(bp));
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.sizeToScene();
		stage.showAndWait();
	}
	
	public void einfuegen() {
		if(selectedElementForCopy == null) return;
		mediator.elementEinfuegen(selectedElementForCopy, selectedElement);
	}
	
	public void kopieren() {
		selectedElementForCopy = selectedElement;
	}
	
	public void loeschen(){
		if(selectedElement == null) return;
		mediator.deleteElement(selectedElement);		
	}
	
	public void uebernehmen(){
		if(selectedElement == null) return;				
		switch(selectedElement.getValue().getViewTyp()){
		case IInterface:
			IInterface interface1 = (IInterface) selectedElement.getValue();
			interface1.setPortBezeichnungkurz(interfaceNameKurz.getText());
			interface1.setPortBezeichnunglang(interfaceNameLang.getText());
			try{
				if(interfacePortRange.getText().isEmpty() || interfacePortRange.getText().equals("-"))
					interface1.setPortRange("", "");
				else
					interface1.setPortRange(interfacePortRange.getText().split("-")[0], interfacePortRange.getText().split("-")[1]);
			}catch(InvalidPortRangeException | IndexOutOfBoundsException ipe){
				if(ipe instanceof InvalidPortRangeException)
					mediator.nachrichtAnzeigen(ipe.getMessage());
				else
					mediator.nachrichtAnzeigen("Ein Fehler ist aufgetreten, geben sie den Portrange bitte korrekt ein!");
			}
			break;
		case ICommand:
			ICommand command = (ICommand)selectedElement.getValue();
			command.setName(commandName.getText());
			command.setExeccommand(execcommand.getText());		
			command.setType(commandType.getValue());
			break;		
		case IParameter:
			IParameter parameter = (IParameter)selectedElement.getValue();
			parameter.setName(commandName.getText());
			parameter.setType(commandType.getValue());
			parameter.setExeccommand(execcommand.getText());		
			parameter.setRequired(required.isSelected());
			break;
		case ISection:
			ISection section = (ISection)selectedElement.getValue();
			section.setName(sectionName.getText());
			break;
		case ISnippet:
			ISnippet snippet = (ISnippet)selectedElement.getValue();
			snippet.setName(snippetName.getText());
			break;
		default:
			break;		
		}
		mediator.applyChange(xmlTree);
	}
	
	public void abbrechen(){
		if(selectedElement == null) return;
		switch(selectedElement.getValue().getViewTyp()){
		case IInterface:
			IInterface interface1 = (IInterface) selectedElement.getValue();
			interfaceNameLang.setText(interface1.getPortBezeichnunglang());
			interfaceNameKurz.setText(interface1.getPortBezeichnungkurz());
			interfacePortRange.setText(interface1.getPortRange());
			break;
		case ICommand:
			ICommand command = (ICommand)selectedElement.getValue();
			commandName.setText(command.getName());
			execcommand.setText(command.getExeccommand());
			commandType.setValue(command.getType());
			break;		
		case IParameter:
			IParameter parameter = (IParameter)selectedElement.getValue();
			commandName.setText(parameter.getName());
			execcommand.setText(parameter.getExeccommand());
			commandType.setValue(parameter.getType());	
			required.setSelected(parameter.isRequired());
			break;
		case ISection:
			ISection section = (ISection)selectedElement.getValue();
			sectionName.setText(section.getName());			
			break;
		case ISnippet:
			ISnippet snippet = (ISnippet)selectedElement.getValue();
			snippetName.setText(snippet.getName());
			break;
		default:
			break;		
		}
	}
	
	public void speichern() {
		if(text.getText().isEmpty()) return;
		mediator.dateiSpeichern(text.getText());
	}
	
	public void speichernunter(){ // Speichern unter
		if(text.getText().isEmpty()) return;
		mediator.dateiSpeichernUnter(text.getText());	
	}
	
	public void öffnen(){ // Speichern unter
		mediator.dateiöffnen();	
	}

	public void programschließen() {
		mediator.exit();
		
	}
	
	private void updateXMLView(TreeItem<IViewElement> xmlTree){
		this.xmlTree.setRoot(xmlTree);
	}
	
	private void updateXMLText(String xmlText){		
		text.setText(xmlText);				
//		for(String line : xmlText.split("\n")){
//			if(line.contains(ISnippet.name)){
//				Text t = new Text(line+"\n");
//				t.setFill(Paint.valueOf(Color.BLUE.toString()));
//				text.getChildren().add(t);
//			}
//			else if(line.contains(ISection.name)){
//				Text t = new Text(line+"\n");
//				t.setFill(Paint.valueOf(Color.ROSYBROWN.toString()));
//				text.getChildren().add(t);
//			}
//			else if(line.contains(ICommand.name)){
//				Text t = new Text(line+"\n");
//				t.setFill(Paint.valueOf(Color.GREEN.toString()));
//				text.getChildren().add(t);
//			}else if(line.contains(IParameter.name)){
//				Text t = new Text(line+"\n");
//				t.setFill(Paint.valueOf(Color.BEIGE.toString()));
//				text.getChildren().add(t);
//			}else{
//				Text t = new Text(line+"\n");
//				t.setFill(Paint.valueOf(Color.BLACK.toString()));
//				text.getChildren().add(t);
//			}			
//		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handle(WorkerStateEvent arg0) {
		enableInputFields();
		if(arg0.getSource() instanceof GenerateXMLStringCommand)
			updateXMLText((String)arg0.getSource().getValue());
		if(arg0.getSource() instanceof GenerateTemplateViewCommand)
			updateXMLView((TreeItem<IViewElement>)arg0.getSource().getValue());
	}
	
	private void disableInputFields(){		
		snippetName.setDisable(true);
		interfaceNameLang.setDisable(true);
		interfaceNameKurz.setDisable(true);
		interfacePortRange.setDisable(true);
		sectionName.setDisable(true);
		commandName.setDisable(true);
		commandType.setDisable(true);
		execcommand.setDisable(true);
		required.setDisable(true);
	}
	
	private void enableInputFields(){
		snippetName.setDisable(false);
		interfaceNameLang.setDisable(false);
		interfaceNameKurz.setDisable(false);
		interfacePortRange.setDisable(false);
		sectionName.setDisable(false);
		commandName.setDisable(false);
		commandType.setDisable(false);
		execcommand.setDisable(false);
	}
	
	private void showParameters() {		
		resetGridPane();
		int i = 1;
		if(selectedElement.getValue().getViewTyp().equals(ViewTyp.ICommand)) {
			for (int i2 = 0; i2 < ((ICommand)selectedElement.getValue()).getParameters().size(); i2++) {
				IParameter parameter = ((ICommand)selectedElement.getValue()).getParameters().get(i2);
				Button button = new Button(parameter.getName());
				button.setUserData(i2);
				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						//Reset all the input fields
						resetGridPane();
						resetInputFields();

						//Start the logic
						selectedElement = selectedElement.getChildren().get((int) ((Button)arg0.getSource()).getUserData());
						xmlTree.getSelectionModel().select(selectedElement);
						selectedElement.setExpanded(true);
						
						scrolltoElement((IViewElement)parameter);						
						setSelectedElementParameters();
					}
				});				
				GridPane.setMargin(button, new Insets(5, 0, 0, 0));
				parameterGridPane.add(button, 1, i++);
			}
		}else {
			for (int i2 = 0; i2 < ((IParameter)selectedElement.getValue()).getParameters().size(); i2++) {
				IParameter parameter = ((IParameter)selectedElement.getValue()).getParameters().get(i2);
				Button button = new Button(parameter.getName());
				button.setUserData(i2);
				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						//Reset all input fields
						resetGridPane();
						resetInputFields();
						
						//Start the logic
						selectedElement = selectedElement.getChildren().get((int) ((Button)arg0.getSource()).getUserData());						
						xmlTree.getSelectionModel().select(selectedElement);
						selectedElement.setExpanded(true);
						
						scrolltoElement((IViewElement) parameter);
						setSelectedElementParameters();
					}
				});		
				GridPane.setMargin(button, new Insets(5, 0, 0, 0));
				parameterGridPane.add(button, 1, i++);
			}
		}
	}
	
	private void resetInputFields() {
		snippetName.setText("");
		sectionName.setText("");
		commandName.setText("");
		commandType.getSelectionModel().select(null);	
		execcommand.setText("");
		interfaceNameKurz.setText("");
		interfaceNameLang.setText("");
		interfacePortRange.setText("");
		required.setDisable(true);
		required.setSelected(false);		
		resetGridPane();		
	}
	
	public void setSelectedElementParameters(){
		TreeItem<IViewElement> parent = selectedElement.getParent();
		switch(selectedElement.getValue().getViewTyp()){						
		case IInterface:
			interfaceNameKurz.setText(((IInterface)selectedElement.getValue()).getPortBezeichnungkurz());
			interfaceNameLang.setText(((IInterface)selectedElement.getValue()).getPortBezeichnunglang());
			interfacePortRange.setText(((IInterface)selectedElement.getValue()).getPortRange());
			break;
		case IParameter:		
			required.setDisable(false);
			//Setting the labels correctly
			commandLabel.setText(parameterPrompt);
			commandNameLabel.setText(parameterNamePrompt);
			commandTypLabel.setText(parameterTypPrompt);	
			
			//Setting the parameter values
			commandName.setText(((IParameter)selectedElement.getValue()).getName());
			commandType.getSelectionModel().select(((IParameter)selectedElement.getValue()).getType());
			execcommand.setText(((IParameter)selectedElement.getValue()).getExeccommand());
			required.setSelected(((IParameter)selectedElement.getValue()).isRequired());
			
			//Parent attributes
				for(;!parent.getValue().getViewTyp().equals(ViewTyp.ISection);parent = parent.getParent());
			sectionName.setText(((ISection)parent.getValue()).getName());
			snippetName.setText(((ISnippet)parent.getParent().getValue()).getName());		
			showParameters();
			break;
		case ICommand:
			//Setting labels correctly
			commandLabel.setText(commandPrompt);
			commandNameLabel.setText(commandNamePrompt);
			commandTypLabel.setText(commandTypPrompt);	
			
			//Command attributes
			commandName.setText(((ICommand)selectedElement.getValue()).getName());
			commandType.getSelectionModel().select(((ICommand)selectedElement.getValue()).getType());
			execcommand.setText(((ICommand)selectedElement.getValue()).getExeccommand());
			//Parameter attriubtes
			for(;!parent.getValue().getViewTyp().equals(ViewTyp.ISection);parent = parent.getParent());				
			sectionName.setText(((ISection)parent.getValue()).getName());
			snippetName.setText(((ISnippet)parent.getParent().getValue()).getName());
			showParameters();
			break;
		case ISection:
			sectionName.setText(((ISection)selectedElement.getValue()).getName());
			snippetName.setText(((ISnippet)selectedElement.getParent().getValue()).getName());
			break;
		case ISnippet:
			snippetName.setText(((ISnippet)selectedElement.getValue()).getName());
			break;
		default:
			break;
		}
	}

	private void resetGridPane() {
		for(int i=1;i<parameterGridPane.getChildren().size();i++)
			parameterGridPane.getChildren().remove(i);		
	}
	
	private void scrolltoElement(IViewElement element){
		String[] lines = text.getText().split("\n");
		switch(element.getViewTyp()){
		case ICommand:								
			for(String line : lines)
				if(line.contains(ICommand.name))
					if(line.contains(((ICommand)element).getName()+"\"")){
						text.selectRange(text.getText().indexOf(line), text.getText().indexOf(line)+line.length());
						break;
					}
			break;
		case IInterface:			
			for(String line : lines)
				if(line.contains(IInterface.name))
					if(line.contains(((IInterface)element).getPortBezeichnunglang())
							&& ((IInterface)element).getPortRange().equals("-") || line.contains(((IInterface)element).getPortRange())){								
						text.selectRange(text.getText().indexOf(line), text.getText().indexOf(line)+line.length());
						break;
					}
			break;
		case IParameter:
			for(String line : lines)
				if(line.contains(IParameter.name))
					if(line.contains(((IParameter)element).getName()+"\"")){
						text.selectRange(text.getText().indexOf(line), text.getText().indexOf(line)+line.length());
						break;
					}
			break;
		case ISection:
			for(String line : lines)
				if(line.contains(ISection.name))
					if(line.contains(((ISection)element).getName()+"\"")){
						text.selectRange(text.getText().indexOf(line), text.getText().indexOf(line)+line.length());
						break;
					}
			break;
		case ISnippet:
			for(String line : lines)
				if(line.contains(ISnippet.name))
					if(line.contains(((ISnippet)element).getName()+"\"")){
						text.selectRange(text.getText().indexOf(line), text.getText().indexOf(line)+line.length());
						break;
					}
			break;
		default:
			break;		
		}
	}
	
	class TreeItemChangeListener implements ChangeListener<TreeItem<IViewElement>>{

		@Override
		public void changed(ObservableValue<? extends TreeItem<IViewElement>> arg0,
				TreeItem<IViewElement> oldValue, TreeItem<IViewElement> newValue) {
			if(newValue == null) return;
			if(oldValue != null) resetInputFields();

			//Start the logic
			resetGridPane();
			scrolltoElement(newValue.getValue());
			selectedElement = newValue;
			setSelectedElementParameters();
		}			
		
	}
	
	class MenuItemHandler implements EventHandler<ActionEvent>{
		private String menuName;
		
		public MenuItemHandler(String menuName) {
			this.menuName = menuName;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(menuName.contains(":")){
				if(selectedElement == null || !selectedElement.getValue().getViewTyp().equals(ViewTyp.ISnippet)) return;
				mediator.addGeneratedSection(menuName.split(":")[0], menuName.split(":")[1], ((ISnippet)selectedElement.getValue()).getName());
			}else
				mediator.addGeneratedSnippet(menuName);
		}
		
	}

	class NewElementButtonHandler implements EventHandler<ActionEvent>{
		private ToggleGroup toggleGroup;
		private String selected;
		private Stage stage;
		Button hinzufügen;
		
		public NewElementButtonHandler(ToggleGroup toggleGroup) {
			this.toggleGroup = toggleGroup;
		}
		@Override
		public void handle(ActionEvent arg0) {
			selected = (String) toggleGroup.getSelectedToggle().getUserData();
			((Node) arg0.getSource()).getScene().getWindow().hide();
			stage = new Stage();
			stage.setResizable(true);
			stage.setAlwaysOnTop(true);
			stage.setScene(new Scene(getRoot()));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setAlwaysOnTop(true);
			stage.sizeToScene();
			stage.show();
		}
		
		public AnchorPane getButtons(){
			//Anchropane
			AnchorPane anchorPane = new AnchorPane();
			anchorPane.setPrefWidth(30);
			
			//Content at the buttom and listseners
			hinzufügen = new Button("Hinzufügen");
			Button abbrechen = new Button("Abbrechen");
			abbrechen.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					stage.close();
				}
			});			
			anchorPane.setPrefWidth(30);
			anchorPane.getChildren().addAll(hinzufügen, abbrechen);
			AnchorPane.setRightAnchor(hinzufügen, 1.0);
			AnchorPane.setLeftAnchor(abbrechen, 1.0);
			return anchorPane;
		}
		
		public BorderPane getRoot(){
			BorderPane borderPane = new BorderPane();			
			borderPane.setBottom(getButtons());
			borderPane.setCenter(getCenter());
			borderPane.setTop(getTop());
			return borderPane;
		}
		
		public Label getTop(){
			Label title = new Label("Bitte geben Sie die erforderlichen Daten ein!");
			title.setFont(new Font("Cambria", 25));			
			return title;
		}
		
		public VBox getCenter(){
			//Setting general elements			
			Label nameLabel = new Label("Name");			
			nameLabel.setFont(new Font("Cambria", 18));
			TextField name = new TextField();
			
			VBox vBox = new VBox(10);
			vBox.getChildren().add(new HBox(10, nameLabel, name));
			//Now comes the specific stuff
			switch(selected){
			case "Snippet":
				hinzufügen.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						mediator.addSnippet(name.getText());
						stage.close();
					}
				});
				break;
			case "Section":
				hinzufügen.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						mediator.addSection(name.getText(), selectedElement);
						stage.close();
					}
				});
				break;
			case "Command":
				TextField execcommand = new TextField();
				ComboBox<Type> typ = new ComboBox<Type>(commandType.getItems());
				vBox.getChildren().add(new HBox(10, new Label("Execcommand"), execcommand));
				vBox.getChildren().add(new HBox(10, new Label("Commandtyp"), typ));
				hinzufügen.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						mediator.addCommand(name.getText(), execcommand.getText(), typ.getValue(), selectedElement);
						stage.close();
					}
				});
				break;
			case "Interface":
				TextField portbezeichnunglang = new TextField();
				TextField portbezeichnungkurz = new TextField();
				TextField portRange = new TextField();
				portRange.setPromptText("1-24 ... 0/1 - 0/24");
				vBox.getChildren().add(new HBox(10, new Label("Portbezeichnung, lang"), portbezeichnunglang));
				vBox.getChildren().add(new HBox(10, new Label("Portbezeichnung, kurz"), portbezeichnungkurz));
				vBox.getChildren().add(new HBox(10, new Label("Portrange"), portRange));
				hinzufügen.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						mediator.addInterface(portbezeichnunglang.getText(), portbezeichnungkurz.getText(), portRange.getText());
						stage.close();
					}
				});
				break;
			case "Parameter":
				TextField execcommand1 = new TextField();
				ComboBox<Type> typ1 = new ComboBox<Type>(commandType.getItems());
				CheckBox required = new CheckBox("Required");
				vBox.getChildren().add(new HBox(10, new Label("Execcommand"), execcommand1));
				vBox.getChildren().add(new HBox(10, new Label("Parametertyp"), typ1));
				vBox.getChildren().add(new HBox(required));
				hinzufügen.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						mediator.addParameter(name.getText(), execcommand1.getText(), typ1.getValue(), required.isSelected(), selectedElement);
						stage.close();
					}
				});
				break;
			}
			return vBox;
		}
	}
}




























