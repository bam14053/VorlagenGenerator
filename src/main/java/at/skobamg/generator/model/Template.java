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
	private ArrayList<IInterface> interfaces;

	public Template(String switchName, String iOSVersion,
			ArrayList<ISnippet> snippets, ArrayList<IInterface> interfaces) {
		super();
		this.switchName = switchName;
		this.iOSVersion = iOSVersion;
		this.snippets = snippets;
		this.interfaces = interfaces;
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

	@Override
	public void setSnippets(ArrayList<ISnippet> snippets) {
		this.snippets = snippets;
	}

	@Override
	public void setInterfaces(ArrayList<IInterface> interfaces) {
		this.interfaces = interfaces;
	}

	@Override
	public ArrayList<IInterface> getInterfaces() {
		return interfaces;
	}

	@Override
	public ViewTyp getViewTyp() {
		return ViewTyp.ITemplate;
	}
	
	@Override
	public String toString() {
		return switchName+":"+iOSVersion;
	}
}
