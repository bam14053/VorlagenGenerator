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
	private ArrayList<ISection> sections = new ArrayList<>();
	
	public Snippet(String name){
		this.name = name;
	}
	
	public Snippet(String name, ArrayList<ISection> sections) {
		super();
		this.name = name;
		this.sections = sections;
	}

	@Override
	public ArrayList<ISection> getSections() {
		return sections;
	}
	
	@Override
	public String toString() {
		return ISnippet.name+": "+name;
	}

	@Override
	public boolean equals(Object obj) {
		if(((IViewElement)obj).getViewTyp().equals(ViewTyp.ISnippet))
			if(((Snippet)obj).name.equals(name))
				return true;
		return false;
	}

	@Override
	public ISection getSection(String sectionName) {
		if(sections.contains(new Section(sectionName, null)))			
			return sections.get(sections.indexOf(new Section(sectionName, null)));
		return null;
	}

	@Override
	public void addSection(ISection section) {
		sections.add(section);
	}

	@Override
	public void deleteSection(String sectionName) {
		sections.remove(new Section(sectionName, null));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ViewTyp getViewTyp() {
		return ViewTyp.ISnippet;
	}
}
