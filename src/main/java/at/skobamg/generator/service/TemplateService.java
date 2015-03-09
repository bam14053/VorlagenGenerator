/**
 * 
 */
package at.skobamg.generator.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import at.skobamg.generator.model.IInterface;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.ITemplate;
import at.skobamg.generator.model.InvalidTypeException;
import at.skobamg.generator.model.Template;

/**
 * @author abideen
 *
 */
public class TemplateService implements ITemplateService{

	@Override
	public ITemplate openTemplate(File file) throws ParserConfigurationException, InvalidTypeException, SAXException, IOException {		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
		Element element = db.parse(file).getDocumentElement();
		if(!element.getNodeName().equals(ITemplate.name)) return null;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Parsing through interface
		NodeList list = element.getElementsByTagName(IInterface.name);
		ArrayList<IInterface> interfaceList = new ArrayList<IInterface>();		
		for(int i = 0; i < list.getLength(); i++)
			interfaceList.add(ElementParseService.parseInterface((Element) list.item(i)));				
		//Parsing through snippets
		list = element.getElementsByTagName(ISnippet.name);
		ArrayList<ISnippet> snippetList = new ArrayList<ISnippet>();
		for(int i = 0; i < list.getLength(); i++)
			snippetList.add(ElementParseService.parseSnippet((Element) list.item(i)));		
		return new Template(element.getAttribute(ITemplate.propertySwitchname), element.getAttribute(ITemplate.propertySwitchversion), snippetList, interfaceList);	
	}
			

	@Override
	public boolean saveTemplate(File file, String xmlString){		
		try {
			if(!file.exists()) file.createNewFile();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));		
			bufferedWriter.write(xmlString);
			bufferedWriter.flush();
			bufferedWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
