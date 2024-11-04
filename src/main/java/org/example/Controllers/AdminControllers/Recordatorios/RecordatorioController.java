package org.example.Controllers.AdminControllers.Recordatorios;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.Exceptions.CodigoIncorrectoException;
import org.example.Misc.Values;

import javax.swing.*;


public class RecordatorioController {

    @FXML
    private TextField idRecordatorio;
    @FXML
    private Button eliminar;

    @FXML
    private TextArea cajaRecordatorios;

    @FXML
    public void eliminarRecordatorio() {
        try {
            int codigoRecordatorio = Integer.parseInt(idRecordatorio.getText());
            Values.my.deleteRecordatorio(codigoRecordatorio);
        }catch (NumberFormatException ex1){
            JOptionPane.showMessageDialog(null,"Código Incorrecto");
        }
        catch (CodigoIncorrectoException a){
            JOptionPane.showMessageDialog(null,a.getMessage(),"Error",JOptionPane.ERROR_MESSAGE,null);
        }

    }

}
