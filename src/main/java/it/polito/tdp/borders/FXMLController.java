/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryAndNumber;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<Country> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	String annoS = txtAnno.getText();
		try {
			int anno = Integer.parseInt(annoS);

			model.creaGrafo(anno);
			boxNazione.getItems().addAll(model.getVertex());
			
			List<CountryAndNumber> list = model.getCountryAndNumber();

			if (list.size() == 0) {
				txtResult.appendText("Non ci sono stati corrispondenti\n");
			} else {
				txtResult.appendText("Stati nell'anno "+anno+"\n");
				for (CountryAndNumber c : list) {
					txtResult.appendText(String.format("%s %d\n",
							c.getCountry().getStateName(), c.getNumber()));
				}
			}

		} catch (NumberFormatException e) {
			txtResult.appendText("Errore di formattazione dell'anno\n");
			return;
		}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	if (boxNazione.getItems().isEmpty()) {
    		txtResult.setText("Prima crea il grafo!");
    		return;
    	}
    	if (boxNazione.getValue() == null) {
    		txtResult.setText("Devi scegliere un opzione");
    		return;
    	}
    	
    	model.simula(boxNazione.getValue());
    	txtResult.setText("Impiegati "+model.getTempo()+" passi\n");
    	Map <Country, Integer> popolazoni = model.getPopStati();
    	
    	for (Country c : popolazoni.keySet()) {
    		txtResult.appendText(c+": "+popolazoni.get(c)+"\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
