package dad.gonzacker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TiendaController implements Initializable {

    // view

    @FXML
    private Button buyButton1;

    @FXML
    private Button buyButton2;

    @FXML
    private Button buyButton3;

    @FXML
    private Label moneyLabel1;

    @FXML
    private Label moneyLabel2;

    @FXML
    private Label moneyLabel3;

    @FXML
    private BorderPane root;

    public TiendaController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tiendaView.fxml"));
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
    void onBuyAction1(ActionEvent event) {

    }

    @FXML
    void onBuyAction2(ActionEvent event) {

    }

    @FXML
    void onBuyAction3(ActionEvent event) {

    }

    @FXML
    void onCloseAction(ActionEvent event) {

    }

}
