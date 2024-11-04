package org.example.Clases;

import org.example.Clases.Nivel;

import java.util.ArrayList;
import java.util.Iterator;

public class Estanteria {
    private String nombre;
    private int numEstantes = 0;
    private ArrayList<Nivel> niveles;

    public Estanteria(String idEstanteria){
        this.nombre = idEstanteria;
        niveles = new ArrayList<>();
    }

    public Estanteria(String idEstanteria, ArrayList<Nivel> niveles){
        this.nombre = idEstanteria;
        setNumEstantes(niveles);
        this.niveles = niveles;
    }
    public void setIdEstanteria(String idEstanteria){
        this.nombre = idEstanteria;
    }
    public String getIdEstanteria() {
        return nombre;
    }
    public int getNumEstantes() {
        return numEstantes;
    }

    public void setNumEstantes(ArrayList<Nivel> niveles) {
        this.numEstantes = niveles.size();
    }

    public void añadirNivel(Nivel niv){
        niveles.add(niv);
    }

    public void eliminarNivel(String nivel){
        Iterator<Nivel> it = niveles.iterator();
        boolean encontrado = false;
        while(it.hasNext()){
            Nivel nv = it.next();
            String idNivel = nv.getIdNivel();
            if(nv.getIdNivel().equals(nivel)){
                it.remove();
                System.out.println("Nivel eliminado: " + nv);
                encontrado = true;
            }
        }
        if(encontrado == false){
            System.out.println("El id del Nivel no se encuentra en la lista");
        }
    }

    @Override
    public String toString(){
        String phrase = "IdEstanteria: " + getIdEstanteria().toUpperCase() + "\nNumero de estantes: " + getNumEstantes() + "\n\n≏≏≏≏≏≏≏≏≏Niveles≏≏≏≏≏≏≏≏≏\n\n";
        for (Nivel nv :niveles){
            phrase = phrase + nv +"\n";
        }
        return phrase + "≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏≏";
    }
}
