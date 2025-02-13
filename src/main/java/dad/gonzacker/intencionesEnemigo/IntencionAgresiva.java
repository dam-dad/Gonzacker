package dad.gonzacker.intencionesEnemigo;

import dad.gonzacker.components.EnemyEntity;
import dad.gonzacker.components.UserEntity;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class IntencionAgresiva implements EnemyIntention{
    @Override
    public void iniciarIntencion(EnemyEntity enemigo) {

        Image imgAtaque = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/intenciones/Ataque.png")));
        Image imgDefensa = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/intenciones/Escudo.png")));
        Image imgCura = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/intenciones/Curar.png")));

        Random random = new Random();
        int numero = random.nextInt(6) + 1; // Número aleatorio entre 1 y 6

        System.out.println("Número de intencion: " + numero);

        enemigo.setIntencionFutura(numero);

        switch (numero) {
            case 1:
            case 2:
            case 3: // Aumenta la probabilidad de ataque
                //System.out.println("El enemigo planea atacar:" + 4);
                //enemigo.atacar(usuario, 4);
                enemigo.getIntencionComponent().setImagenIntencion(imgAtaque);
                enemigo.getIntencionComponent().setNumeroIntencionTexto("4");

                break;
            case 4:
                //System.out.println("El enemigo planea curarse agresivamente.");
                //enemigo.curarse(2);
                enemigo.getIntencionComponent().setImagenIntencion(imgCura);
                enemigo.getIntencionComponent().setNumeroIntencionTexto("2");
                break;
            case 5:
            case 6:
                //System.out.println("El enemigo planea fortalecerse para el próximo ataque.");
                //enemigo.fortalecer(3);
                enemigo.getIntencionComponent().setImagenIntencion(imgDefensa);
                enemigo.getIntencionComponent().setNumeroIntencionTexto("3");
                break;
            default:
                System.out.println("Número fuera de rango.");
        }
    }
}
