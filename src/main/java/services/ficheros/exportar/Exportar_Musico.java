/*
 * Clase encargada de exportar datos de músicos
 * a diferentes formatos de archivo: TXT, CSV, BINARIO y JSON.
 * Los datos se reciben en formato matriz (String[][]),
 * donde cada fila representa un registro de la tabla Musico.
 */
package services.ficheros.exportar;

import excepciones.Excepciones;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;

public class Exportar_Musico {

    // EXPORTAR A TXT guarda los datos separados por ";" en musico.txt
    public static void exportarTXT(String[][] datos) {

        try {

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar txt.");
            }
            try (BufferedWriter bw
                    = new BufferedWriter(new FileWriter("TXT\\musico.txt"))) {

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

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar csv.");
            }

            try (BufferedWriter bw
                    = new BufferedWriter(new FileWriter("CSV\\musico.csv"))) {

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

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar binario.");
            }

            try (ObjectOutputStream os
                    = new ObjectOutputStream(new FileOutputStream("Binario\\musico.bin"))) {

                os.writeObject(datos);

                System.out.println("Binario exportado correctamente");
            }

        } catch (Exception e) {
            System.out.println("Error binario: " + e.getMessage());
        }
    }

    // EXPORTAR A JSON convierte manualmente la matriz a formato JSON y la guarda en musico.json
    public static void exportarJSON(String[][] datos) {

        try {

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar json.");
            }

            try (BufferedWriter bw
                    = new BufferedWriter(new FileWriter("JSON\\musico.json"))) {

                bw.write("[");
                bw.newLine();

                for (int i = 0; i < datos.length; i++) {

                    if (datos[i] == null || datos[i][0] == null) {
                        break;
                    }

                    bw.write("  {");

                    bw.write("\"codigo\":\"" + (datos[i][0] == null ? "" : datos[i][0]) + "\",");
                    bw.write("\"codigo_banda\":\"" + (datos[i][1] == null ? "" : datos[i][1]) + "\",");
                    bw.write("\"nombre\":\"" + (datos[i][2] == null ? "" : datos[i][2]) + "\",");
                    bw.write("\"fecha_nacimiento\":\"" + (datos[i][3] == null ? "" : datos[i][3]) + "\",");
                    bw.write("\"lugar_residencia\":\"" + (datos[i][4] == null ? "" : datos[i][4]) + "\",");
                    bw.write("\"nacionalidad\":\"" + (datos[i][5] == null ? "" : datos[i][5]) + "\",");
                    bw.write("\"instrumento\":\"" + (datos[i][6] == null ? "" : datos[i][6]) + "\"");

                    bw.write("}");

                    if (i < datos.length - 1 && datos[i + 1] != null && datos[i + 1][0] != null) {
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
