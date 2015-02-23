package at.skobamg.generator.logic;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.model.GeneratorModel;
import at.skobamg.generator.model.IGeneratorModel;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.ITemplate;
import at.skobamg.generator.model.Section;
import at.skobamg.generator.model.Snippet;
import at.skobamg.generator.model.Template;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

public class CreateTemplateCommand extends Service<ITemplate> {
	@Autowired
	private IGeneratorModel generatorModel;
	private ArrayList<CheckBoxTreeItem<String>> checkedItems;
	private String switchname;
	private String switchversion;
	
	public CreateTemplateCommand(IGeneratorModel generatorModel, ArrayList<CheckBoxTreeItem<String>> checkedItems, String name, String version){
		this.generatorModel = generatorModel;
		this.checkedItems = checkedItems;
		switchname = name;
		switchversion = version;
	}

	@Override
	protected Task<ITemplate> createTask() {
		return new Task<ITemplate>() {

			@Override
			protected ITemplate call() throws Exception {
				ArrayList<ISnippet> snippets = new ArrayList<>();
				for(int i = 0; i < checkedItems.size();i++){
					if(checkedItems.get(i).getChildren().size() > 0){
						snippets.add(new Snippet(checkedItems.get(i).getValue()));
						i++;
						for(;i < checkedItems.size() && checkedItems.get(i).isLeaf();i++)
							if(checkedItems.get(i).isSelected()){
								ISnippet snippet = generatorModel.getSnippet(snippets.get(snippets.size()-1).getName());
								ISection section = snippet.getSection(checkedItems.get(i).getValue());
//								ISection section = generatorModel.getSnippet(snippets.get(snippets.size()-1).getName()).getSection(checkedItems.get(i).getValue());
								snippets.get(snippets.size()-1).addSection(section);
							}
					}
				}
				return new Template(switchname, switchversion, snippets);
			}
		};		
	}

}
