package org.example.Misc;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GestorTableModel {

    private StringProperty nombre = new SimpleStringProperty();
    private StringProperty tipo = new SimpleStringProperty();
    private StringProperty stockActual = new SimpleStringProperty();
    private IntegerProperty spinnerValue = new SimpleIntegerProperty();
    private StringProperty formato = new SimpleStringProperty();

    public String getFormato() {
        return formato.get();
    }

    public StringProperty formatoProperty() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato.set(formato);
    }

    public GestorTableModel(String nombre, String tipo, String stockActual){
        this.nombre = new SimpleStringProperty(nombre);
        this.tipo = new SimpleStringProperty(tipo);
        this.stockActual = new SimpleStringProperty(stockActual);
    }

    public int getSpinnerValue() {
        return spinnerValue.get();
    }

    public IntegerProperty spinnerValueProperty() {
        return spinnerValue;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getTipo() {
        return tipo.get();
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public String getStockActual() {
        return stockActual.get();
    }

    public StringProperty stockActualProperty() {
        return stockActual;
    }

    public void setStockActual(String stockActual) {
        this.stockActual.set(stockActual);
    }

    public void setSpinnerValue(int spinnerValue) {
        this.spinnerValue.set(spinnerValue);
    }

}
