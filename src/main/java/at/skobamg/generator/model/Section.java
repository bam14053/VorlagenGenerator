/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;

import org.w3c.dom.Element;

/**
 *
 */
public class Section implements ISection {
	private String name;
	private ArrayList<ICommand> commands;
	
	public Section(String name, ArrayList<ICommand> commands) {
		super();
		this.name = name;
		this.commands = commands;
	}

	@Override
	public String getSectionName() {
		return name;
	}	
}
