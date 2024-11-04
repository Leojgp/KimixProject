package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.Misc.*;
import org.example.PDF.PdfManager;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GestorController {


    private ArrayList<GestorTableModel> productosStockMin = new ArrayList<>();
    
    public TableView<GestorTableModel> tabla;

    public TableColumn<GestorTableModel,String> nombre;

    public TableColumn<GestorTableModel,String> tipo;
    public TableColumn<GestorTableModel,String> stockActual;
    public TableColumn<GestorTableModel, Integer> cantidad;
    public BarChart<String,Integer> grafica;

    public TableColumn<GestorTableModel,String> formato;

    @FXML
    public void initialize(){
        tabla.setEditable(true);
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        stockActual.setCellValueFactory(new PropertyValueFactory<>("stockActual"));
        cantidad.setCellValueFactory(new PropertyValueFactory<>("spinnerValue"));
        formato.setCellValueFactory(new PropertyValueFactory<>("formato"));


        // Esto de aquí abajo está generado con chatGPT.
        formato.setCellFactory(TextFieldTableCell.forTableColumn());
        formato.setOnEditCommit(event -> {
            GestorTableModel data = event.getRowValue();
            data.formatoProperty().set(event.getNewValue());
        });

        cantidad.setCellFactory(column -> new TableCell<GestorTableModel,Integer>(){
            private final Spinner<Integer> spinner = new Spinner<>(0, 100, 0);

            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setGraphic(null);
                } else {
                    spinner.getValueFactory().setValue(value);
                    spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                        if (newValue != null) {
                            getTableView().getItems().get(getIndex()).setSpinnerValue(newValue);
                        }
                    });
                    setGraphic(spinner);
                }

            }
        });

        InicializarTabla(Values.my.stockMinimoQuery());
    }

    public void generar(ActionEvent actionEvent) {
        ObservableList<GestorTableModel> list = tabla.getItems();
        DirectoryChooser chooser = new DirectoryChooser();
        File directorio = null;
        directorio  = chooser.showDialog(new Stage());
        if (directorio != null){
            boolean success = PdfManager.stockMinPDF(list,directorio);
            if (success){
                Stages.operacionStages(Stages.operacion.OCULTAR);
                JOptionPane.showMessageDialog(null,"PDF CREADO CORRECTAMENTE", "ÉXITO",JOptionPane.INFORMATION_MESSAGE);
                Stages.operacionStages(Stages.operacion.MOSTRAR);
                Stages.gestorComprasStage.toFront();
            }else {
                Stages.operacionStages(Stages.operacion.OCULTAR);
                JOptionPane.showMessageDialog(null,"ERROR AL CREAR EL PDF", "ERROR",JOptionPane.ERROR_MESSAGE);
                Stages.operacionStages(Stages.operacion.MOSTRAR);
                Stages.gestorComprasStage.toFront();
            }
        }else {
            Stages.operacionStages(Stages.operacion.OCULTAR);
            JOptionPane.showMessageDialog(null,"SELECCIONE UNA RUTA VÁLIDA", "ERROR",JOptionPane.ERROR_MESSAGE);
            Stages.operacionStages(Stages.operacion.MOSTRAR);
            Stages.gestorComprasStage.toFront();
        }
    }
    private void InicializarTabla(ArrayList<GestorTableModel> arrayList){
        productosStockMin = arrayList;
        ObservableList<GestorTableModel> observableList = FXCollections.observableArrayList(productosStockMin);
        tabla.setItems(observableList);
        HashMap<Values.tipos,Integer> productos = contarProductos(productosStockMin);

        //Inicializamos la grafica
        grafica.setTitle("Gráfica de productos bajo stock mínimo");
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        for (Values.tipos tipos : productos.keySet()) {
            switch (tipos){
                case reactivos -> {
                    series.getData().add(new XYChart.Data<>("REACTIVOS",productos.get(tipos)));
                }
                case prodauxiliares -> {
                    series.getData().add(new XYChart.Data<>("AUXILIARES",productos.get(tipos)));
                }
                case materiales -> {
                    series.getData().add(new XYChart.Data<>("MATERIALES",productos.get(tipos)));
                }
            }
        }
        grafica.getData().add(series);
        
        
    }

    private HashMap<Values.tipos,Integer> contarProductos(ArrayList<GestorTableModel> arrayList){
        HashMap<Values.tipos,Integer> dev = new HashMap<>();
        int cantidadReactivos = 0;
        int cantidadMateriales = 0;
        int cantidadAuxiliares = 0;
        for (GestorTableModel gestorTableModel : arrayList) {
            Values.tipos tipo = Auxiliar.bdProductTranslator(gestorTableModel.getTipo());
            switch (tipo){
                case materiales -> cantidadMateriales++;
                case prodauxiliares -> cantidadAuxiliares++;
                case reactivos -> cantidadReactivos++;
            }
        }
        dev.put(Values.tipos.reactivos,cantidadReactivos);
        dev.put(Values.tipos.materiales,cantidadMateriales);
        dev.put(Values.tipos.prodauxiliares,cantidadAuxiliares);
        return dev;
    }

}
