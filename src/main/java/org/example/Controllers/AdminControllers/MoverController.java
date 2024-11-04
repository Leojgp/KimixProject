package org.example.Controllers.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.Controllers.ResultsController;
import org.example.Misc.Stages;
import org.example.Misc.Values;

import javax.swing.*;

public class MoverController {



    @FXML
    public void initialize() {
        prodActual.setText(prod);
    }

    public static int cod = 0;
    public static Values.tipos tipo;

    public static String prod = "";
    @FXML
    private Label prodActual;

    @FXML
    private TextField ubicacion;
    @FXML
    private TextField localizacion;

    @FXML
    public void cambiar(){
        String ubi = ubicacion.getText();
        String location = localizacion.getText();
        Values.my.moverSentencia(cod,ubi, location, tipo);
        Stages.moverStage.close();
        ResultsController.refrescarResultados();
    }


}
