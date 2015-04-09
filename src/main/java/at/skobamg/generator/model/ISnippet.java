package at.skobamg.generator.model;

import java.util.ArrayList;

public interface ISnippet extends IViewElement{
	public static String name="snippet";
	public static String propertyName = "name";
	public static String propertyBindInterface = "bind-interface";
	
	public ArrayList<ISection> getSections();
	public ISection getSection(String sectionName);
	public void addSection(ISection section);
	public void deleteSection(ISection section);
	public void setName(String name);
	public String getName();
	public void setBindInterface(boolean binding);
	public boolean isBindInterface();
	@Override
	public String toString();
}
