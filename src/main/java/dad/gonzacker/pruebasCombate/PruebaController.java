package dad.gonzacker.pruebasCombate;

import dad.gonzacker.components.CartaPequeniaComponent;
import dad.gonzacker.models.Carta;
import dad.gonzacker.models.Deck;
import dad.gonzacker.models.EfectoCarta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PruebaController implements Initializable {

    private Deck deck = new Deck();
    private List<Carta> mano = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deck = new Deck(); // Inicializar el deck

        // Cargar imágenes
        Image imgAtaque = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Prueba_Imagen.jpg")));

        // Agregar cartas con efectos generados por la clase EfectoCarta
        deck.agregarCarta(new Carta(1, "Ataque","Place holder" ,"Inflige 2 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(2))));
        deck.agregarCarta(new Carta(2, "Ataque 2","Place holder" ,"Inflige 3 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(3))));
        deck.agregarCarta(new Carta(3, "Ataque 3","Place holder" ,"Inflige 4 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(4))));
        deck.agregarCarta(new Carta(4, "Ataque 4","Place holder" ,"Inflige 5 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(5))));

        deck.barajar();


    }

    public PruebaController() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pruebaCombate/pruebaCartas.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private FlowPane cartasZoneFlowPane;

    @FXML
    private GridPane enemyGridPane;

    @FXML
    private Label energyLabel;

    @FXML
    private BorderPane root;

    @FXML
    private Label turnLabel;

    @FXML
    void onFinishTurnAction(ActionEvent event) {

    }

    @FXML
    void onDrawAction(ActionEvent event) {
        Carta carta = deck.robarCarta();
        mano.add(carta);
        CartaPequeniaComponent cartaComponent = new CartaPequeniaComponent(carta);
        cartasZoneFlowPane.getChildren().add(cartaComponent);
    }

    private void jugarCarta(CartaPequeniaComponent cartaComponent) {
        cartasZoneFlowPane.getChildren().remove(cartaComponent); // Quitar de la UI
        mano.remove(cartaComponent.getCarta()); // Quitar de la lista
        deck.descartarCarta(cartaComponent.getCarta()); // Enviar al descarte
    }

    public BorderPane getRoot() {
        return root;
    }
}
