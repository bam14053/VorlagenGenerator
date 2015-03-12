/**
 * 
 */
package at.skobamg.generator.view;

/**
 * @author 
 *
 */
public interface IScreens {
	//Public fields
	public static String commandPrompt = "Commands";
	public static String commandNamePrompt = "Name des Commands";
	public static String commandTypPrompt = "Typ des Commands";
	public static String parameterPrompt = "Parameter";
	public static String parameterNamePrompt = "Name des Parameter";
	public static String parameterTypPrompt = "Typ des Parameter";
	Object getView();
}
