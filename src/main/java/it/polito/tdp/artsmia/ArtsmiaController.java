package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.db.Adiacenza;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    private 
    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	if(this.model.adiacenze()==null) {
    		this.txtResult.appendText("Devi prima creare un grafo!");
    		return;
    	}else {
    		List<Adiacenza> a= this.model.adiacenze();
    		Collections.sort(a);
    		for(Adiacenza c: a) {
    			this.txtResult.appendText(c.getA1().getNome()+" - "+c.getA2().getNome()+" peso: "+c.getPeso()+"\n");
    		}
    	}
    	txtResult.appendText("Calcola artisti connessi");
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso");
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String role= this.boxRuolo.getValue();
    	this.model.creaGrafo(role);
    	txtResult.appendText("Crea grafo\n");
    	this.txtResult.appendText("#vertici: "+this.model.vertici()+"\n#archi: "+this.model.archi());
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.boxRuolo.getItems().addAll(this.model.listRole());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
