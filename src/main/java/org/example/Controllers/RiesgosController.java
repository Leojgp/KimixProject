package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.util.ArrayList;


public class RiesgosController {
    @FXML
    private FlowPane flowPane;
    private static ArrayList<File> imagenes = new ArrayList<>();

    @FXML
    public void initialize(){
        ObservableList<ImageView> imageViews = FXCollections.observableArrayList();


        for (File imagen : imagenes) {
            Image image = new Image(imagen.getAbsolutePath());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(160);
            imageView.setFitHeight(160);
            imageViews.add(imageView);
        }

        // Agregar los ImageViews al FlowPane
        flowPane.getChildren().addAll(imageViews);
    }

    public static void setImagenes(ArrayList<File> imagenes) {
        RiesgosController.imagenes = imagenes;
    }
}
