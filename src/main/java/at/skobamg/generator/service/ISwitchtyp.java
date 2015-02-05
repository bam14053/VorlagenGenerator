package at.skobamg.generator.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface ISwitchtyp {
	/**
	 * Load all saved Switchtypes
	 */
	boolean speichern(); 
	boolean laden();
	boolean neuenSwitchTyp(String switchname, String iOSVerison);
	String[] getIOSVersionen(String switchtyp);
	String[] getSwitchNamen();
	HashMap<String, ArrayList<String>> getSwitchtypen();
}
