package org.example.Controllers.AdminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.example.Misc.AdminThread;
import org.example.Misc.Auxiliar;
import org.example.Misc.Stages;
import org.example.Misc.Values;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminController {


    @FXML
    private Button añadir;


    @FXML
    private Button añadirRec;

    @FXML
    private Button eliminarRec;

    @FXML
    private TextArea recordatorio;


    public static ExecutorService executorService;

    @FXML
    public void initialize() {
        ArrayList<String[]> resultados = Values.my.selectRecordatorios(false);
        // El builder sirve para optimizar grandes cadenas de String, es más eficiente (Información proporcionada por Manel)
        StringBuilder builder = new StringBuilder();
        for (String[] resultado : resultados) {
            // Si el resultado en la posición 0 contiene RECORDATORIO sabemos que el tamaño del Array es de 5
            // y si no lo es, es de 3 (Reactivos, Materiales, Auxiliares)
            builder.append(resultado[0]);
            if (resultado[0].matches(".*RECORDATORIO.*")) {
                builder.append("Id: " + resultado[1] + " , ");
                builder.append("Nombre: " + resultado[2] + " , ");
                builder.append("Descripción: " + resultado[3] + " , ");
                builder.append("Fecha: " + resultado[4] + "\n");
            } else {
                builder.append("código " + resultado[1] + " , ");
                builder.append("nombre: " + resultado[2] + "\n");
            }

        }
        recordatorio.setText(builder.toString());
        AdminThread.setController(this);
        // INICIAMOS EL HILO PARA REFRESCAR CADA X SEG EL LOG DE RECORDATORIOS
        // ESTO LO HAGO POR SI HAY VARIOS PROFESORES ACTUALIZANDO EL STOCK DE LOS PRODUCTOS
        // DE ESTA MANERA MANTENEMOS LA INFORMACIÓN ACTUALIZADA PARA EVITAR CONFLICTOS
        if (!AdminThread.isRun()) {
            executorService = Executors.newSingleThreadExecutor();
            executorService.submit(AdminThread.getTask());
        }

    }

    @FXML
    public void addProduct() {
        try {
            if (!Auxiliar.isShowing(Stages.product_Stage)) {
                File file = new File("src\\main\\java\\org\\example\\Sources\\addProducts.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.product_Stage.setScene(new Scene(root));
                Stages.product_Stage.setResizable(false);
                Stages.product_Stage.setTitle("AÑADIR PRODUCTOS");
                Stages.product_Stage.show();
            }
        } catch (IOException e) {
            if (Values.log) {
                System.out.println("ERROR EN ADMIN CONTROLLER: " + e.getMessage());
            }
        } catch (Exception e) {
            if (Values.log) {
                System.out.println("ERROR IN STARTING searchStage");
            }
        }
    }





    @FXML
    public void addRec() {
        try {
            if (!Auxiliar.isShowing(Stages.recordatorio_stageAdd)) {
                File file = new File("src\\main\\java\\org\\example\\Sources\\recordatorio.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.recordatorio_stageAdd.setScene(new Scene(root));
                Stages.recordatorio_stageAdd.setResizable(false);
                Stages.recordatorio_stageAdd.setTitle("AÑADIR RECORDATORIOS");
                Stages.recordatorio_stageAdd.show();
            }

        } catch (IOException e) {
            if (Values.log) {
                System.out.println("ERROR EN ADMIN CONTROLLER: " + e.getMessage());
            }
        } catch (Exception e) {
            if (Values.log) {
                System.out.println("ERROR IN STARTING searchStage");
            }
        }
    }

    @FXML
    public void removeRec() {
        try {
            if (!Auxiliar.isShowing(Stages.recordatorio_stageDel)) {
                File file = new File("src\\main\\java\\org\\example\\Sources\\eliminarRecordatorio.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.recordatorio_stageDel.setScene(new Scene(root));
                Stages.recordatorio_stageDel.setResizable(false);
                Stages.recordatorio_stageDel.setTitle("ELIMINAR RECORDATORIOS");
                Stages.recordatorio_stageDel.show();
            }


        } catch (IOException e) {
            if (Values.log) {
                System.out.println("ERROR EN ADMIN CONTROLLER: " + e.getMessage());
            }
        } catch (Exception e) {
            if (Values.log) {
                System.out.println("ERROR IN STARTING searchStage");
            }
        }
    }

    @FXML
    public void importadorCSV() {
        try {
            if (!Auxiliar.isShowing(Stages.csvImportStage)) {
                File file = new File("src\\main\\java\\org\\example\\Sources\\csv.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.csvImportStage.setScene(new Scene(root));
                Stages.csvImportStage.setResizable(false);
                Stages.csvImportStage.setTitle("KIMIX EASY CSV");
                Stages.csvImportStage.show();
            }
        } catch (IOException e) {
            if (Values.log) {
                System.out.println("ERROR EN ADMIN CONTROLLER: " + e.getMessage());
            }
        } catch (Exception e) {
            if (Values.log) {
                System.out.println("ERROR IN STARTING csvStage");
            }
        }

    }


    public void setLogText(String text) {
        recordatorio.setText(text);
    }


    public void mostrarStats(ActionEvent actionEvent) {
        try {
            if (!Auxiliar.isShowing(Stages.statsStage)) {
                File file = new File("src\\main\\java\\org\\example\\Sources\\stats.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.statsStage.setScene(new Scene(root));
                Stages.statsStage.setResizable(false);
                Stages.statsStage.setTitle("ESTADÍSTICAS");
                Stages.statsStage.show();
            }
        } catch (IOException e) {
            if (Values.log) {
                System.out.println("ERROR EN ADMIN CONTROLLER: " + e.getMessage());
            }
        } catch (Exception e) {
            if (Values.log) {
                System.out.println("ERROR IN STARTING STATS");
            }
        }
    }

    public void gestorCompras(ActionEvent actionEvent) {
        try {
            if (!Auxiliar.isShowing(Stages.gestorComprasStage)) {
                File file = new File("src\\main\\java\\org\\example\\Sources\\gestor.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.gestorComprasStage.setScene(new Scene(root));
                Stages.gestorComprasStage.setResizable(false);
                Stages.gestorComprasStage.setTitle("GESTOR DE COMPRAS DE KIMIX");
                Stages.gestorComprasStage.show();
            }
        } catch (IOException e) {
            if (Values.log) {
                System.out.println("ERROR EN ADMIN CONTROLLER: " + e.getMessage());
            }
        }





    }

    public void ayuda(ActionEvent actionEvent) {

        String url = "https://www.youtube.com/watch?v=qKqPSrC45r4"; // URL del video de YouTube
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                showAlert("Error", "ERROR AL ABRIR EL NAVEGADOR");
            }
        } else {
            showAlert("Error", "Tu sistema no soporta esta opción.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    }


