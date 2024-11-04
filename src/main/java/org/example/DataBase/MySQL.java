package org.example.DataBase;

import org.example.Clases.Admin;
import org.example.Clases.Alumno;
import org.example.Controllers.GestorController;
import org.example.Exceptions.CodigoIncorrectoException;
import org.example.Misc.Auxiliar;
import org.example.Misc.GestorTableModel;
import org.example.Misc.ValidadorFechas;
import org.example.Misc.Values;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


//CONFIO EN LOS GORILLAS
public class MySQL implements MySqlInterface {

    //Credenciales de la base de datos

    static final String DB_URL = "jdbc:mysql://localhost/bdquímica";
    static String USER = "root";
    static String PASS = "kimix24";
    static private Connection conex;

    private PreparedStatement stmt;
    private ResultSet rs;
    private ResultSetMetaData meta;


    public boolean conecta() {
        boolean conectar = false;
        try {
            conex = DriverManager.getConnection(DB_URL, USER, PASS);
            JOptionPane.showMessageDialog(null, "Se estableció la conexion");
            conectar = true;
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Error al iniciar SQL");
            System.exit(0);

        } catch (Exception e) {

        }
        return conectar;
    }


    public void cierra_conexion() {
        try {
            if (conex != null) {
                conex.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void cierraSentencia() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    // Ejecuta una Consulta del tipo SELECT....
    // con la que se obtiene un ResultSet

    public ArrayList<String[]> ejecutaSelect(String parametroBusqueda, String table, String buscar) {
        rs = null;
        String buscar1 = "%" + buscar + "%";
        final String sql = "SELECT * FROM " + table + " WHERE " + parametroBusqueda + " LIKE ? ;";
        ArrayList<String[]> devolver = new ArrayList<>();
        try {
            stmt = conex.prepareStatement(sql);
            stmt.setString(1, buscar1);
            rs = stmt.executeQuery();
            if (table.equalsIgnoreCase("reactivos")) {
                while (rs.next()) {
                    String[] array = new String[10];
                    array[0] = rs.getString("CodProd");
                    array[1] = ValidadorFechas.conversorFecha(rs.getString("FechaCaducidad"));
                    array[2] = rs.getString("Pureza");
                    array[3] = rs.getString("Formato");
                    array[4] = rs.getString("Riesgo");
                    array[5] = rs.getString("Localizacion");
                    array[6] = rs.getString("Stock");
                    array[7] = rs.getString("Ubicacion");
                    array[8] = rs.getString("Nombre");
                    array[9] = rs.getString("stockMin");
                    devolver.add(array);

                }
            } else if (table.equalsIgnoreCase("materiales")) {
                while (rs.next()) {
                    String[] array = new String[10];
                    array[0] = rs.getString("CodProd");
                    array[1] = ValidadorFechas.conversorFecha(rs.getString("FechaCompra"));
                    array[2] = rs.getString("Subcategoria");
                    array[3] = rs.getString("NroSerie");
                    array[4] = rs.getString("Descripcion");
                    array[5] = rs.getString("Localizacion");
                    array[6] = rs.getString("Stock");
                    array[7] = rs.getString("Ubicacion");
                    array[8] = rs.getString("Nombre");
                    array[9] = rs.getString("stockMin");
                    devolver.add(array);
                }
            } else {
                while (rs.next()) {
                    String[] array = new String[7];
                    array[0] = rs.getString("CodProd");
                    array[1] = rs.getString("Formato");
                    array[2] = rs.getString("Localizacion");
                    array[3] = rs.getString("Stock");
                    array[4] = rs.getString("Ubicacion");
                    array[5] = rs.getString("Nombre");
                    array[6] = rs.getString("stockMin");
                    devolver.add(array);
                }
            }
        } catch (SQLException e) {
            if (Values.log) {
                System.out.println("ERROR EN LA SENTENCIA SQL: " + e.getMessage());
            }

        }
        return devolver;
    }

    public boolean login(String username, String password) {
        username = username.toLowerCase();
        password = password.toLowerCase();
        boolean dev = true;
        rs = null;
        final String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ? ";
        try {
            stmt = conex.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String user = rs.getString("usuario");
                if (user.equalsIgnoreCase("admin")) {
                    Values.setCurrentUser(new Admin());
                } else if (user.equalsIgnoreCase("alumno")) {
                    Values.setCurrentUser(new Alumno());
                } else {
                    dev = false;
                }
            } else {
                dev = false;
            }
        } catch (SQLException e) {
            if (Values.log) {
                System.out.println("ERROR EN LA CONSULTA SQL : LOGIN");
                System.out.println(e.getMessage());
            }
            dev = false;
        }
        return dev;
    }

    public boolean addMaterial(String fechaCompra, String subcategoria, String nroSerie, String descripcion,
                               String localizacion, int stock, String ubicacion, String nombre, int minStock) {
        boolean dev = false;
        try {
            String query = "INSERT INTO materiales (FechaCompra, Subcategoria, NroSerie, Descripcion, " +
                    "Localizacion, Stock, Ubicacion, Nombre, stockMin) VALUES (?,?,?,?,?,?,?,?,?)";
            stmt = conex.prepareStatement(query);
            stmt.setString(1, fechaCompra);
            stmt.setString(2, subcategoria);
            stmt.setString(3, nroSerie);
            stmt.setString(4, descripcion);
            stmt.setString(5, localizacion);
            stmt.setInt(6, stock);
            stmt.setString(7, ubicacion);
            stmt.setString(8, nombre);
            stmt.setInt(9, minStock);
            stmt.executeUpdate();
            dev = true;
        } catch (SQLException ex) {
            if (Values.log) {
                System.out.println("ERROR EN LA SENTENCIA SQL" + ex.getMessage());
            }
        }
        return dev;
    }

    public boolean addProductoAuxiliar(String formato, String localizacion, int stock, String ubicacion, String nombre, int minStock) {
        boolean dev = false;
        try {
            String query = "INSERT INTO prodauxiliares (Formato, Localizacion, Stock, Ubicacion, Nombre, stockMin) VALUES (?,?,?,?,?,?)";
            stmt = conex.prepareStatement(query);
            stmt.setString(1, formato);
            stmt.setString(2, localizacion);
            stmt.setInt(3, stock);
            stmt.setString(4, ubicacion);
            stmt.setString(5, nombre);
            stmt.setInt(6, minStock);
            stmt.executeUpdate();
            dev = true;
        } catch (SQLException ex) {
            if (Values.log) {
                System.out.println("ERROR EN LA SENTENCIA SQL" + ex.getMessage());
            }
        }
        return dev;
    }

    public boolean addReactivo(String fecha, String pureza, String formato, String riesgo, String localizacion, int stock
            , String ubicacion, String nombre, int minStock) {
        boolean dev = false;
        try {
            String query = "INSERT INTO reactivos (FechaCaducidad, Pureza, Formato, Riesgo, Localizacion, Stock, " +
                    "Ubicacion, Nombre, stockMin) VALUES (?,?,?,?,?,?,?,?,?)";
            stmt = conex.prepareStatement(query);
            stmt.setString(1, fecha);
            stmt.setString(2, pureza);
            stmt.setString(3, formato);
            stmt.setString(4, riesgo);
            stmt.setString(5, localizacion);
            stmt.setInt(6, stock);
            stmt.setString(7, ubicacion);
            stmt.setString(8, nombre);
            stmt.setInt(9, minStock);
            stmt.executeUpdate();
            dev = true;
        } catch (SQLException ex) {
            if (Values.log) {
                System.out.println("ERROR EN LA SENTENCIA SQL" + ex.getMessage());
            }
        }
        return dev;
    }

    public void deleteProduct(int codigoProducto, Values.tipos tipo) throws CodigoIncorrectoException {
        try {
            String query = "DELETE FROM " + tipo.toString() + " WHERE CodProd = ?";
            stmt = conex.prepareStatement(query);
            stmt.setInt(1, codigoProducto);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            if (Values.log) {
                System.out.println("ERROR EN EL CÓDIGO DEL PRODUCTO: " + ex.getMessage());
                throw new CodigoIncorrectoException();
            }
        }
    }

    public void addRecordatorio(String nombre, String descripcion, String fechaCreacion) {
        try {
            String query = "INSERT INTO recordatorios (nombre, descripcion, fecha) VALUES (?,?,?);";
            stmt = conex.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setString(3, fechaCreacion);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha creado correctamente el recordatorio");
        } catch (SQLException ex) {
            if (Values.log) {
                System.out.println("ERROR EN LA SENTENCIA SQL: " + ex.getMessage());
            }
        }
    }

    public void deleteRecordatorio(int codigoRecordatorio) throws CodigoIncorrectoException {
        try {
            String query = "DELETE FROM recordatorios WHERE id = ?";

            stmt = conex.prepareStatement(query);
            stmt.setInt(1, codigoRecordatorio);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente el recordatorio");

        } catch (SQLException ex) {
            if (Values.log) {
                System.out.println("ERROR EN EL CÓDIGO DEL RECORDATORIO: " + ex.getMessage());
                throw new CodigoIncorrectoException();
            }
        }
    }

    public boolean changeStock(String tabla, int stock, int stockMin, int code) {

        //Devuelve un false si falla el cambio de stock
        boolean dev = false;
        try {
            int filasAfectadasStock = 0;
            int filasAfectadasStockMin = 0;
            final String updateStock = "UPDATE " + tabla + " SET Stock = ? WHERE CodProd = ?";
            final String updateStockMin = "UPDATE " + tabla + " SET stockMin = ? WHERE CodProd = ?";
            stmt = conex.prepareStatement(updateStock);
            stmt.setInt(1, stock);
            stmt.setInt(2, code);
            filasAfectadasStock = stmt.executeUpdate();
            stmt = conex.prepareStatement(updateStockMin);
            stmt.setInt(1, stockMin);
            stmt.setInt(2, code);
            filasAfectadasStockMin = stmt.executeUpdate();
            if (filasAfectadasStock > 0 || filasAfectadasStockMin > 0) {
                dev = true;
            }
        } catch (SQLException ex) {
            if (Values.log) {
                System.out.println("Error en change stock sql: " + ex.getMessage());
            }
        }
        return dev;
    }

    public ArrayList<String[]> selectRecordatorios(boolean onlyRecordatorio) {
        rs = null;
        String sql = "SELECT * FROM RECORDATORIOS WHERE fecha <= CURDATE();";
        ArrayList<String[]> devolver = new ArrayList<>();
        try {
            if (onlyRecordatorio) {
                stmt = conex.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String[] array = new String[5];
                    array[0] = "RECORDATORIO: ";
                    array[1] = rs.getString("id");
                    array[2] = rs.getString("nombre");
                    array[3] = rs.getString("descripcion");
                    array[4] = rs.getString("fecha");
                    devolver.add(array);
                    // Los recordatorios se muestran con un JOptionPane al iniciar el panel de admin
                    // No lo hemos puesto en el botón del menú porque daba conflicto con el hilo AdminThread
                    JOptionPane.showMessageDialog(null, array[3], array[0] + array[2], 2);

                }

            } else {
                stmt = conex.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String[] array = new String[5];
                    array[0] = "RECORDATORIO: ";
                    array[1] = rs.getString("id");
                    array[2] = rs.getString("nombre");
                    array[3] = rs.getString("descripcion");
                    array[4] = rs.getString("fecha");
                    devolver.add(array);
                }

                //REACTIVOS
                String sql1 = "SELECT * FROM reactivos WHERE stock <= stockMin;";
                stmt = conex.prepareStatement(sql1);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String[] array = new String[3];
                    array[0] = "REACTIVO CON STOCK MÍNIMO ALCANZADO: ";
                    array[1] = rs.getString("CodProd");
                    array[2] = rs.getString("Nombre");
                    devolver.add(array);
                }


                //MATERIALES
                String sql2 = "SELECT * FROM materiales WHERE stock <= stockMin;";
                stmt = conex.prepareStatement(sql2);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String[] array = new String[3];
                    array[0] = "MATERIAL CON STOCK MÍNIMO ALCANZADO: ";
                    array[1] = rs.getString("CodProd");
                    array[2] = rs.getString("Nombre");
                    devolver.add(array);
                }


                //AUXILIARES
                String sql3 = "SELECT * FROM prodauxiliares WHERE stock <= stockMin;";
                stmt = conex.prepareStatement(sql3);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String[] array = new String[3];
                    array[0] = "AUXILIARES CON STOCK MÍNIMO ALCANZADO: ";
                    array[1] = rs.getString("CodProd");
                    array[2] = rs.getString("Nombre");
                    devolver.add(array);
                }
            }
        } catch (SQLException z) {
            if (Values.log) {
                System.out.println("ERROR EN SQL RECORDATORIO SELECT: " + z.getMessage());
            }
        }
        return devolver;
    }

    public boolean moverProducto(String codigo, String tipo, String localizacion, String ubicacion) {
        boolean correct = false;


        return correct;
    }

    public void moverSentencia(int codigo, String ubicacion, String localizacion, Values.tipos tipo) {
        try {
            String query = "UPDATE " + tipo.toString() + " SET Ubicacion = ?, Localizacion = ? WHERE CodProd = ?;";
            stmt = conex.prepareStatement(query);
            stmt.setString(1, ubicacion);
            stmt.setString(2, localizacion);
            stmt.setInt(3, codigo);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            if (Values.log) {
                System.out.println("ERROR EN LA SENTENCIA SQL: " + ex.getMessage());
            }
        }
    }

    public HashMap<String, Integer> statsProductos() {
        HashMap<String, Integer> dev = new HashMap<>();
        String[] queries = {
                "SELECT COUNT(*) AS num FROM reactivos",
                "SELECT COUNT(*) AS num FROM materiales",
                "SELECT COUNT(*) AS num FROM prodauxiliares"
        };
        String[] llaves = {"REACTIVOS", "MATERIALES", "AUXILIARES"};

        for (int i = 0; i < queries.length; i++) {
            try {
                stmt = conex.prepareStatement(queries[i]);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    dev.put(llaves[i], rs.getInt("num"));
                }

            } catch (SQLException ex) {
                if (Values.log) {
                    System.out.println("ERROR EN LA SENTENCIA SQL: " + ex.getMessage());
                }
            }
        }

        return dev;
    }
    public ResultSet inventarioPDF(Values.tipos tipo){
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + tipo.toString();
            stmt = conex.prepareStatement(query);
            resultSet = stmt.executeQuery();
        }catch (SQLException ex){
            if (Values.log){
                System.out.println("ERROR EN SQL PDF: " + ex.getMessage());
            }
        }
        return resultSet;
    }

    public ArrayList<GestorTableModel> stockMinimoQuery(){
        ArrayList<GestorTableModel> devolver = new ArrayList<>();
            try {
            //REACTIVOS
            String sql1 = "SELECT * FROM reactivos WHERE stock <= stockMin;";
            stmt = conex.prepareStatement(sql1);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("Nombre");
                String tipo = "REACTIVO";
                String stock = rs.getString("Stock");
                GestorTableModel modelo = new GestorTableModel(nombre,tipo,stock);
                devolver.add(modelo);
            }


            //MATERIALES
            String sql2 = "SELECT * FROM materiales WHERE stock <= stockMin;";
            stmt = conex.prepareStatement(sql2);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("Nombre");
                String tipo = "MATERIAL";
                String stock = rs.getString("Stock");
                GestorTableModel modelo = new GestorTableModel(nombre,tipo,stock);
                devolver.add(modelo);
            }


            //AUXILIARES
            String sql3 = "SELECT * FROM prodauxiliares WHERE stock <= stockMin;";
            stmt = conex.prepareStatement(sql3);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("Nombre");
                String tipo = "AUXILIAR";
                String stock = rs.getString("Stock");
                GestorTableModel modelo = new GestorTableModel(nombre,tipo,stock);
                devolver.add(modelo);
            }
        }catch (SQLException ex){
            if (Values.log){
                System.out.println("ERROR EN MYSQL AL SELECCIONAR EL STOCK BAJO MINIMOS: " + ex.getMessage());
            }
        }
        return devolver;
    }





}

