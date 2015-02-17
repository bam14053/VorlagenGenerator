/**
 * 
 */
package at.skobamg.generator.view;

import java.util.ArrayList;

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
	private TextField portrange1;
	@FXML
	private TextField portbezeichnunglang1;
	@FXML
	private TextField portbezeichnungkurz1;
	@FXML
	private Button zuruck;
	@FXML
	private Button weiter;
	@FXML
	private Label portrangeTitle;
	private boolean portRange;	
	private ArrayList<String> ports = new ArrayList<String>();
	private int pos;
	
	public void zeigeFenster(boolean portRange) {
		pos = GridPane.getRowIndex(portbezeichnunglang1)+1;
		if(portRange)
		{
			title.setText("Geben sie die Arten und Ranges für Beschränkung der Interfaces an!!");
			portrange1.setDisable(false);
			portrangeTitle.setDisable(false);
		}
		else {
			title.setText("Geben Sie die Arten der Interfaces an!!");
			portrange1.setDisable(true);
			portrangeTitle.setDisable(true);
		}
		
		this.portRange = portRange;
	}
	
	public void neueBezeichnung(ActionEvent actionEvent) {
		view.getChildren().remove(weiter);
		view.getChildren().remove(zuruck);
		
		((GridPane)view).add(weiter,GridPane.getColumnIndex(weiter), GridPane.getRowIndex(weiter)+1);
		((GridPane)view).add(zuruck,GridPane.getColumnIndex(zuruck), GridPane.getRowIndex(zuruck)+1);
		if(portRange) {
			TextField portrange = new TextField();
			portrange.setPromptText("1-24 ... 0/1 - 0/24");
			((GridPane)view).add(portrange, GridPane.getColumnIndex(portrange1), pos++);			
		}
		((Node)actionEvent.getSource()).getScene().getWindow().sizeToScene();
	}
}
