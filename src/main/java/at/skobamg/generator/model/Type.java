package at.skobamg.generator.model;

public enum Type {
	String, Integer, Choice, Multi, Delim, Bool ;
	
	public static Type getType(String type) throws InvalidTypeException{
		switch(type){
		case "string":
		case "String": return String; 
		case "int":
		case "Integer":
		case "integer": return Integer;
		case "choice":
		case "Choice": return Choice;
		case "multiple":
		case "Multiple":
		case "multi":
		case "Multi": return Multi;
		case "delim":
		case "Delim": return Delim;
		case "bool":
		case "Bool": return Bool;
		case "": return null;
		default: throw new InvalidTypeException("Invalid type given: "+type);
		}
	}
}
