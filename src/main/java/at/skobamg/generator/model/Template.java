/**
 * 
 */
package at.skobamg.generator.model;
/**
 *
 */
public class Template{
	private String switchName;
	private String iOSVersion;
	private String xmlString;
	
	public Template(String switchName, String iOSVersion) {
		this.switchName = switchName;
		this.iOSVersion = iOSVersion;
	}

	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}

	public String getIOSVersion() {
		return iOSVersion;
	}

	public void setIOSVersion(String iOSVersion) {
		this.iOSVersion = iOSVersion;
	}
	/**
	 * @return the xmlString
	 */
	public String getXmlString() {
		return xmlString;
	}

	/**
	 * @param xmlString the xmlString to set
	 */
	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}
}
