package at.skobamg.generator.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import at.skobamg.generator.model.Verzeichnisse;

public class Switchtyp implements ISwitchtyp{
	ArrayList<String> switchnamen = new ArrayList<String>();

	@Override
	public ArrayList<String> getSwitchnamen() {
		return switchnamen;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean laden() {
		if(!Verzeichnisse.checkSwitchtypDateiExists())
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

	@Override
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
	public void switchHinzufügen(String switchname) {
		if(!switchnamen.contains(switchname))
			switchnamen.add(switchname);
	}

}
