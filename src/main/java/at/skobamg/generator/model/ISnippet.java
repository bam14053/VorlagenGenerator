package at.skobamg.generator.model;

import java.util.ArrayList;

public interface ISnippet {
	public static String name="snippet";
	public static String propertyName = "name";
	
	public ArrayList<ISection> getSections();
}
