package dad.gonzacker.components;

import dad.gonzacker.controllers.CombateController;
import dad.gonzacker.intencionesEnemigo.EnemyIntention;
import dad.gonzacker.models.Carta;
import dad.gonzacker.patronesEnemigo.EnemyPattern;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;

public class EnemyEntity extends Entity {

    private UserEntity usuario;
    private EnemyPattern patron;
    private EnemyIntention intention;
    private CombateController controller; // Cambiar al controlador correcto en un futuro
    private int intencionFutura;

    public EnemyEntity(double vidaMaxima, double escudo, Image image, EnemyPattern patron, EnemyIntention intention, UserEntity usuario) {
        super("/fxml/enemyEntity.fxml");
        setVidaMaxima(vidaMaxima);
        setVidaActual(vidaMaxima);
        setEscudoActual(escudo);
        this.patron = patron;
        this.intention = intention;
        this.usuario = usuario;
        imagenEntidad.set(image);
    }


    public void realizarAccionAleatoria() {
        patron.ejecutarPatron(this, usuario, this.intencionFutura);
    }

    public void generarIntencion(){
        intention.iniciarIntencion(this);
    }

    public void iniciarIntencion(){

    }

    @FXML
    private Intencion intencionComponent;

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
                String[] partes = data.split(";", 4);
                if (partes.length < 4) return;

                int costeCarta = Integer.parseInt(partes[0]);
                String nombreCarta = partes[1];
                String tipoCarta = partes[2];
                String efectosCarta = partes[3];

                if (controller != null) {
                    controller.handleCardEffectEnemigo(costeCarta,nombreCarta, tipoCarta, efectosCarta, this);
                } else {
                    System.out.println("El controlador aún no ha sido asignado.");
                }
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }

    public void aplicarEfectosCarta(int costeCarta,String nombreCarta, String tipoCarta, String efectosCarta) {
        if (controller != null) {
            controller.handleCardEffectEnemigo(costeCarta,nombreCarta, tipoCarta, efectosCarta, this);
        } else {
            System.out.println("El controlador aún no ha sido asignado.");
        }
    }

    public CombateController getController() {
            return controller;
    }

    public void setController(CombateController controller) {
        this.controller = controller;
    }

    public int getIntencionFutura() {
        return intencionFutura;
    }

    public void setIntencionFutura(int intencionFutura) {
        this.intencionFutura = intencionFutura;
    }

    public Intencion getIntencionComponent() {
        return intencionComponent;
    }

    public void setIntencionComponent(Intencion intencionComponent) {
        this.intencionComponent = intencionComponent;
    }
}
