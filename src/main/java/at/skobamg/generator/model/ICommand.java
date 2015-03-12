package at.skobamg.generator.model;

import java.util.ArrayList;

public interface ICommand extends IViewElement{		
	public static String name="command";
	public static String propertyName = "name";
	public static String propertyType = "type";
	public static String propertyExeccommand = "execcommand";		
	public void setName(String name);
	public void setType(Type type);
	public void setExeccommand(String command);
	public boolean addParameter(IParameter parameter);
	public boolean addCommand(ICommand command);
	public ArrayList<IParameter> getParameters();
	public ArrayList<ICommand> getCommands();
	public String getName();
	public Type getType();
	public String getExeccommand();
	@Override
	public ViewTyp getViewTyp();
	public float getID();
	public boolean removeCommand(ICommand command);
	public boolean removeParameter(IParameter parameter);
	public boolean addCommandtoCommand(String commandName, String execcommand,
			Type type, ICommand parentCommand);
	public boolean addCommandtoParameter(String commandName,
			String execcommand, Type type, IParameter parentParameter);
	public boolean addParametertoCommand(String parameterName,
			String execcommand, Type type, boolean required,
			ICommand parentCommand);
	public boolean addParametertoParameter(String parameterName,
			String execcommand, Type type, boolean required,
			IParameter parentParameter);
}
