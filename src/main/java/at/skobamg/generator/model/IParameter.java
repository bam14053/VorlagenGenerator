package at.skobamg.generator.model;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public interface IParameter {
	public static String name="parameter";
	public static String propertyName = "name";
	public static String propertyType = "type";
	public static String propertyExeccommand = "execcommand";	
	public static String propertyRequired = "required";
	
	static IParameter getParameter(Element parameter) throws InvalidTypeException {
		ArrayList<ICommand> commands = new ArrayList<>();
		ArrayList<IParameter> parameters = new ArrayList<>();
		NodeList commandList = parameter.getElementsByTagName(ICommand.name);
		NodeList parameterList = parameter.getElementsByTagName(IParameter.name);
		
		for(int i = 0; i < commandList.getLength(); i++)
			if(commandList.item(i).getParentNode().equals(parameter))
				commands.add(ICommand.getCommand((Element)commandList.item(i)));
		for(int i = 0; i < parameterList.getLength(); i++)
			if(parameterList.item(i).getParentNode().equals(parameter))
				parameters.add(getParameter((Element)parameterList.item(i)));		

		return new Parameter(parameter.getAttribute(propertyName), Type.getType(parameter.getAttribute(propertyType)),
				parameter.getAttribute(propertyExeccommand), Boolean.parseBoolean(parameter.getAttribute(propertyRequired)), commands, parameters);
		
	}
	
	public ArrayList<IParameter> getParameters();
	public ArrayList<ICommand> getCommands();
	public Element toXMLELement(Document document);
}
