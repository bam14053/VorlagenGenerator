package at.skobamg.generator.model;

import java.util.ArrayList;

import at.skobamg.generator.model.Interface.InvalidPortRangeException;
import javafx.scene.control.TreeItem;

public interface ITemplate extends IViewElement{
	public static String name="template";
	public static String propertySwitchversion = "switch-version";
	public static String propertySwitchname = "switch-name";
	
	public String getSwitchVersion();
	public String getSwitchName();
	public ArrayList<ISnippet> getSnippets();
	public ISnippet getSnippet(String name);
	public void addSnippet(String snippetName);
	public void setSnippets(ArrayList<ISnippet> snippets);
	public void setInterfaces(ArrayList<IInterface> interfaces);
	public ArrayList<IInterface> getInterfaces();
	public void deleteElement(TreeItem<IViewElement> element);
	public void addSection(String sectionName, ISnippet snippet);
	public void addInterface(String portbezeichnunglang,
			String portbezeichnungkurz, String portRange) throws InvalidPortRangeException;
	public void addCommand(String commandName, String execcommand, Type type,
			TreeItem<IViewElement> parent);
	public void addParameter(String parameterName, String execcommand, Type type,
			boolean required, TreeItem<IViewElement> parent);
}
