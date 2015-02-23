/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	
	public Parameter(String name, Type type, String execcommand,
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
		Element comElement = document.createElement(IParameter.name);
		comElement.setAttribute(IParameter.propertyName, name);
		if(type != null)
			comElement.setAttribute(IParameter.propertyType, type.toString());
		if(execcommand != null || !execcommand.isEmpty())
			comElement.setAttribute(IParameter.propertyExeccommand, execcommand);
		for(ICommand command : commands)
			comElement.appendChild(command.toXMLELement(document));
		for(IParameter parameter : parameters)
			comElement.appendChild(parameter.toXMLELement(document));
		return comElement;
	}
}
