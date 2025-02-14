package dad.gonzacker.controllers;

import dad.gonzacker.GonzackerApp;
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

import static dad.gonzacker.models.LogicaCarta.roboDeCartas;

public class MapController implements Initializable  {

    // logic

    private Parent previousController;
    private HashMap<Button, java.util.List<Button>> conexiones = new HashMap<>();

    private IntegerProperty dinero = new SimpleIntegerProperty();
    private UserEntity jugador = new UserEntity();
    private List<EnemyEntity> enemigos = new ArrayList<>();
    private ObjectProperty<Deck> deck = new SimpleObjectProperty<>(new Deck());
    private List<Carta> cartas = new ArrayList<>();
    private int combateActual = 1;

    private Image imgAtaque1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Ataque1.png")));
    private Image imgAtaque2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Ataque2.png")));
    private Image imgAtaque3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Ataque3.png")));
    private Image imgDefensa1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Defensa1.png")));
    private Image imgDefensa2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Defensa2.png")));
    private Image imgDefensa3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Defensa3.png")));
    private Image imgCura1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Cura1.png")));
    private Image imgRoba1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Roba1.png")));
    private Image imgDefAtq = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/DefAtq3.png")));
    private Image imgCurDef = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/CurDef3.png")));
    private Image imgAtqRob = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/RobAtq3.png")));


    Image imageEnemigo1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Enemy1.png")));
    Image imageEnemigo2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Enemy3b.png")));
    Image imageEnemigo3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Enemy4.png")));
    Image imageEnemigo4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/AndromedaRangerCaptain.png")));
    Image imageBoss = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/EnemyBossUnderworld.png")));

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
        for (int i = 0; i < 2; i++) {
            cartas.add(new Carta(1, "Ataque 1", Tipos.Enemy ,"Inflige 2 de daño", imgAtaque1, Collections.singletonList(EfectoCarta.ataque(10))));
            cartas.add(new Carta(1, "Ataque 2",Tipos.Enemy ,"Inflige 3 de daño", imgAtaque2, Collections.singletonList(EfectoCarta.ataque(10))));
            cartas.add(new Carta(1, "Ataque 3",Tipos.Enemy  ,"Inflige 6 de daño", imgAtaque3, Collections.singletonList(EfectoCarta.ataque(10))));
            cartas.add(new Carta(1, "Defensa 1", Tipos.Field ,"Defiende 2 de daño", imgDefensa1, Collections.singletonList(EfectoCarta.escudo(2))));
            cartas.add(new Carta(1, "Defensa 2",Tipos.Field ,"Defiende 3 de daño", imgDefensa2, Collections.singletonList(EfectoCarta.escudo(3))));
            cartas.add(new Carta(1, "Defensa 3",Tipos.Field  ,"Defiende 6 de daño", imgDefensa3, Collections.singletonList(EfectoCarta.escudo(6))));
        }
        cartas.add(new Carta(1, "Cura 1", Tipos.Field ,"Cura 3 de daño", imgCura1, Collections.singletonList(EfectoCarta.curacion(3))));
        cartas.add(new Carta(2, "Defensa/Ataque 1", Tipos.Enemy, "Defensa 3 \n Ataque 3", imgDefAtq, Arrays.asList(EfectoCarta.escudo(3), EfectoCarta.ataque(3))));
        cartas.add(new Carta(2, "Cura/Defensa 1", Tipos.Field ,"Cura 3 \n Defiende 3", imgCurDef, Arrays.asList(EfectoCarta.curacion(3), EfectoCarta.escudo(3))));
        cartas.add(new Carta(2, "Ataque/Robo 1", Tipos.Enemy, "Ataque 3 \n Robo 2", imgAtqRob, Arrays.asList(EfectoCarta.robar(2), EfectoCarta.ataque(3))));
        cartas.add(new Carta(0, "Robo carta 1",Tipos.Field  ,"Roba 2", imgRoba1, Collections.singletonList(EfectoCarta.robar(2))));


        // se añaden 3 cartas aleatorias al deck

        Random random = new Random();
        deck.get().getCartas().add(cartas.get(random.nextInt(cartas.size())));
        deck.get().getCartas().add(cartas.get(random.nextInt(cartas.size())));
        deck.get().getCartas().add(cartas.get(random.nextInt(cartas.size())));
        deck.get().getCartas().add(cartas.get(random.nextInt(cartas.size())));
        deck.get().getCartas().add(cartas.get(random.nextInt(cartas.size())));
        deck.get().getCartas().add(cartas.get(random.nextInt(cartas.size())));
        deck.get().getCartas().add(cartas.get(random.nextInt(cartas.size())));
        deck.get().getCartas().add(cartas.get(random.nextInt(cartas.size())));



        // enemigos
        EnemyEntity enemigo1 = new EnemyEntity(10, 0, imageEnemigo1,new PatronDefensivo(), new IntencionDefensiva(),GonzackerApp.getCombateController().getUser());
        EnemyEntity enemigo2 = new EnemyEntity(6, 2, imageEnemigo1,new PatronAgresivo(), new IntencionAgresiva(),GonzackerApp.getCombateController().getUser());
        EnemyEntity enemigo3 = new EnemyEntity(8, 1, imageEnemigo2,new PatronDefensivo(), new IntencionDefensiva(),GonzackerApp.getCombateController().getUser());
        EnemyEntity enemigo4 = new EnemyEntity(12, 2, imageEnemigo2,new PatronAgresivo(), new IntencionAgresiva(),GonzackerApp.getCombateController().getUser());
        EnemyEntity enemigo5 = new EnemyEntity(15, 3, imageEnemigo3,new PatronDefensivo(), new IntencionDefensiva(),GonzackerApp.getCombateController().getUser());
        EnemyEntity enemigo6 = new EnemyEntity(20, 4, imageEnemigo3,new PatronAgresivo(), new IntencionAgresiva(),GonzackerApp.getCombateController().getUser());
        EnemyEntity enemigo7 = new EnemyEntity(25, 5, imageEnemigo4,new PatronDefensivo(), new IntencionDefensiva(),GonzackerApp.getCombateController().getUser());


        enemigos.add(enemigo1);
        enemigos.add(enemigo2);
        enemigos.add(enemigo3);
        enemigos.add(enemigo4);
        enemigos.add(enemigo5);
        enemigos.add(enemigo6);
        enemigos.add(enemigo7);


        // bindings

        dinero.set(5);

        shieldLabel.textProperty().bind(jugador.escudoActualProperty().asString());
        healthLabel.textProperty().bind(jugador.vidaActualProperty().asString());
        moneyLabel.textProperty().bind(dinero.asString());

        jugador.vidaActualProperty().addListener((o, ov, nv) -> {
            if (nv.intValue() <= 0) {
                GonzackerApp.setRoot(GonzackerApp.getGameOverController().getRoot());
            }
        });

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
        // se le pasa al combate controller el jugador, los enemigos y el deck

        List<EnemyEntity> enemigosCombate= new ArrayList<>();

        enemigosCombate.add(new EnemyEntity(40, 10, imageBoss,new PatronAgresivo(), new IntencionAgresiva(),GonzackerApp.getCombateController().getUser()));

        GonzackerApp.getCombateController().setEnemigos(enemigosCombate);
        GonzackerApp.getCombateController().setUpEnemigo();
        GonzackerApp.getCombateController().distribuirEnemigos();
        GonzackerApp.getCombateController().getUser().setVidaActual(jugador.getVidaActual());
        GonzackerApp.getCombateController().getUser().setEscudoActual(jugador.getEscudoActual());
        GonzackerApp.getCombateController().getUser().setEnergia(jugador.getEnergiaMaxima());
        GonzackerApp.getCombateController().getUser().setEnergiaMaxima(jugador.getEnergiaMaxima());
        GonzackerApp.getCombateController().getUser().setVidaMaxima(jugador.getVidaMaxima());
        GonzackerApp.getCombateController().getDeck().getCartas().setAll(deck.get().getCartas());

        GonzackerApp.getCombateController().setBossfight(true);

        roboDeCartas(GonzackerApp.getCombateController(), 5);


        // se cambia la vista

        GonzackerApp.setRoot(GonzackerApp.getCombateController().getRoot());

    }


    @FXML
    void onCombatAction(ActionEvent event) {
        habilitarSiguientes((Button) event.getSource());

        Random random = new Random();
        List<EnemyEntity> enmigosAux = enemigos;
        List<EnemyEntity> enemigosCombate= new ArrayList<>();

        for (int i = 0; i < combateActual; i++) {
            EnemyEntity enemigo = enmigosAux.get(random.nextInt(enmigosAux.size()));
            enemigosCombate.add(enemigo);
            enmigosAux.remove(enemigo);

            if (enmigosAux.isEmpty()) break;
        }

        combateActual++;

        // se le pasa al combate controller el jugador, los enemigos y el deck

        GonzackerApp.getCombateController().setEnemigos(enemigosCombate);
        GonzackerApp.getCombateController().setUpEnemigo();
        GonzackerApp.getCombateController().distribuirEnemigos();
        GonzackerApp.getCombateController().getUser().setVidaActual(jugador.getVidaActual());
        GonzackerApp.getCombateController().getUser().setEscudoActual(jugador.getEscudoActual());
        GonzackerApp.getCombateController().getUser().setEnergia(jugador.getEnergiaMaxima());
        GonzackerApp.getCombateController().getUser().setEnergiaMaxima(jugador.getEnergiaMaxima());
        GonzackerApp.getCombateController().getUser().setVidaMaxima(jugador.getVidaMaxima());
        GonzackerApp.getCombateController().getDeck().getCartas().setAll(deck.get().getCartas());

        roboDeCartas(GonzackerApp.getCombateController(), 5);

        // se establecen las recompensas

        GonzackerApp.getRecompensasContoller().setMoney(random.nextInt(10));
        GonzackerApp.getRecompensasContoller().establecerRecompensa(cartas.get(random.nextInt(cartas.size())));

        // se cambia la vista

        GonzackerApp.setRoot(GonzackerApp.getCombateController().getRoot());


    }

    @FXML
    void onEventAction(ActionEvent event) {

        GonzackerApp.setRoot(GonzackerApp.getEventoController().getRoot());

        habilitarSiguientes((Button) event.getSource());
    }

    @FXML
    void onRestAction(ActionEvent event) {

        jugador.setVidaActual(jugador.getVidaActual() + 5);

        if (jugador.getVidaActual() > jugador.getVidaMaxima()) {
            jugador.setVidaActual(jugador.getVidaMaxima());
        }

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


    public UserEntity getJugador() {
        return jugador;
    }

    public void setJugador(UserEntity jugador) {
        this.jugador = jugador;
    }

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

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}
