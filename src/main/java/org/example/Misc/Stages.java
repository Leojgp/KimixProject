package org.example.Misc;

import javafx.stage.Stage;

import java.lang.reflect.Field;

public class Stages {
    public enum operacion {
        OCULTAR, MOSTRAR
    }

    // STAGES
    public static Stage product_Stage = new Stage();
    public static Stage deleteProduct_Stage = new Stage();
    public static Stage changeStockStage = new Stage();
    public static Stage recordatorio_stageAdd = new Stage();
    public static Stage recordatorio_stageDel = new Stage();
    public static Stage csvImportStage = new Stage();
    public static Stage moverStage = new Stage();
    public static Stage admin_stage = new Stage();
    public static Stage resultStage = new Stage();
    public static Stage searchStage = new Stage();
    public static Stage statsStage = new Stage();
    public static Stage pdfInventarioStage = new Stage();
    public static Stage notificacionStage = new Stage();
    public static Stage gestorComprasStage = new Stage();


    public static void operacionStages(operacion op) {
        Class<?> clase = Stages.class;
        Field[] campos = clase.getDeclaredFields();
        switch (op){
            case MOSTRAR -> mostrarStages(campos);
            case OCULTAR -> ocultarStages(campos);
        }

    }

    private static void ocultarStages(Field[] fields) {
        try {
            for (Field field : fields) {
                Stage stage = (Stage) field.get(null);
                if (stage != null){
                    stage.setIconified(true);
                }
            }
        } catch (IllegalAccessException e) {
            if (Values.log){
                System.out.println("ERROR AL OCULTAR EL STAGE: " + e.getMessage());
            }
        }
    }

    private static void mostrarStages(Field[] fields) {
        try {
            for (Field field : fields) {
                Stage stage = (Stage) field.get(null);
                if (stage != null){
                    stage.setIconified(false);
                }
            }
        } catch (IllegalAccessException e) {
            if (Values.log){
                System.out.println("ERROR AL MOSTRAR EL STAGE: " +  e.getMessage());
            }
        }
    }


}
