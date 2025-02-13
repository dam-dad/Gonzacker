package dad.gonzacker.intencionesEnemigo;

import dad.gonzacker.components.EnemyEntity;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class IntencionDefensiva implements EnemyIntention{
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
                //System.out.println("El enemigo se cura.");
                //enemigo.curarse(4);
                enemigo.getIntencionComponent().setImagenIntencion(imgCura);
                enemigo.getIntencionComponent().setNumeroIntencionTexto("4");
                break;
            case 2:
            case 3:
                //System.out.println("El enemigo se fortalece.");
                //enemigo.fortalecer(3);
                enemigo.getIntencionComponent().setImagenIntencion(imgDefensa);
                enemigo.getIntencionComponent().setNumeroIntencionTexto("3");
                break;
            case 4:
            case 5:
            case 6:
                //System.out.println("El enemigo ataca levemente.");
                //enemigo.atacar(usuario, 2);
                enemigo.getIntencionComponent().setImagenIntencion(imgAtaque);
                enemigo.getIntencionComponent().setNumeroIntencionTexto("2");
                break;
            default:
                System.out.println("Número fuera de rango.");
        }
    }
}
