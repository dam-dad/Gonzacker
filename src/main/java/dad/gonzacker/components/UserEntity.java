package dad.gonzacker.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;

public class UserEntity extends Entity{

    private IntegerProperty energia = new SimpleIntegerProperty();
    private IntegerProperty energiaMaxima = new SimpleIntegerProperty();

    public UserEntity() {
        super();
        setVidaMaxima(50.0);
        setVidaActual(50.0);
        setEscudoActual(3);
        energia.set(3);
        energiaMaxima.set(3);
    }


    @Override
    protected void extraInitialize() {
        //Futuros metodos
    }

    public IntegerProperty energiaProperty() {
        return energia;
    }

    public int getEnergia() {
        return energia.get();
    }

    public void setEnergia(int energia) {
        this.energia.set(energia);
    }

    public int getEnergiaMaxima() {
        return energiaMaxima.get();
    }

    public IntegerProperty energiaMaximaProperty() {
        return energiaMaxima;
    }

    public void setEnergiaMaxima(int energiaMaxima) {
        this.energiaMaxima.set(energiaMaxima);
    }

    public void gastarEnergia(int cantidad) {
        energia.set(Math.max(0, energia.get() - cantidad));
    }

    public void recuperarEnergia(int cantidad) {
        energia.set(energia.get() + cantidad);
    }

    public void recuperarEnergiaTurno() {
        if (energia.get() < energiaMaxima.get()){
            energia.set(energiaMaxima.get());
        }
    }

    public void recibirDaño(double cantidad){
        double nuevoEscudo = escudoActual.get();
        if (nuevoEscudo > 0){
            double cantidadTemporal = cantidad-nuevoEscudo;

            if (cantidadTemporal < 0){
                cantidadTemporal = 0;
            }

            nuevoEscudo -= cantidad;
            cantidad = cantidadTemporal;

            if (nuevoEscudo < 0) {
                nuevoEscudo = 0;
            }

            escudoActual.set(nuevoEscudo);
        }

        if (cantidad > 0) {
            double nuevaVida = vidaActual.get() - cantidad;
            if (nuevaVida < 0) nuevaVida = 0;
            vidaActual.set(nuevaVida);
            System.out.println("Vida de la entidad: " + nuevaVida);
        }
    }

}
