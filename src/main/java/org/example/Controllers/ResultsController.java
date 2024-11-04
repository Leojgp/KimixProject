package org.example.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.Clases.Admin;
import org.example.Controllers.AdminControllers.MoverController;
import org.example.Controllers.AdminControllers.StockController;
import org.example.Exceptions.CodigoIncorrectoException;
import org.example.Misc.Auxiliar;
import org.example.Misc.Stages;
import org.example.Misc.Values;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ResultsController {
    private static ArrayList<String[]> resultados;

    private static Values.tipos tipo;

    @FXML
    private Pane tipPane;


    public static void setTipo(Values.tipos tipo) {
        ResultsController.tipo = tipo;
    }


    //Reactivos
    @FXML
    private TableColumn<String[], String> nombreR;

    @FXML
    private TableColumn<String[], String> codigoR;

    @FXML
    private TableColumn<String[], String> fechaR;

    @FXML
    private TableColumn<String[], String> purezaR;

    @FXML
    private TableColumn<String[], String> formatoR;

    @FXML
    private TableColumn<String[], String> riesgoR;

    @FXML
    private TableColumn<String[], String> localizacionR;

    @FXML
    private TableColumn<String[], String> stockR;

    @FXML
    private TableColumn<String[], String> stockMinR;


    @FXML
    private TableColumn<String[], String> ubicacionR;


    //Auxiliares
    @FXML
    private TableColumn<String[], String> stockMinA;

    @FXML
    private TableColumn<String[], String> ubicacionA;

    @FXML
    private TableColumn<String[], String> stockA;

    @FXML
    private TableColumn<String[], String> localizacionA;

    @FXML
    private TableColumn<String[], String> formatoA;

    @FXML
    private TableColumn<String[], String> codigoA;

    @FXML
    private TableColumn<String[], String> nombreA;


    //Materiales
    @FXML
    private TableColumn<String[], String> nombreM;

    @FXML
    private TableColumn<String[], String> codigoM;

    @FXML
    private TableColumn<String[], String> fechaM;

    @FXML
    private TableColumn<String[], String> subcategoriaM;

    @FXML
    private TableColumn<String[], String> serieM;

    @FXML
    private TableColumn<String[], String> descripcionM;

    @FXML
    private TableColumn<String[], String> localizacionM;

    @FXML
    private TableColumn<String[], String> stockMinM;

    @FXML
    private TableColumn<String[], String> stockM;

    @FXML
    private TableColumn<String[], String> ubicacionM;


    @FXML
    private Pane auxiliarPane;

    @FXML
    private Pane reactivoPane;

    @FXML
    private Pane materialPane;


    //TABLA REACTIVOS
    @FXML
    private TableView<String[]> tabla;

    //TABLA AUXILIARES
    @FXML
    private TableView<String[]> tabla1;

    //TABLA MATERIALES
    @FXML
    private TableView<String[]> tabla2;

    public static SearchController searchController = new SearchController();
    private ContextMenu contextMenu = new ContextMenu();


    public static void setResultados(ArrayList<String[]> resultados) {
        ResultsController.resultados = resultados;
    }

    @FXML
    public void initialize() {


        tipPane.setVisible(false);
        if (ResultsController.tipo.equals(Values.tipos.reactivos)) {
            this.reactivoPane.setVisible(true);
            this.auxiliarPane.setVisible(false);
            this.materialPane.setVisible(false);
            codigoR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
            fechaR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
            purezaR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
            formatoR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
            riesgoR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
            localizacionR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5]));
            stockR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[6]));
            ubicacionR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[7]));
            nombreR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[8]));
            stockMinR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[9]));
            tabla.getItems().addAll(resultados);
            tipPane.setVisible(true);
            if (Values.getCurrentUser() instanceof Admin){
                contextMenuReactivos();
            }
        } else if (ResultsController.tipo.equals(Values.tipos.materiales)) {
            this.reactivoPane.setVisible(false);
            this.auxiliarPane.setVisible(false);
            this.materialPane.setVisible(true);
            codigoM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
            fechaM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
            subcategoriaM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
            serieM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
            descripcionM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
            localizacionM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5]));
            stockM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[6]));
            ubicacionM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[7]));
            nombreM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[8]));
            stockMinM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[9]));
            tabla2.getItems().addAll(resultados);
            if (Values.getCurrentUser() instanceof Admin){
                contextMenuMateriales();
            }
        } else {
            this.reactivoPane.setVisible(false);
            this.auxiliarPane.setVisible(true);
            this.materialPane.setVisible(false);
            codigoA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
            formatoA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
            localizacionA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
            stockA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
            ubicacionA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
            nombreA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5]));
            stockMinA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[6]));
            tabla1.getItems().addAll(resultados);
            if (Values.getCurrentUser() instanceof Admin){
                contextMenuAuxiliares();
            }
        }

        tabla.setOnMouseClicked(mouseEvent -> {
            // Si es doble click y la fila seleccionada no está vacía:
            if (mouseEvent.getClickCount() == 2 && !tabla.getSelectionModel().isEmpty()) {
                try {
                    String[] rowData = tabla.getSelectionModel().getSelectedItem();
                    String riesgos = rowData[4];
                    ArrayList<File> imagenes = Auxiliar.pictogramas(riesgos);
                    if (!imagenes.isEmpty()) {
                        RiesgosController.setImagenes(imagenes);
                        File file = new File("src/main/java/org/example/Sources/riesgos.fxml");
                        FXMLLoader loader = new FXMLLoader(file.toURL());
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.setTitle("RIESGOS DEL PRODUCTO");
                        stage.show();
                    }
                } catch (IOException a) {
                    if (Values.log) {
                        System.out.println("Error en results stage" + a.getMessage());
                    }
                }


            }
        });

    }

    private void contextMenuReactivos() {
        MenuItem stockItem = new MenuItem("Cambiar Stock");
        MenuItem deleteItem = new MenuItem("Borrar");
        MenuItem moveItem = new MenuItem("Mover");
        stockItem.setOnAction(event -> cambiarStock(Values.tipos.reactivos));
        deleteItem.setOnAction(event -> borrar(Values.tipos.reactivos));
        moveItem.setOnAction(event -> moverProd(Values.tipos.reactivos));
        contextMenu.getItems().addAll(stockItem, deleteItem, moveItem);
        tabla.setContextMenu(contextMenu);
    }

    private void contextMenuMateriales() {
        MenuItem stockItem = new MenuItem("Cambiar Stock");
        MenuItem deleteItem = new MenuItem("Borrar");
        MenuItem moveItem = new MenuItem("Mover");
        stockItem.setOnAction(event -> cambiarStock(Values.tipos.materiales));
        deleteItem.setOnAction(event -> borrar(Values.tipos.materiales));
        moveItem.setOnAction(event -> moverProd(Values.tipos.materiales));
        contextMenu.getItems().addAll(stockItem, deleteItem, moveItem);
        tabla2.setContextMenu(contextMenu);
    }

    private void contextMenuAuxiliares() {
        MenuItem stockItem = new MenuItem("Cambiar Stock");
        MenuItem deleteItem = new MenuItem("Borrar");
        MenuItem moveItem = new MenuItem("Mover");
        stockItem.setOnAction(event -> cambiarStock(Values.tipos.prodauxiliares));
        deleteItem.setOnAction(event -> borrar(Values.tipos.prodauxiliares));
        moveItem.setOnAction(event -> moverProd(Values.tipos.prodauxiliares));
        contextMenu.getItems().addAll(stockItem, deleteItem, moveItem);
        tabla1.setContextMenu(contextMenu);
    }


    private void moverProd(Values.tipos tipo) {
        MoverController.cod = selectedRowCode(tipo);
        MoverController.prod = "PRODUCTO SELECCIONADO: " +  selectedProduct(tipo).toUpperCase();
        MoverController.tipo = tipo;
        if (MoverController.cod >= 0) {
            try {
                Auxiliar.isShowingClose(Stages.moverStage);
                File file = new File("src\\main\\java\\org\\example\\Sources\\mover.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.moverStage.setScene(new Scene(root));
                Stages.moverStage.setResizable(false);
                Stages.moverStage.setTitle("MOVER PRODUCTOS");
                Stages.moverStage.show();
                Stages.moverStage.toFront();
            } catch (IOException e) {
                if (Values.log) {
                    System.out.println("ERROR EN RESULTS CONTROLLER: " + e.getMessage());
                }
            } catch (Exception e) {
                if (Values.log) {
                    System.out.println("ERROR IN STARTING moverStage");
                }
            }
        }
    }

    private void cambiarStock(Values.tipos tipo) {
        StockController.codigo = selectedRowCode(tipo);
        StockController.tipo = tipo;
        StockController.prod = "PRODUCTO SELECCIONADO: " +  selectedProduct(tipo).toUpperCase();
        try {
            Auxiliar.isShowingClose(Stages.changeStockStage);
            File file = new File("src\\main\\java\\org\\example\\Sources\\cambiarStock.fxml");
            FXMLLoader loader = new FXMLLoader(file.toURL());
            Parent root = loader.load();
            Stages.changeStockStage.setScene(new Scene(root));
            Stages.changeStockStage.setResizable(false);
            Stages.changeStockStage.setTitle("STOCK MANAGER");
            Stages.changeStockStage.show();
        } catch (IOException e) {
            if (Values.log) {
                System.out.println("ERROR EN LOGIN CONTROLLER: " + e.getMessage());
            }
        } catch (Exception e) {
            if (Values.log) {
                System.out.println("ERROR IN STARTING searchStage");
            }
        }

    }

    private void borrar(Values.tipos tipo) {
        String name = selectedProduct(tipo);
        int code = selectedRowCode(tipo);
        contextMenu.hide();
        Stages.resultStage.hide();
        Stages.searchStage.hide();
        int opc = JOptionPane.showConfirmDialog(null,"SEGURO QUE QUIERES BORRAR " + name.toUpperCase() + "?" ,"ESTÁS A PUNTO DE ELIMINARLO", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
        if (opc == 0){
            try {
                Values.my.deleteProduct(code,tipo);
                JOptionPane.showMessageDialog(null,name.toUpperCase() + "ELIMINADO CORRECTAMENTE. ");
                Stages.searchStage.show();
                Stages.resultStage.show();
            } catch (CodigoIncorrectoException e) {
                JOptionPane.showMessageDialog(null,"ERROR AL ELIMINAR EL PRODUCTO.", "ERROR", JOptionPane.ERROR_MESSAGE);
                Stages.searchStage.show();
                Stages.resultStage.show();
            }
        }else{
            Stages.searchStage.show();
            Stages.resultStage.show();
        }
        refrescarResultados();
    }

    private int selectedRowCode(Values.tipos tipo) {
        int code = -1;
        //Lo inicializamos
        String[] rowData = new String[11];
        switch (tipo) {
            case materiales -> {
                rowData = tabla2.getSelectionModel().getSelectedItem();
            }
            case reactivos -> {
                rowData = tabla.getSelectionModel().getSelectedItem();
            }
            case prodauxiliares -> {
                rowData = tabla1.getSelectionModel().getSelectedItem();
            }

        }
        try {
            code = Integer.parseInt(rowData[0]);
        } catch (NumberFormatException a) {
            if (Values.log) {
                System.out.println("ERROR EN RESULTS CONTROLLER : " + a.getMessage());
            }
        }

        return code;

    }

    private String selectedProduct(Values.tipos tipo){

            String name = "";
            String[] rowData = new String[11];
            switch (tipo) {
                case materiales -> {
                    rowData = tabla2.getSelectionModel().getSelectedItem();
                    name = rowData[8];
                }
                case reactivos -> {
                    rowData = tabla.getSelectionModel().getSelectedItem();
                    name = rowData[8];
                }
                case prodauxiliares -> {
                    rowData = tabla1.getSelectionModel().getSelectedItem();
                    name = rowData[5];
                }

            }
            return name;
        }

        public static void refrescarResultados(){
            Stages.resultStage.close();
            searchController.search();
        }
    }



        
        

