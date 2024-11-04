package org.example.Clases;

import org.example.Clases.Producto;

import java.time.LocalDateTime;

public class Material extends Producto {
    private String descripcion;
    private int numSerie;
    private String subcategoria;
    private LocalDateTime fechaAdquisicion;

    public Material(int stock, String nombre, String descripcion, int numSerie, String subcategoria, LocalDateTime fechaAdquisicion){
        super( stock, nombre);
        this.descripcion = descripcion;
        setNumSerie(numSerie);
        this.subcategoria = subcategoria;
        this.fechaAdquisicion = LocalDateTime.MIN;

    }
    public LocalDateTime getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDateTime fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public int getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(int numSerie) {
        if(numSerie> 0){
        this.numSerie = numSerie;}
        else this.numSerie = 0;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
