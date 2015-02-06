package at.skobamg.generator.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Switchtyp implements ISwitchtyp{
	ArrayList<String> switchnamen = new ArrayList<String>();

	public ArrayList<String> getSwitchnamen() {
		return switchnamen;
	}

	@SuppressWarnings("unchecked")
	public boolean laden() {
		if(!Verzeichnisse.checkTypDateiExists())
			return false;
		try{
			FileInputStream fis = new FileInputStream(Verzeichnisse.switchtypDatei);
			ObjectInputStream ois = new ObjectInputStream(fis);
	        switchnamen = (ArrayList<String>) ois.readObject();
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
			oos.writeObject(switchnamen);
			oos.close();
			fos.close();
			return true;
		}catch(IOException ioe){
		}
		return false;
	}

	@Override
	public boolean switchHinzufügen(String switchname) {
		if(switchnamen.contains(switchname))
			return false;
		else
			return switchnamen.add(switchname);
	}

}
