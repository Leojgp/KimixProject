package org.example.Misc;

import java.io.File;
import java.io.IOException;

public class Csvs {

    private static final File auxiliares = new File("src/main/java/org/example/Sources/CSV/auxiliares.csv");
    private static final File materiales = new File("src/main/java/org/example/Sources/CSV/materiales.csv");
    private static final File reactivos = new File("src/main/java/org/example/Sources/CSV/reactivos.csv");
    private static final String username = System.getProperty("user.name");
    private static final String rutaDescargas = "C:\\Users\\" + username + "\\Downloads\\";



    public static boolean descargar(String tipo){

        boolean correct = false;
        tipo = tipo.toLowerCase();
        //Usamos el proccess builder para ejecutar el comando
        ProcessBuilder builder = new ProcessBuilder();
        if (tipo.matches(".*reac.*")){
            builder.command("cmd.exe","/c","copy",reactivos.getAbsolutePath(),rutaDescargas + "reactivos" + generarRandom() + ".csv");
            try {
                Process proceso = builder.start();
                proceso.waitFor();
                correct = true;
            } catch (IOException e) {
                if (Values.log){
                    System.out.println("Error en la descarga.");
                }
            } catch (InterruptedException e) {
                if (Values.log){
                    System.out.println("Proceso de copiado interrumpido");
                }
            }
        } else if (tipo.matches(".*auxi.*")) {
            builder.command("cmd.exe","/c","copy",auxiliares.getAbsolutePath(),rutaDescargas  + "auxiliares" + generarRandom() + ".csv");
            try {
                Process proceso = builder.start();
                proceso.waitFor();
                correct = true;
            } catch (IOException e) {
                if (Values.log){
                    System.out.println("Error en la descarga.");
                }
            } catch (InterruptedException e) {
                if (Values.log){
                    System.out.println("Proceso de copiado interrumpido");
                }
            }

        }else {
            builder.command("cmd.exe","/c","copy",materiales.getAbsolutePath(),rutaDescargas  + "materiales" + generarRandom() + ".csv");
            try {
                Process proceso = builder.start();
                proceso.waitFor();
                correct = true;
            } catch (IOException e) {
                if (Values.log){
                    System.out.println("Error en la descarga.");
                }
            } catch (InterruptedException e) {
                if (Values.log){
                    System.out.println("Proceso de copiado interrumpido");
                }
            }

        }
        return correct;
    }

    //Este método genera un numero aleatorio para concatenar al archivo. Esto hace que se puedan descargar dos
    // plantillas del mismo tipo , ya que tienen diferente nombre.
    private static String generarRandom(){
        double rnd = Math.random() * 10000;
        int dev = (int) rnd;
        return String.valueOf(dev);
    }

}
