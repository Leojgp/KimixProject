package org.example.Misc;

public class ValidadorFechas {
    public static String conversorFecha(String fecha){
        String devolver = "";
        if (fecha != null){
            fecha = fecha.trim();
            //DEL CSV A LA BASE DE DATOS
            // DD/MM/AAAA
            if (fecha.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")){
                String[] split = fecha.split("/");
                devolver = split[2] + "/" + split[1] + "/" + split[0];

                // DD-MM-AAAA
            }else if (fecha.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")){
                String[] split = fecha.split("-");
                devolver = split[2] + "/" + split[1] + "/" + split[0];

                // DE LA BASE DE DATOS A LOS RESULTADOS
            } else if (fecha.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
                String[] split = fecha.split("/");
                devolver = split[2] + "/" + split[1] + "/" + split[0];
            } else if (fecha.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
                String[] split = fecha.split("-");
                devolver = split[2] + "/" + split[1] + "/" + split[0];
            } else if (fecha.matches("[0-9]{2}/[0-9]{2}/[0-9]{2}")) {
                String[] split = fecha.split("/");
                devolver = split[2] + "/" + split[1] + "/" + split[0];
            }else {
                devolver = null;
            }
        }

        return devolver;
    }
}
