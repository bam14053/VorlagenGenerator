/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

	@Override
	public Element toXMLELement(Document document) {
		Element comElement = document.createElement(ICommand.name);
		comElement.setAttribute(ICommand.propertyName, name);
		if(type != null)
			comElement.setAttribute(ICommand.propertyType, type.toString());
		if(execcommand != null || !execcommand.isEmpty())
			comElement.setAttribute(ICommand.propertyExeccommand, execcommand);
		for(IParameter parameter : parameters)
			comElement.appendChild(parameter.toXMLELement(document));
		for(ICommand command : commands)
			comElement.appendChild(command.toXMLELement(document));		
		return comElement;
	}
}
