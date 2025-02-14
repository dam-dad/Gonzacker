package dad.gonzacker.controllers;

import dad.gonzacker.GonzackerApp;
import dad.gonzacker.components.CartaPequeniaComponent;
import dad.gonzacker.models.Carta;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TiendaController implements Initializable {

    // model

    private IntegerProperty money = new SimpleIntegerProperty();
    private IntegerProperty cost1 = new SimpleIntegerProperty();
    private IntegerProperty cost2 = new SimpleIntegerProperty();
    private IntegerProperty cost3 = new SimpleIntegerProperty();

    // view

    @FXML
    private Button buyButton1;

    @FXML
    private Button buyButton2;

    @FXML
    private Button buyButton3;

    @FXML
    private Label costLabel1;

    @FXML
    private Label costLabel2;

    @FXML
    private Label costLabel3;

    @FXML
    private Label moneyLabel;

    @FXML
    private BorderPane root;

    @FXML
    private GridPane shopGridPane;

    public TiendaController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tiendaView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCartas(Carta carta1 , Carta carta2, Carta carta3) {
        shopGridPane.add(new CartaPequeniaComponent(carta1), 0, 0);
        shopGridPane.add(new CartaPequeniaComponent(carta2), 1, 0);
        shopGridPane.add(new CartaPequeniaComponent(carta3), 2, 0);
    }

    public void setPrecios(int precio1, int precio2, int precio3) {
        cost1.set(precio1);
        cost2.set(precio2);
        cost3.set(precio3);
    }

    public void resetShop() {
        buyButton1.setDisable(false);
        buyButton2.setDisable(false);
        buyButton3.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // bindings

        moneyLabel.textProperty().bind(money.asString());

        costLabel1.textProperty().bind(cost1.asString());
        costLabel2.textProperty().bind(cost2.asString());
        costLabel3.textProperty().bind(cost3.asString());

        money.addListener((observable, oldValue, newValue) -> {
            if (!buyButton1.disableProperty().get()) buyButton1.setDisable(newValue.intValue() < cost1.get());
            if (!buyButton2.disableProperty().get()) buyButton2.setDisable(newValue.intValue() < cost2.get());
            if (!buyButton3.disableProperty().get()) buyButton3.setDisable(newValue.intValue() < cost3.get());
        });

    }

    @FXML
    void onBuyAction1(ActionEvent event) {
        GonzackerApp.getMapController().getDeck().agregarCarta( ((CartaPequeniaComponent) shopGridPane.getChildren().get(6)).getCarta());
        money.set(money.get() - cost1.get());
        GonzackerApp.getMapController().setDinero(money.get());
        buyButton1.setDisable(true);
    }

    @FXML
    void onBuyAction2(ActionEvent event) {
        GonzackerApp.getMapController().getDeck().agregarCarta( ((CartaPequeniaComponent) shopGridPane.getChildren().get(7)).getCarta());
        money.set(money.get() - cost2.get());
        GonzackerApp.getMapController().setDinero(money.get());
        buyButton2.setDisable(true);
    }

    @FXML
    void onBuyAction3(ActionEvent event) {
        GonzackerApp.getMapController().getDeck().agregarCarta( ((CartaPequeniaComponent) shopGridPane.getChildren().get(8)).getCarta());
        money.set(money.get() - cost3.get());
        GonzackerApp.getMapController().setDinero(money.get());
        buyButton3.setDisable(true);
    }

    @FXML
    void onCloseAction(ActionEvent event) {
        GonzackerApp.setRoot(GonzackerApp.getMapController().getRoot());
    }

    // getters and setters

    public BorderPane getRoot() {
        return root;
    }

    public int getCost1() {
        return cost1.get();
    }

    public IntegerProperty cost1Property() {
        return cost1;
    }

    public void setCost1(int cost1) {
        this.cost1.set(cost1);
    }

    public int getCost2() {
        return cost2.get();
    }

    public IntegerProperty cost2Property() {
        return cost2;
    }

    public void setCost2(int cost2) {
        this.cost2.set(cost2);
    }

    public int getCost3() {
        return cost3.get();
    }

    public IntegerProperty cost3Property() {
        return cost3;
    }

    public void setCost3(int cost3) {
        this.cost3.set(cost3);
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
