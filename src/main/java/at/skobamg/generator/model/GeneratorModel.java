/**
 * 
 */
package at.skobamg.generator.model;
import java.util.HashMap;

/**
 *
 */
public class GeneratorModel implements IGeneratorModel{
	HashMap<String, ISnippet> snippets = new HashMap<>();
	
	@Override
	public boolean addSnippet(String name, ISnippet snippet) {
		snippets.put(name, snippet);
		return snippets.containsKey(name);
	}
	
	@Override
	public boolean deleteSnippet(String name) {
		snippets.remove(name);
		return snippets.containsKey(name);
	}
	
	@Override
	public void addSnippets(HashMap<String, ISnippet> snippets) {
		this.snippets.putAll(snippets);
	}

	@Override
	public ISnippet getSnippet(String name) {
		return snippets.get(name);
	}

	@Override
	public HashMap<String, ISnippet> getAllSnippets() {
		return snippets;
	}

	
}
