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
		if(((Section)obj).name.equals(name))
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
}
