/**
 * 
 */
package at.skobamg.generator.service;
import java.util.HashMap;
import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.InvalidTypeException;

/**
 *
 */
public interface ISnippetTemplateService {
	HashMap<String, ISnippet> snippetsLaden() throws InvalidTypeException;	
}
