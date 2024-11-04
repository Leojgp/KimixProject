module KIMIX {
    requires java.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.desktop;
    requires java.sql;
    requires com.gluonhq.charm.glisten;
    requires jdk.jdi;
    requires javafx.web;
    requires java.net.http;
    requires layout;
    requires kernel;
    requires org.slf4j;
    requires io;
    opens org.example.PDF;
    opens org.example.Controllers;
    opens org.example.LoaderFXML;
    opens org.example.Controllers.AdminControllers;
    opens org.example.Misc;
    opens org.example.Controllers.AdminControllers.Recordatorios;
    opens org.example.Controllers.AdminControllers.Stats;

}