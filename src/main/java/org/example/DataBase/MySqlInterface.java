package org.example.DataBase;

import java.util.ArrayList;

public interface MySqlInterface {
    public ArrayList<String[]> ejecutaSelect(String parametroBusqueda, String table, String buscar);
    public boolean login(String username, String password);
    public boolean addMaterial(String fechaCompra, String subcategoria, String nroSerie, String descripcion,
                               String localizacion, int stock, String ubicacion, String nombre, int minStock);

    public boolean addProductoAuxiliar(String formato, String localizacion, int stock,
                                       String ubicacion, String nombre, int minStock);

    public boolean addReactivo(String fecha, String pureza, String formato, String riesgo, String localizacion, int stock
            , String ubicacion, String nombre, int minStock);


}
