package dad.gonzacker.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VictoriaController implements Initializable {

// view

    @FXML
    private BorderPane root;

    public VictoriaController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/winView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    // getters and setters

    public BorderPane getRoot() {
        return root;
    }
}
