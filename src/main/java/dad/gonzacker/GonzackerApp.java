package dad.gonzacker;

import dad.gonzacker.controllers.MapController;
import dad.gonzacker.controllers.MenuController;
import dad.gonzacker.controllers.SettingsController;
import dad.gonzacker.controllers.TiendaController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GonzackerApp extends Application {

    // controllers

    private static MenuController menuController = new MenuController();
    private static SettingsController settingsController = new SettingsController();
    private static MapController mapController = new MapController();
    private static TiendaController tiendaController = new TiendaController();

    private static Scene scene = new Scene(menuController.getRoot());

    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(scene);
        stage.setTitle("Gonzacker");
        stage.setFullScreen(true);
        stage.show();

    }

    // getters and setters

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

    public static MapController getMapController() {
        return mapController;
    }

    public static void setMapController(MapController mapController) {
        GonzackerApp.mapController = mapController;
    }


    public static TiendaController getShopController() {
        return tiendaController;
    }
}
