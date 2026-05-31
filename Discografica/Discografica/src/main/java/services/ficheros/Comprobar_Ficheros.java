/*
 * Clase encargada de comprobar y crear
 * los ficheros necesarios del sistema
 * (TXT, CSV, JSON y BINARIO).
 */
package services.ficheros;

import excepciones.Excepciones;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author isard
 */
public class Comprobar_Ficheros {

    // Comprueba y crea la carpeta TXT y sus archivos 
    public static void ficheroTXT() throws Excepciones {

        File carpetaTXT = new File("TXT");

        if (!carpetaTXT.exists()) {
            carpetaTXT.mkdir();
        }

        File[] archivos = {
            new File(carpetaTXT, "banda.txt"),
            new File(carpetaTXT, "musico.txt"),
            new File(carpetaTXT, "album.txt"),
            new File(carpetaTXT, "cancion.txt")
        };

        for (File archivo : archivos) {

            if (!archivo.exists()) {

                try {
                    archivo.createNewFile();

                } catch (IOException e) {
                    throw new Excepciones("Error creando archivo TXT: " + archivo.getName() + " -> " + e.getMessage());
                }
            }
        }
    }

    // Comprueba y crea la carpeta CSV y sus archivos 
    public static void ficheroCSV() throws Excepciones {

        File carpetaCSV = new File("CSV");

        if (!carpetaCSV.exists()) {
            carpetaCSV.mkdir();
        }

        File[] archivos = {
            new File(carpetaCSV, "banda.csv"),
            new File(carpetaCSV, "musico.csv"),
            new File(carpetaCSV, "album.csv"),
            new File(carpetaCSV, "cancion.csv")
        };

        for (File archivo : archivos) {

            if (!archivo.exists()) {

                try {
                    archivo.createNewFile();

                } catch (IOException e) {
                    throw new Excepciones("Error creando archivo CSV: " + archivo.getName() + " -> " + e.getMessage());
                }
            }
        }
    }

    // Comprueba y crea la carpeta JSON y sus archivos 
    public static void ficheroJSON() throws Excepciones {

        File carpetaJSON = new File("JSON");

        if (!carpetaJSON.exists()) {
            carpetaJSON.mkdir();
        }

        File[] archivos = {
            new File(carpetaJSON, "banda.json"),
            new File(carpetaJSON, "musico.json"),
            new File(carpetaJSON, "album.json"),
            new File(carpetaJSON, "cancion.json")
        };

        for (File archivo : archivos) {

            if (!archivo.exists()) {

                try {
                    archivo.createNewFile();

                } catch (IOException e) {
                    throw new Excepciones("Error creando archivo JSON: " + archivo.getName() + " -> " + e.getMessage());
                }
            }
        }
    }

    // Comprueba y crea la carpeta BINARIO y sus archivos 
    public static void ficheroBinario() throws Excepciones {

        File carpetaBinaria = new File("Binario");

        if (!carpetaBinaria.exists()) {
            carpetaBinaria.mkdir();
        }

        File[] archivos = {
            new File(carpetaBinaria, "banda.bin"),
            new File(carpetaBinaria, "musico.bin"),
            new File(carpetaBinaria, "album.bin"),
            new File(carpetaBinaria, "cancion.bin")
        };

        for (File archivo : archivos) {

            if (!archivo.exists()) {

                try {
                    archivo.createNewFile();

                } catch (IOException e) {
                    throw new Excepciones("Error creando archivo BINARIO: " + archivo.getName() + " -> " + e.getMessage());
                }
            }
        }
    }
}
