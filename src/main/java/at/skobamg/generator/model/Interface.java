package at.skobamg.generator.model;
public class Interface implements IInterface {	
	private String portBezeichnunglang;
	private String portBezeichnungkurz;
	private String portRange = "-";
		
	public Interface(String portBezeichnunglang, String portBezeichnungkurz,
			String first, String last) throws InvalidPortRangeException {
		super();
		this.portBezeichnunglang = portBezeichnunglang;
		this.portBezeichnungkurz = portBezeichnungkurz;
		setPortRange(first.trim(), last.trim());
	}
	
	public void setPortRange(String first, String last) throws InvalidPortRangeException{
		try{				
			if(first.contains("/") || last.contains("/"))		
				if(Integer.parseInt(first.split("/")[2]) > Integer.parseInt(last.split("/")[2]))
						throw new InvalidPortRangeException("Die angegebene Portrange stimmt nicht: Endport muss größer sein als Anfangsport");			
			else if(first.contains("/"))
				if(Integer.parseInt(first.split("/")[2]) > Integer.parseInt(last))
					throw new InvalidPortRangeException("Die angegebene Portrange stimmt nicht: Endport muss größer sein als Anfangsport");
			else 
				if(Integer.parseInt(first) > Integer.parseInt(last))
					throw new InvalidPortRangeException("Die angegebene Portrange stimmt nicht: Endport muss größer sein als Anfangsport");
			this.portRange = first+"-"+last;
		}catch(NumberFormatException nfe){
			throw new InvalidPortRangeException("Bitte geben sie eine valide Portrange an, wie z.B.: 1-24, 0/1 - 0/24, 0/1 - 24");
		}
	}

	public Interface(String portBezeichnunglang, String portBezeichnungkurz) {
		super();
		this.portBezeichnunglang = portBezeichnunglang;
		this.portBezeichnungkurz = portBezeichnungkurz;		
	}
	
	public String getPortBezeichnunglang() {
		return portBezeichnunglang;
	}

	public void setPortBezeichnunglang(String portBezeichnunglang) {
		this.portBezeichnunglang = portBezeichnunglang;
	}

	public String getPortBezeichnungkurz() {
		return portBezeichnungkurz;
	}

	public void setPortBezeichnungkurz(String portBezeichnungkurz) {
		this.portBezeichnungkurz = portBezeichnungkurz;
	}

	public String getPortRange() {
		return portRange;
	}
	
	public class InvalidPortRangeException extends Exception{
		public InvalidPortRangeException(String string) {
			super(string);
		}

		private static final long serialVersionUID = 6869836075010587603L;
	}

	@Override
	public ViewTyp getViewTyp() {
		return ViewTyp.IInterface;
	}
	
	@Override
	public String toString(){
		return IInterface.name+": "+portBezeichnunglang+": "+portRange;
	}
}

