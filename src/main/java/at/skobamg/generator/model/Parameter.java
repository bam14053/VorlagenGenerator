/**
 * 
 */
package at.skobamg.generator.model;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 */
public class Parameter implements IParameter{
	/**
	 * Names des Parameters
	 */
	private String name;
	/**
	 * Wenn typ gesetzt, weitere Parameter folgen
	 */
	private Type type;
	/**
	 * Execcomand für Parameter
	 */
	private String execcommand;
	/**
	 * Muss ich diesen parameter angeben
	 */
	private boolean required;
	/**
	 * Wenn ein bestimmter Parameter weitere Commands zulässt
	 */
	private ArrayList<ICommand> commands = new ArrayList<>();
	private ArrayList<IParameter> parameters = new ArrayList<>();
	private final float id = new Random().nextFloat()+Float.MAX_VALUE;
	
	public Parameter(String name, String execcommand, Type type,
			boolean required, ArrayList<ICommand> commands,
			ArrayList<IParameter> parameters) {
		super();
		this.name = name;
		this.type = type;
		this.execcommand = execcommand;
		this.required = required;
		this.commands = commands;
		this.parameters = parameters;
	}
	
	public Parameter(String name, String execcommand, Type type,
			boolean required) {
		super();
		this.name = name;
		this.type = type;
		this.execcommand = execcommand;
		this.required = required;
	}

	@Override
	public ArrayList<IParameter> getParameters() {
		return parameters;
	}

	@Override
	public ArrayList<ICommand> getCommands() {
		return commands;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public String getExeccommand() {
		return execcommand;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public ViewTyp getViewTyp() {
		return ViewTyp.IParameter;
	}

	@Override
	public String toString() {
		return IParameter.name+": "+name;
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
	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public boolean addParameter(IParameter parameter) {
		return parameters.add(parameter);
	}

	@Override
	public boolean addCommand(ICommand command) {
		return commands.add(command);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IParameter && ((IParameter)obj).getID() == id)
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

	@Override
	public boolean addCommandToCommand(String commandName, String execcommand,
			Type type, ICommand parentCommand) {
		if(commands.contains(parentCommand))
			return commands.get(commands.indexOf(parentCommand)).addCommand(new Command(commandName, type, execcommand));
		for(ICommand command : commands)
			if(command.addCommandtoCommand(commandName, execcommand, type, parentCommand))
				return true;
		for(IParameter parameter : parameters)
			if(parameter.addCommandToCommand(commandName, execcommand, type, parentCommand))
				return true;
		return false;
	}

	@Override
	public boolean addCommandtoParameter(String commandName,
			String execcommand, Type type, IParameter parentParameter) {
		if(parameters.contains(parentParameter))
			return parameters.get(parameters.indexOf(parentParameter)).addCommand(new Command(commandName, type, execcommand));		
		for(ICommand command : commands)
			if(command.addCommandtoParameter(commandName, execcommand, type, parentParameter))
				return true;
		for(IParameter parameter : parameters)
			if(parameter.addCommandtoParameter(commandName, execcommand, type, parentParameter))
				return true;
		return false;
	}

	@Override
	public boolean addParameterToCommand(String parameterName, String execcommand, Type type, boolean required,
			ICommand parentCommand) {
		if(commands.contains(parentCommand))
			return commands.get(commands.indexOf(parentCommand)).addParameter(new Parameter(parameterName, execcommand, type, required));
		for(ICommand command : commands)
			if(command.addParametertoCommand(parameterName, execcommand, type, required, parentCommand))
				return true;
		for(IParameter parameter : parameters)
			if(parameter.addParameterToCommand(parameterName, execcommand, type, required, parentCommand))
				return true;
		return false;
	}

	@Override
	public boolean addParametertoParameter(String parameterName, String execcommand, Type type, boolean required, 
			IParameter parentParameter) {
		if(parameters.contains(parentParameter))
			return parameters.get(parameters.indexOf(parentParameter)).addParameter(new Parameter(parameterName, execcommand, type, required));		
		for(ICommand command : commands)
			if(command.addParametertoParameter(parameterName, execcommand, type, required, parentParameter))
				return true;
		for(IParameter parameter : parameters)
			if(parameter.addParametertoParameter(parameterName, execcommand, type, required, parentParameter))
				return true;
		return false;
	}

}
