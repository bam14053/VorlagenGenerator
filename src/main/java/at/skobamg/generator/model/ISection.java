package at.skobamg.generator.model;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface ISection extends IViewElement{
	//Property for each field	
	public static String name = "section";
	public static String propertyName = "name";
	
	public String getName();	
	public ArrayList<ICommand> getCommands();
	public void addCommand(ICommand command);
	public void setName(String name);
	public void removeCommand(ICommand command);
	public void removeParameter(IParameter value);
}
