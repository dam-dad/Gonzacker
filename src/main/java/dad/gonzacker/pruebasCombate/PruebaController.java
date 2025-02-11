package dad.gonzacker.pruebasCombate;

import dad.gonzacker.components.CartaPequeniaComponent;
import dad.gonzacker.components.EnemyEntity;
import dad.gonzacker.components.UserEntity;
import dad.gonzacker.models.Carta;
import dad.gonzacker.models.Deck;
import dad.gonzacker.models.EfectoCarta;
import dad.gonzacker.models.Tipos;
import dad.gonzacker.patronesEnemigo.PatronAgresivo;
import dad.gonzacker.patronesEnemigo.PatronDefensivo;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PruebaController implements Initializable {

    private Deck deck = new Deck();
    private List<Carta> mano = new ArrayList<>();
    private IntegerProperty userLife = new SimpleIntegerProperty();
    private IntegerProperty userShield = new SimpleIntegerProperty();
    private List<EnemyEntity> enemigos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deck = new Deck(); // Inicializar el deck

        userLife.set(50); // A cambiar por la vida actual del personaje en cada momento

        // Bindings

        // Cambios en todos los controladores
        user.vidaActualProperty().bindBidirectional(userLife);

        // Cambios únicamente en combate
        user.escudoActualProperty().bind(userShield);

        energyLabel.setText(user.getEnergia() + "");
        maxEnergyLabel.setText(user.getEnergiaMaxima() + "");

        // Cargar imágenes
        Image imgAtaque = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Prueba_Imagen.jpg")));
        Image imageEnemigo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/SlimeBlue.png")));

        // Agregar cartas con efectos generados por la clase EfectoCarta
        deck.agregarCarta(new Carta(1, "Ataque", Tipos.Enemy ,"Inflige 2 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(2))));
        deck.agregarCarta(new Carta(2, "Ataque 2",Tipos.Enemy ,"Inflige 3 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(3))));
        deck.agregarCarta(new Carta(3, "Ataque 3",Tipos.Enemy  ,"Inflige 4 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(4))));
        deck.agregarCarta(new Carta(4, "Ataque 4",Tipos.Enemy  ,"Inflige 5 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(5))));

        deck.barajar();

        EnemyEntity enemigo1 = new EnemyEntity(10, 0, imageEnemigo,new PatronDefensivo(), user);
        EnemyEntity enemigo2 = new EnemyEntity(6, 2, imageEnemigo,new PatronAgresivo(), user);

        enemigos.add(enemigo1);
        enemigos.add(enemigo2);

        enemyGridPane.add(enemigo1, 0, 0);
        enemyGridPane.add(enemigo2, 1, 0);

        configurarDragAndDrop(userField);
        configurarDragAndDrop(battleField);


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
    private AnchorPane battleField;

    @FXML
    private AnchorPane userField;

    @FXML
    private FlowPane cartasZoneFlowPane;

    @FXML
    private GridPane enemyGridPane;

    @FXML
    private Label energyLabel;

    @FXML
    private Label maxEnergyLabel;

    @FXML
    private BorderPane root;

    @FXML
    private Label turnLabel;

    private void configurarDragAndDrop(AnchorPane campo) {
        if (campo != null) {
            // Aceptar que una carta sea arrastrada sobre este AnchorPane
            campo.setOnDragOver(event -> {
                if (event.getGestureSource() instanceof CartaPequeniaComponent) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });
        }
    }

    @FXML
    void onFieldDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasString()) {
            String data = dragboard.getString();

            // Buscar la carta en la mano
            Carta cartaArrastrada = null;
            for (Carta carta : mano) {
                if (carta.getNombre().equals(data)) { // Suponiendo que el Dragboard contiene el nombre de la carta
                    cartaArrastrada = carta;
                    break;
                }
            }
            if (cartaArrastrada != null) {
                System.out.println("Carta soltada en el campo: " + cartaArrastrada.getNombre());

                // Buscar el componente visual correspondiente
                CartaPequeniaComponent cartaComponent = null;
                for (javafx.scene.Node node : cartasZoneFlowPane.getChildren()) {
                    if (node instanceof CartaPequeniaComponent) {
                        CartaPequeniaComponent temp = (CartaPequeniaComponent) node;
                        if (temp.getCarta().equals(cartaArrastrada)) {
                            cartaComponent = temp;
                            break;
                        }
                    }
                }

                if (cartaComponent != null) {
                    jugarCarta(cartaComponent);
                }
            }
        }
        event.setDropCompleted(true);
        event.consume();
    }

    @FXML
    void onFinishTurnAction(ActionEvent event) {
        for (EnemyEntity enemigo : enemigos) {
            enemigo.realizarAccionAleatoria();
        }
        System.out.println("Turno enemigo finalizado.");
    }

    @FXML
    private UserEntity user;

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
