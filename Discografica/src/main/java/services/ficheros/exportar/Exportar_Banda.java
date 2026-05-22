/*
 * Clase encargada de exportar datos de bandas
 * a diferentes formatos de archivo: TXT, CSV, BINARIO y JSON.
 * Los datos se reciben en formato matriz (String[][]),
 * donde cada fila representa un registro de la tabla Banda.
 */
package services.ficheros.exportar;

import excepciones.Excepciones;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;

public class Exportar_Banda {

    // EXPORTAR A TXT guarda los datos separados por ";" en banda.txt
    public static void exportarTXT(String[][] datos) {

        try {

            if (datos == null || datos.length == 0) {
                throw new Excepciones("No hay datos para exportar en TXT.");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("TXT\\banda.txt"))) {

                for (int i = 0; i < datos.length; i++) {

                    if (datos[i] == null || datos[i][0] == null) {
                        break;
                    }

                    for (int j = 0; j < datos[i].length; j++) {

                        bw.write(datos[i][j] == null ? "" : datos[i][j]);

                        if (j < datos[i].length - 1) {
                            bw.write(";");
                        }
                    }

                    bw.newLine();
                }

                System.out.println("TXT exportado correctamente");
            }

        } catch (Exception e) {
            System.out.println("Error TXT: " + e.getMessage());
        }
    }

    // EXPORTAR A CSV misma estructura que TXT pero con extensión .csv
    public static void exportarCSV(String[][] datos) {

        try {

            if (datos == null || datos.length == 0) {
                throw new Excepciones("No hay datos para exportar en CSV.");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("CSV\\banda.csv"))) {

                for (int i = 0; i < datos.length; i++) {

                    if (datos[i] == null || datos[i][0] == null) {
                        break;
                    }

                    for (int j = 0; j < datos[i].length; j++) {

                        bw.write(datos[i][j] == null ? "" : datos[i][j]);

                        if (j < datos[i].length - 1) {
                            bw.write(";");
                        }
                    }

                    bw.newLine();
                }

                System.out.println("CSV exportado correctamente");
            }

        } catch (Exception e) {
            System.out.println("Error CSV: " + e.getMessage());
        }
    }

    // EXPORTAR A BINARIO serializa la matriz completa de datos y la guarda en .bin
    public static void exportarBinario(String[][] datos) {

        try {

            if (datos == null || datos.length == 0) {
                throw new Excepciones("No hay datos para exportar en BINARIO.");
            }

            try (ObjectOutputStream os
                    = new ObjectOutputStream(new FileOutputStream("Binario\\banda.bin"))) {

                os.writeObject(datos);

                System.out.println("Binario exportado correctamente");
            }

        } catch (Exception e) {
            System.out.println("Error binario: " + e.getMessage());
        }
    }

    // EXPORTAR A JSON convierte manualmente la matriz a formato JSON y la guarda en banda.json
    public static void exportarJSON(String[][] datos) {

        try {

            if (datos == null || datos.length == 0) {
                throw new Excepciones("No hay datos para exportar en JSON.");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("JSON\\banda.json"))) {

                bw.write("[");
                bw.newLine();

                for (int i = 0; i < datos.length; i++) {

                    if (datos[i] == null || datos[i][0] == null) {
                        break;
                    }

                    bw.write("  {");

                    bw.write("\"codigo\":\"" + (datos[i][0] == null ? "" : datos[i][0]) + "\",");
                    bw.write("\"nombre\":\"" + (datos[i][1] == null ? "" : datos[i][1]) + "\",");
                    bw.write("\"anios_actuacion\":\"" + (datos[i][2] == null ? "" : datos[i][2]) + "\",");
                    bw.write("\"lugar_origen\":\"" + (datos[i][3] == null ? "" : datos[i][3]) + "\",");
                    bw.write("\"genero\":\"" + (datos[i][4] == null ? "" : datos[i][4]) + "\"");

                    bw.write("}");

                    if (i < datos.length - 1
                            && datos[i + 1] != null
                            && datos[i + 1][0] != null) {
                        bw.write(",");
                    }

                    bw.newLine();
                }

                bw.write("]");

                System.out.println("JSON exportado correctamente");

            }

        } catch (Exception e) {
            System.out.println("Error JSON: " + e.getMessage());
        }
    }
}
