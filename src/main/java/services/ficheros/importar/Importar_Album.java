/*
 * Clase encargada de importar datos de álbumes
 * desde distintos formatos de archivo (TXT, CSV, JSON, BINARIO)
 * hacia la base de datos MySQL.
 * También resuelve relaciones con Banda y Músico.
 */
package services.ficheros.importar;

import com.google.gson.Gson;
import excepciones.Excepciones;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

public class Importar_Album {

    /*
     * Importa los datos del álbum a MySQL
     * Inserta registros en la tabla Album
     */
    public static void importarAlbum_MySQL(String[][] datos) throws Excepciones {

        if (datos == null || datos.length == 0) {
            throw new Excepciones("No hay datos para importar en Album");
        }

        String sql = """
        INSERT INTO Album(codigo, autor, codigo_banda, codigo_musico,
        titulo, anio_publicacion, tipo, duracion)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            for (String[] f : datos) {

                if (f == null) {
                    continue;
                }

                Integer codigo = parseNullableInt(f[0]);
                String tipoAutor = f[1];
                Integer idAutor = parseNullableInt(f[2]);

                Integer banda = null;
                Integer musico = null;
                String autor = null;

                // Comprobar si es Banda o Musico
                if ("Banda".equalsIgnoreCase(tipoAutor)) {
                    banda = idAutor;
                    autor = (banda != null) ? getNombreBanda(banda) : null;

                } else if ("Musico".equalsIgnoreCase(tipoAutor)) {
                    musico = idAutor;
                    autor = (musico != null) ? getNombreMusico(musico) : null;

                } else {
                    throw new Excepciones("Tipo inválido: " + tipoAutor);
                }

                ps.setInt(1, codigo);
                ps.setString(2, autor);
                ps.setObject(3, banda);
                ps.setObject(4, musico);
                ps.setString(5, f[4]);
                ps.setString(6, f[5]);
                ps.setString(7, f[6]);
                ps.setString(8, f[7]);

                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

            System.out.println("Album importado correctamente");

        } catch (Exception e) {
            throw new Excepciones("Error MySQL Album: " + e.getMessage());
        }
    }

    // Obtener nombre de Banda por código
    public static String getNombreBanda(int codigo) {
        return consultarNombre("SELECT nombre FROM Banda WHERE codigo = ?", codigo);
    }

    /*
     * Obtener nombre de Musico por código
     */
    public static String getNombreMusico(int codigo) {
        return consultarNombre("SELECT nombre FROM Musico WHERE codigo = ?", codigo);
    }

    // Consulta genérica para obtener nombre
    public static String consultarNombre(String sql, int codigo) {

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error consultando nombre: " + e.getMessage());
        }

        return null;
    }

    // Importar datos desde TXT o CSV
    public static String[][] importarTexto(File archivo) throws Excepciones {

        String[][] datos = new String[100][20];

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int i = 0;

            while ((linea = br.readLine()) != null) {
                datos[i++] = linea.split(";");
            }

            return datos;

        } catch (Exception e) {
            throw new Excepciones("Error TXT/CSV: " + e.getMessage());
        }
    }

    // Importar datos desde JSON
    public static String[][] importarJSON(File archivo) throws Excepciones {

        try (FileReader reader = new FileReader(archivo)) {

            Gson gson = new Gson();

            List<java.util.Map<String, String>> lista
                    = gson.fromJson(reader,
                            new com.google.gson.reflect.TypeToken<
                                    List<java.util.Map<String, String>>>() {
                            }.getType());

            if (lista == null || lista.isEmpty()) {
                throw new Excepciones("JSON vacío");
            }

            String[][] datos = new String[lista.size()][8];
            int j = 0;

            for (var obj : lista) {

                if (obj == null) {
                    continue;
                }

                datos[j++] = new String[]{
                    obj.get("codigo"),
                    obj.get("autor"),
                    clean(obj.get("codigo_banda")),
                    clean(obj.get("codigo_musico")),
                    obj.get("titulo"),
                    obj.get("anio_publicacion"),
                    obj.get("tipo"),
                    obj.get("duracion")
                };
            }

            return java.util.Arrays.copyOf(datos, j);

        } catch (Exception e) {
            throw new Excepciones("Error JSON: " + e.getMessage());
        }
    }

    // Importar datos desde BINARIO
    public static String[][] importarBinario(File archivo) throws Excepciones {

        if (archivo.length() == 0) {
            throw new Excepciones("Archivo BINARIO vacío");
        }

        try (ObjectInputStream ois
                = new ObjectInputStream(new FileInputStream(archivo))) {

            Object obj = ois.readObject();

            if (obj == null) {
                throw new Excepciones("Archivo binario vacío");
            }

            if (!(obj instanceof String[][])) {
                throw new Excepciones("Formato binario incorrecto");
            }

            return (String[][]) obj;

        } catch (Exception e) {
            throw new Excepciones("Error BINARIO: " + e.toString());
        }
    }

    // Convertir String a Integer (permitiendo null)
    private static Integer parseNullableInt(String v) {
        if (v == null || v.equals("null") || v.isEmpty()) {
            return null;
        }
        return Integer.parseInt(v);
    }

    // Limpiar valores null en String
    private static String clean(String v) {
        if (v == null || v.equals("null")) {
            return null;
        }
        return v;
    }
}
