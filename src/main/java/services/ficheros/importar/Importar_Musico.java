/*
 * Clase encargada de importar datos de álbumes
 * desde distintos formatos de archivo (TXT, CSV, JSON, BINARIO)
 * hacia la base de datos MySQL.
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
import java.util.List;

public class Importar_Musico {

    /*
     * Importa los datos del musico a MySQL
     * Inserta registros en la tabla Musico
     */
    public static void importarMusicoDesdeArchivo(File archivo, String formato) throws Excepciones {

        String[][] datos;

        switch (formato) {

            case "TXT", "CSV" ->
                datos = importarTexto(archivo);

            case "JSON" ->
                datos = importarJSON(archivo);

            case "Binario" ->
                datos = importarBinario(archivo);

            default ->
                throw new Excepciones("Formato no válido");
        }

        importarMusico_MySQL(datos);
    }

    /*
     * Inserta los músicos en la base de datos MySQL
     */
    public static void importarMusico_MySQL(String[][] datos) throws Excepciones {

        if (datos == null || datos.length == 0) {
            throw new Excepciones("No hay datos para importar en Musico");
        }

        String sql = "INSERT INTO Musico(codigo, codigo_banda, nombre, fecha_nacimiento, lugar_residencia, nacionalidad, instrumento) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            for (String[] fila : datos) {

                if (fila == null || fila.length < 7) {
                    continue;
                }

                // Código del músico
                ps.setInt(1, parseIntSafe(fila[0]));

                // Código de banda (puede ser null)
                if (fila[1] == null || fila[1].equals("null") || fila[1].isEmpty()) {
                    ps.setNull(2, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(2, parseIntSafe(fila[1]));
                }

                ps.setString(3, fila[2]);
                ps.setString(4, fila[3]);
                ps.setString(5, fila[4]);
                ps.setString(6, fila[5]);
                ps.setString(7, fila[6]);

                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

            System.out.println("Musico importada correctamente");

        } catch (Exception e) {
            throw new Excepciones("Error MySQL Musico: " + e.getMessage());
        }
    }

    /*
     * Importa datos desde TXT o CSV
     */
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

    /*
     * Importa datos desde JSON
     */
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

            String[][] datos = new String[lista.size()][7];
            int j = 0;

            for (java.util.Map<String, String> obj : lista) {

                if (obj == null) {
                    continue;
                }

                datos[j++] = new String[]{
                    obj.get("codigo"),
                    obj.get("codigo_banda"),
                    obj.get("nombre"),
                    obj.get("fecha_nacimiento"),
                    obj.get("lugar_residencia"),
                    obj.get("nacionalidad"),
                    obj.get("instrumento")
                };
            }

            return java.util.Arrays.copyOf(datos, j);

        } catch (Exception e) {
            throw new Excepciones("Error JSON: " + e.getMessage());
        }
    }

    /*
     * Importa datos desde BINARIO
     */
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
            throw new Excepciones("Error BINARIO: " + e.getMessage());
        }
    }

    /*
     * Convierte String a int seguro
     */
    private static int parseIntSafe(String value) {
        if (value == null || value.equals("null") || value.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(value);
    }
}
