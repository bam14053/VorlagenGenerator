package at.skobamg.generator.model;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public interface ICommand extends IViewElement{		
	public static String name="command";
	public static String propertyName = "name";
	public static String propertyType = "type";
	public static String propertyExeccommand = "execcommand";		
	public void setName(String name);
	public void setType(Type type);
	public void setExeccommand(String command);
	public void addParameter(IParameter parameter);
	public void addCommand(ICommand command);
	public ArrayList<IParameter> getParameters();
	public ArrayList<ICommand> getCommands();
	public String getName();
	public Type getType();
	public String getExeccommand();
	public ViewTyp getViewTyp();
	public float getID();
	public boolean removeCommand(ICommand command);
	public boolean removeParameter(IParameter parameter);
}
