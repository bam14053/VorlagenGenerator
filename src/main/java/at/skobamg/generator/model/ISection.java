package at.skobamg.generator.model;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface ISection {
	//Property for each field	
	public static String name = "section";
	public static String propertyName = "name";
	
	
	static ISection getSection(Element section) throws InvalidTypeException {
		ArrayList<ICommand> commands = new ArrayList<>();
		NodeList commandList = section.getElementsByTagName(ICommand.name);		
		
		for(int i = 0; i < commandList.getLength(); i++)
			if(commandList.item(i).getParentNode().equals(section))
				commands.add(ICommand.getCommand((Element)commandList.item(i)));
		
		return new Section(section.getAttribute(propertyName), commands);
		
	}
	
	public String getName();	
	public ArrayList<ICommand> getCommands();
	public void addCommand(ICommand command);
}
