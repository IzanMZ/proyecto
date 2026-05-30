/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Contenedor_tablas;

import modelo.Musico;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author isard
 */
public class Contenedor_Musico {

    // Método que guarda la información de un músico en un archivo de texto llamado "musico.txt".
     
    public static void guardarMusicoTXT(Musico musico) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("Contenedor\\musico.txt", true))) {

            // Guarda el código de la banda del músico
            bw.write("Codigo banda: " + musico.getCodigo_banda());
            bw.newLine();

            // Guarda el nombre del músico
            bw.write("Nombre: " + musico.getNombre());
            bw.newLine();

            // Guarda la fecha de nacimiento
            bw.write("Fecha nacimiento: " + musico.getFecha_nac());
            bw.newLine();

            // Guarda la residencia del músico
            bw.write("Residencia: " + musico.getResidencia());
            bw.newLine();

            // Guarda la nacionalidad
            bw.write("Nacionalidad: " + musico.getNacionalidad());
            bw.newLine();

            // Guarda el instrumento que toca
            bw.write("Instrumento: " + musico.getInstrumento());
            bw.newLine();

            bw.write("-----------------------------------");
            bw.newLine();

            System.out.println("Inserccion guardada en musico.txt.");

        } catch (IOException e) {

            System.out.println("Error al guardar TXT: " + e.getMessage());
        }
    }
}
