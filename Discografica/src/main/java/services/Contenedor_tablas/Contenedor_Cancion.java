/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Contenedor_tablas;

import modelo.Cancion;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author isard
 */
public class Contenedor_Cancion {

    //  Método que guarda la información de una canción en el archivo "cancion.txt".
     
    public static void guardarCancionTXT(Cancion cancion) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("Contenedor\\cancion.txt", true))) {

            // Guarda el código del álbum al que pertenece la canción
            bw.write("Codigo album: " + cancion.getCodigo_album());
            bw.newLine();

            // Guarda la posición de la canción dentro del álbum
            bw.write("Posicion: " + cancion.getPosicion());
            bw.newLine();

            // Guarda el título de la canción
            bw.write("Titulo: " + cancion.getTitulo());
            bw.newLine();

            // Guarda el nombre del compositor
            bw.write("Compositor: " + cancion.getCompositor());
            bw.newLine();

            // Guarda la duración de la canción
            bw.write("Duracion: " + cancion.getDuracion());
            bw.newLine();

            bw.write("-----------------------------------");
            bw.newLine();

            System.out.println("Inserccion guardada en cancion.txt.");

        } catch (IOException e) {

            System.out.println("Error al guardar TXT: " + e.getMessage());
        }
    }
}
