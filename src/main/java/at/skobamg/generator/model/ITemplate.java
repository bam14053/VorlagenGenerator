package at.skobamg.generator.model;

import java.util.ArrayList;

public interface ITemplate {
	public static String name="template";
	public static String propertySwitchversion = "switch-version";
	public static String propertySwitchname = "switch-name";
	
	public String getSwitchVersion();
	public String getSwitchName();
	public ArrayList<ISnippet> getSnippets();
	public ISnippet getSnippet(String name);
}
