package com.mycompany.discografica_musica;

import services.Sql.Sql_ComprobarTabla;
import services.ficheros.Comprobar_Ficheros;

import swing.Menu;
import excepciones.Excepciones;

public class Discografica_musicaMain {

    public static void main(String[] args) {

        // Comprobar que las tablas están creadas
        try {
            Sql_ComprobarTabla.crearTablaBanda();
            Sql_ComprobarTabla.crearTablaMusico();
            Sql_ComprobarTabla.crearTablaAlbum();
            Sql_ComprobarTabla.crearTablaCancion();

            // Comprobar que los ficheros existen
            Comprobar_Ficheros.ficheroTXT();
            Comprobar_Ficheros.ficheroCSV();
            Comprobar_Ficheros.ficheroJSON();
            Comprobar_Ficheros.ficheroBinario();
            
        } catch (Excepciones e) {
            System.out.println("Error inicializando base de datos: " + e.getMessage());
        }
        //Comprobar si existe los contenedores
        services.Contenedor_tablas.Comprobar_contenedor.Contenedor();

        //Llamada al swing
        Menu menu = new Menu();
        menu.setVisible(true);

    }

}
