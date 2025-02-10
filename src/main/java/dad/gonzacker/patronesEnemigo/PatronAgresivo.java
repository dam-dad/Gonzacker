package dad.gonzacker.patronesEnemigo;

import dad.gonzacker.components.EnemyEntity;
import dad.gonzacker.components.UserEntity;

import java.util.Random;

public class PatronAgresivo implements EnemyPattern {
    @Override
    public void ejecutarPatron(EnemyEntity enemigo, UserEntity usuario) {
        Random random = new Random();
        int numero = random.nextInt(6) + 1; // Número aleatorio entre 1 y 6

        System.out.println("Número generado: " + numero);

        switch (numero) {
            case 1:
            case 2:
            case 3: // Aumenta la probabilidad de ataque
                System.out.println("El enemigo realiza un ataque agresivo.");
                enemigo.atacar(usuario, 4); // Hace más daño
                break;
            case 4:
                System.out.println("El enemigo se cura agresivamente.");
                enemigo.curarse(5);
                break;
            case 5:
            case 6:
                System.out.println("El enemigo se fortalece para el próximo ataque.");
                enemigo.fortalecer(3);
                break;
            default:
                System.out.println("Número fuera de rango.");
        }
    }
}
