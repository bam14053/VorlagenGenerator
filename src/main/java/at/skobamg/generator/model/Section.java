/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;

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
	
	@Override
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

	@Override
	public void addCommandtoCommand(String commandName, String execcommand,
			Type type, ICommand parentCommand) {
		if(commands.contains(parentCommand))
			commands.get(commands.indexOf(parentCommand)).addCommand(new Command(commandName, type, execcommand));
		else for(ICommand command : commands)
				if(command.addCommandtoCommand(commandName, execcommand, type, parentCommand))
					return;
	}

	@Override
	public void addCommandtoParameter(String commandName, String execcommand,
			Type type, IParameter parentParameter) {
		for(ICommand command : commands)
			if(command.addCommandtoParameter(commandName, execcommand, type, parentParameter))
				return;
	}

	@Override
	public void addParametertoCommand(String parameterName, String execcommand,
			Type type, boolean required, ICommand parentCommand) {
		if(commands.contains(parentCommand))
			commands.get(commands.indexOf(parentCommand)).addParameter(new Parameter(parameterName, execcommand, type, required));
		else for(ICommand command : commands)
				if(command.addParametertoCommand(parameterName, execcommand, type, required, parentCommand))
					return;
	}

	@Override
	public void addParametertoParameter(String parameterName,
			String execcommand, Type type, boolean required, IParameter parentParameter) {
		for(ICommand command : commands)
			if(command.addParametertoParameter(parameterName, execcommand, type, required, parentParameter))
				return;
	}
}
