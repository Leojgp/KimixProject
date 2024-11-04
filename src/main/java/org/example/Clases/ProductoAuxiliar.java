package org.example.Clases;

import org.example.Clases.Producto;

public class ProductoAuxiliar extends Producto {
    private String formato;
    public ProductoAuxiliar( int stock, String nombre, String formato) {
        super( stock, nombre);
        this.formato = formato;
    }
    public String getFormato() {
        return formato;
    }
    public void setFormato(String formato) {
        this.formato = formato;
    }

}
