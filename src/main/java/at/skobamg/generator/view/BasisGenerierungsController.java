package at.skobamg.generator.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.mediator.IEventMediator;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BasisGenerierungsController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;
	private ArrayList<CheckBoxTreeItem<String>> items;	
	@FXML
	private Button zurueck;
	
	@Override
	public Pane getView() {
		items = new ArrayList<>();
		
		//Start to generate the view
		VBox vbox = new VBox(0);
		vbox.setPadding(new Insets(2));
		vbox.setSpacing(0);
		HashMap<String, ISnippet> snippets = mediator.getSnippets();
		for(String name : mediator.getSnippets().keySet()){
			CheckBoxTreeItem<String> snippet = new CheckBoxTreeItem<String>(name);
			items.add(snippet);			
			snippet.setExpanded(true);
			
			TreeView<String> tree = new TreeView<>(snippet);
			tree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());			
			for(ISection section : snippets.get(name).getSections()){
				CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(section.getName());
				items.add(item);
				snippet.getChildren().add(item);
			}
			
			tree.setRoot(snippet);
			tree.setShowRoot(true);
			vbox.getChildren().add(tree);
		}
		
		((BorderPane)view).setCenter(vbox);
		return super.getView();
	}
	
	public void zurueck(ActionEvent actionEvent){
		mediator.zurueckZumInterfacedefinitionsfenster();
		
		
	}
	
	public void weiter(ActionEvent actionEvent){
		((Node)actionEvent.getSource()).getScene().getWindow().hide();
		mediator.xmlGenerieren(items);
	}
	
}
