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
     * Importa los datos del banda a MySQL
     * Inserta registros en la tabla Banda
     */
    public static void importarBandaDesdeArchivo(File archivo, String formato)
            throws Excepciones {

        String[][] datos;

        // Selección del tipo de archivo
        switch (formato) {

            // Archivos de texto o CSV
            case "TXT", "CSV" -> {
                datos = importarTexto(archivo);
            }

            // Archivo JSON
            case "JSON" -> {
                datos = importarJSON(archivo);
            }

            // Archivo binario serializado
            case "Binario" -> {
                datos = importarBinario(archivo);
            }

            // Formato no soportado
            default ->
                throw new Excepciones("Formato no válido");
        }

        // Una vez convertidos los datos, se importan a MySQL
        importarBanda_MySQL(datos);
    }

    // Inserta los datos de Banda en la base de datos MySQL usando batch insert para mayor eficiencia. 
    public static void importarBanda_MySQL(String[][] datos)
            throws Excepciones {

        // Validación de datos
        if (datos == null || datos.length == 0) {
            throw new Excepciones("No hay datos para importar en Banda");
        }

        // SQL fijo para la tabla Banda
        String sql = "INSERT INTO Banda (codigo, nombre, anios_actuacion, lugar_origen, genero) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                // Conexión a la base de datos
                Connection con = configuracion.constant.conectar(); // PreparedStatement para batch insert
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Control manual de transacciones
            con.setAutoCommit(false);

            // Recorre cada fila del array de datos
            for (String[] fila : datos) {

                // Si la fila es inválida, se detiene el proceso
                if (fila == null || fila[0] == null) {
                    break;
                }

                // Asignación de valores a la consulta SQL
                ps.setInt(1, Integer.parseInt(fila[0])); // codigo
                ps.setString(2, fila[1]); // nombre
                ps.setString(3, fila[2]); // anios_actuacion
                ps.setString(4, fila[3]); // lugar_origen
                ps.setString(5, fila[4]); // genero

                // Añade la fila al batch
                ps.addBatch();
            }

            // Ejecuta todas las inserciones
            ps.executeBatch();

            // Confirma la transacción
            con.commit();

            System.out.println("Banda importada correctamente");

        } catch (Exception e) {
            // Manejo de errores
            throw new Excepciones("Error MySQL Banda: " + e.getMessage());
        }
    }

    // Lee un archivo TXT o CSV y lo convierte en una matriz String[][] separando por ";" 
    public static String[][] importarTexto(File archivo) throws Excepciones {

        // Estructura fija (100 filas x 20 columnas)
        String[][] datos = new String[100][20];

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;
            int i = 0;

            // Lee el archivo línea por línea
            while ((linea = br.readLine()) != null) {
                datos[i++] = linea.split(";");
            }

            return datos;

        } catch (Exception e) {
            throw new Excepciones("Error TXT/CSV: " + e.getMessage());
        }
    }

    // Lee un archivo JSON y lo convierte a String[][] usando Gson. 
    public static String[][] importarJSON(File archivo) throws Excepciones {

        try (FileReader reader = new FileReader(archivo)) {

            Gson gson = new Gson();

            // Convierte JSON a lista de mapas clave-valor
            List<java.util.Map<String, String>> lista
                    = gson.fromJson(reader,
                            new com.google.gson.reflect.TypeToken<
                                    List<java.util.Map<String, String>>>() {
                            }.getType());

            // Validación
            if (lista == null || lista.isEmpty()) {
                throw new Excepciones("JSON vacío");
            }

            String[][] datos = new String[lista.size()][5];
            int j = 0;

            // Conversión de cada objeto JSON a fila
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

            // Ajusta tamaño real del array
            return java.util.Arrays.copyOf(datos, j);

        } catch (Exception e) {
            throw new Excepciones("Error JSON: " + e.getMessage());
        }
    }

    // Lee un archivo binario (.bin) y lo convierte en String[][] usando ObjectInputStream. 
    public static String[][] importarBinario(File archivo)
            throws Excepciones {

        try (ObjectInputStream ois
                = new ObjectInputStream(new FileInputStream(archivo))) {

            return (String[][]) ois.readObject();

        } catch (Exception e) {
            throw new Excepciones("Error BINARIO: " + e.getMessage());
        }
    }
}
