package dad.gonzacker.pruebasCombate;

import dad.gonzacker.components.CartaPequeniaComponent;
import dad.gonzacker.components.EnemyEntity;
import dad.gonzacker.components.UserEntity;
import dad.gonzacker.intencionesEnemigo.IntencionAgresiva;
import dad.gonzacker.intencionesEnemigo.IntencionDefensiva;
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

import static dad.gonzacker.models.LogicaCarta.*;

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
        user.escudoActualProperty().bindBidirectional(userShield);

        energyLabel.setText(user.getEnergia() + "");
        maxEnergyLabel.setText(user.getEnergiaMaxima() + "");

        // Cargar imágenes
        Image imgAtaque = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Prueba_Imagen.jpg")));
        Image imageEnemigo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/SlimeBlue.png")));

        // Agregar cartas con efectos generados por la clase EfectoCarta
        deck.agregarCarta(new Carta(1, "Ataque 1", Tipos.Enemy ,"Inflige 2 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(2))));
        deck.agregarCarta(new Carta(2, "Ataque 2",Tipos.Enemy ,"Inflige 3 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(3))));
        deck.agregarCarta(new Carta(2, "Ataque 2",Tipos.Enemy ,"Inflige 3 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(3))));
        deck.agregarCarta(new Carta(3, "Ataque 3",Tipos.Enemy  ,"Inflige 4 de daño", imgAtaque, Collections.singletonList(EfectoCarta.ataque(4))));
        deck.agregarCarta(new Carta(1, "Defensa 1", Tipos.Field ,"Defiende 2 de daño", imgAtaque, Collections.singletonList(EfectoCarta.escudo(2))));
        deck.agregarCarta(new Carta(2, "Defensa 3",Tipos.Field ,"Defiende 3 de daño", imgAtaque, Collections.singletonList(EfectoCarta.escudo(3))));
        deck.agregarCarta(new Carta(3, "Defensa 5",Tipos.Field  ,"Defiende 5 de daño", imgAtaque, Collections.singletonList(EfectoCarta.escudo(5))));

        deck.barajar();

        //Creacion momentanea, esto se hará fuera en un futuro

        EnemyEntity enemigo1 = new EnemyEntity(10, 0, imageEnemigo,new PatronDefensivo(), new IntencionDefensiva(),user);
        EnemyEntity enemigo2 = new EnemyEntity(6, 2, imageEnemigo,new PatronAgresivo(), new IntencionAgresiva(),user);

        // Simular la array que se usará en un futuro

        enemigos.add(enemigo1);
        enemigos.add(enemigo2);

        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (index < enemigos.size()) {
                    enemyGridPane.add(enemigos.get(index), j, i);
                    index++;
                }
            }
        }

        for (EnemyEntity enemigo : enemigos) {
            enemigo.setController(this);
            enemigo.generarIntencion();
            enemigo.vidaActualProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.intValue() <= 0) {
                    eliminarEnemigo(enemigo);
                }
            });
        }

        roboDeCartas(this,5);

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

    @FXML
    private UserEntity user;

    @FXML
    void onFinishTurnAction(ActionEvent event) {
        for (EnemyEntity enemigo : enemigos) {
            enemigo.realizarAccionAleatoria();
            enemigo.generarIntencion();
        }
        System.out.println("Turno enemigo finalizado.");
        energyLabel.setText(maxEnergyLabel.getText());
        descartarMano();
        roboDeCartas(this,5);
    }

    @FXML
    void onDrawAction(ActionEvent event) {
        roboDeCartas(this,1);
    }

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
            String[] partes = data.split(";", 4); // Separar nombre, tipo y efectos
            if (partes.length < 4) return; // Si no tiene los 3 elementos, no hacemos nada

            int costeCarta = Integer.parseInt(partes[0]);
            String nombreCarta = partes[1];
            String tipoCarta = partes[2];
            String efectosCarta = partes[3];

            // Buscar la carta en la mano por nombre
            if (!verificarEnergia(costeCarta)){
                return;
            }

            Carta cartaArrastrada = buscarCartaPorNombre(nombreCarta);

            if (cartaArrastrada != null) {
                System.out.println("Carta soltada en el campo: " + cartaArrastrada.getNombre());
                System.out.println("Efectos: " + efectosCarta);


                if (tipoCarta.equals(Tipos.Enemy.name())) {
                    System.out.println("Carta de tipo Enemy no eliminada. No se hace nada.");
                    return;
                }
                // Si la carta es de tipo "Field", realizamos los efectos y la descartamos
                else if (tipoCarta.equals(Tipos.Field.name())) {
                    System.out.println("Carta de tipo Field: aplicando efectos y descartando.");

                    energyLabel.setText(Integer.parseInt(energyLabel.getText()) - costeCarta + "");

                    // Aquí aplicamos los efectos de la carta, como daño, curación, etc.
                    String[] efectos = efectosCarta.split(",");
                    for (String efecto : efectos) {
                        if (efecto.startsWith("escudo:")) {
                            String[] parts = efecto.split(":");
                            int defensa = Integer.parseInt(parts[1]);
                            System.out.println("Carta de defensa: " + defensa);
                            crearEscudo(user,defensa); // Aplica el daño
                        }
                        // Aquí podrías agregar más efectos como curación, escudo, etc.
                    }
                }

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

    private boolean verificarEnergia(int coste) {

        return (Integer.parseInt(energyLabel.getText()) - coste) >= 0;
    }

    public void handleCardEffectEnemigo(int costeCarta,String nombreCarta, String tipoCarta, String efectosCarta, EnemyEntity enemigo) {


        if (!verificarEnergia(costeCarta)){
            return;
        }
        // Buscar la carta en la mano
        Carta cartaArrastrada = buscarCartaPorNombre(nombreCarta);

        if (cartaArrastrada != null) {
            System.out.println("Carta soltada: " + cartaArrastrada.getNombre());
            System.out.println("Efectos: " + efectosCarta);

            // Aplicar efectos dependiendo del tipo de carta
            if (tipoCarta.equals(Tipos.Field.name())) {
                System.out.println("Carta de tipo Enemy: No se aplica efecto.");
            } else if (tipoCarta.equals(Tipos.Enemy.name())) {
                System.out.println("Carta de tipo Field: Aplicando efectos...");

                energyLabel.setText(Integer.parseInt(energyLabel.getText()) - costeCarta + "");

                String[] efectos = efectosCarta.split(",");
                for (String efecto : efectos) {
                    if (efecto.startsWith("ataque:")) {
                        String[] parts = efecto.split(":");
                        int daño = Integer.parseInt(parts[1]);
                        System.out.println("Carta de ataque soltada con daño: " + daño);
                        reducirVida(enemigo,daño);
                    }
                    // Aquí se podrían agregar más efectos, como curación, daño, etc.
                }

                // Aquí también podríamos eliminar la carta de la mano y del FlowPane
                eliminarCartaDeMano(cartaArrastrada);
            }
        }
    }

    private void descartarMano(){
        Iterator<javafx.scene.Node> iterator = cartasZoneFlowPane.getChildren().iterator();

        while (iterator.hasNext()) {
            javafx.scene.Node node = iterator.next();
            if (node instanceof CartaPequeniaComponent) {
                // Eliminar el nodo con el iterador
                CartaPequeniaComponent cartaDescartar = (CartaPequeniaComponent) node;
                mano.remove(cartaDescartar.getCarta());
                deck.descartarCarta(cartaDescartar.getCarta());
                iterator.remove(); // Elimina el nodo de la colección de manera segura
            }
        }
    }


    private void eliminarCartaDeMano(Carta carta) {
        CartaPequeniaComponent cartaComponent = obtenerComponenteDeCarta(carta);

        if (cartaComponent != null) {
            cartasZoneFlowPane.getChildren().remove(cartaComponent);
            mano.remove(carta);
            deck.descartarCarta(carta); // O si tienes un mazo de descarte, lo agregas allí
        }
    }

    private CartaPequeniaComponent obtenerComponenteDeCarta(Carta carta) {
        for (javafx.scene.Node node : cartasZoneFlowPane.getChildren()) {
            if (node instanceof CartaPequeniaComponent) {
                CartaPequeniaComponent temp = (CartaPequeniaComponent) node;
                if (temp.getCarta().equals(carta)) {
                    return temp;
                }
            }
        }
        return null;
    }

    private Carta buscarCartaPorNombre(String nombre) {
        for (Carta carta : mano) {
            if (carta.getNombre().equals(nombre)) {
                return carta;
            }
        }
        return null; // Si no se encuentra la carta, retornamos null
    }


    private void jugarCarta(CartaPequeniaComponent cartaComponent) {
        cartasZoneFlowPane.getChildren().remove(cartaComponent); // Quitar de la UI
        mano.remove(cartaComponent.getCarta()); // Quitar de la lista
        deck.descartarCarta(cartaComponent.getCarta()); // Enviar al descarte
    }


    private void eliminarEnemigo(EnemyEntity enemigo) {

        // Removerlo de la lista
        enemigos.remove(enemigo);

        // Removerlo visualmente del GridPane
        enemyGridPane.getChildren().remove(enemigo);

    }

    public FlowPane getCartasZoneFlowPane() {
        return cartasZoneFlowPane;
    }

    public List<Carta> getMano() {
        return mano;
    }

    public Deck getDeck() {
        return deck;
    }

    public BorderPane getRoot() {
        return root;
    }
}
