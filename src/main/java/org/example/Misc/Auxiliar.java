package org.example.Misc;

import javafx.stage.Stage;
import org.example.Clases.Admin;

import java.io.File;
import java.util.ArrayList;

public class Auxiliar {
    public static boolean comprobarUsuario() {
        boolean dev = false;
        if (Values.getCurrentUser() instanceof Admin){
            dev = true;
        }
        return dev;
    }
    public static Values.tipos bdProductTranslator(String tipo){
        tipo = tipo.toLowerCase().trim();
        Values.tipos dev;
        if (tipo.matches(".*aux.*")){
            dev = Values.tipos.prodauxiliares;
        }else if (tipo.matches(".*react.*")){
            dev = Values.tipos.reactivos;
        }else{
            dev = Values.tipos.materiales;
        }
        return dev;
    }

    public static String tipoProducto(String tipo){
        if(tipo.equalsIgnoreCase("material")){
            tipo = "Material";
        } else if(tipo.trim().equalsIgnoreCase("ProductoAuxiliar")){
            tipo = "Producto Auxiliar";
        }
        else{
            tipo = "Reactivo";
        }
        return tipo;
    }

    //Esta clase devuelve un arrayList de imagenes según los peligros que contenga el resultado de búsqueda
    //Implementaremos a traves de patrones la devolucion de imagenes para aumentar la flexibilidad del programa.
    //Ejemplo : Si contiene comb añadiremos el pictograma "comburente"
    public static ArrayList<File> pictogramas(String busqueda){
        ArrayList<File> dev = new ArrayList<>();
        busqueda = busqueda.toLowerCase();
        //Comburente
        if (busqueda.matches(".*comb.*")){
            dev.add(new File("src/main/java/org/example/Sources/IMAGENES/Pictogramas/comburente.png"));
        }
        //Carcinógeno
        if (busqueda.matches(".*carci.*")){
            dev.add(new File("src/main/java/org/example/Sources/IMAGENES/Pictogramas/carcinógeno.png"));
        }
        //Corrosivo
        if (busqueda.matches(".*corro.*")){
            dev.add(new File("src/main/java/org/example/Sources/IMAGENES/Pictogramas/corrosivo.png"));
        }
        //Inflamable
        if (busqueda.matches(".*infl.*")){
            dev.add(new File("src/main/java/org/example/Sources/IMAGENES/Pictogramas/inflamable.png"));
        }
        //Irritante nocivo
        if (busqueda.matches(".*irrit.*") || busqueda.matches(".*noci.*")){
            dev.add(new File("src/main/java/org/example/Sources/IMAGENES/Pictogramas/irritante_nocivo.png"));
        }
        //Peligro medio ambiental
        if (busqueda.matches(".*medio.*") || busqueda.matches(".*ambiental.*")){
            dev.add(new File("src/main/java/org/example/Sources/IMAGENES/Pictogramas/peligroMedioA.png"));
        }
        //Tóxico
        if (busqueda.matches(".*toxic.*") || busqueda.matches(".*tóxic.*")){
            dev.add(new File("src/main/java/org/example/Sources/IMAGENES/Pictogramas/toxicidad.png"));
        }
        // Atencion
        if (busqueda.matches(".*gas.*")){
            dev.add(new File("src/main/java/org/example/Sources/IMAGENES/Pictogramas/gases.png"));
        }
        if (busqueda.matches(".*bio.*")){
            dev.add(new File("src/main/java/org/example/Sources/IMAGENES/Pictogramas/riesgoBiologico.png"));
        }
        return dev;
    }


    // CONTROL DE SATURACIÓN DE VENTANAS
    // PARA EVITAR QUE EL USUARIO ABRA MUCHAS VENTANAS DISPONEMOS DE ÉSTE MÉTODO:
    // SI LA VENTANA YA ESTÁ ABIERA SE SUPERPONE A LAS DEMÁS Y NO SE ABRE OTRA NUEVA.

    public static boolean isShowing(Stage stage){
        boolean dev = false;
        if (stage.isShowing()) {
            stage.toFront();
            dev = true;
        }if (stage.isIconified()){
            stage.setIconified(false);
        }
        return dev;
    }

    //Igual que el método anterior, solo que si existe la ventana la cierra,
    // Lo usaremos en el menú contextual
    public static void isShowingClose(Stage stage){
        if (stage.isShowing()){
            stage.close();

        }
    }
}
