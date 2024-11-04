package org.example.Controllers.AdminControllers.Recordatorios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.Exceptions.CodigoIncorrectoException;
import org.example.Misc.Values;

import javax.swing.*;

public class AddRecordatorio {

    @FXML
    private Button añadirRecordatorioBoton;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField descripcion;
    @FXML
    private TextField nombre;

    @FXML
    public void añadirRecordatorio() {
        try {

            String nombreRec = nombre.getText();
            String desc = descripcion.getText();
            String fcha = "";
            if(fecha.getValue()!=null){
                fcha = fecha.getValue().toString();
            }

            Values.my.addRecordatorio(nombreRec, desc, fcha);

        }catch (NumberFormatException ex1){
            JOptionPane.showMessageDialog(null,"");
        }

    }

}
