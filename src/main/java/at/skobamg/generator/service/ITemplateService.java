package at.skobamg.generator.service;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import at.skobamg.generator.model.ITemplate;
import at.skobamg.generator.model.InvalidTypeException;

public interface ITemplateService {
	public ITemplate openTemplate(File file) throws ParserConfigurationException, InvalidTypeException, SAXException, IOException;
	public boolean saveTemplate(File file, String xmlString) throws IOException;
}
