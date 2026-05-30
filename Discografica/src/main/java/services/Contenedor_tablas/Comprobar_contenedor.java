/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Contenedor_tablas;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author isard
 */
public class Comprobar_contenedor {

    // Método encargado de crear la carpeta "Contenedor" y los archivos TXT necesarios para almacenar información.
     
    public static void Contenedor() {

        
        File carpetaTXT = new File("Contenedor");

        // Verifica si la carpeta no existe. Si no existe, la crea usando mkdir().
       
        if (!carpetaTXT.exists()) {
            carpetaTXT.mkdir();
        }

        // Arreglo que contiene los archivos TXT que se crearán dentro de la carpeta.
         
        File[] archivos = {
            // Archivo para guardar bandas
            new File(carpetaTXT, "banda.txt"),
            // Archivo para guardar músicos
            new File(carpetaTXT, "musico.txt"),
            // Archivo para guardar álbumes
            new File(carpetaTXT, "album.txt"),
            // Archivo para guardar canciones
            new File(carpetaTXT, "cancion.txt")
        };

        // Recorre todos los archivos del arreglo.
       
        for (File archivo : archivos) {

            // Verifica si el archivo no existe, si no existe, lo crea.
            if (!archivo.exists()) {

                try {

                    // Crea físicamente el archivo TXT
                    archivo.createNewFile();

                } catch (IOException e) {

                    
                    System.out.println("Error al crear archivo: " + e.getMessage());
                }
            }
        }
    }
}
