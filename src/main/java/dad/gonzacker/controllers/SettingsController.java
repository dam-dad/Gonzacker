package dad.gonzacker.controllers;

import dad.gonzacker.GonzackerApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    // logic

    private Parent previousController;

    // view

    @FXML
    private Slider musicSlider;

    @FXML
    private VBox root;

    @FXML
    private Slider soundSlider;

    public SettingsController(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settingsView.fxml"));
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
    void onApply(ActionEvent event) {

    }

    @FXML
    void onCancelAction(ActionEvent event) {
        GonzackerApp.setRoot(previousController);
    }

    // getters and setters

    public VBox getRoot() {
        return root;
    }

    public Parent getPreviousController() {
        return previousController;
    }

    public void setPreviousController(Parent previousController) {
        this.previousController = previousController;
    }
}
