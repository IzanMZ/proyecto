package services.Sql;

import excepciones.Excepciones;
import java.sql.Connection;
import java.sql.Statement;

// Clase encargada de crear las tablas de la base de datos si no existen.
 
public class Sql_ComprobarTabla {

    // Crea la tabla Banda si no existe.
     
    public static void crearTablaBanda() throws Excepciones {

        String sql = """
        CREATE TABLE IF NOT EXISTS Banda (
            codigo INT PRIMARY KEY,
            nombre VARCHAR(30) NOT NULL,
            anios_actuacion VARCHAR(50) NOT NULL,
            lugar_origen VARCHAR(100) NOT NULL,
            genero VARCHAR(30) NOT NULL
        )
        """;

        try (Connection con = configuracion.constant.conectar(); Statement st = con.createStatement()) {

            st.execute(sql);

        } catch (Exception e) {
            throw new Excepciones("Error creando tabla Banda: " + e.getMessage());
        }
    }

    // Crea la tabla Musico si no existe.
     
    public static void crearTablaMusico() throws Excepciones {

        String sql = """
        CREATE TABLE IF NOT EXISTS Musico (
            codigo INT PRIMARY KEY,
            codigo_banda INT,
            nombre VARCHAR(30) NOT NULL,
            fecha_nacimiento VARCHAR(30) NOT NULL,
            lugar_residencia VARCHAR(100) NOT NULL,
            nacionalidad VARCHAR(50) NOT NULL,
            instrumento VARCHAR(100) NOT NULL,
            FOREIGN KEY (codigo_banda) REFERENCES Banda(codigo) ON DELETE SET NULL
        )
        """;

        try (Connection con = configuracion.constant.conectar(); Statement st = con.createStatement()) {

            st.execute(sql);

        } catch (Exception e) {
            throw new Excepciones("Error creando tabla Musico: " + e.getMessage());
        }
    }

    // Crea la tabla Album si no existe.
     
    public static void crearTablaAlbum() throws Excepciones {

        String sql = """
        CREATE TABLE IF NOT EXISTS Album (
            codigo INT PRIMARY KEY,
            autor VARCHAR(30) CHECK(autor IN ('Banda', 'Musico')),
            codigo_banda INT,
            codigo_musico INT,
            titulo VARCHAR(50) NOT NULL,
            anio_publicacion INT NOT NULL,
            tipo VARCHAR(50) CHECK(tipo IN ('estudio', 'directo','recopilatorio')),
            duracion VARCHAR(70) NOT NULL,
            FOREIGN KEY (codigo_banda) REFERENCES Banda(codigo) ON DELETE CASCADE,
            FOREIGN KEY (codigo_musico) REFERENCES Musico(codigo) ON DELETE CASCADE
        )
        """;

        try (Connection con = configuracion.constant.conectar(); Statement st = con.createStatement()) {

            st.execute(sql);

        } catch (Exception e) {
            throw new Excepciones("Error creando tabla Album: " + e.getMessage());
        }
    }

    // Crea la tabla Cancion si no existe.
     
    public static void crearTablaCancion() throws Excepciones {

        String sql = """
        CREATE TABLE IF NOT EXISTS Cancion (
            codigo INT PRIMARY KEY,
            codigo_album INT NOT NULL,
            posicion INT NOT NULL,
            titulo VARCHAR(50) NOT NULL,
            compositor VARCHAR(50) NOT NULL,
            duracion VARCHAR(70) NOT NULL,
            FOREIGN KEY (codigo_album) REFERENCES Album(codigo) ON DELETE CASCADE
        )
        """;

        try (Connection con = configuracion.constant.conectar(); Statement st = con.createStatement()) {

            st.execute(sql);

        } catch (Exception e) {
            throw new Excepciones("Error creando tabla Cancion: " + e.getMessage());
        }
    }
}
