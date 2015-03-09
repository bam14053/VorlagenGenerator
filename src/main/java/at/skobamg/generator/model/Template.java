/**
 * 
 */
package at.skobamg.generator.model;
import java.util.ArrayList;

import at.skobamg.generator.model.Interface.InvalidPortRangeException;
import javafx.scene.control.TreeItem;
/**
 *
 */
public class Template implements ITemplate{
	private String switchName;
	private String iOSVersion;
	private ArrayList<ISnippet> snippets;
	private ArrayList<IInterface> interfaces;

	public Template(String switchName, String iOSVersion,
			ArrayList<ISnippet> snippets, ArrayList<IInterface> interfaces) {
		super();
		this.switchName = switchName;
		this.iOSVersion = iOSVersion;
		this.snippets = snippets;
		this.interfaces = interfaces;
	}

	@Override
	public String getSwitchVersion() {
		return iOSVersion;
	}

	@Override
	public String getSwitchName() {
		return switchName;
	}

	@Override
	public ArrayList<ISnippet> getSnippets() {
		return snippets;
	}

	@Override
	public ISnippet getSnippet(String name) {
		if(snippets.contains(new Snippet(name, null)))
			return snippets.get(snippets.indexOf(new Snippet(name, null)));
		return null;
	}

	@Override
	public void setSnippets(ArrayList<ISnippet> snippets) {
		this.snippets = snippets;
	}

	@Override
	public void setInterfaces(ArrayList<IInterface> interfaces) {
		this.interfaces = interfaces;
	}

	@Override
	public ArrayList<IInterface> getInterfaces() {
		return interfaces;
	}

	@Override
	public ViewTyp getViewTyp() {
		return ViewTyp.ITemplate;
	}
	
	@Override
	public String toString() {
		return switchName+":"+iOSVersion;
	}

	@Override
	public void deleteElement(TreeItem<IViewElement> element) {
		TreeItem<IViewElement> parent = element;
		switch(element.getValue().getViewTyp()){
		case IInterface:
			interfaces.remove(element.getValue());
			break;
		case ISnippet:
			snippets.remove(element.getValue());
			break;
		case ISection:
			snippets.get(snippets.indexOf(element.getParent().getValue())).deleteSection((ISection) element.getValue());
			break;
		case ICommand:
			while((parent = parent.getParent()).getValue().getViewTyp() != ViewTyp.ISection );
			snippets.get(snippets.indexOf(parent.getParent().getValue())).
				getSection(((ISection) parent.getValue()).getName()).
					removeCommand((ICommand) element.getValue());			
			break;
		case IParameter:
			while((parent = parent.getParent()).getValue().getViewTyp() != ViewTyp.ISection );
			snippets.get(snippets.indexOf(parent.getParent().getValue())).
				getSection(((ISection) parent.getValue()).getName()).
					removeParameter((IParameter) element.getValue());
			break;
		default:
			break;
		}
	}

	@Override
	public void addSnippet(String snippetName) {
		snippets.add(new Snippet(snippetName));
	}

	@Override
	public void addSection(String sectionName, ISnippet snippet) {
		snippets.get(snippets.indexOf(snippet)).addSection(new Section(sectionName));
	}

	@Override
	public void addInterface(String portbezeichnunglang,
			String portbezeichnungkurz, String portRange) throws InvalidPortRangeException {
		if(portRange.isEmpty() || portRange.equals("-"))
			interfaces.add(new Interface(portbezeichnunglang, portbezeichnungkurz));
		else
			interfaces.add(new Interface(portbezeichnungkurz, portbezeichnungkurz, portRange.split("-")[0], portRange.split("-")[1]));
	}

	@Override
	public void addCommand(String commandName, String execcommand, Type type,
			TreeItem<IViewElement> parent) {
		if(parent.getValue().getViewTyp().equals(ViewTyp.ISection))
			snippets.get(snippets.indexOf(parent.getParent().getValue())).getSection(((ISection)parent.getValue()).getName()).
				addCommand(new Command(commandName, type, execcommand));
		else{
			TreeItem<IViewElement> sectionParent = parent;
			while(!(sectionParent = sectionParent.getParent()).getValue().getViewTyp().equals(ViewTyp.ISection));
			if(parent.getValue().getViewTyp().equals(ViewTyp.ICommand))
				snippets.get(snippets.indexOf(sectionParent.getParent().getValue())).getSection(((ISection)sectionParent.getValue()).getName()).
					addCommandtoCommand(commandName, execcommand, type, (ICommand)parent.getValue());
			else
				snippets.get(snippets.indexOf(sectionParent.getParent().getValue())).
					getSection(((ISection)sectionParent.getValue()).getName()).
						addCommandtoParameter(commandName, execcommand, type, (IParameter)parent.getValue());			
		}
	}

	@Override
	public void addParameter(String parameterName, String execcommand,
			Type type, boolean required, TreeItem<IViewElement> parent) {
		TreeItem<IViewElement> sectionParent = parent;
		while(!(sectionParent = sectionParent.getParent()).getValue().getViewTyp().equals(ViewTyp.ISection));
		if(parent.getValue().getViewTyp().equals(ViewTyp.ICommand))
			snippets.get(snippets.indexOf(sectionParent.getParent().getValue())).getSection(((ISection)sectionParent.getValue()).getName()).
				addParametertoCommand(parameterName, execcommand, type, required, (ICommand)parent.getValue());
		else
			snippets.get(snippets.indexOf(sectionParent.getParent().getValue())).getSection(((ISection)sectionParent.getValue()).getName()).
				addParametertoParameter(parameterName, execcommand, type, required, (IParameter)parent.getValue());
	}
}
