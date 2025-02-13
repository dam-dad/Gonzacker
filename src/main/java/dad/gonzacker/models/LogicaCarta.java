package dad.gonzacker.models;

import dad.gonzacker.components.CartaPequeniaComponent;
import dad.gonzacker.components.EnemyEntity;
import dad.gonzacker.components.UserEntity;
import dad.gonzacker.controllers.CombateController;

public class LogicaCarta {


    //Efectos

    public static void roboDeCartas(CombateController controller, int robos) {
        for (int i = 0; i<robos; i++){
            if (controller.getDeck().getDescarte().isEmpty() && controller.getDeck().getCartas().isEmpty()) {
                return;
            }
            Carta carta = controller.getDeck().robarCarta();
            controller.getMano().add(carta);
            CartaPequeniaComponent cartaComponent = new CartaPequeniaComponent(carta);
            controller.getCartasZoneFlowPane().getChildren().add(cartaComponent);
        }
    }

    public static void crearEscudo(UserEntity user, double defensa){
        user.setEscudoActual(user.getEscudoActual() + defensa);
    }

    public static void curarUser(UserEntity user, double cura){
        user.setVidaActual(user.getVidaActual() + cura);
        if (user.getVidaActual() > user.getVidaMaxima()){
            user.setVidaActual(user.getVidaMaxima());
        }
    }

    public static void reducirVida(EnemyEntity enemigo, double cantidad) {

        double nuevoEscudo = enemigo.getEscudoActual();
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

            enemigo.setEscudoActual(nuevoEscudo);
        }

        if (cantidad > 0) {
            double nuevaVida = enemigo.getVidaActual() - cantidad;
            if (nuevaVida < 0) nuevaVida = 0;
            enemigo.setVidaActual(nuevaVida);
            System.out.println("Vida de la entidad: " + nuevaVida);
        }
    }
}
