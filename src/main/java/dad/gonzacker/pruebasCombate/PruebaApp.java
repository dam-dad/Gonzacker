package dad.gonzacker.pruebasCombate;

import dad.gonzacker.controllers.CombateController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PruebaApp extends Application {

    CombateController combateController = new CombateController();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(combateController.getRoot()));
        stage.setTitle("Pruebas");
        stage.setFullScreen(true);
        stage.show();
    }
}
