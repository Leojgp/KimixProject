package org.example.NotificacionesPackage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent; // Asegúrate de importar desde javafx.event
import org.example.Misc.Stages;

public class NotificacionesController {


    public void initialize(){

    }

    @FXML
    public Label titulo;

    @FXML
    public Label texto;

    @FXML
    public ImageView icono;


    public void cerrar(ActionEvent event) {
        Stages.notificacionStage.close();
    }
}
