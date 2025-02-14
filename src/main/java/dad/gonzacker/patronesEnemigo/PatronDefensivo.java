package dad.gonzacker.patronesEnemigo;

import dad.gonzacker.components.EnemyEntity;
import dad.gonzacker.components.UserEntity;

import java.util.Random;

public class PatronDefensivo implements EnemyPattern {
    @Override
    public void ejecutarPatron(EnemyEntity enemigo, UserEntity usuario, int numero) {
        System.out.println("Número del patron: " + numero);
        switch (numero) {
            case 1:
                System.out.println("El enemigo se cura:" + 4);
                enemigo.curarse(4);
                break;
            case 2:
            case 3:
                System.out.println("El enemigo se fortalece:" + 3);
                enemigo.fortalecer(3);
                break;
            case 4:
            case 5:
            case 6:
                System.out.println("El enemigo realiza un ataque:" + 2);
                enemigo.atacar(usuario, 2);
                break;
            default:
                System.out.println("Número fuera de rango.");
        }
    }
}
