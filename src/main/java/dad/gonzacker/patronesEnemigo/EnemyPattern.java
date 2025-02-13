package dad.gonzacker.patronesEnemigo;

import dad.gonzacker.components.EnemyEntity;
import dad.gonzacker.components.UserEntity;

public interface EnemyPattern {
    void ejecutarPatron(EnemyEntity enemigo, UserEntity usuario, int intencion);
}
