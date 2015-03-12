/**
 * 
 */
package at.skobamg.generator.mediator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import at.skobamg.generator.model.ISnippet;
import at.skobamg.generator.model.IViewElement;
import at.skobamg.generator.model.Type;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

/**
 * Mediatorinterface gibt an welche Methoden Klassen implementieren müssen, die diesen Interface benützen
 *
 */
public interface IEventMediator {
	/**
	 * Navigieren zum Hauptfenster
	 */
	void zumHauptfenster();
	void neuenTemplateErstellen(String switchname, String iosversion);
	void zumNeuenTemplateFenster();
	void zeigeEinschränkungsfenster();
	void zumBenutzerhandbuch() throws IOException;
	void setStage(Stage stage);
	/**
	 * Display a new window displaying an error message
	 * @param error
	 */
	void nachrichtAnzeigen(String nachricht);
	void login(String username, String passwort);
	ArrayList<String> getSwitchNamen();
	void exit();
	void dateiöffnen();
	void zumBasisGenerierungsfenster();
	HashMap<String, ISnippet> getSnippets();
	ISnippet getSnippet(String name);
	void xmlGenerieren(ArrayList<CheckBoxTreeItem<String>> checkedItems);
	void setInterfaceDefinition(String[][] inputText, boolean portRange);
	void applyChange(TreeView<IViewElement> xmlTree);
	void deleteElement(TreeItem<IViewElement> selectedElement);
	void addSnippet(String name);
	void addSection(String name, TreeItem<IViewElement> snippet);
	void addGeneratedSection(String snippetName, String sectionName, String parentSnippet);
	void addCommand(String name, String execcommand, Type type, TreeItem<IViewElement> parent);
	void addInterface(String portbezeichnunglang, String portbezeichnungkurz, String portRange);
	void addParameter(String name, String execcommand, Type type, boolean required, TreeItem<IViewElement> parent);
	void zurueckZumInterfacedefinitionsfenster();
	void dateiSpeichernUnter(String xmlString);
	void dateiSpeichern(String xmlString);
	void elementEinfuegen(TreeItem<IViewElement> selectedElementForCopy, TreeItem<IViewElement> parent);
	String[] getGeneratorSnippets();
	String[] getGeneratorSections();
	void addGeneratedSnippet(String snippetName);	
}
