/*
 * Clase encargada de exportar datos de canciones
 * a diferentes formatos de archivo: TXT, CSV, BINARIO y JSON.
 * Los datos se reciben en formato matriz (String[][]),
 * donde cada fila representa un registro de la tabla Cancion.
 */
package services.ficheros.exportar;

import excepciones.Excepciones;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author isard
 */
public class Exportar_Cancion {

    // EXPORTAR A TXT: guarda los datos separados por ";" en el archivo cancion.txt
    public static void exportarTXT(String[][] datos) {

        try {

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar txt.");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("TXT\\cancion.txt"))) {

                for (int i = 0; i < datos.length; i++) {

                    if (datos[i] == null || datos[i][0] == null) {
                        break;
                    }

                    for (int j = 0; j < datos[i].length; j++) {

                        if (datos[i][j] != null) {
                            bw.write(datos[i][j]);
                        }

                        if (j < datos[i].length - 1 && datos[i][j + 1] != null) {
                            bw.write(";");
                        }
                    }

                    bw.newLine();
                }

                System.out.println("TXT exportado correctamente");

            }

        } catch (Excepciones e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println("Error TXT: " + e.getMessage());
        }
    }

    // EXPORTAR A CSV: mismo formato que TXT pero con extensión .csv
    public static void exportarCSV(String[][] datos) {

        try {

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar csv.");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("CSV\\cancion.csv"))) {

                for (int i = 0; i < datos.length; i++) {

                    if (datos[i] == null || datos[i][0] == null) {
                        break;
                    }

                    for (int j = 0; j < datos[i].length; j++) {

                        if (datos[i][j] != null) {
                            bw.write(datos[i][j]);
                        }

                        if (j < datos[i].length - 1 && datos[i][j + 1] != null) {
                            bw.write(";");
                        }
                    }

                    bw.newLine();
                }

                System.out.println("CSV exportado correctamente");
            }

        } catch (Excepciones e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error CSV: " + e.getMessage());
        }
    }

    // EXPORTAR A BINARIO: guarda la matriz completa serializada en un archivo .bin
    public static void exportarBinario(String[][] datos) {

        try {

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar binario.");
            }

            try (ObjectOutputStream os
                    = new ObjectOutputStream(new FileOutputStream("Binario\\cancion.bin"))) {

                os.writeObject(datos);
                System.out.println("Binario exportado correctamente");
            }

        } catch (Excepciones e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error binario: " + e.getMessage());
        }
    }

    // EXPORTAR A JSON: convierte la matriz manualmente a formato JSON
    public static void exportarJSON(String[][] datos) {

        try {

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar json.");
            }

            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("JSON\\cancion.json"));

            bw.write("[");
            bw.newLine();

            for (int i = 0; i < datos.length; i++) {

                if (datos[i] == null || datos[i][0] == null) {
                    break;
                }

                bw.write("  {");

                bw.write("\"codigo\": \"" + datos[i][0] + "\", ");
                bw.write("\"codigo_album\": \"" + datos[i][1] + "\", ");
                bw.write("\"posicion\": \"" + datos[i][2] + "\", ");
                bw.write("\"titulo\": \"" + datos[i][3] + "\", ");
                bw.write("\"compositor\": \"" + datos[i][4] + "\", ");
                bw.write("\"duracion\": \"" + datos[i][5] + "\"");

                bw.write("}");

                if (i < datos.length - 1
                        && datos[i + 1] != null
                        && datos[i + 1][0] != null) {

                    bw.write(",");
                }

                bw.newLine();
            }

            bw.write("]");
            bw.close();

            System.out.println("JSON exportado correctamente");

        } catch (Excepciones e) {

            System.out.println(e.getMessage());

        } catch (IOException e) {

            System.out.println("Error JSON: " + e.getMessage());
        }
    }
}
