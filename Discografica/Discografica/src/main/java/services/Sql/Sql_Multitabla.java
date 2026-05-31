// Clase encargada de realizar consultas SQL complejas (multitabla) para distintos ejercicios de análisis sobre la base de datos musical.
package services.Sql;

import excepciones.Excepciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Consultas avanzadas (JOIN, GROUP BY, HAVING, agregaciones).
public class Sql_Multitabla {

    /*
     * EJERCICIO 1: Consultar el nombre y el lugar de origen de todas las
     * bandas, indicando para cada banda, los títulos de sus álbumes y el número
     * de canciones de estos álbumes. Este informe deberá estar ordenado por el
     * lugar de origen de la banda de forma ascendente
     */
    public static String[][] ejercicio1() throws Excepciones {

        String sql
                = "select b.nombre,b.lugar_origen, a.titulo as titulos_albumes,"
                + " count(ca.codigo_album) as numero_canciones from banda b join album "
                + "a on b.codigo = a.codigo_banda left join cancion ca on ca.codigo_album = a.codigo "
                + "group by b.nombre, b.lugar_origen,titulos_albumes order by b.lugar_origen asc;";

        int contador = 0;
        String[][] consulta = new String[10][4];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                consulta[contador][0] = rs.getString("nombre");
                consulta[contador][1] = rs.getString("lugar_origen");
                consulta[contador][2] = rs.getString("titulos_albumes");
                consulta[contador][3] = rs.getString("numero_canciones");

                contador++;
            }

        } catch (Exception e) {
            throw new Excepciones("Error ejercicio1: " + e.getMessage());
        }

        return consulta;
    }

    /*
     * EJERCICIO 2: Consultar el nombre y el instrumento de todos los músicos,
     * indicando para cada músico, los títulos de sus álbumes en solitario y el
     * número de canciones de estos álbumes. Este informe deberá estar ordenado
     * por el instrumento del músico de forma ascendente.
     */
    public static String[][] ejercicio2() throws Excepciones {

        String sql
                = "select mu.nombre,mu.instrumento,a.titulo as titulo_album, \n"
                + "count(ca.codigo_album) as canciones_en_album \n"
                + "from musico mu join album a on mu.codigo = a.codigo_musico \n"
                + "join cancion ca on ca.codigo_album = a.codigo\n"
                + "group by mu.nombre,mu.instrumento,a.titulo\n"
                + "order by instrumento asc;";

        int contador = 0;
        String[][] consulta = new String[10][4];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                consulta[contador][0] = rs.getString("nombre");
                consulta[contador][1] = rs.getString("instrumento");
                consulta[contador][2] = rs.getString("titulo_album");
                consulta[contador][3] = rs.getString("canciones_en_album");

                contador++;
            }

        } catch (Exception e) {
            throw new Excepciones("Error ejercicio2: " + e.getMessage());
        }

        return consulta;
    }

    /*
     * EJERCICIO 3: Consultar el año de publicación y el título de todos los
     * álbumes, indicando para cada álbum, los títulos de sus canciones y la
     * duración de estas canciones. Este informe deberá estar ordenado por el
     * año de publicación del álbum de formas ascendente.
     */
    public static String[][] ejercicio3() throws Excepciones {

        String sql
                = "select a.anio_publicacion, "
                + "a.titulo as titulo_album, "
                + "ca.titulo as titulo_cancion "
                + "from album a "
                + "join cancion ca on ca.codigo_album = a.codigo "
                + "order by a.anio_publicacion asc";

        int contador = 0;

        String[][] consulta = new String[100][3];

        try (
                Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                consulta[contador][0]
                        = rs.getString("anio_publicacion");

                consulta[contador][1]
                        = rs.getString("titulo_album");

                consulta[contador][2]
                        = rs.getString("titulo_cancion");

                contador++;
            }

        } catch (Exception e) {

            throw new Excepciones(
                    "Error ejercicio3: " + e.getMessage()
            );
        }

        return consulta;
    }

    /*
     * EJERCICIO 4: 
     * Consultar todos los compositores diferentes de canciones,
     * indicando para cada compositor, los títulos de las canciones que han
     * compuesto y los títulos de los álbumes a los que pertenecen. Este informe
     * deberá estar ordenado por el compositor de la canción de forma
     * ascendente.
     */
    public static String[][] ejercicio4() throws Excepciones {

        String sql
                = "select ca.compositor,ca.titulo as titulo_cancion,a.titulo "
                + "as titulo_album from cancion ca join album a on ca.codigo_album ="
                + " a.codigo group by ca.compositor,titulo_cancion,titulo_album "
                + "order by ca.compositor asc";

        int contador = 0;
        String[][] consulta = new String[10][3];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                consulta[contador][0] = rs.getString("compositor");
                consulta[contador][1] = rs.getString("titulo_cancion");
                consulta[contador][2] = rs.getString("titulo_album");

                contador++;
            }

        } catch (Exception e) {
            throw new Excepciones("Error ejercicio4: " + e.getMessage());
        }

        return consulta;
    }

    /*
     * EJERCICIO 5:
      Consultar el nombre de cada banda junto con la cantidad total de álbumes que ha
      publicado. Este informe deberá estar ordenado por el número de álbumes de forma
      descendente.
     */
    public static String[][] ejercicio5() throws Excepciones {

        String sql
                = "select b.nombre, count(a.codigo) as albumes_publicados "
                + "from banda b join album a on b.codigo = a.codigo_banda "
                + "group by b.nombre order by albumes_publicados desc;";

        int contador = 0;
        String[][] consulta = new String[10][2];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                consulta[contador][0] = rs.getString("nombre");
                consulta[contador][1] = rs.getString("albumes_publicados");

                contador++;
            }

        } catch (Exception e) {
            throw new Excepciones("Error ejercicio5: " + e.getMessage());
        }

        return consulta;
    }

    /**
     * EJERCICIO 6: Consultar el título de los álbumes que contienen más de 12
     * canciones, indicando el nombre de su autor (banda o músico) y el número
     * total de canciones.
     */
    public static String[][] ejercicio6() throws Excepciones {

        String sql
                = "select a.titulo as titulo_album,"
                + "count(ca.codigo) as canciones_en_album,"
                + "coalesce(mu.nombre, b.nombre) as autor,"
                + "case when mu.codigo is not null then 'Musico' "
                + "when b.codigo is not null then 'Banda' end as banda_o_musico "
                + "from album a left join banda b on b.codigo = a.codigo_banda "
                + "left join musico mu on a.codigo_musico = mu.codigo "
                + "join cancion ca on ca.codigo_album = a.codigo "
                + "group by a.codigo,a.titulo,mu.nombre,b.nombre,mu.codigo,b.codigo "
                + "having count(ca.codigo_album) > 12;";

        int contador = 0;
        String[][] consulta = new String[10][4];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                consulta[contador][0] = rs.getString("titulo_album");
                consulta[contador][1] = rs.getString("canciones_en_album");
                consulta[contador][2] = rs.getString("autor");
                consulta[contador][3] = rs.getString("banda_o_musico");

                contador++;
            }

        } catch (Exception e) {
            throw new Excepciones("Error ejercicio6: " + e.getMessage());
        }

        return consulta;
    }

    /**
     * EJERCICIO 7: Consultar el nombre de las bandas que actualmente no tienen
     * ningún músico registrado en la base de datos.
     */
    public static String[][] ejercicio7() throws Excepciones {

        String sql
                = "select b.nombre as nombre_banda "
                + "from banda b left join musico mu "
                + "on b.codigo = mu.codigo_banda "
                + "where mu.codigo is null;";

        int contador = 0;
        String[][] consulta = new String[10][1];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                consulta[contador][0] = rs.getString("nombre_banda");
                contador++;
            }

        } catch (Exception e) {
            throw new Excepciones("Error ejercicio7: " + e.getMessage());
        }

        return consulta;
    }

    /**
     * EJERCICIO 8: Consultar el título de cada álbum y la duración media de sus
     * canciones (en segundos o minutos si el tipo de dato lo permitiera).
     */
    public static String[][] ejercicio8() throws Excepciones {

        String sql
                = "select a.titulo AS titulo_album, round(AVG(CAST(ca.duracion AS UNSIGNED)),2) \n"
                + "as duracion_media from album a join cancion ca on\n"
                + " ca.codigo_album = a.codigo group by a.titulo; ";

        int contador = 0;
        String[][] consulta = new String[10][2];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                consulta[contador][0] = rs.getString("titulo_album");
                consulta[contador][1]
                        = rs.getString("duracion_media");

                contador++;
            }

        } catch (Exception e) {
            throw new Excepciones("Error ejercicio8: " + e.getMessage());
        }

        return consulta;
    }

    /**
     * EJERCICIO 9: Consultar a todos los músicos cuya nacionalidad sea española
     * y que toquen un instrumento que contenga la palabra “Guitarra” (ej:
     * Guitarra eléctrica, Guitarra clásica).
     */
    public static String[][] ejercicio9() throws Excepciones {

        String sql
                = "select nombre, nacionalidad, instrumento "
                + "from musico "
                + "where nacionalidad = 'Española' "
                + "and instrumento like '%Guitarra%';";

        int contador = 0;
        String[][] consulta = new String[10][3];

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                consulta[contador][0] = rs.getString("nombre");
                consulta[contador][1] = rs.getString("nacionalidad");
                consulta[contador][2] = rs.getString("instrumento");

                contador++;
            }

        } catch (Exception e) {
            throw new Excepciones("Error ejercicio9: " + e.getMessage());
        }

        return consulta;
    }

}
