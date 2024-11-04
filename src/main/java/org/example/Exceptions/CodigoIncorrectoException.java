package org.example.Exceptions;

public class CodigoIncorrectoException extends Exception{

    public CodigoIncorrectoException() {
        super("Error en el código del producto.");
    }
}
