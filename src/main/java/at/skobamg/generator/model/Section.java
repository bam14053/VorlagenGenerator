/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;

import org.w3c.dom.Element;

/**
 *
 */
public class Section implements ISection {	
	private String name;
	private ArrayList<ICommand> commands;
	
	public Section(String name, ArrayList<ICommand> commands) {
		super();
		this.name = name;
		this.commands = commands;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(((IViewElement)obj).getViewTyp().equals(ViewTyp.ISection))	
			if(((ISection)obj).getName().equals(name))
				return true;
		return false;
	}

	@Override
	public ArrayList<ICommand> getCommands() {
		return commands;
	}

	@Override
	public void addCommand(ICommand command) {
		commands.add(command);
	}

	@Override
	public ViewTyp getViewTyp() {
		return ViewTyp.ISection;
	}
	
	@Override
	public String toString() {
		return ISection.name+": "+name;
	}
}
