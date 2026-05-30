/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author isard
 */
public class Util_Clases {

    // Método que obtiene el siguiente código disponible.
    public static int codigoMayor(String tabla) {

        // Consulta SQL para obtener el mayor código.
        String sql = "SELECT MAX(codigo) FROM " + tabla;

        // Valor inicial.
        int codigo = 1;

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de consulta.
                 PreparedStatement ps = con.prepareStatement(sql); // Ejecutar consulta.
                 ResultSet rs = ps.executeQuery()) {

            // Verificar resultado.
            if (rs.next()) {

                // Obtener siguiente código.
                codigo = rs.getInt(1) + 1;
            }

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error: " + e.getMessage());
        }

        // Retornar código generado.
        return codigo;
    }

    // Verifica si existe una banda por código
    public static boolean existeBandaCodigo(int codigo) {

        String sql = "SELECT 1 FROM Banda WHERE codigo = ?";

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    // Método que comprueba si existe un músico por código.
    public static boolean existeMusicoPorCodigo(int codigo) {

        // Consulta SQL para buscar un músico por código.
        String sql = "SELECT codigo FROM Musico WHERE codigo = ?";

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de la consulta SQL.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar el código recibido al parámetro de la consulta.
            ps.setInt(1, codigo);

            // Ejecutar la consulta.
            try (ResultSet rs = ps.executeQuery()) {

                // Retorna true si existe el código.
                return rs.next();
            }

        } catch (Exception e) {

            // Mostrar mensaje de error.
            System.out.println("Error: " + e.getMessage());
        }

        // Retorna false si no existe o hubo error.
        return false;
    }

    // Método que comprueba si existe un álbum por código.
    public static boolean existeAlbumCodigo(int codigo) {

        // Consulta SQL para buscar un álbum por código.
        String sql = "SELECT 1 FROM Album WHERE codigo = ?";

        try (
                // Conexión a la base de datos.
                Connection con = configuracion.constant.conectar(); // Preparación de la consulta SQL.
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar el código recibido al parámetro de la consulta.
            ps.setInt(1, codigo);

            // Ejecutar la consulta.
            try (ResultSet rs = ps.executeQuery()) {

                // Retorna true si existe el álbum.
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
