package org.example.Exceptions;

public class CsvException extends Exception{
    public CsvException(){
        super("ERROR EN LA LÍNEA AL LEER EL CSV, COMPRUEBA QUE LOS CAMPOS DE STOCK SEAN NUMÉRICOS Y LAS " +
                "FECHAS ESTÉN EN EL FORMATO CORRECTO");
    }
}
