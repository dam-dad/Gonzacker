package dad.gonzacker.components;

import dad.gonzacker.patronesEnemigo.EnemyPattern;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;


import java.util.Random;

public class EnemyEntity extends Entity {

    private UserEntity usuario;
    private EnemyPattern patron;

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

    // Metodo para reducir la vida de la entidad
    public void reducirVida(double cantidad) {

        double nuevoEscudo = escudoActual.get();
        if (nuevoEscudo > 0){
            double cantidadTemporal = cantidad-nuevoEscudo;

            if (cantidadTemporal < 0){
                cantidadTemporal = 0;
            }

            nuevoEscudo -= cantidad;
            cantidad = cantidadTemporal;

            if (nuevoEscudo < 0) {
                nuevoEscudo = 0;
            }

            escudoActual.set(nuevoEscudo);
        }

        if (cantidad > 0) {
            double nuevaVida = vidaActual.get() - cantidad;
            if (nuevaVida < 0) nuevaVida = 0;
            vidaActual.set(nuevaVida);
            System.out.println("Vida de la entidad: " + nuevaVida);
        }
    }






    private void efectosCartas() {
        setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();

            if (dragboard.hasString()) {
                String data = dragboard.getString();
                String[] efectos = data.split(",");

                for (String efecto : efectos) {
                    if (efecto.startsWith("ataque:")) {
                        String[] parts = efecto.split(":");
                        int daño = Integer.parseInt(parts[1]);
                        System.out.println("Carta de ataque soltada con daño: " + daño);
                        reducirVida(daño);
                    }

                    //                else if (efecto.startsWith("curacion:")) {
                    //                    String[] parts = efecto.split(":");
                    //                    int curacion = Integer.parseInt(parts[1]);
                    //                    System.out.println("Carta de curación soltada con curación: " + curacion);
                    //                    aumentarVida(curacion);
                    //                }
                    //
                    //                else if (efecto.startsWith("escudo:")) {
                    //                    String[] parts = efecto.split(":");
                    //                    int escudo = Integer.parseInt(parts[1]);
                    //                    System.out.println("Carta de escudo soltada con valor: " + escudo);
                    //                    aumentarEscudo(escudo);
                    //                }

                    else {
                        System.out.println("Efecto desconocido: " + data);
                    }
                }
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }

}
