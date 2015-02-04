package at.skobamg.generator.service;

import java.io.File;

public class Verzeichnisse {
	public static String rootVerzeichnis = System.getProperty("user.dir");
	public static String vorlagenVerzeichnis = rootVerzeichnis+"/vorlagen/";
	public static String switchtypDatei = rootVerzeichnis+"/switchtyp.ser";
	
	public static boolean verzeichnisseErstellen(){
		if(!new File(vorlagenVerzeichnis).exists())
			return new File(vorlagenVerzeichnis).mkdir();
		return true;
	}
	
	public static boolean checkTypDateiExists(){
		return new File(switchtypDatei).exists();
	}
}
