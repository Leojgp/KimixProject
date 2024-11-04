package org.example.Clases;

import org.example.Clases.Producto;

import java.util.ArrayList;
import java.util.Iterator;

public class Nivel {
    private String idNivel;
    private ArrayList<Producto> productos;

    public Nivel(String idNivel){
        this.idNivel = idNivel;
        productos = new ArrayList<>();
    }

    public Nivel(String idNivel, ArrayList<Producto> productos){
        this.idNivel = idNivel;
        this.productos = productos;
    }
    public void setIdNivel(String idNivel){
        this.idNivel = idNivel;
    }
    public String getIdNivel(){
        return idNivel;
    }

    public void añadirProducto(Producto pro){
        productos.add(pro);
    }

    public void eliminarProducto(int prod){
        Iterator<Producto> it = productos.iterator();
        boolean encontrado = false;
        while (it.hasNext()) {
            Producto p = it.next();
            if (p.getCodigo() == prod) {
                it.remove();
                System.out.println("Producto eliminado: " + prod);
                encontrado = true;
            }
        }
        if(encontrado == false){
            System.out.println("El codigo del producto no se encuentra en la lista");
        }
    }

    @Override
    public String toString(){
        String phrase = "+---------------+\n   IdNivel: " +   idNivel.toUpperCase() + "   \n+---------------+\n~~ Productos ~~\n";
        for (Producto pr :productos){
            phrase = phrase + pr +"\n";
        }
        return phrase;
    }

}
