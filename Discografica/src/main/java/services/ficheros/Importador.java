package services.ficheros;

import excepciones.Excepciones;
import excepciones.YaImportado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import java.util.Map;

public class Importador {

    // Mapa que define las columnas de cada tabla de la base de datos
    private static final Map<String, String[]> COLUMNAS = Map.of(
            "Banda", new String[]{
                "codigo", "nombre", "anios_actuacion", "lugar_origen", "genero"
            },
            "Musico", new String[]{
                "codigo", "codigo_banda", "nombre", "fecha_nacimiento",
                "lugar_residencia", "nacionalidad", "instrumento"
            },
            "Album", new String[]{
                "codigo", "autor", "codigo_banda", "codigo_musico",
                "titulo", "anio_publicacion", "tipo", "duracion"
            },
            "Cancion", new String[]{
                "codigo", "codigo_album", "posicion",
                "titulo", "compositor", "duracion"
            }
    );

    /**
     * Importa datos a una tabla de MySQL usando batch insert. Realiza
     * validaciones, limpieza de datos y control de duplicados.
     */
    public static void importar_MySQL(String tabla, String[][] datos)
            throws Excepciones {

        // Verifica que la tabla esté definida en el mapa COLUMNAS
        if (!COLUMNAS.containsKey(tabla)) {
            throw new Excepciones("Tabla no soportada: " + tabla);
        }

        // Verifica que existan datos para importar
        if (datos == null || datos.length == 0) {
            throw new Excepciones("No hay datos para importar");
        }

        // Obtiene las columnas asociadas a la tabla
        String[] cols = COLUMNAS.get(tabla);

        // Construye la parte SQL de columnas: "col1,col2,col3..."
        String columnasSQL = String.join(",", cols);

        // Genera placeholders "?,?,?,?" según número de columnas
        String placeholders = String.join(",",
                java.util.Collections.nCopies(cols.length, "?"));

        // Construye el SQL final de inserción
        String sql = "INSERT INTO " + tabla
                + " (" + columnasSQL + ") VALUES (" + placeholders + ")";

        try (
                // Conexión a la base de datos
                Connection con = configuracion.constant.conectar(); // PreparedStatement para batch insert
                 PreparedStatement ps = con.prepareStatement(sql)) {

            // Se gestiona la transacción manualmente para mejorar rendimiento
            con.setAutoCommit(false);

            // Lista para almacenar registros duplicados detectados
            List<String> duplicados = new java.util.ArrayList<>();

            int filaNum = 0;

            // Recorre cada fila de datos a importar
            for (String[] fila : datos) {

                filaNum++;

                // Ignora filas nulas o vacías
                if (fila == null || fila.length == 0 || fila[0] == null) {
                    continue;
                }

                // Limpia el código principal (PK)
                String codigoStr = fila[0].trim();

                if (codigoStr.isEmpty()) {
                    continue;
                }

                int codigo;

                // Convierte el código a entero
                try {
                    codigo = Integer.parseInt(codigoStr);
                } catch (NumberFormatException e) {
                    System.out.println("Código inválido en fila " + filaNum);
                    continue;
                }

               
                // VERIFICACIÓN DE DUPLICADOS
               
                try (PreparedStatement psCheck = con.prepareStatement(
                        "SELECT 1 FROM " + tabla + " WHERE codigo = ?")) {

                    psCheck.setInt(1, codigo);

                    try (ResultSet rs = psCheck.executeQuery()) {

                        // Si existe el registro, se marca como duplicado
                        if (rs.next()) {
                            duplicados.add(
                                    "Fila " + filaNum
                                    + " | código " + codigo
                                    + " | tabla " + tabla
                            );
                            continue;
                        }
                    }
                }

      
                //  INSERT
           
                for (int i = 0; i < cols.length; i++) {

                    if (i < fila.length) {

                        // Si el valor es null  se inserta NULL en BD
                        if (fila[i] == null || "null".equalsIgnoreCase(fila[i])) {
                            ps.setNull(i + 1, Types.VARCHAR);
                        } else {
                            // Limpieza de espacios
                            ps.setObject(i + 1, fila[i].trim());
                        }

                    } else {
                        // Si faltan columnas en la fila, se completa con NULL
                        ps.setNull(i + 1, Types.VARCHAR);
                    }
                }

                // Añade la fila al batch
                ps.addBatch();
            }

            // Ejecuta todas las inserciones en lote
            ps.executeBatch();

            // Confirma la transacción
            con.commit();

            System.out.println("Importado a MySQL: " + tabla);

            // INFORME DE DUPLICADOS
            if (!duplicados.isEmpty()) {

                StringBuilder msg = new StringBuilder("YaImportado:\n\n");

                for (String d : duplicados) {
                    msg.append(d).append("\n");
                }

                // Lanza excepción con el detalle de duplicados
                throw new YaImportado(msg.toString());
            }

        } catch (Exception e) {
           
            throw new Excepciones("Error MySQL: " + e.getMessage());
        }
    }
}
