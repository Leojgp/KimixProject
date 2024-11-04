package org.example.Misc;
import javafx.concurrent.Task;
import org.example.Controllers.AdminControllers.AdminController;
import org.example.Controllers.GestorController;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdminThread {

    //ESTA CLASE ES UTILIZADA PARA LA ACTUALIZACIÓN CADA X segundos del log del panel de admin.

    private static boolean run = false;

    private static AdminController controller;

    //He usado Task en lugar de Thread ya que daba conflicto.
    //Uso una lambda para implementar el método call.
    private static Task<Void> task = new Task() {
        @Override
        protected Object call() throws Exception {
            run = true;
            while (run) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    if (Values.log) {
                        System.out.println("Error in adminPanel thread: " + e.getMessage());
                    }
                }

                //OPTIMIZACIÓN DEL HILO:SI ESTÁ CERRADO O MINIMIZADO EL ADMIN PANEL NO HACE CONSULTAS SQL, LO QUE MEJORA MUCHO EL RENDIMIENTO
                if (!Stages.admin_stage.isIconified() && Stages.admin_stage.isShowing()){
                    ArrayList<String[]> resultados = Values.my.selectRecordatorios(false);
                    StringBuilder builder = new StringBuilder();
                    for (String[] resultado : resultados) {
                        builder.append(resultado[0]);
                        if (resultado[0].contains("RECORDATORIO")) {
                            builder.append("Código: " + resultado[1] + " , ");
                            builder.append("Nombre: " + resultado[2] + " , ");
                            builder.append("Descripción: " + resultado[3] + " , ");
                            builder.append("Fecha: " + resultado[4] + "\n");
                        } else {
                            builder.append("código " + resultado[1] + " , ");
                            builder.append("nombre: " + resultado[2] + "\n");
                        }
                    }
                    controller.setLogText(builder.toString());

                }

            }
            return null;
        }

    };

    public static void setController(AdminController controller) {
        AdminThread.controller = controller;
    }

    public static Task<Void> getTask() {
        return task;
    }

    public static void setRun(boolean run) {
        AdminThread.run = run;
    }

    public static boolean isRun() {
        return run;
    }
}
