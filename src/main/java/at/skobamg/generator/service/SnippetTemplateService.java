/**
 * 
 */
package at.skobamg.generator.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import at.skobamg.generator.model.Command;
import at.skobamg.generator.model.ICommand;
import at.skobamg.generator.model.IParameter;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.InvalidTypeException;
import at.skobamg.generator.model.Parameter;
import at.skobamg.generator.model.Section;
import at.skobamg.generator.model.Snippet;
import at.skobamg.generator.model.Type;
import at.skobamg.generator.model.Verzeichnisse;

/**
 *
 */
public class SnippetTemplateService implements ISnippetTemplateService {
	class XMLFileFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String name) {
			if(name.endsWith(".xml"))
				return true;
			return false;
		}		
	}
	
	public ArrayList<ISection> parseSnippet(Element snippet) throws InvalidTypeException {
		ArrayList<ISection> sections = new ArrayList<>();
		NodeList nodeList = snippet.getElementsByTagName(ISection.name);
		for(int i = 0; i < nodeList.getLength(); i++)
			sections.add(parseSection((Element)nodeList.item(i)));
		return sections;
	}
	
	public ISection parseSection(Element section) throws InvalidTypeException {
		ArrayList<ICommand> commands = new ArrayList<>();
		NodeList commandList = section.getElementsByTagName(ICommand.name);		
		
		for(int i = 0; i < commandList.getLength(); i++)
			if(commandList.item(i).getParentNode().equals(section))
				commands.add(parseCommand((Element)commandList.item(i)));
		
		return new Section(section.getAttribute(ISection.propertyName), commands);
		
	}
	
	public ICommand parseCommand(Element command) throws InvalidTypeException {
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
	
	public IParameter parseParameter(Element parameter) throws InvalidTypeException {
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

	@Override
	public HashMap<String, ISnippet> snippetsLaden() throws InvalidTypeException {
		HashMap<String, ISnippet> snippets = new HashMap<String, ISnippet>();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
			for(File file : new File(Verzeichnisse.snippetsVorlagen).listFiles(new XMLFileFilter())){							
				Element element = db.parse(file).getDocumentElement();
				if(element.getNodeName().equals(ISnippet.name))
					snippets.put(element.getAttribute(ISnippet.propertyName), new Snippet(element.getAttribute(ISnippet.propertyName), parseSnippet(element)));										
			}
			return snippets;
		}catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} return null;
	}
}
