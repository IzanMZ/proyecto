/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Contenedor_tablas;

import modelo.Album;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author isard
 */
public class Contenedor_Album {

    //Método que guarda la información de un álbum en el archivo "album.txt".
     
    public static void guardarAlbumTXT(Album album) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("Contenedor\\album.txt", true))) {

            // Guarda el autor del álbum (Banda o Músico)
            bw.write("Autor: " + album.getAutor());
            bw.newLine();

            // Guarda el código de la banda asociada al álbum
            bw.write("Codigo Banda: " + album.getCodigo_banda());
            bw.newLine();

            // Guarda el código del músico asociado al álbum
            bw.write("Codigo Musico: " + album.getCodigo_musico());
            bw.newLine();

            // Guarda el título del álbum
            bw.write("Titulo: " + album.getTitulo());
            bw.newLine();

            // Guarda el año de publicación
            bw.write("Anio: " + album.getAnio_publicacion());
            bw.newLine();

            // Guarda el tipo de álbum
            bw.write("Tipo: " + album.getTipo());
            bw.newLine();

            // Guarda la duración total del álbum
            bw.write("Duracion: " + album.getDuracion());
            bw.newLine();

            bw.write("-----------------------------------");
            bw.newLine();

            System.out.println("Inserccion guardada en album.txt.");

        } catch (IOException e) {

            System.out.println("Error al guardar TXT: " + e.getMessage());
        }
    }
}
