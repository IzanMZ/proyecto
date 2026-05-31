/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Sql.Album;

import modelo.Album;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.Util_Clases;
import utils.Util_Tiempo;

// Clase encargada de realizar operaciones SQL CRUD sobre la tabla Album.
public class Sql_Album {

    // Método que inserta un nuevo álbum en la base de datos.
    public static void insertar(Album album) {

        // Validación: comprobar si ya existe un álbum con el mismo título
        if (existeAlbumTitulo(album.getTitulo())) {
            System.out.println("Error: ya existe un álbum con ese título");
            return;
        }

        // Validación: comprobar si existe la Banda
        if (album.getCodigo_banda() != null
                && !Util_Clases.existeBandaCodigo(album.getCodigo_banda())) {

            System.out.println("Error: la banda no existe");
            return;
        }

        // Validación: comprobar si existe el Músico
        if (album.getCodigo_musico() != null
                && !Util_Clases.existeMusicoPorCodigo(album.getCodigo_musico())) {

            System.out.println("Error: el músico no existe");
            return;
        }

        String sql = "INSERT INTO Album "
                + "(codigo, autor, codigo_banda, codigo_musico, titulo, anio_publicacion, tipo, duracion) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection con = configuracion.constant.conectar(); PreparedStatement os = con.prepareStatement(sql)) {

            // Código del álbum
            os.setInt(1, Util_Clases.codigoMayor("Album"));

            // Autor del álbum
            os.setString(2, album.getAutor());

            // Código banda (puede ser null)
            if (album.getCodigo_banda() != null) {
                os.setInt(3, album.getCodigo_banda());
            } else {
                os.setNull(3, java.sql.Types.INTEGER);
            }

            // Código músico (puede ser null)
            if (album.getCodigo_musico() != null) {
                os.setInt(4, album.getCodigo_musico());
            } else {
                os.setNull(4, java.sql.Types.INTEGER);
            }

            // Título del álbum
            os.setString(5, album.getTitulo());

            // Año de publicación
            os.setInt(6, album.getAnio_publicacion());

            // Tipo de álbum
            os.setString(7, album.getTipo());

            // Normalización de duración
            album.setDuracion(Util_Tiempo.normalizarDuracion(album.getDuracion()));

            // Duración
            os.setString(8, album.getDuracion());

            os.executeUpdate();

            System.out.println("Álbum insertado");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que actualiza un álbum existente en la base de datos.
    public static void update(Album album, int codigo) {

        // Validación: comprobar si el álbum existe antes de actualizar
        if (!Util_Clases.existeAlbumCodigo(codigo)) {
            System.out.println("Error: el álbum no existe");
            return;
        }

        // Validación: comprobar si existe la Banda
        if (album.getCodigo_banda() != null
                && !Util_Clases.existeBandaCodigo(album.getCodigo_banda())) {

            System.out.println("Error: la banda no existe");
            return;
        }

        // Validación: comprobar si existe el Músico
        if (album.getCodigo_musico() != null
                && !Util_Clases.existeMusicoPorCodigo(album.getCodigo_musico())) {

            System.out.println("Error: el músico no existe");
            return;
        }

        // Consulta SQL de actualización del álbum
        String sql = """
         UPDATE Album
                SET autor = ?,
                codigo_banda = ?,
                codigo_musico = ?,
                titulo = ?,
                anio_publicacion = ?,
                tipo = ?,
                duracion = ?
                WHERE codigo = ?
         """;

        try (Connection con = configuracion.constant.conectar(); PreparedStatement os = con.prepareStatement(sql)) {

            // Autor del álbum
            os.setString(1, album.getAutor());

            // Código de banda (puede ser null)
            if (album.getCodigo_banda() != null) {
                os.setInt(2, album.getCodigo_banda());
            } else {
                os.setNull(2, java.sql.Types.INTEGER);
            }

            // Código de músico (puede ser null)
            if (album.getCodigo_musico() != null) {
                os.setInt(3, album.getCodigo_musico());
            } else {
                os.setNull(3, java.sql.Types.INTEGER);
            }

            // Título del álbum
            os.setString(4, album.getTitulo());

            // Año de publicación
            os.setInt(5, album.getAnio_publicacion());

            // Tipo de álbum
            os.setString(6, album.getTipo());

            // Normalización de duración
            album.setDuracion(Util_Tiempo.normalizarDuracion(album.getDuracion()));

            // Duración total
            os.setString(7, album.getDuracion());

            // Código del álbum a actualizar
            os.setInt(8, codigo);

            // Ejecución del UPDATE
            os.executeUpdate();

            System.out.println("Álbum actualizado correctamente");

        } catch (Exception e) {

            // Error de base de datos o conexión
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que elimina un álbum por su código.
    public static void delete(int codigo) {

        // Validación: comprobar si el álbum existe
        if (!Util_Clases.existeAlbumCodigo(codigo)) {
            System.out.println("Error: el álbum no existe");
            return;
        }

        String sql = "DELETE FROM Album WHERE codigo = ?";

        try (Connection con = configuracion.constant.conectar(); PreparedStatement os = con.prepareStatement(sql)) {

            os.setInt(1, codigo);

            os.executeUpdate();

            System.out.println("Álbum eliminado");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que consulta un álbum por su código.
    public static String[][] consultarPorCodigo(int codigo) {

        // Validación: comprobar si el álbum existe
        if (!Util_Clases.existeAlbumCodigo(codigo)) {
            System.out.println("Error: el álbum no existe");
            return new String[0][0];
        }

        String sql = "SELECT * FROM Album WHERE codigo = ?";

        String[][] consulta = new String[1][8];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    consulta[0][0] = String.valueOf(rs.getInt("codigo"));
                    consulta[0][1] = rs.getString("autor");
                    consulta[0][2] = rs.getString("codigo_banda");
                    consulta[0][3] = rs.getString("codigo_musico");
                    consulta[0][4] = rs.getString("titulo");
                    consulta[0][5] = rs.getString("anio_publicacion");
                    consulta[0][6] = rs.getString("tipo");
                    consulta[0][7] = rs.getString("duracion");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return consulta;
    }

    // Método que consulta toda la tabla Album.
    public static String[][] consultarTabla() {

        // Consulta SQL ordenada por la columna titulo ascendentemente.
        String sql = "SELECT * FROM Album ORDER BY titulo ASC";

        int contador = 0;
        String[][] consulta = new String[100][8];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    consulta[contador][0] = String.valueOf(rs.getInt("codigo"));
                    consulta[contador][1] = rs.getString("autor");
                    consulta[contador][2] = rs.getString("codigo_banda");
                    consulta[contador][3] = rs.getString("codigo_musico");
                    consulta[contador][4] = rs.getString("titulo");
                    consulta[contador][5] = rs.getString("anio_publicacion");
                    consulta[contador][6] = rs.getString("tipo");
                    consulta[contador][7] = rs.getString("duracion");

                    contador++;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return consulta;
    }

   

    // Método que comprueba si existe un álbum por título.
    public static boolean existeAlbumTitulo(String titulo) {

        // Consulta SQL para buscar un álbum por título.
        String sql = "SELECT 1 FROM Album WHERE titulo = ?";

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de la consulta SQL.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar el título recibido al parámetro de la consulta.
            ps.setString(1, titulo);

            // Ejecutar la consulta.
            try (ResultSet rs = ps.executeQuery()) {

                // Retorna true si existe el álbum.
                return rs.next();
            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

   

}
