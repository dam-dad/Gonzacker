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
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Entity extends BorderPane implements Initializable {

    // model

    private DoubleProperty vidaActual = new SimpleDoubleProperty();
    private DoubleProperty vidaMaxima = new SimpleDoubleProperty();
    private ObjectProperty<Image> imagenEntidad = new SimpleObjectProperty<>();

    // view

    @FXML
    private ImageView entityImage;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private Label hpLabel;

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

        hpBar.progressProperty().bind(Bindings.createDoubleBinding(() -> {
            return ((vidaActual.get())/vidaMaxima.get());
        }, vidaActual , vidaMaxima));

        entityImage.imageProperty().bind(imagenEntidad);

    }

    // getters and setters


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

}
