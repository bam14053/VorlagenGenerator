/**
 * 
 */
package at.skobamg.generator.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

import org.springframework.beans.factory.annotation.Autowired;

import at.skobamg.generator.mediator.IEventMediator;

/**
 * @author abi
 *
 */
public class TemplateAuswahlController extends ScreensAbstract {
	@Autowired
	private IEventMediator mediator;
	@FXML
	private ComboBox<String> switchname;
	@FXML
	private ComboBox<String> iosversion;

	public void zurückzumHauptFenster(){
		mediator.zumHauptfenster();
	}

	@Override
	public Pane getView() {
		switchname.getItems().clear();
		switchname.setVisibleRowCount(4);
		for(String s : mediator.getSwitchNamen())
			switchname.getItems().add(s);		
		switchname.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				if(arg2 == null) return;
				iosversion.getItems().clear();		
				iosversion.getSelectionModel().select(null);
				for(String s : mediator.getIOSVersionen(arg2))
					iosversion.getItems().add(s);
				iosversion.setValue(null);
				iosversion.setPromptText("IOS Version aussuchen");				
			}
		});
		return super.getView();
	}
	
	
}
