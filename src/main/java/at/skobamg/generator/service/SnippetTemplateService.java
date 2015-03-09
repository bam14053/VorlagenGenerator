/**
 * 
 */
package at.skobamg.generator.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.InvalidTypeException;
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

	@Override
	public HashMap<String, ISnippet> snippetsLaden() throws InvalidTypeException {
		HashMap<String, ISnippet> snippets = new HashMap<String, ISnippet>();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
			for(File file : new File(Verzeichnisse.snippetsVorlagen).listFiles(new XMLFileFilter())){							
				Element element = db.parse(file).getDocumentElement();
				if(element.getNodeName().equals(ISnippet.name))
					snippets.put(element.getAttribute(ISnippet.propertyName), ElementParseService.parseSnippet(element));								
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
