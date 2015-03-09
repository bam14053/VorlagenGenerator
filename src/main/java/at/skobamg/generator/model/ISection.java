package at.skobamg.generator.model;

import java.util.ArrayList;

public interface ISection extends IViewElement{
	//Property for each field	
	public static String name = "section";
	public static String propertyName = "name";
	
	public String getName();	
	public ArrayList<ICommand> getCommands();
	public void addCommand(ICommand command);
	public void setName(String name);
	public void removeCommand(ICommand command);
	public void removeParameter(IParameter value);
	public void addCommandtoCommand(String commandName, String execcommand,
			Type type, ICommand parentCommand);
	public void addCommandtoParameter(String commandName, String execcommand,
			Type type, IParameter parentParameter);
	public void addParametertoCommand(String parameterName, String execcommand,
			Type type, boolean required, ICommand parentCommand);
	public void addParametertoParameter(String parameterName,
			String execcommand, Type type, boolean required, IParameter parentParameter);
}
