package dad.gonzacker.pruebasCombate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PruebaApp extends Application {

    PruebaController pruebaController = new PruebaController();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(pruebaController.getRoot()));
        stage.setTitle("Pruebas");
        stage.show();
    }
}
