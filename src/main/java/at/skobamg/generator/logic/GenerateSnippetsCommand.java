package at.skobamg.generator.logic;

import java.util.ArrayList;
import java.util.function.Predicate;

import at.skobamg.generator.model.IGeneratorModel;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.Snippet;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBoxTreeItem;

public class GenerateSnippetsCommand extends Service<ArrayList<ISnippet>> {
	private IGeneratorModel generatorModel;
	private ArrayList<CheckBoxTreeItem<String>> checkedItems;
	
	public GenerateSnippetsCommand(IGeneratorModel generatorModel, ArrayList<CheckBoxTreeItem<String>> checkedItems){
		this.generatorModel = generatorModel;
		this.checkedItems = checkedItems;
	}

	@Override
	protected Task<ArrayList<ISnippet>> createTask() {
		return new Task<ArrayList<ISnippet>>() {

			@Override
			protected ArrayList<ISnippet> call() throws Exception {
				ArrayList<ISnippet> snippets = new ArrayList<>();
				for(int i = 0; i < checkedItems.size();){
					if(checkedItems.get(i).getChildren().size() > 0){
						snippets.add(new Snippet(checkedItems.get(i).getValue()));
						i++;
						for(;i < checkedItems.size() && checkedItems.get(i).isLeaf();i++)
							if(checkedItems.get(i).isSelected()){
								ISnippet snippet = generatorModel.getSnippet(snippets.get(snippets.size()-1).getName());
								ISection section = snippet.getSection(checkedItems.get(i).getValue());
								snippets.get(snippets.size()-1).addSection(section);
							}						
					}					
				}
				snippets.removeIf(new Predicate<ISnippet>() {
					@Override
					public boolean test(ISnippet t) {
						if(t.getSections().isEmpty())
							return true;
						return false;
					}					
				});
				return snippets;
			}
		};		
	}

}
