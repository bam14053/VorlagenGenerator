/**
 * 
 */
package at.skobamg.generator;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.skobamg.generator.view.HauptfensterController;
import at.skobamg.generator.view.SwitchtypController;
import at.skobamg.generator.view.TemplateAuswahlController;

/**
 * @author abi
 *
 */
@Configuration
public class MainAppFactory {
	@Bean
	public HauptfensterController mainController(){
		return (HauptfensterController)controllerLaden(Main.class.getResource("view/xml/Hauptfenster.fxml"));	
	}
	
	@Bean
	public SwitchtypController switchMenuView(){
		return (SwitchtypController)controllerLaden(Main.class.getResource("view/xml/Switchtypfenster.fxml"));
	}
	
	@Bean
	public TemplateAuswahlController templateAuswahlController(){
		return (TemplateAuswahlController)controllerLaden(Main.class.getResource("view/xml/TemplateAuswahlFenster.fxml"));
	}
	
	protected Object controllerLaden(URL url){
		FXMLLoader loader = new FXMLLoader(url);
		try {
			loader.load();
		} catch (IOException e) {
			System.out.println("Program konnte nicht geladen werden");
		}
		return loader.getController();
	}
}