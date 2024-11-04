package org.example.PDF;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.collections.ObservableList;
import org.example.Misc.GestorTableModel;
import org.example.Misc.Values;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class PdfManager {

    public static boolean inventarioPDF(ArrayList<String[]> productos, String ruta, Values.tipos tipo) {
        boolean success = true;
        PdfDocument pdfDoc = null;
        Document documento = null;
        try {
            PdfWriter writer = new PdfWriter(ruta);
            pdfDoc = new PdfDocument(writer);
            documento = new Document(pdfDoc);
            FontProvider fontProvider = new FontProvider();
            fontProvider.addFont(StandardFonts.HELVETICA);
            documento.setFontProvider(fontProvider);
            documento.setFontFamily("Helvetica");
            switch (tipo) {
                case reactivos ->
                        documento.add(new Paragraph("REACTIVOS").setTextAlignment(TextAlignment.CENTER).setFontSize(14));
                case prodauxiliares ->
                        documento.add(new Paragraph("PRODUCTOS AUXILIARES").setTextAlignment(TextAlignment.CENTER).setFontSize(14));
                case materiales ->
                        documento.add(new Paragraph("MATERIALES").setTextAlignment(TextAlignment.CENTER).setFontSize(14));
            }

            documento.add(new Paragraph("FECHA DEL INFORME: " +
                    LocalDate.now().toString() + "\n").setTextAlignment(TextAlignment.CENTER).setFontSize(13));

            Table tabla = crearTablaStock(productos);
            documento.add(tabla);

        } catch (IOException | RuntimeException ex) {
            success = false;
        } finally {
            if (documento != null) {
                documento.close();
            }
        }

        return success;
    }

    private static Table crearTablaStock(ArrayList<String[]> productos) throws RuntimeException {


        int numColumnas = productos.get(0).length;
        float[] anchosColumnas = new float[numColumnas - 1];
        Arrays.fill(anchosColumnas, 1);
        Table tabla = new Table(anchosColumnas);

        boolean primero = false;
        for (String header : productos.get(0)) {
            if (primero){
                tabla.addHeaderCell(new Cell().add(new Paragraph(header).setFontSize(8).setBold()));
            }
            primero = true;

        }

        for (int i = 1; i < productos.size(); i++) {
            String[] producto = productos.get(i);
            boolean primero1 = false;
            for (String columna : producto) {
                if (primero1){
                    if (columna == null) {
                        columna = "";
                    }
                    Cell celda = new Cell().add(new Paragraph(columna).setFontSize(6));
                    celda.setKeepTogether(true);
                    tabla.addCell(celda);
                }
                primero1 = true;
            }
        }

        return tabla;
    }

    public static ArrayList<String[]> resultSetConverter(ResultSet rs) {
        ArrayList<String[]> dev = new ArrayList<>();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numCol = metaData.getColumnCount();
            String[] nombreCol = new String[numCol];
            for (int i = 1; i <= numCol; i++) {
                if (i != 1) {
                    nombreCol[i - 1] = metaData.getColumnName(i);
                }

            }
            dev.add(nombreCol);

            while (rs.next()) {
                String[] addRow = new String[numCol];
                for (int i = 1; i <= numCol; i++) {
                    String celda = rs.getString(i);
                    if (celda != null) {
                        if (celda.equalsIgnoreCase("0000-00-00")) {
                            addRow[i - 1] = "No está reflejada";
                        } else {
                            addRow[i - 1] = celda;
                        }
                    }

                }
                dev.add(addRow);
            }
        } catch (SQLException ex) {
            if (Values.log) {
                System.out.println("ERROR EN RESULTSET CONVERTER (PDF MANAGER): " + ex.getMessage());
            }
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException ex) {
                if (Values.log) {
                    System.out.println("ERROR AL CERRAR RESULTSET (PDF MANAGER): " + ex.getMessage());
                }
            }
        }
        return dev;
    }


    public static boolean stockMinPDF(ObservableList<GestorTableModel> productosModeloTabla, File directorio) {
        boolean success = true;
        PdfDocument pdfDoc = null;
        Document documento = null;
        try {
            PdfWriter writer = new PdfWriter(directorio.getAbsolutePath() + "/ProductosNecesitados" + generarRandom() + ".pdf");
            pdfDoc = new PdfDocument(writer);
            documento = new Document(pdfDoc);
            FontProvider fontProvider = new FontProvider();
            fontProvider.addFont(StandardFonts.HELVETICA);
            documento.setFontProvider(fontProvider);
            documento.setFontFamily("Helvetica");
            documento.add(new Paragraph("PRODUCTOS NECESITADOS").setTextAlignment(TextAlignment.CENTER).setFontSize(23));

            documento.add(new Paragraph("FECHA DEL INFORME: " +
                    LocalDate.now().toString() + "\n\n\n\n\n").setTextAlignment(TextAlignment.CENTER).setFontSize(19));


            Table tabla = crearTablaListaCompra(productosModeloTabla);
            tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
            documento.add(tabla);

        } catch (IOException | RuntimeException ex) {
            success = false;
        } finally {
            if (documento != null) {
                documento.close();
            }
        }


        return success;
    }

    private static Table crearTablaListaCompra(ObservableList<GestorTableModel> productosModelo) {
        Table tabla = new Table(4);
        tabla.addHeaderCell(new Cell().add(new Paragraph("NOMBRE").setFontSize(15).setBold()));
        tabla.addHeaderCell(new Cell().add(new Paragraph("TIPO").setFontSize(15).setBold()));
        tabla.addHeaderCell(new Cell().add(new Paragraph("CANTIDAD").setFontSize(15).setBold()));
        tabla.addHeaderCell(new Cell().add(new Paragraph("FORMATO").setFontSize(15).setBold()));

        for (GestorTableModel gestorTableModel : productosModelo) {
            String nombre = "";
            String tipo = "";
            String cantidad = "";
            String formato = "";
            try {
                nombre = gestorTableModel.getNombre();
                tipo = gestorTableModel.getTipo();
                cantidad = Integer.toString(gestorTableModel.getSpinnerValue());
                formato = gestorTableModel.getFormato();
            }catch (NullPointerException a){
                // No hago nada ya que formato puede quedar sin rellenarse en una fila.
            }
            Cell celda1 = new Cell().add(new Paragraph(nombre).setFontSize(12).setHorizontalAlignment(HorizontalAlignment.CENTER));
            celda1.setKeepTogether(true);
            tabla.addCell(celda1);


            Cell celda2 = new Cell().add(new Paragraph(tipo).setFontSize(12).setHorizontalAlignment(HorizontalAlignment.CENTER));
            celda2.setKeepTogether(true);
            tabla.addCell(celda2);


            Cell celda3 = new Cell().add(new Paragraph(cantidad).setFontSize(12).setHorizontalAlignment(HorizontalAlignment.CENTER));
            celda3.setKeepTogether(true);
            tabla.addCell(celda3);

            Cell celda4 = new Cell().add(new Paragraph(formato).setFontSize(12).setHorizontalAlignment(HorizontalAlignment.CENTER));
            celda4.setKeepTogether(true);
            tabla.addCell(celda4);
        }
        return tabla;
    }

    private static String generarRandom() {
        double rnd = Math.random() * 100;
        int dev = (int) rnd;
        return String.valueOf(dev);
    }
}
