package dad.gonzacker.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Carta> cartas;
    private List<Carta> mano;     // Cartas en la mano del jugador
    private List<Carta> descarte; // Cartas descartadas

    public Deck() {
        this.cartas = new ArrayList<>();
        this.mano = new ArrayList<>();
        this.descarte = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        cartas.add(carta);
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public List<Carta> getMano() {
        return mano;
    }

    public List<Carta> getDescarte() {
        return descarte;
    }

    public Carta robarCarta() {
        if (cartas.isEmpty()) {
            reciclarDescarte();
        }

        if (!cartas.isEmpty()) {
            Carta cartaRobada = cartas.remove(0);
            mano.add(cartaRobada);
            return cartaRobada;
        }

        return null; // No hay cartas disponibles (deck y descarte vacíos)
    }

    public List<Carta> robarCartas(int cantidad) {
        List<Carta> cartasRobadas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            Carta carta = robarCarta();
            if (carta != null) {
                cartasRobadas.add(carta);
            }
        }
        return cartasRobadas;
    }

    public void descartarCarta(Carta carta) {
        if (mano.contains(carta)) {
            mano.remove(carta);
            descarte.add(carta);
        }
    }

    public void reciclarDescarte() {
        if (!descarte.isEmpty()) {
            System.out.println("⚠️ Barajando descarte de nuevo en el mazo...");
            cartas.addAll(descarte);
            descarte.clear();
            barajar();
        }
    }

    public void imprimirEstado() {
        System.out.println("📜 Estado del Mazo:");
        System.out.println("Cartas en mazo: " + cartas.size());
        System.out.println("Cartas en mano: " + mano.size());
        System.out.println("Cartas en descarte: " + descarte.size());
    }
}

