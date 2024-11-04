package org.example.Controllers.AdminControllers.Stats;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.example.Misc.Auxiliar;
import org.example.Misc.Stages;
import org.example.Misc.Values;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class statsController {


    public Label cantidades;
    @FXML
    private PieChart stats;


    @FXML
    public void initialize(){
        HashMap<String,Integer> estadisticas = new HashMap<>();
        estadisticas = Values.my.statsProductos();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        String cantidades = "CANTIDADES: ";
        for (String producto : estadisticas.keySet()) {
            int numProducto = estadisticas.get(producto);
            cantidades += producto.toUpperCase() + ": " + numProducto + "   ";
            pieChartData.add(new PieChart.Data(producto,numProducto));
        }
        this.cantidades.setText(cantidades);
        stats.setData(pieChartData);
    }

    public void inventarioPdf(ActionEvent actionEvent) {
        try {
            if (!Auxiliar.isShowing(Stages.pdfInventarioStage)){
                File file = new File("src\\main\\java\\org\\example\\Sources\\statsPDF.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.pdfInventarioStage.setScene(new Scene(root));
                Stages.pdfInventarioStage.setResizable(false);
                Stages.pdfInventarioStage.setTitle("GENERAR PDF DEL INVENTARIO");
                Stages.pdfInventarioStage.show();
                Stages.pdfInventarioStage.toFront();
            }
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

    public void prodCaducados(ActionEvent actionEvent) {



    }
}
