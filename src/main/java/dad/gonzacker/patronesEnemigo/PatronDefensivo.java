package dad.gonzacker.patronesEnemigo;

import dad.gonzacker.components.EnemyEntity;
import dad.gonzacker.components.UserEntity;

import java.util.Random;

public class PatronDefensivo implements EnemyPattern {
    @Override
    public void ejecutarPatron(EnemyEntity enemigo, UserEntity usuario) {
        Random random = new Random();
        int numero = random.nextInt(6) + 1;

        System.out.println("Número generado: " + numero);

        switch (numero) {
            case 1:
                System.out.println("El enemigo se cura.");
                enemigo.curarse(4);
                break;
            case 2:
            case 3:
                System.out.println("El enemigo se fortalece.");
                enemigo.fortalecer(3);
                break;
            case 4:
            case 5:
            case 6:
                System.out.println("El enemigo ataca levemente.");
                enemigo.atacar(usuario, 2);
                break;
            default:
                System.out.println("Número fuera de rango.");
        }
    }
}
