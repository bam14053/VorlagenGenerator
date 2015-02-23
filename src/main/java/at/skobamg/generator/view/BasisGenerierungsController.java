package at.skobamg.generator.view;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.mediator.IEventMediator;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BasisGenerierungsController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;
	
	@Override
	public Pane getView() {
		VBox vbox = new VBox(0);
		vbox.setPadding(new Insets(2));
		vbox.setSpacing(0);
		HashMap<String, ISnippet> snippets = mediator.getSnippets();
		for(String name : mediator.getSnippets().keySet()){
			CheckBoxTreeItem<String> snippet = new CheckBoxTreeItem<String>(name);
			snippet.setExpanded(true);
			
			TreeView<String> tree = new TreeView<>(snippet);
			tree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());			
			for(ISection section : snippets.get(name).getSections()){
				CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(section.getSectionName());
				snippet.getChildren().add(item);
			}
			
			tree.setRoot(snippet);
			tree.setShowRoot(true);
			vbox.getChildren().add(tree);
		}
		
		((BorderPane)view).setCenter(vbox);
		return super.getView();
	}
	
}
