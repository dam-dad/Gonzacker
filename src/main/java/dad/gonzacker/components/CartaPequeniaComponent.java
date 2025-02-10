package dad.gonzacker.components;

import dad.gonzacker.models.Carta;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//public class CartaPequeniaComponent extends StackPane implements Initializable {
//    // Model
//    private IntegerProperty coste = new SimpleIntegerProperty();
//    private StringProperty nombre = new SimpleStringProperty();
//    private StringProperty descripcion = new SimpleStringProperty();
//    private ObjectProperty<Image> imagen = new SimpleObjectProperty<>();
//    private ArrayList<String> efectos = new ArrayList<>();
//
//
//    public CartaPequeniaComponent() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carta_pequenia.fxml"));
//            loader.setController(this);
//            loader.setRoot(this);
//            loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException("Error cargando carta_pequenia.fxml", e);
//        }
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println("Inicializando CartaPequeniaComponent...");
//
//        costeLabel.textProperty().bindBidirectional(coste, new NumberStringConverter());
//        efectoLabel.textProperty().bindBidirectional(descripcion);
//        nombreLabel.textProperty().bindBidirectional(nombre);
//        imagenView.imageProperty().bind(imagen);
//
//        // Asegurar que los eventos de arrastre se capturen correctamente
//        this.setPickOnBounds(true);
//
//        setOnDragDetected(event -> {
//            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
//            ClipboardContent clipboardContent = new ClipboardContent();
//
//            efectos.clear();
//
//            // Agregar efectos a la lista
//            efectos.add(ataque(5));
//            efectos.add(ataque(3));
//
//            String efectosConcatenados = String.join(",", efectos);
//
//            clipboardContent.putString(efectosConcatenados);
//
//            dragboard.setContent(clipboardContent);
//            event.consume();
//        });
//    }
//
//    private String ataque(int daño) {
//        String ataque = "ataque:" + daño;
//        return ataque;
//    }
//
//    @FXML
//    private Label costeLabel;
//
//    @FXML
//    private Label efectoLabel;
//
//    @FXML
//    private ImageView imagenView;
//
//    @FXML
//    private Label nombreLabel;
//
//    // Getters y Setters
//    public int getCoste() { return coste.get(); }
//    public void setCoste(int coste) { this.coste.set(coste); }
//    public String getNombre() { return nombre.get(); }
//    public void setNombre(String nombre) { this.nombre.set(nombre); }
//    public String getDescripcion() { return descripcion.get(); }
//    public void setDescripcion(String descripcion) { this.descripcion.set(descripcion); }
//    public Image getImagen() { return imagen.get(); }
//    public void setImagen(Image imagen) { this.imagen.set(imagen); }
//}

public class CartaPequeniaComponent extends StackPane implements Initializable {
    private Carta carta;

    public CartaPequeniaComponent() {
        this.carta = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carta_pequenia.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error cargando carta_pequenia.fxml", e);
        }
    }

    public CartaPequeniaComponent(Carta carta) {
        this.carta = carta;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/carta_pequenia.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error cargando carta_pequenia.fxml", e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (carta != null) {
            costeLabel.setText(String.valueOf(carta.getCoste()));
            nombreLabel.setText(carta.getNombre());
            efectoLabel.setText(carta.getDescripcion());
            imagenView.setImage(carta.getImagen());
        }

        this.setPickOnBounds(true);

        setOnDragDetected(event -> {
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent clipboardContent = new ClipboardContent();

            String efectosConcatenados = String.join(",", carta.getEfectos());

            clipboardContent.putString(efectosConcatenados);

            dragboard.setContent(clipboardContent);
            event.consume();
        });
    }

    @FXML
    private Label costeLabel;

    @FXML
    private Label efectoLabel;

    @FXML
    private ImageView imagenView;

    @FXML
    private Label nombreLabel;

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public Label getCosteLabel() {
        return costeLabel;
    }

    public void setCosteLabel(Label costeLabel) {
        this.costeLabel = costeLabel;
    }

    public Label getEfectoLabel() {
        return efectoLabel;
    }

    public void setEfectoLabel(Label efectoLabel) {
        this.efectoLabel = efectoLabel;
    }

    public ImageView getImagenView() {
        return imagenView;
    }

    public void setImagenView(ImageView imagenView) {
        this.imagenView = imagenView;
    }

    public Label getNombreLabel() {
        return nombreLabel;
    }

    public void setNombreLabel(Label nombreLabel) {
        this.nombreLabel = nombreLabel;
    }
}

