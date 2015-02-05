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
	private Berechtigung berechtigung;	
	private String xmlString;
	
	public Template(String switchName, String iOSVersion,
			Berechtigung berechtigung) {
		this.switchName = switchName;
		this.iOSVersion = iOSVersion;
		this.berechtigung = berechtigung;
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

	public Berechtigung getBerechtigung() {
		return berechtigung;
	}

	public void setBerechtigung(Berechtigung berechtigung) {
		this.berechtigung = berechtigung;
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
