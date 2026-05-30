/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author isard
 */
public class Util_Tiempo {

    // Convierte "MM:SS" o "HH:MM:SS" a formato MM:SS (sin horas)
    public static String normalizarDuracion(String duracion) {

        String[] partes = duracion.split(":");

        int minutos;
        int segundos;

        if (partes.length == 2) {
            // MM:SS
            minutos = Integer.parseInt(partes[0]);
            segundos = Integer.parseInt(partes[1]);

        } else if (partes.length == 3) {
            // HH:MM:SS → convertir a minutos totales
            int horas = Integer.parseInt(partes[0]);
            minutos = Integer.parseInt(partes[1]);
            segundos = Integer.parseInt(partes[2]);

            minutos = (horas * 60) + minutos;

        } else {
            throw new IllegalArgumentException("Formato de duración inválido");
        }

        // normalización de segundos a minutos
        minutos += segundos / 60;
        segundos = segundos % 60;

        return String.format("%02d:%02d", minutos, segundos);
    }

}
