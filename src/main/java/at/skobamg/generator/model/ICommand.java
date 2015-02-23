package at.skobamg.generator.model;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public interface ICommand {		
	public static String name="command";
	public static String propertyName = "name";
	public static String propertyType = "type";
	public static String propertyExeccommand = "execcommand";	

	static ICommand getCommand(Element command) throws InvalidTypeException {
		ArrayList<ICommand> commands = new ArrayList<>();
		ArrayList<IParameter> parameters = new ArrayList<>();
		NodeList commandList = command.getElementsByTagName(ICommand.name);
		NodeList parameterList = command.getElementsByTagName(IParameter.name);
		
		for(int i = 0; i < commandList.getLength(); i++)
			if(commandList.item(i).getParentNode().equals(command))
				commands.add(getCommand((Element)commandList.item(i)));
		for(int i = 0; i < parameterList.getLength(); i++)
			if(parameterList.item(i).getParentNode().equals(command))
				parameters.add(IParameter.getParameter((Element)parameterList.item(i)));		
		return new Command(command.getAttribute(propertyName), Type.getType(command.getAttribute(propertyType)), 
				command.getAttribute(propertyExeccommand), commands, parameters);
		
	}
	
	public ArrayList<IParameter> getParameters();
	public ArrayList<ICommand> getCommands();
	public Element toXMLELement(Document document);
}
