/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Contenedor_tablas;

import modelo.Banda;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author isard
 */
public class Contenedor_Banda {

    // Método que guarda la información de una banda en el archivo "banda.txt".
     
    public static void guardarBandaTXT(Banda banda) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("Contenedor\\banda.txt", true))) {

            // Guarda el nombre de la banda
            bw.write("Nombre: " + banda.getNombre());
            bw.newLine();

            // Guarda los años de actuación de la banda
            bw.write("Anios actuacion: " + banda.getAnios_actuacion());
            bw.newLine();

            // Guarda el lugar de origen de la banda
            bw.write("Lugar origen: " + banda.getLugar_origen());
            bw.newLine();

            // Guarda el género musical de la banda
            bw.write("Genero: " + banda.getGenero());
            bw.newLine();

            bw.write("-----------------------------------");
            bw.newLine();

            System.out.println("Inserccion guardada en banda.txt.");

        } catch (IOException e) {

            System.out.println("Error al guardar TXT: " + e.getMessage());
        }
    }
}
