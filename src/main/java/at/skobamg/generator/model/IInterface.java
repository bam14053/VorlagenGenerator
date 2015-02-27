package at.skobamg.generator.model;

import at.skobamg.generator.model.Interface.InvalidPortRangeException;

public interface IInterface extends IViewElement{
	public static String name="interface";
	public static String propertyPortBezeichnunglang = "portBezeichnunglang";
	public static String propertyPortBezeichnungkurz = "portBezeichnungkurz";
	public static String propertyPortRange="portRange";
	
	public String getPortBezeichnunglang();
	public void setPortBezeichnunglang(String portBezeichnunglang);
	public String getPortBezeichnungkurz();
	public void setPortBezeichnungkurz(String portBezeichnungkurz);
	public String getPortRange();
	public void setPortRange(String first, String last) throws InvalidPortRangeException;
}
