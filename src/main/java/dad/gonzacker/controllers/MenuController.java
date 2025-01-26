package dad.gonzacker.controllers;

import dad.gonzacker.GonzackerApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    // view

    @FXML
    private VBox root;

    public MenuController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onExitAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void onSettingsAction(ActionEvent event) {
        GonzackerApp.setRoot(GonzackerApp.getSettingsController().getRoot());
        GonzackerApp.getSettingsController().setPreviousController(root);
    }

    @FXML
    void onStartAction(ActionEvent event) {
        GonzackerApp.getMapController().limpiarLineas();

        GonzackerApp.setRoot(GonzackerApp.getMapController().getRoot());
        GonzackerApp.getMapController().setPreviousController(root);
        Platform.runLater(() -> {
            GonzackerApp.getMapController().redibujar();
        });
    }

    // getters and setters

    public VBox getRoot() {
        return root;
    }
}
