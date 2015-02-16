package at.skobamg.generator.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface ISwitchtyp {
	/**
	 * Load all saved Switchtypes
	 */
	boolean speichern(); 
	boolean laden();
	void switchHinzufügen(String switchname);
	ArrayList<String> getSwitchnamen();
}
