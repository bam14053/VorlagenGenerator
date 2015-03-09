/**
 * 
 */
package at.skobamg.generator.service;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Element;
import at.skobamg.generator.model.ICommand;
import at.skobamg.generator.model.IParameter;
import at.skobamg.generator.model.ISection;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.InvalidTypeException;

/**
 *
 */
public interface ISnippetTemplateService {
	HashMap<String, ISnippet> snippetsLaden() throws InvalidTypeException;	
}
