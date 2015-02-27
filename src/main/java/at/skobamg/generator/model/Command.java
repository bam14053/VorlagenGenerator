/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;

/**
 *
 */
public class Command implements ICommand {
	private String name;
	private Type type;
	private String execcommand;
	private ArrayList<ICommand> commands;
	private ArrayList<IParameter> parameters;
	
	public Command(String name, Type type, String execcommand,
			ArrayList<ICommand> commands, ArrayList<IParameter> parameters) {
		super();
		this.name = name;
		this.type = type;
		this.execcommand = execcommand;
		this.commands = commands;
		this.parameters = parameters;
	}

	@Override
	public ArrayList<IParameter> getParameters() {
		return parameters;
	}

	@Override
	public ArrayList<ICommand> getCommands() {
		return commands;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String getExeccommand() {
		return execcommand;
	}

	@Override
	public ViewTyp getViewTyp() {
		return ViewTyp.ICommand;
	}
	
	@Override
	public String toString(){
		return ICommand.name+": "+name;		
	}
}
