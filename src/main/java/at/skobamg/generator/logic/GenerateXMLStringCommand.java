package at.skobamg.generator.logic;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import at.skobamg.generator.model.ICommand;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.ITemplate;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GenerateXMLStringCommand extends Service<String> {
	private ITemplate template;
	private boolean toFile;
	private String file;
	
	public final void setParameters(ITemplate template, boolean toFile){
		this.template = template;
		this.toFile = toFile;
	}
	
	public final void setFile(String file){
		this.file = file;
	}
	
	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws ParserConfigurationException, TransformerException{
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				
				Document doc = docBuilder.newDocument();
				
				//Setting templates properties
				Element rootElement = doc.createElement(ITemplate.name);				
				rootElement.setAttribute(ITemplate.propertySwitchname, template.getSwitchName());
				rootElement.setAttribute(ITemplate.propertySwitchversion, template.getSwitchVersion());
				
				//Moving on to Snippets
				for(ISnippet snippet : template.getSnippets()){
					Element snippetElement = doc.createElement(ISnippet.name);
					snippetElement.setAttribute(ISnippet.propertyName, snippet.getName());
					for(ISection section : snippet.getSections()){
						Element sectionElement = doc.createElement(ISection.name);
						sectionElement.setAttribute(ISection.propertyName, section.getName());						
						for(ICommand command : section.getCommands())
							sectionElement.appendChild(command.toXMLELement(doc));	
						snippetElement.appendChild(sectionElement);
					}
					rootElement.appendChild(snippetElement);			
				}
				doc.appendChild(rootElement);
				
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer(); 
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(System.out);
				
				
				transformer.transform(source, result);
				
				return doc.getTextContent();				
			}
			
		};
	}

}
