package org.example.Controllers.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Clases.CsvImporter;
import org.example.Exceptions.CsvException;
import org.example.Exceptions.CsvLineaException;
import org.example.Misc.Auxiliar;
import org.example.Misc.Csvs;
import org.example.Misc.Values;

import javax.swing.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

public class CSVController {

    @FXML
    public void initialize() {
        tipo.getItems().addAll("REACTIVOS", "MATERIALES", "AUXILIARES");

        //FECHA DE MODIFICACION DEL CSV A LA ACTUAL
        String filePath1 = "src/main/java/org/example/Sources/CSV/auxiliares.csv";
        String filePath2 = "src/main/java/org/example/Sources/CSV/materiales.csv";
        String filePath3 = "src/main/java/org/example/Sources/CSV/reactivos.csv";
        Path path1 = Paths.get(filePath1);
        Path path2 = Paths.get(filePath2);
        Path path3 = Paths.get(filePath3);


        FileTime now = FileTime.fromMillis(System.currentTimeMillis());

        try {
            Files.setLastModifiedTime(path1, now);
            Files.setLastModifiedTime(path2, now);
            Files.setLastModifiedTime(path3, now);
        } catch (IOException e) {
        }


    }

    @FXML
    private ChoiceBox tipo;
    @FXML
    private Pane success;
    @FXML
    private Label successText;

    @FXML
    public void importar() {
        try {
            if (tipo != null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos csv", "*.csv"),
                        new FileChooser.ExtensionFilter("Todos los archivos", "*.*"));
                File archivo = fileChooser.showOpenDialog(new Stage());
                if (archivo != null) {
                    ArrayList<String[]> importar = new ArrayList<>();
                    Values.tipos tipoImporte = null;
                    if (archivo.getName().toLowerCase().matches(".*react.*")) {
                        tipoImporte = Values.tipos.reactivos;
                        importar = CsvImporter.comprobar(archivo, tipoImporte);
                    } else if (archivo.getName().toLowerCase().matches(".*mater.*")) {
                        tipoImporte = Values.tipos.materiales;
                        importar = CsvImporter.comprobar(archivo, tipoImporte);
                    } else if (archivo.getName().toLowerCase().matches(".*auxi.*")) {
                        tipoImporte = Values.tipos.prodauxiliares;
                        importar = CsvImporter.comprobar(archivo, tipoImporte);
                    }
                    importarArrayList(importar, tipoImporte);
                    success.setVisible(true);
                    successText.setText("CSV IMPORTADO CORRECTAMENTE");

                } else {
                    JOptionPane.showMessageDialog(null, "Porfavor, seleccione primero el tipo de plantilla.");
                }
            }


        } catch (CsvLineaException e) {
            success.setVisible(true);
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR AL IMPORTAR", 1);
        } catch (CsvException e2) {
            success.setVisible(false);
            JOptionPane.showMessageDialog(null, e2.getMessage(), "ERROR AL IMPORTAR", 1);
        }

    }

    @FXML
    public void descargar() {
        if (Csvs.descargar(tipo.getValue().toString().toLowerCase())) {
            success.setVisible(true);
            successText.setText("GUARDADO EN DESCARGAS");
        }

    }

    private void importarArrayList(ArrayList<String[]> arrayList, Values.tipos tipo) throws CsvException {
        if (tipo != null) {
            for (String[] strings : arrayList) {
                switch (tipo) {
                    case reactivos -> {
                        CsvImporter.crearReactivos(strings);
                    }
                    case materiales -> {
                        CsvImporter.crearMateriales(strings);
                    }
                    case prodauxiliares -> {
                        CsvImporter.crearAuxiliares(strings);
                    }
                }
            }
        }
    }
}




