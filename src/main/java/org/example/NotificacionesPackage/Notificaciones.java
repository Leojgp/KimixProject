package org.example.NotificacionesPackage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.example.Misc.Auxiliar;
import org.example.Misc.Stages;
import org.example.Misc.Values;

public class Notificaciones {
    public enum tipoNotificacion{
        ERROR,EXITO
    }
    private static ImageView icono =  null; //notificacionesController.icono;
    private static Label texto =    null;  //notificacionesController.texto;
    private static Label titulo =   null; //notificacionesController.titulo;

    public static void setNotificacion(String titulo1, String descripcion,tipoNotificacion tipo){
        File file = new File("src\\main\\java\\org\\example\\Sources\\notificacion.fxml");
        //CERRAMOS LA NOTIFICACIÓN ANTIGUA SI ESTUVIERA ABIERTA
        Auxiliar.isShowingClose(Stages.notificacionStage);
        try {
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL());
            System.out.println("0");
            Parent root = loader.load();
            System.out.println("01");
            Stages.notificacionStage.setScene(new Scene(root));
            System.out.println("02");
            Stages.notificacionStage.setResizable(false);
            Stages.notificacionStage.setTitle("NOTIFICACION");
            System.out.println("1");
            if (icono != null && texto != null && titulo != null){
                texto.setText(descripcion);
                titulo.setText(titulo1);
                System.out.println("2");
                switch (tipo){
                    case ERROR -> {
                        texto.setTextFill(Color.RED);
                        icono.setVisible(false);
                    }
                    case EXITO -> {
                        texto.setTextFill(Color.GREEN);
                        icono.setImage(new Image("@IMAGENES/check.png"));
                        icono.setVisible(true);
                    }
                }
                System.out.println("3");
                Stages.notificacionStage.show();
            }

        } catch (IOException e) {
            if (Values.log){
                System.out.println("ERROR EN NOTIFICACIONES: " + e.getMessage());
            }
        }


    }

    public static void setObjects(ImageView icono1, Label texto1 , Label titulo1){
        icono = icono1;
        texto = texto1;
        titulo = titulo1;
    }

}
