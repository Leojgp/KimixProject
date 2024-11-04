package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.Misc.Auxiliar;
import org.example.Misc.Stages;
import org.example.Misc.Values;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class SearchController {


    @FXML
    public void initialize() {
        filtro1.getItems().addAll("SALA", "NOMBRE");
        filtro1.setValue("NOMBRE");
        filtro2.getItems().addAll("MATERIAL", "AUXILIAR", "REACTIVO");
        filtro2.setValue("REACTIVO");
        filtro1.setMinSize(10, 10);
        buscar.setMinSize(10, 10);
        if (!Auxiliar.comprobarUsuario()) {
            admin.setVisible(false);
        }
    }


    @FXML
    private ChoiceBox filtro1;
    //Filtro 2 : Tipo producto
    @FXML
    private ChoiceBox filtro2;
    @FXML
    private TextField buscador;

    @FXML
    private Label errorText;

    @FXML
    private Button buscar;

    @FXML
    private MenuItem admin;


    private Stage webStage = new Stage();

    @FXML
    public void search() {
        String getFiltro1 = "";
        String getFiltro2 = "";
        ArrayList<String[]> resultado = new ArrayList<>();
        try {
            getFiltro1 = filtro1.getValue().toString().toLowerCase();
            getFiltro2 = filtro2.getValue().toString().toLowerCase();
        } catch (RuntimeException e) {
            errorText.setText("Por favor, seleccione los filtros de búsqueda.");
        }

        String searchText = buscador.getText();
        Values.tipos nombreTabla;
        String parametro = "";
        //Transformamos el filtro en los nombres de la base de datos.
        nombreTabla = Auxiliar.bdProductTranslator(getFiltro2);
        switch (getFiltro1) {
            case "sala":
                parametro = "Localizacion";
                break;
            case "nombre":
                parametro = "Nombre";
                break;
        }
        if (!getFiltro1.isEmpty() && !getFiltro2.isEmpty()) {

            resultado = Values.my.ejecutaSelect(parametro, nombreTabla.toString(), searchText);

        }

        if (!resultado.isEmpty() && !Auxiliar.isShowing(Stages.resultStage)) {
            try {
                ResultsController.searchController = this;
                errorText.setText("");
                ResultsController.setResultados(resultado);
                ResultsController.setTipo(nombreTabla);
                File file = new File("src\\main\\java\\org\\example\\Sources\\resultados.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.resultStage.setScene(new Scene(root));
                Stages.resultStage.setResizable(false);
                Stages.resultStage.setTitle("RESULTADOS DE BÚSQUEDA");
                Stages.resultStage.show();
            } catch (IOException a) {
                if (Values.log) {
                    System.out.println("ERROR IN SEARCH CONTROLLER: " + a.getMessage());
                }
            }
        } else {
            errorText.setText("No se encontró ningún producto.");
        }

    }

    @FXML
    public void showAdminPanel() {
        if (!Auxiliar.isShowing(Stages.admin_stage)) {
            try {
                Stages.operacionStages(Stages.operacion.OCULTAR);
                Values.my.selectRecordatorios(true);
                Stages.operacionStages(Stages.operacion.MOSTRAR);
                File file = new File("src\\main\\java\\org\\example\\Sources\\admin_panel.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.admin_stage.setScene(new Scene(root));
                //Aquí utilizo una lambda para manejar el evento, como hay un hilo corriendo refrescando el log de
                //los recordatorios en el admin panel .
                Stages.admin_stage.setOnCloseRequest(windowEvent -> {
                    Stages.admin_stage.hide();
                });
                Stages.admin_stage.setResizable(false);
                Stages.admin_stage.setTitle("PANEL DE ADMINISTRACIÓN DE KIMIX");
                Stages.admin_stage.show();
                Stages.admin_stage.toFront();
            } catch (IOException e) {
                if (Values.log) {
                    System.out.println("ERROR EN LOGIN CONTROLLER: " + e.getMessage());
                }
            }

        }
    }


    @FXML
    public void creditos() {
        try {
            File file = new File("src\\main\\java\\org\\example\\Sources\\creditos.fxml");
            FXMLLoader loader = new FXMLLoader(file.toURL());
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("KIMIX DESARROLLADO PARA EL IES ZAIDÍN VERGELES");
            stage.show();
        } catch (IOException a) {
            if (Values.log) {
                System.out.println("ERROR EN WEB LOADER: " + a.getMessage());
            }
        }
    }

}
