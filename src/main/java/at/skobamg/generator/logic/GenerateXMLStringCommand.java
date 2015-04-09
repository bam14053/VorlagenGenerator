package at.skobamg.generator.logic;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import at.skobamg.generator.model.ICommand;
import at.skobamg.generator.model.IInterface;
import at.skobamg.generator.model.IParameter;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.ITemplate;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GenerateXMLStringCommand extends Service<String> {
	private ITemplate template;
	Document doc;
	
	public GenerateXMLStringCommand(ITemplate template){
		this.template = template;
	}
	
	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws IOException, ParserConfigurationException{
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				
				doc = docBuilder.newDocument();
				
				//Setting templates properties
				Element rootElement = doc.createElement(ITemplate.name);				
				rootElement.setAttribute(ITemplate.propertySwitchname, template.getSwitchName());
				rootElement.setAttribute(ITemplate.propertySwitchversion, template.getSwitchVersion());
				
				for(IInterface interf : template.getInterfaces()){
					Element interfElement = doc.createElement(IInterface.name);
					interfElement.setAttribute(IInterface.propertyPortBezeichnunglang, interf.getPortBezeichnunglang());
					interfElement.setAttribute(IInterface.propertyPortBezeichnungkurz, interf.getPortBezeichnungkurz());
					if(!interf.getPortRange().equals("-"))
						interfElement.setAttribute(IInterface.propertyPortRange, interf.getPortRange());
					rootElement.appendChild(interfElement);
				}
				
				//Moving on to Snippets
				for(ISnippet snippet : template.getSnippets()){
					Element snippetElement = doc.createElement(ISnippet.name);
					snippetElement.setAttribute(ISnippet.propertyName, snippet.getName());
					if(snippet.isBindInterface())
						snippetElement.setAttribute(ISnippet.propertyBindInterface,  ""+snippet.isBindInterface());
					for(ISection section : snippet.getSections()){
						Element sectionElement = doc.createElement(ISection.name);
						sectionElement.setAttribute(ISection.propertyName, section.getName());						
						for(ICommand command : section.getCommands())
							sectionElement.appendChild(parseCommand(command));	
						snippetElement.appendChild(sectionElement);
					}
					rootElement.appendChild(snippetElement);			
				}
				doc.appendChild(rootElement);				
				OutputFormat format = new OutputFormat(doc);
				format.setIndenting(true);
				
				StringWriter stringOut = new StringWriter();				
				XMLSerializer xml = new XMLSerializer(stringOut, format);
				xml.serialize(doc);				
				
				return stringOut.toString();		
			}
		};
	}
	
	private Element parseCommand(ICommand command){
		Element comElement = doc.createElement(ICommand.name);
		comElement.setAttribute(ICommand.propertyName, command.getName());
		if(command.getType() != null)
			comElement.setAttribute(ICommand.propertyType, command.getType().toString());
		if(command.getExeccommand() != null && !command.getExeccommand().isEmpty())
			comElement.setAttribute(ICommand.propertyExeccommand, command.getExeccommand());
		for(IParameter parameter : command.getParameters())
			comElement.appendChild(parseParameter(parameter));
		for(ICommand com : command.getCommands())
			comElement.appendChild(parseCommand(com));		
		return comElement;
		
	}
	
	private Element parseParameter(IParameter parameter){
		Element comElement = doc.createElement(IParameter.name);
		comElement.setAttribute(IParameter.propertyName, parameter.getName());
		if(parameter.getType() != null)
			comElement.setAttribute(IParameter.propertyType, parameter.getType().toString());
		if(parameter.getExeccommand() != null && !parameter.getExeccommand().isEmpty())
			comElement.setAttribute(IParameter.propertyExeccommand, parameter.getExeccommand());
		if(parameter.isRequired())
			comElement.setAttribute(IParameter.propertyRequired, "true");
		for(ICommand command : parameter.getCommands())
			comElement.appendChild(parseCommand(command));
		for(IParameter param : parameter.getParameters())
			comElement.appendChild(parseParameter(param));
		return comElement;
	}

}
