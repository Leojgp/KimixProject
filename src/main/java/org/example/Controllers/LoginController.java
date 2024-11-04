package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.Misc.AdminThread;
import org.example.Misc.Stages;
import org.example.Misc.Values;

import java.io.File;
import java.io.IOException;



public class LoginController {



    public LoginController(){

    }
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    public Label errorText;

    private static Stage loginStage = new Stage();

    public static void setLoginStage(Stage loginStage) {
        LoginController.loginStage = loginStage;
    }

    @FXML
    public void loginCheck() {
        String username = user.getText();
        String pass = password.getText();
        if (Values.my.login(username, pass)) {
            try {

                File file = new File("src\\main\\java\\org\\example\\Sources\\KIMIX_BUSQUEDA.fxml");
                FXMLLoader loader = new FXMLLoader(file.toURL());
                Parent root = loader.load();
                Stages.searchStage.setScene(new Scene(root));
                Stages.searchStage.setResizable(false);
                Stages.searchStage.setTitle("KIMIX BÚSQUEDA");
                Stages.searchStage.show();
                Stages.searchStage.setOnCloseRequest(windowEvent -> {
                    AdminThread.setRun(false);
                    AdminThread.getTask().cancel();
                    System.exit(0);
                });
                loginStage.close();

            } catch (IOException e) {
                if (Values.log) {
                    System.out.println("ERROR EN LOGIN CONTROLLER: " + e.getMessage());
                }
            } catch (Exception e) {
                if (Values.log){
                    System.out.println("ERROR IN STARTING searchStage");
                }
            }
        } else {
            // Si el inicio de sesión falla, se limpian los campos de usuario y contraseña
            user.setText("");
            password.setText("");
            errorText.setText("ERROR, USUARIO Y/O CONTRASEÑA INCORRECTOS");

        }
    }


}