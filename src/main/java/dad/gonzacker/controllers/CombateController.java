package dad.gonzacker.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CombateController implements Initializable {

    // model

    private IntegerProperty energia = new SimpleIntegerProperty();
    private IntegerProperty enegiaMaxima = new SimpleIntegerProperty();
    private IntegerProperty turno = new SimpleIntegerProperty();

    // view

    @FXML
    private GridPane enemyGridPane;

    @FXML
    private Label energyLabel;

    @FXML
    private BorderPane root;

    @FXML
    private Label turnLabel;

    public CombateController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/combateView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // bindings

        turnLabel.textProperty().bind(turno.asString());
        energyLabel.textProperty().bind(energia.asString().concat("/").concat(enegiaMaxima.asString()));

    }

    @FXML
    void onFinishTurnAction(ActionEvent event) {
        turno.set(turno.get() + 1);
        energia.set(enegiaMaxima.get());
    }

}
