package dad.gonzacker.models;

import java.util.List;
import java.util.stream.Collectors;

public class EfectoCarta {

    public static String ataque(int daño) {
        return "ataque:" + daño;
    }

    public static String curacion(int cantidad) {
        return "curacion:" + cantidad;
    }

    public static String escudo(int cantidad) {
        return "escudo:" + cantidad;
    }

    public static String robar(int cantidad) {
        return "robar:" + cantidad;
    }

    public static List<String> aplicarEfectos(List<String> efectos) {
        return efectos.stream().map(String::trim).collect(Collectors.toList());
    }
}