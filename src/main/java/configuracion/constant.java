/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author isard
 */
public class constant {

    private static final String url = "jdbc:mysql://localhost:3306/Discografica_musica";
    private static final String user = "root";
    private static final String password = "1234";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
