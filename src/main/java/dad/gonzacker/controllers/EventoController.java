package dad.gonzacker.controllers;

import dad.gonzacker.GonzackerApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class EventoController implements Initializable {

    @FXML
    private VBox root;

    public EventoController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventoView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void onNeutralAction(ActionEvent event) {
        GonzackerApp.getMapController().setDinero(GonzackerApp.getMapController().getDinero() + 10);
        GonzackerApp.setRoot(GonzackerApp.getMapController().getRoot());
    }

    @FXML
    void onNothingAction(ActionEvent event) {
        GonzackerApp.setRoot(GonzackerApp.getMapController().getRoot());
    }

    @FXML
    void onRiskAction(ActionEvent event) {

        Random random = new Random();

        GonzackerApp.getMapController().getDeck().agregarCarta(GonzackerApp.getMapController().getCartas().get(random.nextInt(GonzackerApp.getMapController().getCartas().size())));
        GonzackerApp.getMapController().setDinero(GonzackerApp.getMapController().getDinero() + 20);
        GonzackerApp.setRoot(GonzackerApp.getMapController().getRoot());
    }

    // getters and setters

    public VBox getRoot() {
        return root;
    }

}
