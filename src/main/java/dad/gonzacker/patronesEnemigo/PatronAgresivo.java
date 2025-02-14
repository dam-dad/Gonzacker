package dad.gonzacker.patronesEnemigo;

import dad.gonzacker.components.EnemyEntity;
import dad.gonzacker.components.UserEntity;

import java.util.Random;

public class PatronAgresivo implements EnemyPattern {
    @Override
    public void ejecutarPatron(EnemyEntity enemigo, UserEntity usuario, int numero) {
        System.out.println("Numero del patron: " + numero);
        switch (numero) {
            case 1:
            case 2:
            case 3: // Aumenta la probabilidad de ataque
                System.out.println("El enemigo realiza un ataque:" + 4);
                enemigo.atacar(usuario, 4); // Hace más daño
                break;
            case 4:
                System.out.println("El enemigo se cura:" + 2);
                enemigo.curarse(2);
                break;
            case 5:
            case 6:
                System.out.println("El enemigo se fortalece:" + 3);
                enemigo.fortalecer(3);
                break;
            default:
                System.out.println("Número fuera de rango.");
        }
    }
}
