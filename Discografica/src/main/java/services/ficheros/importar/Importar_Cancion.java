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

public class Importar_Cancion {

    /*
     * Importa los datos del cancion a MySQL
     * Inserta registros en la tabla Cancion
     */
    public static void importarCancionDesdeArchivo(File archivo, String formato) throws Excepciones {

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

        importarCancion_MySQL(datos);
    }

    // Inserta las canciones en la base de datos MySQL
    public static void importarCancion_MySQL(String[][] datos) throws Excepciones {

        if (datos == null || datos.length == 0) {
            throw new Excepciones("No hay datos para importar en Cancion");
        }

        String sql = """
        INSERT INTO Cancion(codigo, codigo_album, posicion, titulo, compositor, duracion)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = configuracion.constant.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            for (String[] fila : datos) {

                if (fila == null) {
                    continue;
                }

                int codigo = Integer.parseInt(fila[0]);
                int codigoAlbum = Integer.parseInt(fila[1]);

                ps.setInt(1, codigo);
                ps.setInt(2, codigoAlbum);
                ps.setString(3, fila[2]);
                ps.setString(4, fila[3]);
                ps.setString(5, fila[4]);
                ps.setString(6, fila[5]);

                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

            System.out.println("Cancion importada correctamente");

        } catch (Exception e) {
            throw new Excepciones("Error MySQL Cancion: " + e.getMessage());
        }
    }

    // Importa datos desde TXT o CSV
    public static String[][] importarTexto(File archivo) throws Excepciones {

        //Validación si existe o no
        if (archivo == null || !archivo.exists()) {
            throw new Excepciones("El archivo TXT/CSV no existe");
        }

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

    // Importa datos desde JSON
    public static String[][] importarJSON(File archivo) throws Excepciones {
        
        //Validación: de si existe json
        if (archivo == null || !archivo.exists()) {
            throw new Excepciones("El archivo JSON no existe");
        }

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

            String[][] datos = new String[lista.size()][6];
            int j = 0;

            for (java.util.Map<String, String> obj : lista) {

                if (obj == null) {
                    continue;
                }

                datos[j++] = new String[]{
                    obj.get("codigo"),
                    obj.get("codigo_album"),
                    obj.get("posicion"),
                    obj.get("titulo"),
                    obj.get("compositor"),
                    obj.get("duracion")
                };
            }

            return java.util.Arrays.copyOf(datos, j);

        } catch (Exception e) {
            throw new Excepciones("Error JSON: " + e.getMessage());
        }
    }

    // Importa datos desde BINARIO
    public static String[][] importarBinario(File archivo) throws Excepciones {

        try (ObjectInputStream ois
                = new ObjectInputStream(new FileInputStream(archivo))) {

            return (String[][]) ois.readObject();

        } catch (Exception e) {
            throw new Excepciones("Error BINARIO: " + e.getMessage());
        }
    }
}
