package services.Sql.Cancion;

import modelo.Cancion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.Util_Clases;
import utils.Util_Tiempo;

//Clase encargada de realizar operaciones SQL CRUD sobre la tabla Cancion.
public class Sql_Cancion {

    //Método que inserta una nueva canción en la base de datos.
    public static void insertar(Cancion cancion) {

        // Validación: comprobar que el álbum existe en la base de datos
        if (!Util_Clases.existeAlbumCodigo(cancion.getCodigo_album())) {
            throw new RuntimeException("Error: el álbum no existe");
        }

        // Validación: comprobar que no existe ya una canción en esa posición del álbum
        if (buscarPorAlbumYPosicion(cancion.getCodigo_album(),
                cancion.getPosicion()) != null) {
            System.out.println("Error: ya existe una canción en esa posición del álbum"
            );
            return;

        }

        // Sentencia SQL para insertar una nueva canción
        String sql = "INSERT INTO Cancion VALUES (?,?,?,?,?,?)";

        try (Connection con = configuracion.constant.conectar(); PreparedStatement os = con.prepareStatement(sql)) {

            // Código autogenerado.
            os.setInt(1, Util_Clases.codigoMayor("Cancion"));

            // Código del álbum al que pertenece la canción
            os.setInt(2, cancion.getCodigo_album());

            // Posición de la canción dentro del álbum
            os.setInt(3, cancion.getPosicion());

            // Título de la canción
            os.setString(4, cancion.getTitulo());

            // Compositor de la canción
            os.setString(5, cancion.getCompositor());

            // Normalización de la duración (formato MM:SS)
            cancion.setDuracion(
                    Util_Tiempo.normalizarDuracion(cancion.getDuracion())
            );

            // Inserción de la duración ya normalizada en la base de datos
            os.setString(6, cancion.getDuracion());

            // Ejecutar la inserción en la base de datos
            os.executeUpdate();

            System.out.println("Canción insertada correctamente");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que actualiza una canción existente en la base de datos.
    public static void update(Cancion cancion, int codigo) {

        String sql = """
        UPDATE Cancion
        SET codigo_album = ?,
            posicion = ?,
            titulo = ?,
            compositor = ?,
            duracion = ?
        WHERE codigo = ?
    """;

        try (Connection con = configuracion.constant.conectar(); PreparedStatement os = con.prepareStatement(sql)) {

            // Validación: comprobar que la canción existe en la base de datos
            if (!existeCancion(codigo)) {
                throw new Exception("La canción no existe");
            }

            // Validación: comprobar que el álbum existe en la base de datos
            if (!Util_Clases.existeAlbumCodigo(cancion.getCodigo_album())) {
                throw new Exception("El álbum no existe");
            }

            // Código del álbum al que pertenece la canción
            os.setInt(1, cancion.getCodigo_album());

            // Posición de la canción dentro del álbum
            os.setInt(2, cancion.getPosicion());

            // Título de la canción
            os.setString(3, cancion.getTitulo());

            // Compositor de la canción
            os.setString(4, cancion.getCompositor());

            // Normalización de la duración (formato MM:SS)
            cancion.setDuracion(
                    Util_Tiempo.normalizarDuracion(cancion.getDuracion())
            );

            // Inserción de la duración ya normalizada en la base de datos
            os.setString(5, cancion.getDuracion());

            // Código de la canción a actualizar
            os.setInt(6, codigo);

            os.executeUpdate();

            System.out.println("Canción actualizada correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que elimina una canción por su código.
    public static void delete(int codigo) {

        // Validación: comprobar que la canción existe antes de eliminarla
        if (!existeCancion(codigo)) {
            System.out.println("Error: la cancion no existe");
            return;
        }

        String sql = "DELETE FROM Cancion WHERE codigo = ?";

        try (Connection con = configuracion.constant.conectar(); PreparedStatement os = con.prepareStatement(sql)) {

            // Se asigna el código de la canción a eliminar
            os.setInt(1, codigo);

            // Se ejecuta el DELETE
            os.executeUpdate();

            System.out.println("Canción eliminada correctamente");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que consulta una canción por su código.
    public static String[][] consultarPorCodigo(int codigo) {

        String sql = "SELECT * FROM Cancion WHERE codigo = ?";

        int contador = 0;
        String[][] consulta = new String[1][6];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    consulta[0][0] = String.valueOf(rs.getInt("codigo"));
                    consulta[0][1] = rs.getString("codigo_album");
                    consulta[0][2] = rs.getString("posicion");
                    consulta[0][3] = rs.getString("titulo");
                    consulta[0][4] = rs.getString("compositor");
                    consulta[0][5] = rs.getString("duracion");

                    contador++;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return consulta;
    }

    // Método que consulta toda la tabla Cancion.
    public static String[][] consultarTabla() {

        String sql = "SELECT * FROM Cancion ORDER BY titulo ASC";

        int contador = 0;
        String[][] consulta = new String[100][6];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    consulta[contador][0] = String.valueOf(rs.getInt("codigo"));
                    consulta[contador][1] = rs.getString("codigo_album");
                    consulta[contador][2] = rs.getString("posicion");
                    consulta[contador][3] = rs.getString("titulo");
                    consulta[contador][4] = rs.getString("compositor");
                    consulta[contador][5] = rs.getString("duracion");

                    contador++;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return consulta;
    }

    // Método que busca una canción por código.
    public static Cancion buscarPorCodigo(int codigo) {

        // Validación: comprobar si la banda existe antes de consultar
        if (!Sql_Cancion.existeCancion(codigo)) {
            System.out.println("Error: no existe la cancion con ese código");

        }

        // Consulta SQL para buscar una canción por código.
        String sql = "SELECT * FROM Cancion WHERE codigo = ?";

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de la consulta SQL.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar el código al parámetro.
            ps.setInt(1, codigo);

            // Ejecutar la consulta.
            ResultSet rs = ps.executeQuery();

            // Verificar si existe resultado.
            if (rs.next()) {

                // Retornar objeto Cancion con los datos obtenidos.
                return new Cancion(
                        rs.getInt("codigo_album"),
                        rs.getInt("posicion"),
                        rs.getString("titulo"),
                        rs.getString("compositor"),
                        rs.getString("duracion")
                );
            }

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error buscarPorCodigo: " + e.getMessage());
        }

        // Retorna null si no existe o hubo error.
        return null;
    }

    // Método que comprueba si existe una canción por código.
    public static boolean existeCancion(int codigo) {

        // Consulta SQL para contar canciones con ese código.
        String sql = "SELECT COUNT(*) FROM Cancion WHERE codigo = ?";

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de la consulta SQL.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar el código recibido al parámetro de la consulta.
            ps.setInt(1, codigo);

            // Ejecutar la consulta.
            ResultSet rs = ps.executeQuery();

            // Verificar si existe al menos una canción.
            if (rs.next()) {

                // Retorna true si el contador es mayor que 0.
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error: " + e.getMessage());
        }

        // Retorna false si no existe o hubo error.
        return false;
    }

    // Método que busca una canción por álbum y posición.
    public static Cancion buscarPorAlbumYPosicion(int codigoAlbum, int posicion) {

        // Consulta SQL.
        String sql = """
        SELECT * 
        FROM Cancion 
        WHERE codigo_album = ? 
        AND posicion = ?
    """;

        try (
                // Conexión.
                Connection con = configuracion.constant.conectar(); // Preparar consulta.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Código del álbum.
            ps.setInt(1, codigoAlbum);

            // Posición.
            ps.setInt(2, posicion);

            // Ejecutar consulta.
            ResultSet rs = ps.executeQuery();

            // Si existe resultado.
            if (rs.next()) {

                // Retornar canción encontrada.
                return new Cancion(
                        rs.getInt("codigo_album"),
                        rs.getInt("posicion"),
                        rs.getString("titulo"),
                        rs.getString("compositor"),
                        rs.getString("duracion")
                );
            }

        } catch (Exception e) {

            System.out.println("Error buscarPorAlbumYPosicion: " + e.getMessage());
        }

        // Retorna null si no existe.
        return null;
    }

}
