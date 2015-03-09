package at.skobamg.generator.service;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import at.skobamg.generator.model.Command;
import at.skobamg.generator.model.ICommand;
import at.skobamg.generator.model.IInterface;
import at.skobamg.generator.model.IParameter;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.Interface;
import at.skobamg.generator.model.InvalidTypeException;
import at.skobamg.generator.model.Parameter;
import at.skobamg.generator.model.Section;
import at.skobamg.generator.model.Snippet;
import at.skobamg.generator.model.Type;

public class ElementParseService{
	protected static IInterface parseInterface(Element element) {
		if(element.hasAttribute(IInterface.propertyPortRange))
			return new Interface(element.getAttribute(IInterface.propertyPortBezeichnunglang), IInterface.propertyPortBezeichnungkurz, IInterface.propertyPortRange);
		else
			return new Interface(element.getAttribute(IInterface.propertyPortBezeichnunglang), element.getAttribute(IInterface.propertyPortBezeichnungkurz));
	}
	
	protected static ISection parseSection(Element section) throws InvalidTypeException {
		ArrayList<ICommand> commands = new ArrayList<>();
		NodeList commandList = section.getElementsByTagName(ICommand.name);		
		
		for(int i = 0; i < commandList.getLength(); i++)
			if(commandList.item(i).getParentNode().equals(section))
				commands.add(parseCommand((Element)commandList.item(i)));
		
		return new Section(section.getAttribute(ISection.propertyName), commands);		
	}
	
	protected static ICommand parseCommand(Element command) throws InvalidTypeException {
		ArrayList<ICommand> commands = new ArrayList<>();
		ArrayList<IParameter> parameters = new ArrayList<>();
		NodeList commandList = command.getElementsByTagName(ICommand.name);
		NodeList parameterList = command.getElementsByTagName(IParameter.name);
		
		for(int i = 0; i < commandList.getLength(); i++)
			if(commandList.item(i).getParentNode().equals(command))
				commands.add(parseCommand((Element)commandList.item(i)));
		for(int i = 0; i < parameterList.getLength(); i++)
			if(parameterList.item(i).getParentNode().equals(command))
				parameters.add(parseParameter((Element)parameterList.item(i)));		
		return new Command(command.getAttribute(ICommand.propertyName), Type.getType(command.getAttribute(ICommand.propertyType)), 
				command.getAttribute(ICommand.propertyExeccommand), commands, parameters);		
	}
	
	protected static IParameter parseParameter(Element parameter) throws InvalidTypeException {
		ArrayList<ICommand> commands = new ArrayList<>();
		ArrayList<IParameter> parameters = new ArrayList<>();
		NodeList commandList = parameter.getElementsByTagName(ICommand.name);
		NodeList parameterList = parameter.getElementsByTagName(IParameter.name);
		
		for(int i = 0; i < commandList.getLength(); i++)
			if(commandList.item(i).getParentNode().equals(parameter))
				commands.add(parseCommand((Element)commandList.item(i)));
		for(int i = 0; i < parameterList.getLength(); i++)
			if(parameterList.item(i).getParentNode().equals(parameter))
				parameters.add(parseParameter((Element)parameterList.item(i)));		

		return new Parameter(parameter.getAttribute(IParameter.propertyName), parameter.getAttribute(IParameter.propertyExeccommand),
				Type.getType(parameter.getAttribute(IParameter.propertyType)), Boolean.parseBoolean(parameter.getAttribute(IParameter.propertyRequired)), commands, parameters);		
	}
	
	protected static ISnippet parseSnippet(Element snippetElement) throws InvalidTypeException {
		ISnippet snippet = new Snippet(snippetElement.getAttribute(ISnippet.propertyName));		
		NodeList nodeList = snippetElement.getElementsByTagName(ISection.name);
		
		for(int i = 0; i < nodeList.getLength(); i++)
			snippet.addSection(parseSection((Element)nodeList.item(i)));
		return snippet;
	}
}
