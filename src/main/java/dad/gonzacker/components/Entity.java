package dad.gonzacker.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Entity extends BorderPane implements Initializable {

    // model

    private DoubleProperty vidaActual = new SimpleDoubleProperty();
    private DoubleProperty vidaMaxima = new SimpleDoubleProperty();
    private DoubleProperty escudoActual = new SimpleDoubleProperty();
    private ObjectProperty<Image> imagenEntidad = new SimpleObjectProperty<>();

    // view

    @FXML
    private ImageView entityImage;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private Label hpLabel;

    @FXML
    private ProgressBar shieldBar;

    @FXML
    private Label shieldLabel;

    public Entity(){
        super();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/entity.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // bindings

        hpLabel.textProperty().bindBidirectional(vidaActual , new NumberStringConverter());

        shieldLabel.textProperty().bindBidirectional(escudoActual , new NumberStringConverter());

        hpBar.progressProperty().bind(Bindings.createDoubleBinding(() -> {
            return ((vidaActual.get())/vidaMaxima.get());
        }, vidaActual , vidaMaxima));

        shieldBar.progressProperty().bind(Bindings.createDoubleBinding(() -> {
            return ((escudoActual.get())/vidaMaxima.get());
        }, escudoActual , vidaMaxima));

        entityImage.imageProperty().bind(imagenEntidad);

        // Evento para manejar cuando una carta se suelta sobre la entidad
        setOnDragOver(event -> {
            if (event.getGestureSource() instanceof CartaPequeniaComponent) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();

            if (dragboard.hasString()) {
                String data = dragboard.getString();
                String[] efectos = data.split(",");

                for (String efecto : efectos) {
                    if (efecto.startsWith("ataque:")) {
                        String[] parts = efecto.split(":");
                        int daño = Integer.parseInt(parts[1]);
                        System.out.println("Carta de ataque soltada con daño: " + daño);
                        reducirVida(daño);
                    }

                    //                else if (efecto.startsWith("curacion:")) {
                    //                    String[] parts = efecto.split(":");
                    //                    int curacion = Integer.parseInt(parts[1]);
                    //                    System.out.println("Carta de curación soltada con curación: " + curacion);
                    //                    aumentarVida(curacion);
                    //                }
                    //
                    //                else if (efecto.startsWith("escudo:")) {
                    //                    String[] parts = efecto.split(":");
                    //                    int escudo = Integer.parseInt(parts[1]);
                    //                    System.out.println("Carta de escudo soltada con valor: " + escudo);
                    //                    aumentarEscudo(escudo);
                    //                }

                    else {
                        System.out.println("Efecto desconocido: " + data);
                    }
                }
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }

    // getters and setters


    public double getEscudoActual() {
        return escudoActual.get();
    }

    public DoubleProperty escudoActualProperty() {
        return escudoActual;
    }

    public void setEscudoActual(double escudoActual) {
        this.escudoActual.set(escudoActual);
    }

    public Double getVidaActual() {
        return vidaActual.get();
    }

    public DoubleProperty vidaActualProperty() {
        return vidaActual;
    }

    public void setVidaActual(Double vidaActual) {
        this.vidaActual.set(vidaActual);
    }

    public Double getVidaMaxima() {
        return vidaMaxima.get();
    }

    public DoubleProperty vidaMaximaProperty() {
        return vidaMaxima;
    }

    public void setVidaMaxima(Double vidaMaxima) {
        this.vidaMaxima.set(vidaMaxima);
    }

    public Image getImagenEnemigo() {
        return imagenEntidad.get();
    }

    public ObjectProperty<Image> imagenEnemigoProperty() {
        return imagenEntidad;
    }

    public void setImagenEnemigo(Image imagenEnemigo) {
        this.imagenEntidad.set(imagenEnemigo);
    }



    // Método para reducir la vida de la entidad
    private void reducirVida(double cantidad) {

        double nuevoEscudo = escudoActual.get();
        if (nuevoEscudo > 0){
            double cantidadTemporal = cantidad-nuevoEscudo;

            if (cantidadTemporal < 0){
                cantidadTemporal = 0;
            }

            nuevoEscudo -= cantidad;
            cantidad = cantidadTemporal;

            if (nuevoEscudo < 0) {
                nuevoEscudo = 0;
            }

            escudoActual.set(nuevoEscudo);
        }

        if (cantidad > 0) {
            double nuevaVida = vidaActual.get() - cantidad;
            if (nuevaVida < 0) nuevaVida = 0;
            vidaActual.set(nuevaVida);
            System.out.println("Vida de la entidad: " + nuevaVida);
        }
    }
}
