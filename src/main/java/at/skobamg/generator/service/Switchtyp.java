package at.skobamg.generator.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Switchtyp implements ISwitchtyp{
	HashMap<String, ArrayList<String>> switchtypen = new HashMap<String, ArrayList<String>>();

	public String[] getIOSVersionen(String switchnamen) {
		return switchtypen.get(switchnamen).toArray(new String[switchtypen.size()]);
	}

	public String[] getSwitchNamen() {
		return switchtypen.keySet().toArray(new String[switchtypen.size()]);
	}

	public HashMap<String, ArrayList<String>> getSwitchtypen() {
		return switchtypen;
	}

	@SuppressWarnings("unchecked")
	public boolean laden() {
		if(!Verzeichnisse.checkTypDateiExists())
			return false;
		try{
			FileInputStream fis = new FileInputStream(Verzeichnisse.switchtypDatei);
			ObjectInputStream ois = new ObjectInputStream(fis);
	        switchtypen = (HashMap<String, ArrayList<String>>) ois.readObject();
	        ois.close();
	        fis.close();
	        return true;
		}catch(IOException | ClassNotFoundException ioe){
			
		}
		return false;
		
	}

	public boolean speichern() {
		try{
			FileOutputStream fos = new FileOutputStream(Verzeichnisse.switchtypDatei);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(switchtypen);
			oos.close();
			fos.close();
			return true;
		}catch(IOException ioe){
		}
		return false;
	}

	@Override
	public boolean neuenSwitchTyp(String switchname, String iOSVerison) {
		if(switchtypen.containsKey(switchname))
			return switchtypen.get(switchname).add(iOSVerison);
		else {
			ArrayList<String> iosVersionen = new ArrayList<String>();
			iosVersionen.add(iOSVerison);
			switchtypen.put(switchname, iosVersionen);
			return !switchtypen.isEmpty();
		}
	}

}
