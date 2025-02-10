package dad.gonzacker.models;

import javafx.scene.image.Image;

import java.util.List;

public class Carta {
    private int coste;
    private String nombre;
    private String tipo;
    private String descripcion;
    private Image imagen;
    private List<String> efectos;

    public Carta(int coste,String nombre ,String tipo, String descripcion, Image imagen, List<String> efectos) {
        this.coste = coste;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.efectos = EfectoCarta.aplicarEfectos(efectos); // Aplicamos la limpieza
    }

    public int getCoste() { return coste; }
    public String getNombre() {
        return nombre;
    }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public Image getImagen() { return imagen; }
    public List<String> getEfectos() { return efectos; }
}
