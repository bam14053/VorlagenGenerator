package at.skobamg.generator.model;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public interface IParameter extends IViewElement{
	public static String name="parameter";
	public static String propertyName = "name";
	public static String propertyType = "type";
	public static String propertyExeccommand = "execcommand";	
	public static String propertyRequired = "required";	
	public void setName(String name);
	public void setType(Type type);
	public void setExeccommand(String execcommand);
	public void setRequired(boolean required);
	public void addParameter(IParameter parameter);
	public void addCommand(ICommand command);
	public ArrayList<IParameter> getParameters();
	public ArrayList<ICommand> getCommands();
	public String getName();
	public Type getType();
	public String getExeccommand();
	public boolean isRequired();
	public float getID();
	public boolean removeCommand(ICommand command);
	public boolean removeParameter(IParameter parameter);
}