package dad.gonzacker.controllers;

import dad.gonzacker.GonzackerApp;
import dad.gonzacker.components.CartaPequeniaComponent;
import dad.gonzacker.models.Carta;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecompensasContoller implements Initializable {

    // model

    private IntegerProperty money = new SimpleIntegerProperty();

    // view

    @FXML
    private BorderPane cartaContainer;

    @FXML
    private Label moneyLabel;

    @FXML
    private VBox root;

    public RecompensasContoller(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/recompensaView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // binding
        moneyLabel.textProperty().bind(money.asString().concat(" Gonzacoins"));

    }

    public void establecerRecompensa(Carta carta){
        cartaContainer.setCenter(new CartaPequeniaComponent(carta));
    }

    @FXML
    void onContnueAction(ActionEvent event) {
        GonzackerApp.setRoot(GonzackerApp.getMapController().getRoot());
        GonzackerApp.getMapController().getDeck().agregarCarta(((CartaPequeniaComponent) cartaContainer.getCenter()).getCarta());
        GonzackerApp.getMapController().setDinero(GonzackerApp.getMapController().getDinero() + money.get());
    }

    // getters and setters

    public VBox getRoot() {
        return root;
    }

    public int getMoney() {
        return money.get();
    }

    public IntegerProperty moneyProperty() {
        return money;
    }

    public void setMoney(int money) {
        this.money.set(money);
    }
}
