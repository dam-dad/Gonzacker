package dad.gonzacker;

import dad.gonzacker.controllers.MenuController;
import dad.gonzacker.controllers.SettingsController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GonzackerApp extends Application {

    // controllers

    private static MenuController menuController = new MenuController();
    private static SettingsController settingsController= new SettingsController();


    private static Scene scene = new Scene(menuController.getRoot());

    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(scene);
        stage.setTitle("Gonzacker");
        stage.show();

    }


    public static void setRoot(Parent root){
        scene.setRoot(root);
    }

    public static MenuController getMenuController() {
        return menuController;
    }

    public static void setMenuController(MenuController menuController) {
        GonzackerApp.menuController = menuController;
    }

    public static SettingsController getSettingsController() {
        return settingsController;
    }

    public static void setSettingsController(SettingsController settingsController) {
        GonzackerApp.settingsController = settingsController;
    }
}
