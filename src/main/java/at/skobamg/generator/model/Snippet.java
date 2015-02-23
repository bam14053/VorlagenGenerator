/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;

/**
 *
 */
public class Snippet implements ISnippet {
	private String name;
	private ArrayList<ISection> sections;
	
	public Snippet(String name, ArrayList<ISection> sections) {
		super();
		this.name = name;
		this.sections = sections;
	}

	@Override
	public ArrayList<ISection> getSections() {
		return sections;
	}	
}
