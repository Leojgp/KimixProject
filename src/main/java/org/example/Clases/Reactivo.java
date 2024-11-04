package org.example.Clases;

import java.time.LocalDateTime;

enum Riesgo{
    COMBURENTE, CORROSIVO, TOXICIDADAGUADA, IRRITANTE, NOCIVO, PELIGROMEDIOAMBIENTE, CARCINOGENO, INFLAMABLE, OTRO
}
public class Reactivo extends Producto {
    private LocalDateTime fechaCaducidad;
    private double pureza;
    private String formato;
    private Riesgo riesgos;
    public Reactivo(int stock, String nombre, LocalDateTime fechaCaducidad, double pureza, String formato, String riesgos) {
        super( stock, nombre);
        this.fechaCaducidad = LocalDateTime.MIN;
        this.pureza = pureza;
        this.formato = formato;
        setRiesgos(riesgos);
    }

    public LocalDateTime getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDateTime fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public double getPureza() {
        return pureza;
    }

    public void setPureza(double pureza) {
        this.pureza = pureza;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
    public Riesgo getRiesgos() {
        return riesgos;
    }

    public void setRiesgos(String riesgos) {
        try {
            this.riesgos = Riesgo.valueOf(riesgos.toUpperCase());
        } catch (IllegalArgumentException ex) {
            this.riesgos = Riesgo.OTRO;
        }
    }

}
