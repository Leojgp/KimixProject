package org.example.Clases;

import org.example.Exceptions.CsvException;
import org.example.Exceptions.CsvLineaException;
import org.example.Misc.ValidadorFechas;
import org.example.Misc.Values;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class CsvImporter {
    public static void crearReactivos(String[] partes) throws CsvException {
        try {
            String fecha = partes[0];
            String pureza = partes[1];
            String formato = partes[2];
            String riesgo = partes[3];
            String localizacion = partes[4];
            int stock = Integer.parseInt(partes[6]);
            String ubicacion = partes[5];
            String nombre = partes[7];
            int minStock = Integer.parseInt(partes[8]);
            Values.my.addReactivo(fecha, pureza, formato, riesgo, localizacion, stock, ubicacion, nombre, minStock);
        } catch (NumberFormatException ex) {
            if (Values.log) {
                System.out.println("ERROR PARSING CSV" + ex.getMessage());
            }
            throw new CsvException();
        }

    }

    public static void crearMateriales(String[] partes) throws CsvException {
        try {
            String fechaCompra = partes[0];
            String subcategoria = partes[1];
            String nroSerie = partes[2];
            String descripcion = partes[3];
            String localizacion = partes[4];
            int stock = Integer.parseInt(partes[5]);
            String ubicacion = partes[6];
            String nombre = partes[7];
            int minStock = Integer.parseInt(partes[8]);
            Values.my.addMaterial(fechaCompra, subcategoria, nroSerie, descripcion, localizacion, stock, ubicacion, nombre, minStock);
        } catch (NumberFormatException e) {
            if (Values.log) {
                System.out.println("ERROR IN CSV PARSER: " + e.getMessage());
            }
            throw new CsvException();
        }

    }

    public static void crearAuxiliares(String[] partes) throws CsvException {
        try {
            String formato = partes[0];
            String localizacion = partes[1];
            int stock = Integer.parseInt(partes[2]);
            String ubicacion = partes[3];
            String nombre = partes[4];
            int minStock = Integer.parseInt(partes[5]);
            Values.my.addProductoAuxiliar(formato, localizacion, stock, ubicacion, nombre, minStock);
        } catch (NumberFormatException a) {
            if (Values.log) {
                System.out.println("ERROR IN CSV PARSER: " + a.getMessage());
            }
            throw new CsvException();
        }

    }

    // ESTE MÉTODO COMPRUEBA EL CSV Y LO AÑADE A UN ARRAYLIST EL CUAL SERÁ IMPORTADO A LA BD
    // SI NO PASA LA PRUEBA SE LANZA UNA EXCEPCIÓN
    public static ArrayList<String[]> comprobar(File archivo, Values.tipos tipo) throws CsvLineaException {
        ArrayList<String[]> devolver = new ArrayList<>();
        ArrayList<String> errores = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String line;
            int longitud = 0;

            switch (tipo) {
                case reactivos -> longitud = 9;
                case materiales -> longitud = 9;
                case prodauxiliares -> longitud = 6;
            }
            boolean primeraLinea = false;
            int lineaNumero = 0;

            while ((line = reader.readLine()) != null) {
                lineaNumero++;
                String[] partes = line.split(";");
                if (partes.length == longitud && longitud != 0) {
                    if (primeraLinea) {
                        try {
                            switch (tipo) {
                                case materiales -> {
                                    String fechaCompra = partes[0];
                                    partes[0] = ValidadorFechas.conversorFecha(fechaCompra);
                                    String stock = partes[5];
                                    String minStock = partes[8];
                                    if (isInteger(stock) && isInteger(minStock)) {
                                        devolver.add(partes);
                                    } else {
                                        throw new CsvLineaException("Error en la línea " + lineaNumero);
                                    }
                                }
                                case reactivos -> {
                                    String fecha = partes[0];
                                    partes[0] = ValidadorFechas.conversorFecha(fecha);
                                    String stock = partes[6];
                                    String minStock = partes[8];
                                    if (isInteger(stock) && isInteger(minStock)) {
                                        devolver.add(partes);
                                    } else {
                                        throw new CsvLineaException("Error en la línea " + lineaNumero);
                                    }
                                }
                                case prodauxiliares -> {
                                    String stock = partes[2];
                                    String minStock = partes[5];
                                    if (isInteger(stock) && isInteger(minStock)) {
                                        devolver.add(partes);
                                    } else {
                                        throw new CsvLineaException("Error en la línea " + lineaNumero);
                                    }
                                }
                            }
                        } catch (CsvLineaException excep) {
                            errores.add(excep.getMessage());
                        }
                    }
                    primeraLinea = true;
                } else {
                    errores.add("Error en la línea " + lineaNumero + ": longitud incorrecta");
                }
            }
            reader.close();

        } catch (FileNotFoundException e) {
            if (Values.log) {
                System.out.println("Archivo no encontrado: " + e.getMessage());
            }
            throw new CsvLineaException("Archivo no encontrado: " + e.getMessage());
        } catch (IOException a) {
            if (Values.log) {
                System.out.println("Error al comprobar csv : " + a.getMessage());
            }
            throw new CsvLineaException("Error al comprobar csv: " + a.getMessage());
        }

        if (!errores.isEmpty()) {
            String mostrar = "";
            for (String error : errores) {
               mostrar += error + "\n";
            }
            JOptionPane.showMessageDialog(null, "NO HAN SIDO IMPORTADAS LAS SIGUIENTES LÍNEAS: \n"
                    + mostrar);
            JOptionPane.showMessageDialog(null, "COMPRUEBE LAS FECHAS, EL STOCK DEBE SER NUMÉRICO");

        }

        return devolver;
    }


    private static boolean isInteger(String integer) {
        boolean correct = false;
        try {
            Integer.parseInt(integer);
            correct = true;
        } catch (NumberFormatException a) {
            if (Values.log) {
                System.out.println("No se puede parsear el integer en csv comprobar: " + a.getMessage());
            }
        }

        return correct;
    }

}
