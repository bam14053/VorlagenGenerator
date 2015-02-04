/**
 * 
 */
package at.skobamg.generator.model;
/**
 *
 */
public class Switchtyp implements ISwitchtyp{
	private String switchName;
	private String iOSVersion;
	private Berechtigung berechtigung;	
	
	public Switchtyp(String switchName, String iOSVersion,
			Berechtigung berechtigung) {
		super();
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
}
