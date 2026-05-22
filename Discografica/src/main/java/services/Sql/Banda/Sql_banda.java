package services.Sql.Banda;

import modelo.Banda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.Util_Clases;

// Clase encargada de realizar operaciones SQL CRUD sobre la tabla Banda.
public class Sql_banda {

    // Método que inserta una nueva banda en la base de datos.
    public static void insertar(Banda banda) {

        // VALIDACIÓN: comprobar si la banda ya existe antes de insertarla
        if (existeBanda(banda.getNombre())) {
            System.out.println("La banda ya existe, no se puede insertar");
            return;
        }

        try (Connection con = configuracion.constant.conectar(); PreparedStatement os = con.prepareStatement(
                "INSERT INTO Banda VALUES (?,?,?,?,?)")) {

            // Código único de la banda
            os.setInt(1, Util_Clases.codigoMayor("Banda"));

            // Nombre de la banda
            os.setString(2, banda.getNombre());

            // Años de actuación de la banda
            os.setString(3, banda.getAnios_actuacion());

            // Lugar de origen de la banda
            os.setString(4, banda.getLugar_origen());

            // Género musical de la banda
            os.setString(5, banda.getGenero());

            // Ejecución del INSERT en la base de datos
            os.executeUpdate();

            System.out.println("Banda insertada correctamente");

        } catch (Exception e) {

            // Error en la conexión o ejecución SQL
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que actualiza una banda existente en la base de datos.
    public static void update(Banda banda, int codigo) {

        String sql = """
        UPDATE Banda
        SET nombre = ?,
            anios_actuacion = ?,
            lugar_origen = ?,
            genero = ?
        WHERE codigo = ?
    """;

        try (Connection con = configuracion.constant.conectar(); PreparedStatement os = con.prepareStatement(sql)) {

            // Validación: comprobar si la banda existe antes de actualizarla
        if (!Util_Clases.existeBandaCodigo(codigo)) {
                System.out.println("Error: la banda no existe");
                return;
            }

            os.setString(1, banda.getNombre());
            os.setString(2, banda.getAnios_actuacion());
            os.setString(3, banda.getLugar_origen());
            os.setString(4, banda.getGenero());
            os.setInt(5, codigo);

            os.executeUpdate();

            System.out.println("Banda actualizada correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que elimina una banda por su código.
    public static void delete(int codigo) {

        // Validación: comprobar si la banda existe antes de eliminarla
        if (!Util_Clases.existeBandaCodigo(codigo)) {
            System.out.println("Error: la banda no existe");
            return;
        }

        String sql = "DELETE FROM Banda WHERE codigo = ?";

        try (Connection con = configuracion.constant.conectar(); PreparedStatement os = con.prepareStatement(sql)) {

            os.setInt(1, codigo);

            os.executeUpdate();

            System.out.println("Banda eliminada correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que consulta una banda por su código
    public static String[][] consultarPorCodigo(int codigo) {

        // Validación: comprobar si la banda existe antes de consultar
        if (!Util_Clases.existeBandaCodigo(codigo)) {
            System.out.println("Error: no existe la banda con ese código");
            return new String[0][0];
        }

        String sql = "SELECT * FROM Banda WHERE codigo = ?";

        String[][] resultado = new String[1][5];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    resultado[0][0] = String.valueOf(rs.getInt("codigo"));
                    resultado[0][1] = rs.getString("nombre");
                    resultado[0][2] = rs.getString("anios_actuacion");
                    resultado[0][3] = rs.getString("lugar_origen");
                    resultado[0][4] = rs.getString("genero");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return resultado;
    }

    //  Método que consulta toda la tabla Banda.
    public static String[][] consultarTabla() {

        String sql = "SELECT * FROM Banda ORDER BY nombre ASC";

        int contador = 0;
        String[][] consulta = new String[100][5];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    consulta[contador][0] = String.valueOf(rs.getInt("codigo"));
                    consulta[contador][1] = rs.getString("nombre");
                    consulta[contador][2] = rs.getString("anios_actuacion");
                    consulta[contador][3] = rs.getString("lugar_origen");
                    consulta[contador][4] = rs.getString("genero");

                    contador++;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return consulta;
    }

  

    public static boolean existeBanda(String nombre) {

        // Consulta SQL que comprueba si existe una banda con ese nombre
        String sql = "SELECT 1 FROM Banda WHERE nombre = ?";

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Se asigna el nombre a la consulta
            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                // Si existe al menos una fila, la banda ya existe
                return rs.next();
            }

        } catch (Exception e) {

        
            System.out.println("Error: " + e.getMessage());
        }

       
        return false;
    }

    
    
    
}
