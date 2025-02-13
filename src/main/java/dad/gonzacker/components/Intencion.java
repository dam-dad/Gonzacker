package dad.gonzacker.components;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Intencion extends AnchorPane implements Initializable {

    private StringProperty numeroIntencionTexto = new SimpleStringProperty();
    private ObjectProperty<Image> imagenIntencion = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intentionImage.imageProperty().bind(imagenIntencion);
        intentionLabel.textProperty().bind(numeroIntencionTexto);
    }

    public Intencion() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/intencion.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private ImageView intentionImage;

    @FXML
    private Label intentionLabel;

    public String getNumeroIntencionTexto() {
        return numeroIntencionTexto.get();
    }

    public StringProperty numeroIntencionTextoProperty() {
        return numeroIntencionTexto;
    }

    public void setNumeroIntencionTexto(String numeroIntencionTexto) {
        this.numeroIntencionTexto.set(numeroIntencionTexto);
    }

    public Image getImagenIntencion() {
        return imagenIntencion.get();
    }

    public ObjectProperty<Image> imagenIntencionProperty() {
        return imagenIntencion;
    }

    public void setImagenIntencion(Image imagenIntencion) {
        this.imagenIntencion.set(imagenIntencion);
    }

    public ImageView getIntentionImage() {
        return intentionImage;
    }

    public void setIntentionImage(ImageView intentionImage) {
        this.intentionImage = intentionImage;
    }

    public Label getIntentionLabel() {
        return intentionLabel;
    }

    public void setIntentionLabel(Label intentionLabel) {
        this.intentionLabel = intentionLabel;
    }
}
