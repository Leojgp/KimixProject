package org.example.LoaderFXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Controllers.LoginController;
import org.example.Misc.Values;

import java.io.File;
import java.io.IOException;

public class
Loader extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            if (Values.conectarBD()) {
                File file = new File("src/main/java/org/example/Sources/login.fxml");
                Parent root = FXMLLoader.load(file.toURL());
                primaryStage.setScene(new Scene(root));
                LoginController.setLoginStage(primaryStage);
                primaryStage.setResizable(false);
                primaryStage.setTitle("KIMIX LOGIN");
                primaryStage.show();
            }

        } catch (IOException e) {
            if (Values.log) {
                System.out.println("ERROR IN LOADER: " + e.getMessage());
            }
        }
    }

}