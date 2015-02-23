/**
 * 
 */
package at.skobamg.generator.model;

import java.util.ArrayList;

/**
 *
 */
public class Template implements ITemplate{
	private String switchName;
	private String iOSVersion;
	private ArrayList<ISnippet> snippets;
	
	public Template(String switchName, String iOSVersion,
			ArrayList<ISnippet> snippets) {
		super();
		this.switchName = switchName;
		this.iOSVersion = iOSVersion;
		this.snippets = snippets;
	}

	@Override
	public String getSwitchVersion() {
		return iOSVersion;
	}

	@Override
	public String getSwitchName() {
		return switchName;
	}

	@Override
	public ArrayList<ISnippet> getSnippets() {
		return snippets;
	}

	@Override
	public ISnippet getSnippet(String name) {
		if(snippets.contains(new Snippet(name, null)))
			return snippets.get(snippets.indexOf(new Snippet(name, null)));
		return null;
	}
}
