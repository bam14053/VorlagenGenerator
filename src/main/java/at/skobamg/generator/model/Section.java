/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;
import java.util.Random;

import org.w3c.dom.Element;

/**
 *
 */
public class Section implements ISection {	
	private String name;
	private ArrayList<ICommand> commands;
	
	public Section(String name){
		super();
		this.name = name;
		commands = new ArrayList<ICommand>();
	}
	
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
		if(obj instanceof ISection && ((ISection)obj).getName().equals(name))
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
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void removeCommand(ICommand command) {
		if(commands.remove(command))
			return;
		else{
			for(ICommand tempCommand : commands)
				if(tempCommand.removeCommand(command))
					return;
		}
	}

	@Override
	public void removeParameter(IParameter parameter) {
		for(ICommand command : commands)
			if(command.removeParameter(parameter))
				return;
	}
}
