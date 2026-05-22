package services.Sql.Musico;

import modelo.Musico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.Util_Clases;

// Clase encargada de realizar operaciones SQL CRUD sobre la tabla Musico.
public class Sql_Musico {

    // Método que inserta un nuevo músico.
    public static void insertar(Musico musico) {

        // Verificar si ya existe un músico con el mismo nombre.
        if (existeMusicoPorNombre(musico.getNombre())) {

            System.out.println("Ya existe un músico con ese nombre");
            return;
        }

        // Validación: comprobar si existe la banda
        if (musico.getCodigo_banda() != null
                && !Util_Clases.existeBandaCodigo(musico.getCodigo_banda())) {

            System.out.println("Error: la banda no existe");
            return;
        }

        // Consulta SQL de inserción.
        String sql = "INSERT INTO Musico VALUES (?,?,?,?,?,?,?)";

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de la consulta SQL.
                 PreparedStatement os = con.prepareStatement(sql)) {

            // Código autogenerado.
            os.setInt(1, Util_Clases.codigoMayor("Musico"));

            // Código de banda puede ser null.
            if (musico.getCodigo_banda() != null) {
                os.setInt(2, musico.getCodigo_banda());
            } else {
                os.setNull(2, java.sql.Types.INTEGER);
            }

            // Nombre del músico.
            os.setString(3, musico.getNombre());

            // Fecha de nacimiento.
            os.setString(4, musico.getFecha_nac());

            // Lugar de residencia.
            os.setString(5, musico.getResidencia());

            // Nacionalidad.
            os.setString(6, musico.getNacionalidad());

            // Instrumento.
            os.setString(7, musico.getInstrumento());

            // Ejecutar inserción.
            os.executeUpdate();

            System.out.println("Músico insertado correctamente");

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que actualiza un músico existente.
    public static void update(Musico musico, int codigo) {

        // Consulta SQL de actualización.
        String sql = """
            UPDATE Musico
            SET codigo_banda = ?,
                nombre = ?,
                fecha_nacimiento = ?,
                lugar_residencia = ?,
                nacionalidad = ?,
                instrumento = ?
            WHERE codigo = ?
        """;

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de la consulta SQL.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Validación: comprobar si existe el músico
            if (!Util_Clases.existeMusicoPorCodigo(codigo)) {

                System.out.println("Error: el músico no existe");
                return;
            }

            // Validación: comprobar si existe la banda
            if (musico.getCodigo_banda() != null
                    && !Util_Clases.existeBandaCodigo(musico.getCodigo_banda())) {

                System.out.println("Error: la banda no existe");
                return;
            }

            // Código de banda puede ser null.
            if (musico.getCodigo_banda() != null) {
                ps.setInt(2, musico.getCodigo_banda());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            // Nombre.
            ps.setString(2, musico.getNombre());

            // Fecha de nacimiento.
            ps.setString(3, musico.getFecha_nac());

            // Lugar de residencia.
            ps.setString(4, musico.getResidencia());

            // Nacionalidad.
            ps.setString(5, musico.getNacionalidad());

            // Instrumento.
            ps.setString(6, musico.getInstrumento());

            // Código del músico a actualizar.
            ps.setInt(7, codigo);

            // Ejecutar actualización.
            ps.executeUpdate();

            System.out.println("Músico actualizado correctamente");

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que elimina un músico por código.
    public static void delete(int codigo) {

        // Consulta SQL de eliminación.
        String sql = "DELETE FROM Musico WHERE codigo = ?";

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de la consulta SQL.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar código a eliminar.
            ps.setInt(1, codigo);

            // Ejecutar eliminación.
            ps.executeUpdate();

            System.out.println("Músico eliminado correctamente");

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método que consulta un músico por código.
    public static String[][] consultarPorCodigo(int codigo) {

        // Validación: comprobar si la banda existe antes de consultar
        if (!Util_Clases.existeMusicoPorCodigo(codigo)) {
            System.out.println("Error: no existe la banda con ese código");
            return new String[0][0];
        }

        // Consulta SQL.
        String sql = "SELECT * FROM Musico WHERE codigo = ?";

        // Matriz para almacenar resultado.
        String[][] consulta = new String[1][7];

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de consulta.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar código.
            ps.setInt(1, codigo);

            // Ejecutar consulta.
            try (ResultSet rs = ps.executeQuery()) {

                // Verificar si existe resultado.
                if (rs.next()) {

                    consulta[0][0] = String.valueOf(rs.getInt("codigo"));
                    consulta[0][1] = rs.getString("codigo_banda");
                    consulta[0][2] = rs.getString("nombre");
                    consulta[0][3] = rs.getString("fecha_nacimiento");
                    consulta[0][4] = rs.getString("lugar_residencia");
                    consulta[0][5] = rs.getString("nacionalidad");
                    consulta[0][6] = rs.getString("instrumento");

                } else {

                    // Mensaje si no existe el músico.
                    System.out.println("No existe el músico con ese código");
                }
            }

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error: " + e.getMessage());
        }

        // Retornar resultado.
        return consulta;
    }

    // Método que consulta todos los músicos.
    public static String[][] consultarTabla() {

        // Consulta SQL.
        String sql = "SELECT * FROM Musico ORDER BY nombre ASC";

        // Contador de filas.
        int contador = 0;

        // Matriz para resultados.
        String[][] consulta = new String[100][7];

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de consulta.
                 PreparedStatement ps = con.prepareStatement(sql); // Ejecutar consulta.
                 ResultSet rs = ps.executeQuery()) {

            // Recorrer resultados.
            while (rs.next()) {

                consulta[contador][0] = String.valueOf(rs.getInt("codigo"));
                consulta[contador][1] = rs.getString("codigo_banda");
                consulta[contador][2] = rs.getString("nombre");
                consulta[contador][3] = rs.getString("fecha_nacimiento");
                consulta[contador][4] = rs.getString("lugar_residencia");
                consulta[contador][5] = rs.getString("nacionalidad");
                consulta[contador][6] = rs.getString("instrumento");

                contador++;
            }

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error: " + e.getMessage());
        }

        // Retornar resultados.
        return consulta;
    }

    // Método que comprueba si existe un músico por nombre.
    public static boolean existeMusicoPorNombre(String nombre) {

        // Consulta SQL para buscar un músico por nombre.
        String sql = "SELECT nombre FROM Musico WHERE nombre = ?";

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de la consulta SQL.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar el nombre recibido al parámetro de la consulta.
            ps.setString(1, nombre);

            // Ejecutar la consulta.
            try (ResultSet rs = ps.executeQuery()) {

                // Retorna true si existe al menos un registro.
                return rs.next();
            }

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error: " + e.getMessage());
        }

        // Retorna false si no existe o hubo error.
        return false;
    }
}
