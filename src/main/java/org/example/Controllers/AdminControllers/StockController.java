package org.example.Controllers.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Controllers.ResultsController;
import org.example.Misc.Auxiliar;
import org.example.Misc.Stages;
import org.example.Misc.Values;

import javax.swing.*;

public class StockController {


    public static String prod = "";


    @FXML
    public void initialize() {
        stock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        stockMin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        prodActual.setText(prod);
    }

    @FXML
    private Label prodActual;
    @FXML
    private Spinner<Integer> stockMin;
    @FXML
    private Spinner<Integer> stock;
    public static int codigo = -1;

    public static Values.tipos tipo;

    @FXML
    public void cambiarStock() {
        boolean success = false;
        int newStock = stock.getValue();
        int newStockMin = stockMin.getValue();

        try {
            Stages.operacionStages(Stages.operacion.OCULTAR);
            String tabla = tipo.toString();
            success = Values.my.changeStock(tabla, newStock, newStockMin, codigo);
            if (success) {
                JOptionPane.showMessageDialog(null, "STOCK CAMBIADO CORRECTAMENTE", "ÉXITO", 1);
            } else {
                JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException a) {
            JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        Stages.changeStockStage.close();
        Stages.operacionStages(Stages.operacion.MOSTRAR);
        ResultsController.refrescarResultados();

    }
}
