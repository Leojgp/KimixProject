package org.example.Clases;

import java.util.ArrayList;
import java.util.Iterator;
public class Sala {
    private String idSala;
    private ArrayList<Estanteria> estanterias;
    private static int contadorSala = 0;

    public Sala(String idSala) {
        this.idSala = idSala + contadorSala;
        estanterias = new ArrayList<>();
        contadorSala++;
    }

    public Sala(String idSala, ArrayList<Estanteria> estanterias){
        this.idSala = idSala + contadorSala;
        this.estanterias = estanterias;
        contadorSala++;
    }

    public String getIdSala() {
        return idSala;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public void añadirEstanteria(Estanteria es){
        estanterias.add(es);
    }

    public void eliminarEstanteria(String nombreEstante){
        Iterator<Estanteria> it = estanterias.iterator();
        while(it.hasNext()){
            Estanteria es = it.next();
            String idEstante = es.getIdEstanteria();
            if(idEstante.equals(nombreEstante)){
                it.remove();
            }
        }
    }

    @Override
    public String toString(){
        String phrase = "▁ ▂ ▃ Sala de Química ▃ ▂ ▁ \n\nIdSala: " +   idSala.toUpperCase() + "\n-----Estanterias-----\n";
        for (Estanteria es :estanterias){
            phrase = phrase + es +"\n";
        }
        return phrase;
    }
}
