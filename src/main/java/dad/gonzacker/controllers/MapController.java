package dad.gonzacker.controllers;

import dad.gonzacker.GonzackerApp;
import dad.gonzacker.components.CartaPequeniaComponent;
import dad.gonzacker.components.UserEntity;
import dad.gonzacker.models.Carta;
import dad.gonzacker.models.Deck;
import dad.gonzacker.models.EfectoCarta;
import dad.gonzacker.models.Tipos;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MapController implements Initializable  {

    // logic

    private Parent previousController;
    private HashMap<Button, java.util.List<Button>> conexiones = new HashMap<>();

    private IntegerProperty dinero = new SimpleIntegerProperty();
    private UserEntity jugador = new UserEntity();
    private List<Entity> enemigos = new ArrayList<>();
    private ObjectProperty<Deck> deck = new SimpleObjectProperty<>(new Deck());
    private List<Carta> cartas = new ArrayList<>();

    private Image imgAtaque = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Prueba_Imagen.jpg")));

    // view

    @FXML
    private GridPane gridPane;

    @FXML
    private StackPane root;

    @FXML
    private Pane linePane;

    @FXML
    private Label shieldLabel;

    @FXML
    private Label healthLabel;

    @FXML
    private Label moneyLabel;

    public MapController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mapView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dibujarLineas(linePane, gridPane);
        habilitarSiguientes((Button) gridPane.getChildren().get(1));

        // cartas

        cartas.add(new Carta(1, "Ataque", Tipos.Enemy ,"Inflige 2 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(2))));
        cartas.add(new Carta(2, "Ataque 2",Tipos.Enemy ,"Inflige 3 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(3))));
        cartas.add(new Carta(3, "Ataque 3",Tipos.Enemy  ,"Inflige 4 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(4))));
        cartas.add(new Carta(4, "Ataque 4",Tipos.Enemy  ,"Inflige 5 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(5))));

        // bindings

        dinero.set(5);

        shieldLabel.textProperty().bind(jugador.escudoActualProperty().asString());
        healthLabel.textProperty().bind(jugador.vidaActualProperty().asString());
        moneyLabel.textProperty().bind(dinero.asString());

    }

    private void dibujarLineas(Pane linePane, GridPane gridPane) {
        linePane.getChildren().clear();

        // Obtener posiciones de los botones
        Button btnStart = (Button) gridPane.getChildren().get(1);
        Button btn1 = (Button) gridPane.getChildren().get(2);
        Button btn2 = (Button) gridPane.getChildren().get(3);
        Button btn3 = (Button) gridPane.getChildren().get(4);
        Button btn4 = (Button) gridPane.getChildren().get(5);
        Button btn5 = (Button) gridPane.getChildren().get(6);
        Button btn6 = (Button) gridPane.getChildren().get(7);
        Button btn7 = (Button) gridPane.getChildren().get(8);
        Button btn8 = (Button) gridPane.getChildren().get(9);
        Button btn9 = (Button) gridPane.getChildren().get(10);
        Button btn10 = (Button) gridPane.getChildren().get(11);
        Button btn11 = (Button) gridPane.getChildren().get(12);
        Button btn12 = (Button) gridPane.getChildren().get(13);
        Button btn13 = (Button) gridPane.getChildren().get(14);
        Button btn14 = (Button) gridPane.getChildren().get(15);
        Button btn15 = (Button) gridPane.getChildren().get(16);
        Button btn16 = (Button) gridPane.getChildren().get(17);
        Button btn17 = (Button) gridPane.getChildren().get(18);
        Button btnJefe = (Button) gridPane.getChildren().get(19);

        // Dibujar líneas
        conectarBotones(linePane, btnStart, btn1);
        conectarBotones(linePane, btnStart, btn2);
        conectarBotones(linePane, btn1, btn3);
        conectarBotones(linePane, btn1, btn4);
        conectarBotones(linePane, btn2, btn5);
        conectarBotones(linePane, btn2, btn6);
        conectarBotones(linePane, btn3, btn7);
        conectarBotones(linePane, btn4, btn8);
        conectarBotones(linePane, btn5, btn9);
        conectarBotones(linePane, btn6, btn9);
        conectarBotones(linePane, btn7, btn10);
        conectarBotones(linePane, btn7, btn13);
        conectarBotones(linePane, btn8, btn11);
        conectarBotones(linePane, btn9, btn12);
        conectarBotones(linePane, btn10, btn14);
        conectarBotones(linePane, btn11, btn14);
        conectarBotones(linePane, btn11, btn15);
        conectarBotones(linePane, btn12, btn16);
        conectarBotones(linePane, btn13, btn14);
        conectarBotones(linePane, btn14, btn17);
        conectarBotones(linePane, btn15, btn17);
        conectarBotones(linePane, btn16, btn17);
        conectarBotones(linePane, btn17, btnJefe);
    }

    public void redibujar() {
        dibujarLineas(linePane, gridPane);
    }

    public void limpiarLineas() {
        linePane.getChildren().clear();
    }

    private void conectarBotones(Pane pane, Button btn1, Button btn2) {
        double startX = btn1.getLayoutX() + btn1.getWidth() / 2;
        double startY = btn1.getLayoutY() + btn1.getHeight() / 2;
        double endX = btn2.getLayoutX() + btn2.getWidth() / 2;
        double endY = btn2.getLayoutY() + btn2.getHeight() / 2;

        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.web("#0099FF"));
        line.setStrokeWidth(2);

        pane.getChildren().add(line);

        // Guardar conexiones en el mapa
        conexiones.computeIfAbsent(btn1, k -> new ArrayList<>()).add(btn2);
    }

    private void habilitarSiguientes(Button boton) {
        // Deshabilitar todos los botones excepto el actual
        for (Button btn : conexiones.keySet()) {
            btn.setDisable(true);
        }

        // Habilitar solo los botones conectados
        List<Button> siguientes = conexiones.get(boton);
        if (siguientes != null) {
            for (Button siguiente : siguientes) {
                siguiente.setDisable(false);
            }
        }
    }


    @FXML
    void onExitAction(ActionEvent event) {
        limpiarLineas();
        GonzackerApp.setRoot(previousController);
    }

    @FXML
    void onBossNodeAction(ActionEvent event) {

    }


    @FXML
    void onCombatAction(ActionEvent event) {
        habilitarSiguientes((Button) event.getSource());
    }

    @FXML
    void onEventAction(ActionEvent event) {
        habilitarSiguientes((Button) event.getSource());
    }

    @FXML
    void onRestAction(ActionEvent event) {
        habilitarSiguientes((Button) event.getSource());
    }

    @FXML
    void onShopAction(ActionEvent event) {
        habilitarSiguientes((Button) event.getSource());

        Random random = new Random();

        GonzackerApp.getShopController().setCartas(cartas.get(random.nextInt(cartas.size())), cartas.get(random.nextInt(cartas.size())), cartas.get(random.nextInt(cartas.size())));
        GonzackerApp.getShopController().setPrecios(random.nextInt(10), random.nextInt(10), random.nextInt(10));
        GonzackerApp.getShopController().resetShop();
        GonzackerApp.getShopController().setMoney(dinero.get());
        GonzackerApp.setRoot(GonzackerApp.getShopController().getRoot());

    }




    // getters and setters

    public StackPane getRoot() {
        return root;
    }

    public Parent getPreviousController() {
        return previousController;
    }

    public void setPreviousController(Parent previousController) {
        this.previousController = previousController;
    }

    public Deck getDeck() {
        return deck.get();
    }

    public ObjectProperty<Deck> deckProperty() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck.set(deck);
    }

    public int getDinero() {
        return dinero.get();
    }

    public IntegerProperty dineroProperty() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero.set(dinero);
    }
}
