/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 */
public class Command implements ICommand {
	private String name;
	private Type type;
	private String execcommand;
	private ArrayList<ICommand> commands;
	private ArrayList<IParameter> parameters;
	private final float id = new Random().nextFloat()+Float.MAX_VALUE;
	
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

	@Override
	public float getID() {
		return id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public void setExeccommand(String execcommand) {
		this.execcommand = execcommand;
	}

	@Override
	public void addParameter(IParameter parameter) {
		parameters.add(parameter);
	}

	@Override
	public void addCommand(ICommand command) {
		commands.add(command);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ICommand && ((ICommand)obj).getID() == id)
			return true;
		return false;
	}

	@Override
	public boolean removeCommand(ICommand command) {
		if(commands.remove(command))
			return true;
		for(IParameter parameter : parameters)
			if(parameter.removeCommand(command))
				return true;
		for(ICommand tempCommand : commands)
			if(tempCommand.removeCommand(command))
				return true;
		return false;
	}

	@Override
	public boolean removeParameter(IParameter parameter) {
		if(parameters.remove(parameter))
			return true;
		for(IParameter parameter2 : parameters)
			if(parameter2.removeParameter(parameter))
				return true;
		for(ICommand command : commands)
			if(command.removeParameter(parameter))
				return true;
		return false;
	}
}
