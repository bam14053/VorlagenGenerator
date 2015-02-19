package at.skobamg.generator.model;

import java.io.File;

public class Verzeichnisse {
	public static String rootVerzeichnis = System.getProperty("user.dir");
	public static String vorlagenVerzeichnis = rootVerzeichnis+"/vorlagen/";
	public static String switchtypDatei = rootVerzeichnis+"/switchtyp.ser";
	public static String snippetsVorlagen = rootVerzeichnis+"/snippets/";
	public static String[] verzeichnisse = {
		vorlagenVerzeichnis, snippetsVorlagen
	};
	
	public static void verzeichnisseErstellen(){
		for(int i = 0; i < verzeichnisse.length; i++){
			if(!new File(verzeichnisse[i]).exists())
				new File(verzeichnisse[i]).mkdir();
		}
	}
	
	public static boolean checkSwitchtypDateiExists(){
		return new File(switchtypDatei).exists();
	}	
}
