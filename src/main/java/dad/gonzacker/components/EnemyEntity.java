package dad.gonzacker.components;

import dad.gonzacker.models.Carta;
import dad.gonzacker.patronesEnemigo.EnemyPattern;
import dad.gonzacker.pruebasCombate.PruebaController;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;

public class EnemyEntity extends Entity {

    private UserEntity usuario;
    private EnemyPattern patron;
    private PruebaController controller; // Cambiar al controlador correcto en un futuro

    public EnemyEntity(double vidaMaxima, double escudo, Image image, EnemyPattern patron,UserEntity usuario) {
        super();
        setVidaMaxima(vidaMaxima);
        setVidaActual(vidaMaxima);
        setEscudoActual(escudo);
        this.patron = patron;
        this.usuario = usuario;
        imagenEntidad.set(image);
    }


    public void realizarAccionAleatoria() {
        patron.ejecutarPatron(this, usuario);
    }

    @Override
    protected void extraInitialize() {
        efectosCartas();
    }

    // Ataques del enemigo

    public void atacar(UserEntity user, int daño) {
        user.recibirDaño(daño);
    }

    public void curarse(double cura) {
        vidaActual.set(vidaActual.get() + cura);
        if (vidaActual.get()>vidaMaxima.get()){
            vidaActual.set(vidaMaxima.get());
        }
    }

    public void fortalecer(int escudo) {
        escudoActual.set(escudoActual.get() + escudo);
    }

    // Efectos del jugador

    private void efectosCartas() {
        setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();

            if (dragboard.hasString()) {
                String data = dragboard.getString();
                String[] partes = data.split(";", 3); // Separar nombre, tipo y efectos
                if (partes.length < 3) return;

                String nombreCarta = partes[0];
                String tipoCarta = partes[1];
                String efectosCarta = partes[2];

                if (controller != null) {
                    controller.handleCardEffectEnemigo(nombreCarta, tipoCarta, efectosCarta, this);
                } else {
                    System.out.println("El controlador aún no ha sido asignado.");
                }
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }

    public void aplicarEfectosCarta(String nombreCarta, String tipoCarta, String efectosCarta) {
        if (controller != null) {
            controller.handleCardEffectEnemigo(nombreCarta, tipoCarta, efectosCarta, this);
        } else {
            System.out.println("El controlador aún no ha sido asignado.");
        }
    }

    public PruebaController getController() {
            return controller;
    }

    public void setController(PruebaController controller) {
        this.controller = controller;
    }
}
