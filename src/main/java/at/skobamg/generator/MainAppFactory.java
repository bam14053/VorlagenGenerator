/**
 * 
 */
package at.skobamg.generator;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.skobamg.generator.mediator.EventMediator;
import at.skobamg.generator.mediator.IEventMediator;
import at.skobamg.generator.service.ISwitchtyp;
import at.skobamg.generator.service.Switchtyp;
import at.skobamg.generator.view.HauptfensterController;
import at.skobamg.generator.view.InterfacedefinitionsController;
import at.skobamg.generator.view.LoginfensterController;
import at.skobamg.generator.view.NeuesTemplateController;

/**
 *
 */
@Configuration
public class MainAppFactory {
	@Bean
	public LoginfensterController loginfensterController(){
		return (LoginfensterController)controllerLaden(Thread.currentThread().getContextClassLoader().getResource("Loginfenster.fxml")); 
	}
	
	@Bean
	public NeuesTemplateController neuesTemplateController(){
		return (NeuesTemplateController)controllerLaden(Thread.currentThread().getContextClassLoader().getResource("NeuesTemplatefenster.fxml"));
	}
	
	@Bean
	public HauptfensterController hauptfensterController() {
		return (HauptfensterController)controllerLaden(Thread.currentThread().getContextClassLoader().getResource("Hauptfenster.fxml"));
	}
	
	@Bean
	public InterfacedefinitionsController interfacedefinitionsController() {
		return (InterfacedefinitionsController)controllerLaden(Thread.currentThread().getContextClassLoader().getResource("Interfacedefinitionsfenster.fxml"));
	}
	
	@Bean
	public IEventMediator iEventMediator(){
		return new EventMediator();
	}
	
	@Bean
	public ISwitchtyp iSwitchtyp(){
		return new Switchtyp();
	}
	
	protected Object controllerLaden(URL url){
		FXMLLoader loader = new FXMLLoader(url);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loader.getController();
	}
}
