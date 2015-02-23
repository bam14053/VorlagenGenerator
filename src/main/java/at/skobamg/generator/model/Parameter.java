/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;

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
}
