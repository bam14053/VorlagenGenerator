/**
 * 
 */
package at.skobamg.generator.view;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.logic.GenerateTemplateViewCommand;
import at.skobamg.generator.logic.GenerateXMLStringCommand;
import at.skobamg.generator.mediator.IEventMediator;
import at.skobamg.generator.model.ICommand;
import at.skobamg.generator.model.IInterface;
import at.skobamg.generator.model.IParameter;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.ITemplate;
import at.skobamg.generator.model.IViewElement;
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
	private Button neuerParameterButton;
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
	private IViewElement selectedElement;
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
		commandType.getItems().addAll(Type.values());

//		text.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
//					if(mouseEvent.getClickCount() == 1);
//						
//			}
//		});
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
	
	public void speichernunter(){ // Speichern unter
			mediator.SpeichernUnter();	
	}
	
	public void öffnen(){ // Speichern unter
		mediator.Dateiöffnen();	
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
		if(arg0.getSource() instanceof GenerateXMLStringCommand)
			updateXMLText((String)arg0.getSource().getValue());;
		if(arg0.getSource() instanceof GenerateTemplateViewCommand)
			updateXMLView((TreeItem<IViewElement>)arg0.getSource().getValue());
	}
	
	class TreeItemChangeListener implements ChangeListener<TreeItem<IViewElement>>{

		@Override
		public void changed(ObservableValue<? extends TreeItem<IViewElement>> arg0,
				TreeItem<IViewElement> oldValue, TreeItem<IViewElement> newValue) {
			if(newValue == null) return;
			if(oldValue != null) resetTextFields();
			//Start the logic
			scrolltoElement(newValue.getValue());
			selectedElement = newValue.getValue();
			TreeItem<IViewElement> parent = newValue.getParent();
			switch(selectedElement.getViewTyp()){						
			case IInterface:
				interfaceNameKurz.setText(((IInterface)selectedElement).getPortBezeichnungkurz());
				interfaceNameLang.setText(((IInterface)selectedElement).getPortBezeichnunglang());
				interfacePortRange.setText(((IInterface)selectedElement).getPortRange());
				break;
			case IParameter:		
				resetGridPane();
				//Setting the labels correctly
				commandLabel.setText(parameterPrompt);
				commandNameLabel.setText(parameterNamePrompt);
				commandTypLabel.setText(parameterTypPrompt);	
				
				//Setting the parameter values
				commandName.setText(((IParameter)selectedElement).getName());
				commandType.getSelectionModel().select(((IParameter)selectedElement).getType());
				execcommand.setText(((IParameter)selectedElement).getExeccommand());
				
				//Parent attributes
 				for(;!parent.getValue().getViewTyp().equals(ViewTyp.ISection);parent = parent.getParent());
				sectionName.setText(((ISection)parent.getValue()).getName());
				snippetName.setText(((ISnippet)parent.getParent().getValue()).getName());				
				break;
			case ICommand:
				resetGridPane();
				//Setting labels correctly
				commandLabel.setText(commandPrompt);
				commandNameLabel.setText(commandNamePrompt);
				commandTypLabel.setText(commandTypPrompt);	
				
				//Command attributes
				commandName.setText(((ICommand)selectedElement).getName());
				commandType.getSelectionModel().select(((ICommand)selectedElement).getType());
				execcommand.setText(((ICommand)selectedElement).getExeccommand());
				//Parameter attriubtes
				//Parent attributes
				parent = newValue.getParent();
				for(;!parent.getValue().getViewTyp().equals(ViewTyp.ISection);parent = parent.getParent());				
				sectionName.setText(((ISection)parent.getValue()).getName());
				snippetName.setText(((ISnippet)parent.getParent().getValue()).getName());
				break;
			case ISection:
				sectionName.setText(((ISection)selectedElement).getName());
				snippetName.setText(((ISnippet)newValue.getParent().getValue()).getName());
				break;
			case ISnippet:
				snippetName.setText(((ISnippet)selectedElement).getName());
				break;
			default:
				break;
			}
		}
		
		private void scrolltoElement(IViewElement element){
			String[] lines = text.getText().split("\n");
			switch(element.getViewTyp()){
			case ICommand:								
				for(String line : lines)
					if(line.contains(ICommand.name))
						if(line.contains(((ICommand)element).getName())){
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
						if(line.contains(((IParameter)element).getName())){
							text.selectRange(text.getText().indexOf(line), text.getText().indexOf(line)+line.length());
							break;
						}
				break;
			case ISection:
				for(String line : lines)
					if(line.contains(ISection.name))
						if(line.contains(((ISection)element).getName())){
							text.selectRange(text.getText().indexOf(line), text.getText().indexOf(line)+line.length());
							break;
						}
				break;
			case ISnippet:
				for(String line : lines)
					if(line.contains(ISnippet.name))
						if(line.contains(((ISnippet)element).getName())){
							text.selectRange(text.getText().indexOf(line), text.getText().indexOf(line)+line.length());
							break;
						}
				break;
			default:
				break;		
			}
		}

		private void resetTextFields() {
			snippetName.setText("");
			sectionName.setText("");
			commandName.setText("");
			commandType.getSelectionModel().select(null);	
			execcommand.setText("");
			interfaceNameKurz.setText("");
			interfaceNameLang.setText("");
			interfacePortRange.setText("");
			resetGridPane();
		}

		private void resetGridPane() {
			for(int i=2;i<parameterGridPane.getChildren().size();i++)
				parameterGridPane.getChildren().remove(i);
		}	
	}
}