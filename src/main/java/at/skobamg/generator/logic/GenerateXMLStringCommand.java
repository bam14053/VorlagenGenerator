package at.skobamg.generator.logic;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import org.hibernate.event.internal.OnUpdateVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

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
			protected String call() throws IOException, ParserConfigurationException{
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
				OutputFormat format = new OutputFormat(doc);
				format.setIndenting(true);
				
				if(toFile){
					return "Saved";
				}
				else{
					StringWriter stringOut = new StringWriter();				
					XMLSerializer xml = new XMLSerializer(stringOut, format);
					xml.serialize(doc);				
					System.out.println(stringOut.toString());
					
					return stringOut.toString();	
				}
						
			}
			
		};
	}

}
