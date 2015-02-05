package at.skobamg.generator.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface ISwitchtyp {
	/**
	 * Lädt alle gespeicherten Switchtypen
	 */
	boolean speichern();
	boolean laden();
	boolean neuenSwitchTyp(String switchname, String iOSVerison);
	String[] getIOSVersionen(String switchnamen);
	String[] getSwitchNamen();
	HashMap<String, ArrayList<String>> getSwitchtypen();
}
