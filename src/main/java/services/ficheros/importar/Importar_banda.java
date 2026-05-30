/*
 * Clase encargada de importar datos de la entidad Banda
 * desde diferentes formatos (TXT/CSV, JSON y binario)
 * e insertarlos en MySQL.
 */
package services.ficheros.importar;

import com.google.gson.Gson;
import excepciones.Excepciones;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class Importar_banda {

    /*
     * Método principal:
     * Importa datos de un archivo según su formato y los envía a MySQL.
     */
    public static void importarBandaDesdeArchivo(File archivo, String formato)
            throws Excepciones {

        String[][] datos;

        // Selecciona el tipo de archivo a leer
        switch (formato) {

            // Archivos TXT o CSV (texto separado por ;)
            case "TXT", "CSV" -> {
                datos = importarTexto(archivo);
            }

            // Archivo JSON
            case "JSON" -> {
                datos = importarJSON(archivo);
            }

            // Archivo binario serializado (.bin)
            case "Binario" -> {
                datos = importarBinario(archivo);
            }

            // Si el formato no es válido, lanza error
            default ->
                throw new Excepciones("Formato no válido");
        }

        // VALIDACIÓN FINAL
        if (datos == null || datos.length == 0) {
            throw new Excepciones("No hay datos para importar");
        }

        // Inserta los datos en MySQL
        importarBanda_MySQL(datos);
    }

    /*
     * Inserta los datos en la tabla Banda de MySQL
     * usando batch insert (más eficiente).
     */
    public static void importarBanda_MySQL(String[][] datos)
            throws Excepciones {

        // Validación: si no hay datos, no se puede importar
        if (datos == null || datos.length == 0) {
            throw new Excepciones("No hay datos para importar en Banda");
        }

        // SQL de inserción
        String sql = "INSERT INTO Banda (codigo, nombre, anios_actuacion, lugar_origen, genero) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Control manual de transacción
            con.setAutoCommit(false);

            for (String[] fila : datos) {

                // Ignora filas inválidas
                if (fila == null || fila[0] == null) {
                    break;
                }

                ps.setInt(1, Integer.parseInt(fila[0])); // codigo
                ps.setString(2, fila[1]); // nombre
                ps.setString(3, fila[2]); // anios_actuacion
                ps.setString(4, fila[3]); // lugar_origen
                ps.setString(5, fila[4]); // genero

                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

            System.out.println("Banda importada correctamente");

        } catch (Exception e) {
            throw new Excepciones("Error MySQL Banda: " + e.getMessage());
        }
    }

    /*
     * Lee un archivo TXT o CSV
     * y lo convierte en String[][]
     */
    public static String[][] importarTexto(File archivo) throws Excepciones {

        List<String[]> lista = new java.util.ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                linea = linea.trim();

                // Ignorar líneas vacías
                if (linea.isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(";");

                // Ignorar filas sin código
                if (partes.length == 0 || partes[0].trim().isEmpty()) {
                    continue;
                }

                lista.add(partes);
            }

            

            return lista.toArray(new String[0][]);

        } catch (Exception e) {
            throw new Excepciones("Error TXT/CSV: " + e.getMessage());
        }
    }

    /*
     * Lee un archivo JSON
     * y lo convierte a String[][]
     */
    public static String[][] importarJSON(File archivo) throws Excepciones {

        try (FileReader reader = new FileReader(archivo)) {

            Gson gson = new Gson();

            List<java.util.Map<String, String>> lista
                    = gson.fromJson(reader,
                            new com.google.gson.reflect.TypeToken<
                                    List<java.util.Map<String, String>>>() {
                            }.getType());

            // JSON vacío
            if (lista == null || lista.isEmpty()) {
                throw new Excepciones("JSON vacío");
            }

            String[][] datos = new String[lista.size()][5];
            int j = 0;

            for (java.util.Map<String, String> obj : lista) {

                if (obj == null) {
                    continue;
                }

                datos[j++] = new String[]{
                    obj.get("codigo"),
                    obj.get("nombre"),
                    obj.get("anios_actuacion"),
                    obj.get("lugar_origen"),
                    obj.get("genero")
                };
            }

            return java.util.Arrays.copyOf(datos, j);

        } catch (Exception e) {
            throw new Excepciones("Error JSON: " + e.getMessage());
        }
    }

    /*
     * Lee un archivo binario (.bin)
     * y lo convierte en String[][]
     */
    public static String[][] importarBinario(File archivo)
            throws Excepciones {

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
}
