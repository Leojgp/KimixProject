package org.example.Controllers.AdminControllers;

import com.sun.jdi.PrimitiveValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import org.example.Misc.Values;

import javax.swing.*;
import java.text.Format;
import java.util.Date;

public class ProductsController {


    @FXML
    public void initialize() {
        stockActA.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        stockActR.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        stockActM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        stockMinR.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        stockMinM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        stockMinA.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        tipo.getItems().addAll("MATERIAL", "AUXILIAR", "REACTIVOS");
        tipo.setMinSize(10, 10);
        nombreA.setMinSize(140,32);
        nombreM.setMinSize(140,32);
        nombreR.setMinSize(140,32);

    }

    @FXML
    private TextField descripcionM;
    @FXML
    private TextField purezaA;
    @FXML
    private Button añadirA;
    @FXML
    private TextField ubicacionA;
    @FXML
    private TextField localizacionA;
    @FXML
    private Spinner<Integer> stockActA;
    @FXML
    private TextField nombreA;
    @FXML
    private Pane auxiliarPane;
    @FXML
    private DatePicker dateM;
    @FXML
    private Button añadirM;
    @FXML
    private TextField ubicacionM;
    @FXML
    private TextField localizacionM;
    @FXML
    private TextField formatoA;
    @FXML
    private Pane reactivoPane;
    @FXML
    private TextField formatoR;
    @FXML
    private TextField formatoM;
    @FXML
    private DatePicker dateR;
    @FXML
    private Pane materialPane;
    @FXML
    private Button añadirR;
    @FXML
    private Spinner<Integer> stockActR;
    @FXML
    private TextField ubicacionR;
    @FXML
    private Spinner<Integer> stockMinR;
    @FXML
    private Spinner<Integer> stockMinM;
    @FXML
    private Spinner<Integer> stockMinA;
    @FXML
    private javafx.scene.control.TextField nombreR;
    @FXML
    private javafx.scene.control.TextField localizacionR;
    @FXML
    private javafx.scene.control.TextField purezaR;
    @FXML
    private ChoiceBox tipo;
    @FXML
    private TextField riesgoR;
    @FXML
    private Button cambiarTipoButton;
    @FXML
    private javafx.scene.control.TextField nombreM;
    @FXML
    private javafx.scene.control.TextField subcategoríaM;
    @FXML
    private javafx.scene.control.TextField numSerieM;
    @FXML
    private Spinner<Integer> stockActM;


    public void añadirReactivo() {
        String locReact = localizacionR.getText();
        int stockReact = stockActR.getValue();
        String ubiReact = ubicacionR.getText();
        String nombreReact = nombreR.getText();
        // Obtiene la fecha como una cadena o una cadena vacía si no se seleccionó ninguna fecha;
        String fechaReact = "";
        if (dateR.getValue() != null) {
            fechaReact = dateR.getValue().toString();
        }
        String purezaReact = purezaR.getText();
        String formatoReact = formatoR.getText();
        String riesgoReact = riesgoR.getText();
        int minStockReact = stockMinR.getValue();

        if (Values.my.addReactivo(fechaReact, purezaReact, formatoReact, riesgoReact, locReact, stockReact,
                ubiReact, nombreReact, minStockReact)) {
            JOptionPane.showMessageDialog(null, "Producto añadido correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Error al añadir el producto.");
        }
    }

    public void añadirMaterial() {

        String fechaCompraMat = "";
        if (dateM.getValue() != null) {
            fechaCompraMat = dateM.getValue().toString();
        }
        String subcategoriaMat = subcategoríaM.getText();
        String numSerieMat = numSerieM.getText();
        String descripcionMat = descripcionM.getText();
        String locMat = localizacionM.getText();
        int stockMat = stockActM.getValue();
        String ubiMat = ubicacionM.getText();
        String nombreMat = nombreM.getText();
        int minStockMat = stockMinM.getValue();

        if (Values.my.addMaterial(fechaCompraMat, subcategoriaMat, numSerieMat, descripcionMat,
                locMat, stockMat, ubiMat, nombreMat, minStockMat)) {
            JOptionPane.showMessageDialog(null, "Producto añadido correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Error al añadir el producto.");
        }

    }

    public void añadirAuxiliar() {

        String formatoAux = formatoA.getText();
        String localizacionAux = localizacionA.getText();
        int stockAux = stockActA.getValue();
        String ubicacionAux = ubicacionA.getText();
        String nombreAux = nombreA.getText();
        int minStockAux = stockMinA.getValue();

        if (Values.my.addProductoAuxiliar(formatoAux, localizacionAux, stockAux, ubicacionAux, nombreAux, minStockAux)) {
            JOptionPane.showMessageDialog(null, "Producto añadido correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Error al añadir el producto.");
        }
    }

    public void cambiarTipo() {
        String tipo;
        if (this.tipo != null) {
            tipo = this.tipo.getValue().toString();

            if (tipo.equalsIgnoreCase("material")) {
                this.materialPane.setVisible(true);
                this.auxiliarPane.setVisible(false);
                this.reactivoPane.setVisible(false);
            } else if (tipo.equalsIgnoreCase("auxiliar")) {
                this.materialPane.setVisible(false);
                this.auxiliarPane.setVisible(true);
                this.reactivoPane.setVisible(false);
            } else {
                this.materialPane.setVisible(false);
                this.auxiliarPane.setVisible(false);
                this.reactivoPane.setVisible(true);
            }

        }

    }
}
