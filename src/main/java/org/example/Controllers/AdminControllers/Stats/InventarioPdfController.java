package org.example.Controllers.AdminControllers.Stats;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.Misc.Auxiliar;
import org.example.Misc.Stages;
import org.example.Misc.Values;
import org.example.NotificacionesPackage.Notificaciones;
import org.example.PDF.PdfManager;

import javax.swing.*;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InventarioPdfController {


    private File selectedDirectory = null;
    @FXML
    private ChoiceBox tipos;

    public void initialize(){
        tipos.getItems().addAll("REACTIVOS","MATERIALES","AUXILIARES");
        tipos.setValue("REACTIVOS");
    }


    public void generarPDF(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null){
            Values.tipos tipo = Auxiliar.bdProductTranslator(tipos.getValue().toString());
            ResultSet resultSet = Values.my.inventarioPDF(tipo);
            ArrayList<String[]> informe = PdfManager.resultSetConverter(resultSet);
            if (informe.size() >= 2){
                boolean success = PdfManager.inventarioPDF
                        (informe,selectedDirectory.getAbsolutePath() + "/Inventario" + "-" + tipo.toString() + "-" + generarRandom() + ".pdf",tipo);
                if (success){
                    Stages.operacionStages(Stages.operacion.OCULTAR);
                    JOptionPane.showMessageDialog(null,"Se ha creado el pdf correctamente.","PDF CREADO CORRECTAMENTE",JOptionPane.INFORMATION_MESSAGE);
                    Stages.pdfInventarioStage.close();
                    Stages.operacionStages(Stages.operacion.MOSTRAR);

                }else {
                    Stages.operacionStages(Stages.operacion.OCULTAR);
                    JOptionPane.showMessageDialog(null," No se ha creado el pdf. Revisa la ruta.","ERROR",JOptionPane.ERROR_MESSAGE);
                    Stages.operacionStages(Stages.operacion.MOSTRAR);

                }

            }else {
                Stages.operacionStages(Stages.operacion.OCULTAR);
                JOptionPane.showMessageDialog(null,"No se encontraron productos para crear el informe.","NO HAY PRODUCTOS",JOptionPane.INFORMATION_MESSAGE);
                Stages.pdfInventarioStage.close();
                Stages.operacionStages(Stages.operacion.MOSTRAR);
            }

        }else{
            Stages.operacionStages(Stages.operacion.OCULTAR);
            JOptionPane.showMessageDialog(null,"Por favor, elija una ruta para guardar el archivo.","ERROR EN LA RUTA",JOptionPane.ERROR_MESSAGE);
            Stages.operacionStages(Stages.operacion.MOSTRAR);
        }

    }

    private static String generarRandom(){
        double rnd = Math.random() * 100;
        int dev = (int) rnd;
        return String.valueOf(dev);
    }


}
