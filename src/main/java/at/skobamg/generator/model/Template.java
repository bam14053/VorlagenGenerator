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
}
