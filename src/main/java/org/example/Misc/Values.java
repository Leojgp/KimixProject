package org.example.Misc;

import org.example.Clases.Persona;
import org.example.DataBase.MySQL;

import java.sql.Connection;

public class Values {
    
    public static enum tipos{materiales,reactivos,prodauxiliares}

    private static boolean userSetted = false;
    public final static boolean log = true;
    public final static String loginTable = "usuarios";
    public static MySQL my = new MySQL();

    private static Persona currentUser ;

    public static Persona getCurrentUser() {
        Persona dev = currentUser;
        return dev;
    }
    public static void setCurrentUser(Persona user){
        if (!userSetted){
            currentUser = user;
        }

        userSetted = true;
    }

    public static boolean conectarBD() {
        return my.conecta();
    }
}
