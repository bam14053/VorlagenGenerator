/**
 * 
 */
package at.skobamg.generator.view;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.mediator.IEventMediator;
import sun.awt.image.GifImageDecoder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 */
public class InterfacedefinitionsController extends ScreensAbstract{
	@FXML
	private Label title;
	@FXML
	private Button addButton;
	@FXML
	private TextField portrange;
	@FXML
	private TextField portbezeichnunglang;
	@FXML
	private TextField portbezeichnungkurz;
	@FXML
	private Button zurueck;
	@FXML
	private Button weiter;
	@FXML
	private Label portrangeTitle;
	@Autowired
	private IEventMediator mediator;
	private ArrayList<TextField> portRanges = new ArrayList<>();
	private ArrayList<TextField> portbezeichnungenlang = new ArrayList<>();
	private ArrayList<TextField> portbezeichnungenkurz = new ArrayList<>();
	
	private boolean portRange;	
	private ArrayList<String> ports = new ArrayList<String>();
	private int pos;
	
	public void zeigeFenster(boolean portRange) {
		pos = GridPane.getRowIndex(portbezeichnunglang);
		if(portRange)
		{
			title.setText("Geben sie die Arten und Ranges für Beschränkung der Interfaces an!!");
			portrange.setDisable(false);
			portrangeTitle.setDisable(false);
		}
		else {
			title.setText("Geben Sie die Arten der Interfaces an!!");
			portrange.setDisable(true);
			portrangeTitle.setDisable(true);
		}
		
		this.portRange = portRange;
	}
	
	public void zurueck(ActionEvent actionEvent){
		((Node)actionEvent.getSource()).getScene().getWindow().hide();
		mediator.zumNeuenTemplateFenster();
	}
	
	public void weiter(){
		
	}
	
	public void neueBezeichnung(ActionEvent actionEvent) {
		GridPane.setRowIndex(weiter, GridPane.getRowIndex(weiter)+1);
		GridPane.setRowIndex(zurueck, GridPane.getRowIndex(zurueck)+1);
		++pos;
		if(portRange) {
			TextField range = new TextField();
			range.setPromptText("1-24 ... 0/1 - 0/24");
			portRanges.add(range);
			((GridPane)view).add(range, GridPane.getColumnIndex(portrange), pos);			
		}
		//Einfügen eines neuen Textfield für Portbezeichnung lang
		TextField portbezlang = new TextField();
		portbezlang.setPromptText("FastEthernet ... GigabitEthernet");
		portbezeichnungenlang.add(portbezlang);
		((GridPane)view).add(portbezlang, GridPane.getColumnIndex(portbezeichnunglang), pos);
		//Einfügen eines neuen Textfield für Portbezeichnung kurz
		TextField portbezkurz = new TextField();
		portbezkurz.setPromptText("fa ... gi");
		portbezeichnungenkurz.add(portbezkurz);
		((GridPane)view).add(portbezkurz, GridPane.getColumnIndex(portbezeichnungkurz), pos);
				
		((Node)actionEvent.getSource()).getScene().getWindow().sizeToScene();
	}
}
