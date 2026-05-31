/*
 * Clase encargada de exportar datos de álbumes
 * a diferentes formatos de archivo: TXT, CSV, BINARIO y JSON. 
 * Los datos se reciben en formato matriz (String[][]),
 * donde cada fila representa un registro de la tabla Album.
 */
package services.ficheros.exportar;

import excepciones.Excepciones;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Exportar_Album {

    // EXPORTAR A TXT guarda los datos separados con ";" en el archivo album.txt
    public static void exportarTXT(String[][] datos) {

        try {

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar txt.");
            }

            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("Ficheros\\TXT\\album.txt"));

            for (int i = 0; i < datos.length; i++) {

                if (datos[i] == null || datos[i][0] == null) {
                    break;
                }

                for (int j = 0; j < datos[i].length; j++) {

                    if (datos[i][j] != null) {
                        bw.write(datos[i][j]);
                    } else {
                        bw.write("null");
                    }

                    if (j < datos[i].length - 1) {
                        bw.write(";");
                    }
                }

                bw.newLine();
            }

            bw.close();

            System.out.println("TXT exportado correctamente");

        } catch (Excepciones e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
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

            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("Ficheros\\CSV\\album.csv"));

            for (int i = 0; i < datos.length; i++) {

                if (datos[i] == null || datos[i][0] == null) {
                    break;
                }

                for (int j = 0; j < datos[i].length; j++) {

                    if (datos[i][j] != null) {
                        bw.write(datos[i][j]);
                    } else {
                        bw.write("null");
                    }

                    if (j < datos[i].length - 1) {
                        bw.write(";");
                    }
                }

                bw.newLine();
            }

            bw.close();

            System.out.println("CSV exportado correctamente");

        } catch (Excepciones e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println("Error CSV: " + e.getMessage());
        }
    }

    // EXPORTAR A BINARIO serializa la matriz completa de datos y la guarda en archivo .bin
    public static void exportarBinario(String[][] datos) {

        try {

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar binario.");
            }

            ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream("Ficheros\\Binario\\album.bin")
            );

            os.writeObject(datos);
            os.close();

            System.out.println("Binario exportado correctamente");

        } catch (Excepciones e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println("Error binario: " + e.getMessage());
        }
    }

    // EXPORTAR A JSON convierte manualmente la matriz a formato JSON y la guarda en album.json
    public static void exportarJSON(String[][] datos) {

        try {

            //valida si hay datos en la base de datos
            if (datos == null || datos.length == 0 || datos[0] == null || datos[0][0] == null) {
                throw new Excepciones("No hay datos en la base de datos para exportar json.");
            }

            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("Ficheros\\JSON\\album.json"));

            bw.write("[");
            bw.newLine();

            for (int i = 0; i < datos.length; i++) {

                if (datos[i] == null || datos[i][0] == null) {
                    break;
                }

                bw.write("  {");

                bw.write("\"codigo\": \"" + datos[i][0] + "\", ");
                bw.write("\"autor\": \"" + datos[i][1] + "\", ");
                bw.write("\"codigo_banda\": \"" + datos[i][2] + "\", ");
                bw.write("\"codigo_musico\": \"" + datos[i][3] + "\", ");
                bw.write("\"titulo\": \"" + datos[i][4] + "\", ");
                bw.write("\"anio_publicacion\": \"" + datos[i][5] + "\", ");
                bw.write("\"tipo\": \"" + datos[i][6] + "\", ");
                bw.write("\"duracion\": \"" + datos[i][7] + "\"");

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
