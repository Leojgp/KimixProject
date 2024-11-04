package org.example.Clases;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Producto  {

    //Hacemos una variable estática para llevar el conteo de todos los productos creados
    private static int contadorProductos = 0;
    private int codigo;
    private int stock;
    private String nombre;
    public Producto(int stock, String nombre){
        setStock(stock);
        this.nombre = nombre;
        codigo = ++contadorProductos;
    }

    public int getCodigo(){
        return codigo;
    }
    public void setStock(int stock){
        if(stock>0) {
            this.stock = stock;
        }
        else{
            this.stock = 0;
        }
    }
    public int getStock(){
        return stock;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getNombre(){
        return nombre;
    }

    @Override
    public String toString(){
         return "Codigo: " + codigo + "\nStock: " + stock + "\nNombre: " + nombre + "\n~~~~~~~~~~~~~~~";
    }
}
