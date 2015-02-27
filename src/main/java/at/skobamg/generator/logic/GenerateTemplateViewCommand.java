package at.skobamg.generator.logic;
import at.skobamg.generator.model.ICommand;
import at.skobamg.generator.model.IInterface;
import at.skobamg.generator.model.IParameter;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.ITemplate;
import at.skobamg.generator.model.IViewElement;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;

public class GenerateTemplateViewCommand extends Service<TreeItem<IViewElement>>{
	private ITemplate template;
	
	public GenerateTemplateViewCommand(ITemplate template) {
		this.template = template;
	}
	
	@Override
	protected Task<TreeItem<IViewElement>> createTask() {
		return new Task<TreeItem<IViewElement>>() {
			@Override
			protected TreeItem<IViewElement> call(){
				TreeItem<IViewElement> rootNode = new TreeItem<IViewElement>(template);
				rootNode.setExpanded(true);
				for(IInterface interf : template.getInterfaces())
					rootNode.getChildren().add(new TreeItem<IViewElement>(interf));
				for(ISnippet snippet : template.getSnippets()){
					TreeItem<IViewElement> snippetView = new TreeItem<IViewElement>(snippet);
					for(ISection section : snippet.getSections()){
						TreeItem<IViewElement> sectionView = new TreeItem<IViewElement>(section);
						for(ICommand command : section.getCommands())
							sectionView.getChildren().add(parseCommand(command));
						snippetView.getChildren().add(sectionView);
					}
					rootNode.getChildren().add(snippetView);
				}							
				return rootNode;
			}
		};
	}

	private TreeItem<IViewElement> parseCommand(ICommand command){
		TreeItem<IViewElement> commandElement = new TreeItem<IViewElement>(command);
		for(IParameter parameter: command.getParameters())
			commandElement.getChildren().add(parseParameter(parameter));
		for(ICommand com : command.getCommands())
			commandElement.getChildren().add(parseCommand(com));
		return commandElement;
		
	}
	
	private TreeItem<IViewElement> parseParameter(IParameter parameter){
		TreeItem<IViewElement> parameterElement = new TreeItem<IViewElement>(parameter);
		for(ICommand command : parameter.getCommands())
			parameterElement.getChildren().add(parseCommand(command));
		for(IParameter param : parameter.getParameters())
			parameterElement.getChildren().add(parseParameter(param));
		return parameterElement;
		
	}
}
